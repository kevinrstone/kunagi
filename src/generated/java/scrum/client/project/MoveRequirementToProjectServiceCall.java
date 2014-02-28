// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.project;

public class MoveRequirementToProjectServiceCall extends scrum.client.core.AServiceCall {

    private String destinationProjectId;

    private String requirementId;

    public  MoveRequirementToProjectServiceCall(String destinationProjectId, String requirementId) {
        this.destinationProjectId = destinationProjectId;
        this.requirementId = requirementId;
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().moveRequirementToProject(serviceCaller.getConversationNumber(), destinationProjectId, requirementId, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "MoveRequirementToProject";
    }

}

