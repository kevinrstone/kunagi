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
package scrum.server.release;

import ilarkesto.base.time.Date;
import ilarkesto.concurrent.TaskManager;
import ilarkesto.core.base.Utl;
import ilarkesto.core.logging.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import scrum.client.common.ReferenceSupport;
import scrum.server.ScrumWebApplication;
import scrum.server.admin.User;
import scrum.server.common.Numbered;
import scrum.server.issues.Issue;
import scrum.server.project.Project;

public class Release extends GRelease implements Numbered, ReferenceSupport {

	private static Log log = Log.get(Release.class);

	private static TaskManager taskManager;

	public static void setTaskManager(TaskManager taskManager) {
		Release.taskManager = taskManager;
	}

	public void release(Project project, User user, ScrumWebApplication webApplication) {
		if (isReleased()) {
			log.warn("Already released:", this);
			return;
		}
		if (isScriptAvailable()) {
			ReleaseTask task = new ReleaseTask(user, this);
			webApplication.autowire(task);
			taskManager.start(task);
		} else {
			markReleased(project, user, webApplication);
		}
	}

	public boolean isScriptAvailable() {
		return getProject().isReleaseScriptPathSet();
	}

	public void markReleased(Project project, User user, ScrumWebApplication webApplication) {
		setReleaseDate(Date.today());
		setReleased(true);
		webApplication.postProjectEvent(project, user.getName() + " released " + getReferenceAndLabel(), this);
		webApplication.sendToConversationsByProject(project, this);
		log.debug("Release published:", this);
	}

	public boolean isMajor() {
		return !isBugfix();
	}

	public boolean isBugfix() {
		return isParentReleaseSet();
	}

	public List<Issue> getIssues() {
		List<Issue> ret = new ArrayList<Issue>();
		ret.addAll(getAffectedIssues());
		ret.addAll(getFixIssues());
		return ret;
	}

	@Override
	public void updateNumber() {
		if (getNumber() == 0) setNumber(getProject().generateReleaseNumber());
	}

	@Override
	public String getReference() {
		return scrum.client.release.Release.REFERENCE_PREFIX + getNumber();
	}

	public String getReferenceAndLabel() {
		return getReference() + " " + getLabel();
	}

	@Override
	public boolean isVisibleFor(User user) {
		return getProject().isVisibleFor(user);
	}

	public boolean isEditableBy(User user) {
		return getProject().isEditableBy(user);
	}

	@Override
	public String toString() {
		return getReferenceAndLabel();
	}

	@Override
	public void ensureIntegrity() {
		super.ensureIntegrity();
		updateNumber();
	}

	public static final Comparator<Release> DATE_COMPARATOR = new Comparator<Release>() {

		@Override
		public int compare(Release ra, Release rb) {
			Date a = ra.getReleaseDate();
			Date b = rb.getReleaseDate();
			if (a == null && b == null) return Utl.compare(ra.getLabel(), rb.getLabel());
			if (a == null) return 1;
			if (b == null) return -1;
			return a.compareTo(b);
		}
	};

	public static final Comparator<Release> DATE_REVERSE_COMPARATOR = new Comparator<Release>() {

		@Override
		public int compare(Release ra, Release rb) {
			return -DATE_COMPARATOR.compare(ra, rb);
		}
	};

}