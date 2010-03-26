package scrum.client.issues;

import ilarkesto.core.scope.Scope;
import scrum.client.workspace.ProjectWorkspaceWidgets;

public class AcceptIssueAsBugAction extends GAcceptIssueAsBugAction {

	public AcceptIssueAsBugAction(scrum.client.issues.Issue issue) {
		super(issue);
	}

	@Override
	public String getLabel() {
		return "Accept as bug";
	}

	@Override
	public String getTooltip() {
		return "Accept this issue as bug in a published release. The team needs to fix this.";
	}

	@Override
	public boolean isPermitted() {
		if (!issue.getProject().isProductOwner(getCurrentUser())) return false;
		return true;
	}

	@Override
	public boolean isExecutable() {
		if (issue.isClosed()) return false;
		if (issue.isIdea()) return false;
		return true;
	}

	@Override
	protected void onExecute() {
		issue.acceptAsUrgent();
		Scope.get().getComponent(ProjectWorkspaceWidgets.class).showIssueList(issue);
		addUndo(new Undo());
	}

	class Undo extends ALocalUndo {

		@Override
		public String getLabel() {
			return "Undo Accept as bug: " + issue.getReference() + " " + issue.getLabel();
		}

		@Override
		protected void onUndo() {
			issue.reopen();
		}

	}

}