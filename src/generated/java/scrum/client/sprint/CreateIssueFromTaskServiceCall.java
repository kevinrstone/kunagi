// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.sprint;

public class CreateIssueFromTaskServiceCall extends scrum.client.core.AServiceCall {

    private String taskId;

    public  CreateIssueFromTaskServiceCall(String taskId) {
        this.taskId = taskId;
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall(this);
        serviceCaller.getService().createIssueFromTask(serviceCaller.getConversationNumber(), taskId, new DefaultCallback(this, returnHandler));
    }

    @Override
    public String toString() {
        return "CreateIssueFromTask";
    }

}

