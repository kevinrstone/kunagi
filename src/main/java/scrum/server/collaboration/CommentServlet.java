/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.server.collaboration;

import ilarkesto.core.base.Utl;
import ilarkesto.core.logging.Log;
import ilarkesto.persistence.AEntity;
import ilarkesto.persistence.DaoService;
import ilarkesto.webapp.RequestWrapper;
import ilarkesto.webapp.Servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;

import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;
import scrum.server.WebSession;
import scrum.server.common.AKunagiServlet;
import scrum.server.common.KunagiUtl;
import scrum.server.common.SpamChecker;
import scrum.server.journal.ProjectEvent;
import scrum.server.journal.ProjectEventDao;
import scrum.server.pr.SubscriptionService;
import scrum.server.project.Project;
import scrum.server.project.ProjectDao;

public class CommentServlet extends AKunagiServlet {

	private static final long serialVersionUID = 1;

	private static Log log = Log.get(CommentServlet.class);

	private transient DaoService daoService;
	private transient CommentDao commentDao;
	private transient ProjectDao projectDao;
	private transient ProjectEventDao projectEventDao;
	private transient SubscriptionService subscriptionService;

	@Override
	protected void onRequest(RequestWrapper<WebSession> req) throws IOException {
		String projectId = req.get("projectId");
		String entityId = req.get("entityId");
		String text = req.get("text");
		String name = ilarkesto.core.base.Str.cutRight(req.get("name"), 33);
		if (ilarkesto.core.base.Str.isBlank(name)) name = null;
		String email = ilarkesto.core.base.Str.cutRight(req.get("email"), 33);
		if (ilarkesto.core.base.Str.isBlank(email)) email = null;

		log.info("Comment from the internets");
		log.info("    projectId: " + projectId);
		log.info("    entityId: " + entityId);
		log.info("    name: " + name);
		log.info("    email: " + email);
		log.info("    text: " + text);
		log.info("  Request-Data:");
		log.info(Servlet.toString(req.getHttpRequest(), "        "));

		String message;
		try {
			SpamChecker.check(text, name, email, req);
			message = postComment(projectId, entityId, text, name, email, req.getRemoteHost());
		} catch (Throwable ex) {
			log.error("Posting comment failed.", "\n" + Servlet.toString(req.getHttpRequest(), "  "), ex);
			message = "<h2>Failure</h2><p>Posting your comment failed: <strong>" + Utl.getRootCauseMessage(ex)
					+ "</strong></p><p>We are sorry, please try again later.</p>";
		}

		String returnUrl = req.get("returnUrl");
		if (returnUrl == null) returnUrl = "http://kunagi.org/message.html?#{message}";
		returnUrl = returnUrl.replace("{message}", ilarkesto.core.base.Str.encodeUrlParameter(message));

		req.sendRedirect(returnUrl);
	}

	private String postComment(String projectId, String entityId, String text, String name, String email,
			String remoteHost) {
		if (projectId == null) throw new RuntimeException("projectId == null");
		if (ilarkesto.core.base.Str.isBlank(text)) throw new RuntimeException("Comment is empty.");
		Project project = projectDao.getById(projectId);
		AEntity entity = daoService.getById(entityId);
		Comment comment = commentDao.postComment(entity, "<nowiki>" + text + "</nowiki>", name, email, true);

		String message = "New comment posted";
		if (!ilarkesto.core.base.Str.isBlank(name)) message += " by " + name;
		subscriptionService.notifySubscribers(entity, message, project, email);

		project.updateHomepage(entity);
		String reference = ((ReferenceSupport) entity).getReference();
		String label = ((LabelSupport) entity).getLabel();
		ProjectEvent event = projectEventDao.postEvent(project, comment.getAuthorName() + " commented on " + reference
			+ " " + label, entity);
		if (ilarkesto.core.base.Str.isEmail(email)) subscriptionService.subscribe(email, entity);

		webApplication.sendToConversationsByProject(project, event);

		return "<h2>Comment posted</h2><p>Thank you for your comment! It will be visible in a few minutes.</p><p>Back to <strong>"
		+ KunagiUtl.createExternalRelativeHtmlAnchor(entity) + "</strong>.</p>";
	}

	@Override
	protected void onInit(ServletConfig config) {
		super.onInit(config);
		daoService = webApplication.getDaoService();
		commentDao = webApplication.getCommentDao();
		projectDao = webApplication.getProjectDao();
		projectEventDao = webApplication.getProjectEventDao();
		subscriptionService = webApplication.getSubscriptionService();
	}

}