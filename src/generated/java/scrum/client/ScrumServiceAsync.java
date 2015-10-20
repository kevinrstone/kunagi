// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtServiceAsyncInterfaceGenerator










package scrum.client;

import java.util.*;

@com.google.gwt.user.client.rpc.RemoteServiceRelativePath("scrum")
public interface ScrumServiceAsync {

    void updateEntities(int conversationNumber, java.util.Collection<java.util.Map<String, String>> modified, java.util.Collection<String> deleted,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void exception(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void changePassword(int conversationNumber, java.lang.String newPassword, java.lang.String oldPassword,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void logout(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void resetPassword(int conversationNumber, java.lang.String userId,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void sendTestEmail(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void testLdap(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void updateSystemMessage(int conversationNumber, scrum.client.admin.SystemMessage systemMessage,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void requestComments(int conversationNumber, java.lang.String parentId,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void requestForum(int conversationNumber, boolean all,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void ping(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void startConversation(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void touchLastActivity(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void requestEntity(int conversationNumber, java.lang.String entityId,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void requestEntityByReference(int conversationNumber, java.lang.String reference,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void sleep(int conversationNumber, long millis,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void activateRequirementEstimationVoting(int conversationNumber, java.lang.String requirementId,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void requestRequirementEstimationVotes(int conversationNumber, java.lang.String requirementId,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void requestImpediments(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void convertIssueToStory(int conversationNumber, java.lang.String issueId,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void requestAcceptedIssues(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void requestClosedIssues(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void requestReleaseIssues(int conversationNumber, java.lang.String releaseId,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void sendIssueReplyEmail(int conversationNumber, java.lang.String issueId, java.lang.String from, java.lang.String to, java.lang.String subject, java.lang.String text,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void requestChanges(int conversationNumber, java.lang.String parentId,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void requestProjectEvents(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void closeProject(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void createExampleProject(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void deleteStory(int conversationNumber, java.lang.String storyId,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void moveRequirementToProject(int conversationNumber, java.lang.String destinationProjectId, java.lang.String requirementId,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void selectProject(int conversationNumber, java.lang.String projectId,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void updateProjectHomepage(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void publishRelease(int conversationNumber, java.lang.String releaseId,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void requestRisks(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void search(int conversationNumber, java.lang.String text,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void createIssueFromTask(int conversationNumber, java.lang.String taskId,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void kickStoryFromSprint(int conversationNumber, java.lang.String storyId,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void pullStoryToSprint(int conversationNumber, java.lang.String storyId,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void requestHistory(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void requestHistorySprint(int conversationNumber, java.lang.String sprintId,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

    void switchToNextSprint(int conversationNumber,  com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback);

}