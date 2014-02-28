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
package scrum.client.project;

import java.util.List;

import scrum.client.common.TooltipBuilder;

public class MoveRequirementToBottomAction extends GMoveRequirementToBottomAction {

	public MoveRequirementToBottomAction(scrum.client.project.Requirement requirement) {
		super(requirement);
	}

	@Override
	public String getLabel() {
		return "Move to bottom";
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Move this story to the bottom of the product backlog.");
		if (!getCurrentProject().isProductOwner(getCurrentUser())) {
			tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
		}
	}

	@Override
	public boolean isPermitted() {
		if (!getCurrentProject().isProductOwner(getCurrentUser())) return false;
		return true;
	}

	@Override
	public boolean isExecutable() {
		if (requirement.isInCurrentSprint()) return false;
		if (requirement.isClosed()) return false;
		List<String> order = requirement.getProject().getRequirementsOrderIds();
		int index = order.indexOf(requirement.getId());
		if (index == order.size() - 1) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		Project project = requirement.getProject();
		List<String> order = project.getRequirementsOrderIds();
		order.remove(requirement.getId());
		order.add(requirement.getId());
		project.setRequirementsOrderIds(order);
	}

}