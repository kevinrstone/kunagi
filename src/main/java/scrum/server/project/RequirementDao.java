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
package scrum.server.project;

import ilarkesto.core.fp.Predicate;

import scrum.server.issues.Issue;

public class RequirementDao extends GRequirementDao {

	@Override
	public Requirement newEntityInstance() {
		Requirement requirement = super.newEntityInstance();
		requirement.setLabel("New Story");
		return requirement;
	}

	public Requirement getRequirementByNumber(final int number, final Project project) {
		return getEntity(new Predicate<Requirement>() {

			@Override
			public boolean test(Requirement r) {
				return r.isNumber(number) && r.isProject(project);
			}
		});
	}

	public Requirement postRequirement(Project destinationProject, Requirement template) {
		Requirement requirement = newEntityInstance();

		requirement.setProject(destinationProject);
		requirement.setLabel(template.getLabel());
		requirement.setDescription(template.getDescription());
		requirement.setTestDescription(template.getTestDescription());
		requirement.setThemes(template.getThemes());
		requirement.setExternalTrackerId(template.getExternalTrackerId());
		for (Quality templateQuality : template.getQualitys()) {
			Quality quality = destinationProject.getQualityByLabel(templateQuality.getLabel());
			if (quality == null) continue;
			requirement.addQuality(quality);
		}

		persist(requirement);
		requirement.updateNumber();
		return requirement;
	}

	public Requirement postRequirement(Issue issue) {
		Requirement requirement = newEntityInstance();

		requirement.setProject(issue.getProject());
		requirement.setLabel(issue.getLabel());
		requirement.setDescription(issue.getDescription());
		requirement.setIssue(issue);
		requirement.setThemes(issue.getThemes());
		requirement.setExternalTrackerId(issue.getExternalTrackerId());
		issue.setStory(requirement);

		for (Quality quality : requirement.getProject().getQualitys()) {
			if (quality.isAutoAdd()) requirement.addQuality(quality);
		}

		persist(requirement);
		requirement.updateNumber();
		return requirement;
	}

	// --- test data ---

	public Requirement postRequirement(Project project, String label, Float estimation) {
		Requirement requirement = newEntityInstance();

		requirement.setProject(project);
		requirement.setLabel(label);
		requirement.setEstimatedWork(estimation);

		persist(requirement);
		requirement.updateNumber();
		return requirement;
	}

}
