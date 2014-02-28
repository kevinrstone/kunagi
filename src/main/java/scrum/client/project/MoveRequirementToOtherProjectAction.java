package scrum.client.project;

import scrum.client.common.TooltipBuilder;
import scrum.client.files.ProjectSelectionWidget;

public class MoveRequirementToOtherProjectAction extends GMoveRequirementToOtherProjectAction {

	public MoveRequirementToOtherProjectAction(Requirement requirement) {
		super(requirement);
	}

	@Override
	public String getLabel() {
		return "Move to other Project";
	}

	@Override
	public void updateTooltip(TooltipBuilder tb) {
		tb.setText("Move Story to other Project");
		if (!requirement.getProject().isProductOwner(getCurrentUser())) tb.addRemark(TooltipBuilder.NOT_PRODUCT_OWNER);
	}

	@Override
	public boolean isPermitted() {
		if (!requirement.getProject().isProductOwner(getCurrentUser())) return false;
		return true;
	}

	@Override
	public boolean isExecutable() {
		if (getDao().getProjects().size() < 2) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		ProjectSelectionWidget widget = new ProjectSelectionWidget(new ProjectSelectionWidget.Callback() {

			@Override
			public void onProjectSelected(Project project) {
				new MoveRequirementToProjectServiceCall(project.getId(), requirement.getId()).execute();
			}
		});
		widget.showDialog();
	}

}