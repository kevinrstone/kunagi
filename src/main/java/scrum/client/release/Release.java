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
package scrum.client.release;

import ilarkesto.core.base.Args;
import ilarkesto.core.base.Utl;
import ilarkesto.core.time.Date;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.core.time.Time;
import ilarkesto.gwt.client.HyperlinkWidget;
import ilarkesto.gwt.client.editor.AFieldModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import scrum.client.ScrumGwt;
import scrum.client.collaboration.ForumSupport;
import scrum.client.common.ReferenceSupport;
import scrum.client.common.ShowEntityAction;
import scrum.client.issues.GIssue;
import scrum.client.issues.Issue;
import scrum.client.project.Project;
import scrum.client.project.Requirement;
import scrum.client.sprint.Sprint;

import com.google.gwt.user.client.ui.Widget;

public class Release extends GRelease implements ReferenceSupport, ForumSupport {

	public static final String REFERENCE_PREFIX = "rel";

	public static Release post(Project project, Date date) {
		Args.assertNotNull(project, "project", date, "date");

		Release release = new Release();
		release.setProject(project);
		release.setReleaseDate(date);

		release.persist();
		return release;
	}

	public DateAndTime getReleaseDateAndTime() {
		Date date = getReleaseDate();
		if (date == null) return null;
		Time time = getReleaseTime();
		if (time == null) time = new Time(12, 0);
		return new DateAndTime(getReleaseDate(), time);
	}

	public boolean isMajor() {
		return !isBugfix();
	}

	public boolean isBugfix() {
		return isParentReleaseSet();
	}

	public Set<Release> getBugfixReleases() {
		return GRelease.listByParentRelease(this);
	}

	public List<Issue> getAffectedByIssues() {
		List<Issue> ret = new ArrayList<Issue>();
		for (Issue issue : GIssue.listAll()) {
			if (issue.getAffectedReleases().contains(this)) ret.add(issue);
		}
		return ret;
	}

	public List<Issue> getFixedIssues() {
		List<Issue> ret = new ArrayList<Issue>();
		for (Issue issue : GIssue.listAll()) {
			if (issue.isClosed() && issue.containsFixRelease(this)) ret.add(issue);
		}
		return ret;
	}

	public List<Issue> getPlannedIssues() {
		List<Issue> ret = new ArrayList<Issue>();
		for (Issue issue : GIssue.listAll()) {
			if (!issue.isClosed() && issue.getFixReleases().contains(this)) ret.add(issue);
		}
		return ret;
	}

	@Override
	public String getReference() {
		return REFERENCE_PREFIX + getNumber();
	}

	@Override
	public Widget createForumItemWidget() {
		return new HyperlinkWidget(new ShowEntityAction(ReleaseManagementWidget.class, this, getLabel()));
	}

	public String createIzemizedReleaseNotes() {
		StringBuilder sb = new StringBuilder();

		String releaseNotes = getReleaseNotes();
		if (releaseNotes != null) sb.append(releaseNotes).append("\n\n");

		// add Stories from all Sprints that are part of this Release
		if (someSprintHasStories()) {
			sb.append("'''New Features'''\n\n");
			for (Sprint sprint : getSprints()) {
				for (Requirement story : sprint.getRequirements()) {
					sb.append("* " + (story.isClosed() ? "" : "(UNFINISHED) ")).append(story.getReferenceAndLabel())
							.append("\n");
				}
			}
			sb.append("\n\n");
		}

		// add Bugs that have been fixed for this Release
		if (!getFixedIssues().isEmpty() || !getPlannedIssues().isEmpty()) {
			sb.append("'''Fixed Bugs'''\n\n");
			for (Issue issue : getFixedIssues()) {
				sb.append("* ").append(issue.getReferenceAndLabel()).append("\n");
			}
			for (Issue issue : getPlannedIssues()) {
				sb.append("* (UNFINISHED) ").append(issue.getReferenceAndLabel()).append("\n");
			}
			sb.append("\n\n");
		}

		// add all Bugs that have not been fixed for this Release
		if (!getAffectedByIssues().isEmpty()) {
			sb.append("'''Known Issues'''\n\n");
			for (Issue issue : getAffectedByIssues()) {
				sb.append("* ").append(issue.getReferenceAndLabel() + "\n");
			}
		}

		return sb.toString();
	}

	private boolean someSprintHasStories() {
		for (Sprint sprint : getSprints()) {
			if (!sprint.getRequirements().isEmpty()) return true;
		}
		return false;
	}

	@Override
	public String toHtml() {
		return ScrumGwt.toHtml(this, getLabel());
	}

	@Override
	public String asString() {
		return getReference() + " " + getLabel() + "-> " + isReleased();
	}

	public static final Comparator<Release> DATE_COMPARATOR = new Comparator<Release>() {

		@Override
		public int compare(Release ra, Release rb) {
			DateAndTime a = ra.getReleaseDateAndTime();
			DateAndTime b = rb.getReleaseDateAndTime();
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

	public List<Requirement> getRequirements() {
		List<Requirement> ret = new ArrayList<Requirement>();
		for (Sprint sprint : getSprints()) {
			ret.addAll(sprint.getRequirements());
		}
		return ret;
	}

	private transient AFieldModel<String> parentReleaseLabelModel;

	public AFieldModel<String> getParentReleaseLabelModel() {
		if (parentReleaseLabelModel == null) parentReleaseLabelModel = new AFieldModel<String>() {

			@Override
			public String getValue() {
				return isBugfix() ? "Bugfix for " + getParentRelease().getLabel() : "";
			};
		};
		return parentReleaseLabelModel;
	}

}
