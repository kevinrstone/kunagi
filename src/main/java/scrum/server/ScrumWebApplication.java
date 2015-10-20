/*
 * Copyright 2008, 2009, 2010 Witoslaw Koczewski, Artjom Kochtchi, Fabian Hager, Kacper Grubalski.
 *
 * This file is part of Kunagi.
 *
 * Kunagi is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Kunagi is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with Foobar. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package scrum.server;

import ilarkesto.auth.Auth;
import ilarkesto.auth.OpenId;
import ilarkesto.base.Sys;
import ilarkesto.base.Tm;
import ilarkesto.base.Utl;
import ilarkesto.concurrent.TaskManager;
import ilarkesto.core.base.Str;
import ilarkesto.core.logging.Log;
import ilarkesto.core.persistance.EntitiesBackend;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.core.time.TimePeriod;
import ilarkesto.di.app.BackupApplicationDataDirTask;
import ilarkesto.di.app.WebApplicationStarter;
import ilarkesto.gwt.server.AGwtConversation;
import ilarkesto.io.IO;
import ilarkesto.persistence.AEntity;
import ilarkesto.webapp.AWebApplication;
import ilarkesto.webapp.AWebSession;
import ilarkesto.webapp.DestroyTimeoutedSessionsTask;
import ilarkesto.webapp.GwtSuperDevMode;

import java.io.File;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

import scrum.client.ApplicationInfo;
import scrum.client.admin.SystemMessage;
import scrum.client.workspace.Navigator;
import scrum.server.admin.DeleteDisabledUsersTask;
import scrum.server.admin.DisableInactiveUsersTask;
import scrum.server.admin.DisableUsersWithUnverifiedEmailsTask;
import scrum.server.admin.KunagiAuthenticationContext;
import scrum.server.admin.ProjectUserConfig;
import scrum.server.admin.SystemConfig;
import scrum.server.admin.User;
import scrum.server.common.BurndownChart;
import scrum.server.journal.ProjectEvent;
import scrum.server.pr.EmailSender;
import scrum.server.pr.SubscriptionService;
import scrum.server.project.DeleteOldProjectsTask;
import scrum.server.project.HomepageUpdaterTask;
import scrum.server.project.Project;

public class ScrumWebApplication extends GScrumWebApplication {

	private static final int DATA_VERSION = 37;

	private static final Log log = Log.get(ScrumWebApplication.class);

	private BurndownChart burndownChart;
	private KunagiRootConfig config;
	private ScrumEntityfilePreparator entityfilePreparator;
	private SystemMessage systemMessage;

	public ScrumWebApplication(KunagiRootConfig config) {
		this.config = config;
	}

	public ScrumWebApplication() {
		this(null);
	}

	@Override
	protected int getDataVersion() {
		return DATA_VERSION;
	}

	@Override
	protected EntitiesBackend createEntitiesBackend() {
		return getEntityStore();
	}

	// --- composites ---

	public BurndownChart getBurndownChart() {
		if (burndownChart == null) {
			burndownChart = new BurndownChart();
			burndownChart.setSprintDao(getSprintDao());
		}
		return burndownChart;
	}

	public SystemConfig getSystemConfig() {
		return getSystemConfigDao().getSystemConfig();
	}

	public KunagiRootConfig getConfig() {
		if (config == null) config = new KunagiRootConfig(getApplicationName());
		return config;
	}

	public ScrumEntityfilePreparator getEntityfilePreparator() {
		if (entityfilePreparator == null) {
			entityfilePreparator = new ScrumEntityfilePreparator();
			entityfilePreparator.setBackupDir(getApplicationDataDir() + "/backup/entities");
		}
		return entityfilePreparator;
	}

	public ApplicationInfo getApplicationInfo() {
		boolean defaultAdminPassword = isAdminPasswordDefault();
		return new ApplicationInfo(getApplicationLabel(), getBuildProperties().getReleaseLabel(), getBuildProperties()
			.getBuild(), defaultAdminPassword, getCurrentRelease(), getApplicationDataDir());
	}

	@Override
	public String getApplicationLabel() {
		return "Kunagi";
	}

	private String currentRelease;

	public String getCurrentRelease() {
		if (!getSystemConfig().isVersionCheckEnabled()) return null;
		if (currentRelease == null) {
			String url = "http://kunagi.org/current-release.properties";
			log.info("Checking current release:", url);
			try {
				Properties currentReleaseProperties = IO.loadPropertiesFromUrl(url, IO.UTF_8);
				currentRelease = currentReleaseProperties.getProperty("currentRelease");
				log.info("   ", currentRelease);
			} catch (Throwable ex) {
				return null;
			}
		}
		return currentRelease;
	}

	// --- ---

	@Override
	protected void onPreStart() {
		super.onPreStart();

		if (getConfig().isStartupDelete()) {
			log.warn("DELETING ALL ENTITIES (set startup.delete=false in config.properties to prevent this behavior)");
			IO.delete(getApplicationDataDir() + "/entities");
		}

		Log.setDebugEnabled(isDevelopmentMode() || getConfig().isLoggingDebug());

		String httpProxy = getConfig().getHttpProxyHost();
		if (!Str.isBlank(httpProxy)) {
			int httpProxyPort = getConfig().getHttpProxyPort();
			log.info("Setting HTTP proxy:", httpProxy + ":" + httpProxyPort);
			Sys.setHttpProxy(httpProxy, httpProxyPort);
			OpenId.setHttpProxy(httpProxy, httpProxyPort);
		}

	}

	@Override
	protected void ensureIntegrity() {
		super.ensureIntegrity();

		String url = getConfig().getUrl();
		if (!Str.isBlank(url)) getSystemConfig().setUrl(url);

		if (getUserDao().getEntities().isEmpty()) {
			log.warn("No users. Creating initial user <admin> with default password <"
					+ getSystemConfig().getDefaultUserPassword() + ">");
			User admin = getUserDao().postUser("admin");
			admin.setAdmin(true);
		}

		getReleaseDao().resetScripts();
		getProjectDao().scanFiles();

		// test data
		if (getConfig().isCreateTestData() && getProjectDao().getEntities().isEmpty() && !isUnitTestMode()) {
			createTestData();
		}

		for (ProjectUserConfig config : getProjectUserConfigDao().getEntities()) {
			config.reset();
		}
	}

	@Override
	protected void onStartWebApplication() {

	}

	@Override
	protected GwtSuperDevMode createGwtSuperDevMode() {
		File nocachefile = new File("src/main/webapp/scrum.ScrumGwtApplication/scrum.ScrumGwtApplication.nocache.js");
		if (!nocachefile.exists()) {
			IO.copyFile(new File("etc/" + nocachefile.getName()), nocachefile);
			throw new IllegalStateException("GWT file " + nocachefile.getPath()
				+ " was missing. It is created now. Just restart your web application server.");
		}

		GwtSuperDevMode sdm = new GwtSuperDevMode();
		sdm.setPrecompile(true);
		sdm.setIncremental(false);
		sdm.addSources("src/main/java", "src/generated/java", "../ilarkesto/src/main/java");
		sdm.addModules("scrum.ScrumGwtApplication");
		sdm.setWorkDir(new File("current").getAbsoluteFile().getParentFile().getParentFile());
		return sdm;
	}

	public String createUrl(String relativePath) {
		if (relativePath == null) relativePath = "";
		String prefix = getBaseUrl();

		// return relative path if base is not defined
		if (Str.isBlank(prefix)) return relativePath;

		// concat base and relative and return
		if (prefix.endsWith("/")) {
			if (relativePath.startsWith("/")) return Str.removeSuffix(prefix, "/") + relativePath;
			return prefix + relativePath;
		}
		if (relativePath.startsWith("/")) return prefix + relativePath;
		return prefix + "/" + relativePath;
	}

	public String createUrl(Project project, AEntity entity) {
		return createUrl("#" + Navigator.createToken(project.getId(), null, entity == null ? null : entity.getId()));
	}

	private void createTestData() {
		log.warn("Creating test data");

		getUserDao().postUser("homer");
		getUserDao().postUser("cartman");
		getUserDao().postUser("duke");
		getUserDao().postUser("spinne");

		getProjectDao().postExampleProject(getUserDao().getUserByName("admin"), getUserDao().getUserByName("cartman"),
			getUserDao().getUserByName("admin"));

	}

	@Override
	protected void scheduleTasks(TaskManager tm) {
		tm.scheduleWithFixedDelay(autowire(new BackupApplicationDataDirTask()), Tm.HOUR * 24 + (Tm.MINUTE));
		tm.scheduleWithFixedDelay(autowire(new DestroyTimeoutedSessionsTask()), Tm.MINUTE);
		tm.scheduleWithFixedDelay(autowire(new HomepageUpdaterTask()), Tm.HOUR);
		tm.scheduleWithFixedDelay(autowire(getSubscriptionService().new Task()), Tm.MINUTE);

		if (getConfig().isDisableUsersWithUnverifiedEmails())
			tm.scheduleWithFixedDelay(autowire(new DisableUsersWithUnverifiedEmailsTask()), Tm.SECOND * 10, Tm.HOUR);
		if (getConfig().isDisableInactiveUsers())
			tm.scheduleWithFixedDelay(autowire(new DisableInactiveUsersTask()), Tm.SECOND * 20, Tm.HOUR);
		if (getConfig().isDeleteOldProjects())
			tm.scheduleWithFixedDelay(autowire(new DeleteOldProjectsTask()), Tm.SECOND * 30, Tm.HOUR * 25);
		if (getConfig().isDeleteDisabledUsers())
			tm.scheduleWithFixedDelay(autowire(new DeleteDisabledUsersTask()), Tm.MINUTE * 3, Tm.HOUR * 26);
	}

	@Override
	public String getApplicationDataDir() {
		return getConfig().getDataPath();
	}

	@Override
	protected void onShutdownWebApplication() {
		getSubscriptionService().flush();
	}

	public void shutdown(final long delayInMillis) {
		updateSystemMessage(new SystemMessage("Service is going down for maintenance.", delayInMillis));
		if (delayInMillis == 0) {
			shutdown();
		} else {
			new Thread() {

				@Override
				public void run() {
					Utl.sleep(delayInMillis);
					shutdown();
				}
			}.start();
		}
		log.info("Shutdown scheduled in", new TimePeriod(delayInMillis), "ms.");
	}

	@Override
	public void backupApplicationDataDir() {
		if (isDevelopmentMode()) return;

		updateSystemMessage(new SystemMessage(
				"Data backup in progress. All your changes are delayed until backup finishes."));
		Utl.sleep(3000);
		try {
			super.backupApplicationDataDir();
		} finally {
			updateSystemMessage(new SystemMessage());
		}
	}

	private String getBaseUrl() {
		String url = getSystemConfig().getUrl();
		return url == null ? "http://localhost:8080/kunagi/" : url;
	}

	public boolean isAdminPasswordDefault() {
		User admin = getUserDao().getUserByName("admin");
		if (admin == null) return false;

		return Auth.isPasswordMatchingDefaultPassword(admin, new KunagiAuthenticationContext());
	}

	public synchronized void postProjectEvent(Project project, String message, AEntity subject) {
		ProjectEvent event = getProjectEventDao().postEvent(project, message, subject);
		sendToConversationsByProject(project, event);
		sendToConversationsByProject(project, event.postChatMessage());
		Utl.sleep(100);
	}

	public void sendToConversationsByProject(GwtConversation conversation, AEntity entity) {
		sendToConversationsByProject(conversation.getProject(), entity);
	}

	public void sendToOtherConversationsByProject(GwtConversation conversation, AEntity entity) {
		for (AGwtConversation c : getConversationsByProject(conversation.getProject(), conversation)) {
			c.sendToClient(entity);
		}
	}

	public void sendToConversationsByProject(Project project, AEntity entity) {
		Set<GwtConversation> conversations;
		if (project == null && entity instanceof User) {
			conversations = new HashSet<GwtConversation>();
			for (Project p : getProjectDao().getEntities()) {
				conversations.addAll(getConversationsByProject(p, null));
			}
		} else {
			conversations = getConversationsByProject(project, null);
		}
		for (AGwtConversation c : conversations) {
			c.sendToClient(entity);
		}
	}

	public void sendToClients(AEntity entity) {
		for (AGwtConversation c : getGwtConversations()) {
			c.sendToClient(entity);
		}
	}

	public Set<GwtConversation> getConversationsByProject(Project project, GwtConversation exception) {
		Set<GwtConversation> ret = new HashSet<GwtConversation>();
		for (Object element : getGwtConversations()) {
			if (element == exception) continue;
			GwtConversation conversation = (GwtConversation) element;
			if (project != null && project.equals(conversation.getProject())) ret.add(conversation);
		}
		return ret;
	}

	public Set<User> getConversationUsersByProject(Project project) {
		Set<User> ret = new HashSet<User>();
		for (GwtConversation conversation : getConversationsByProject(project, null)) {
			User user = conversation.getSession().getUser();
			if (user != null) ret.add(user);
		}
		return ret;
	}

	@Override
	protected AWebSession createWebSession(HttpServletRequest httpRequest) {
		WebSession session = new WebSession(context, httpRequest);
		autowire(session);
		return session;
	}

	public void updateSystemMessage(SystemMessage systemMessage) {
		this.systemMessage = systemMessage;
		for (AGwtConversation conversation : getGwtConversations()) {
			log.debug("Sending SystemMessage to:", conversation);
			((GwtConversation) conversation).getNextData().systemMessage = systemMessage;
		}
	}

	public SystemMessage getSystemMessage() {
		return systemMessage;
	}

	public static ScrumWebApplication get() {
		return (ScrumWebApplication) AWebApplication.get();
	}

	public static synchronized ScrumWebApplication get(ServletConfig servletConfig) {
		if (AWebApplication.isStarted()) return get();
		return (ScrumWebApplication) WebApplicationStarter.startWebApplication(ScrumWebApplication.class.getName(),
			servletConfig);
	}

	public void triggerRegisterNotification(User user, String host) {
		if (getConfig().isAdminNotificationOnUserRegistrationDisabled()) return;
		StringBuilder sb = new StringBuilder();
		sb.append("Kunagi URL: ").append(createUrl(null)).append("\n");
		sb.append("Name: ").append(user.getName()).append("\n");
		sb.append("Email: ").append(user.getEmail()).append("\n");
		sb.append("OpenID: ").append(user.getOpenId()).append("\n");
		sb.append("Date/Time: ").append(DateAndTime.now()).append("\n");
		sb.append("Host: ").append(host).append("\n");
		String subject = user.getLabel() + " registered on " + getBaseUrl();
		try {
			getEmailSender().sendEmail((String) null, null, subject, sb.toString());
		} catch (Throwable ex) {
			log.error("Sending notification email failed:", subject, ex);
		}
	}

	private EmailSender emailSender;

	public EmailSender getEmailSender() {
		if (emailSender == null) emailSender = autowire(new EmailSender());
		return emailSender;
	}

	private SubscriptionService subscriptionService;

	public SubscriptionService getSubscriptionService() {
		if (subscriptionService == null) subscriptionService = autowire(new SubscriptionService());
		return subscriptionService;
	}

}
