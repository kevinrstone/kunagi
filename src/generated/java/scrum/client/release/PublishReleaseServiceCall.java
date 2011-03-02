// // ----------> GENERATED FILE - DON'T TOUCH! <----------

package scrum.client.release;

public class PublishReleaseServiceCall extends scrum.client.core.AServiceCall {

    private String releaseId;

    public  PublishReleaseServiceCall(String releaseId) {
        this.releaseId = releaseId;
    }

    public void execute(Runnable returnHandler) {
        serviceCaller.onServiceCall();
        serviceCaller.getService().publishRelease(serviceCaller.getConversationNumber(), releaseId, new DefaultCallback(returnHandler));
    }

    @Override
    public String toString() {
        return "PublishRelease";
    }

}

