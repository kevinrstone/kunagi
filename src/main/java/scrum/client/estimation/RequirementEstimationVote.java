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
package scrum.client.estimation;

import ilarkesto.core.base.Args;
import ilarkesto.core.persistance.EntityDoesNotExistException;

import scrum.client.admin.User;
import scrum.client.project.Requirement;

public class RequirementEstimationVote extends GRequirementEstimationVote {

	public static RequirementEstimationVote post(Requirement requirement, User user) {
		Args.assertNotNull(requirement, "requirement", user, "user");

		RequirementEstimationVote vote = new RequirementEstimationVote();
		vote.setRequirement(requirement);
		vote.setUser(user);

		vote.persist();
		return vote;
	}

	@Override
	protected void onAfterUpdateLastModified() {
		super.onAfterUpdateLastModified();
		try {
			Requirement requirement = getRequirement();
			if (requirement != null) requirement.updateLastModified();
		} catch (EntityDoesNotExistException ex) {
			return;
		}
	}

}