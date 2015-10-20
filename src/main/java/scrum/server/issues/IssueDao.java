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
package scrum.server.issues;

import ilarkesto.core.fp.Predicate;
import ilarkesto.core.time.DateAndTime;

import java.util.Set;

import scrum.server.project.Project;
import scrum.server.project.Requirement;
import scrum.server.sprint.Task;

public class IssueDao extends GIssueDao {

	public Set<Issue> getAcceptedIssues(final Project project) {
		return getEntities(new Predicate<Issue>() {

			@Override
			public boolean test(Issue issue) {
				if (!issue.isProject(project)) return false;
				if (issue.isClosed()) return false;
				return issue.isAccepted();
			}
		});
	}

	public Set<Issue> getClosedIssues(final Project project) {
		return getEntities(new Predicate<Issue>() {

			@Override
			public boolean test(Issue issue) {
				if (!issue.isProject(project)) return false;
				return issue.isClosed();
			}
		});
	}

	public Set<Issue> getOpenIssues(final Project project) {
		return getEntities(new Predicate<Issue>() {

			@Override
			public boolean test(Issue issue) {
				if (!issue.isProject(project)) return false;
				return issue.isOpen();
			}
		});
	}

	public Set<Issue> getPublishedIssues(final Project project) {
		return getEntities(new Predicate<Issue>() {

			@Override
			public boolean test(Issue issue) {
				if (!issue.isProject(project)) return false;
				return issue.isPublished();
			}
		});
	}

	public Set<Issue> getOpenBugs(final Project project) {
		return getEntities(new Predicate<Issue>() {

			@Override
			public boolean test(Issue issue) {
				if (!issue.isProject(project)) return false;
				if (issue.isClosed()) return false;
				return issue.isBug();
			}
		});
	}

	public Set<Issue> getOpenIdeas(final Project project) {
		return getEntities(new Predicate<Issue>() {

			@Override
			public boolean test(Issue issue) {
				if (!issue.isProject(project)) return false;
				if (issue.isClosed()) return false;
				return issue.isIdea();
			}
		});
	}

	public Issue getIssueByNumber(final int number, final Project project) {
		return getEntity(new Predicate<Issue>() {

			@Override
			public boolean test(Issue t) {
				return t.isNumber(number) && t.isProject(project);
			}
		});
	}

	public Issue postIssue(Project project, String label, String text, String additionalInfo, String externalTrackerId,
			String issuerName, String issuerEmail, boolean publish) {
		Issue issue = newEntityInstance();
		issue.setProject(project);
		issue.setLabel(label);
		issue.setExternalTrackerId(externalTrackerId);
		issue.setDescription(text);
		issue.setAdditionalInfo(additionalInfo);
		issue.setDate(DateAndTime.now());
		issue.setIssuerName(issuerName);
		issue.setIssuerEmail(issuerEmail);
		issue.setPublished(publish);
		issue.updateNumber();
		persist(issue);
		issue.updateNumber();
		return issue;
	}

	public Issue postIssue(Project project, String label) {
		Issue issue = newEntityInstance();
		issue.setProject(project);
		issue.setLabel(label);
		persist(issue);
		issue.updateNumber();
		return issue;
	}

	public Issue postIssue(Project project, Requirement requirement) {
		Issue issue = newEntityInstance();
		issue.setProject(project);
		issue.setLabel(requirement.getLabel());
		StringBuilder sb = new StringBuilder();
		if (requirement.isDescriptionSet()) sb.append(requirement.getDescription());
		if (requirement.isTestDescriptionSet()) sb.append("\n\n").append(requirement.getTestDescription());
		issue.setDescription(sb.toString());
		issue.addThemes(requirement.getThemes());
		issue.setExternalTrackerId(requirement.getExternalTrackerId());
		persist(issue);
		issue.updateNumber();
		return issue;
	}

	public Issue postIssue(Task task) {
		Issue issue = newEntityInstance();
		issue.setProject(task.getProject());
		issue.setLabel(task.getLabel());
		issue.setDescription(task.getDescription());
		issue.setDate(DateAndTime.now());
		persist(issue);
		issue.updateNumber();
		return issue;
	}

}