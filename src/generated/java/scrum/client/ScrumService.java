// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtServiceInterfaceGenerator










package scrum.client;

import java.util.*;

@com.google.gwt.user.client.rpc.RemoteServiceRelativePath("scrum")
public interface ScrumService
            extends com.google.gwt.user.client.rpc.RemoteService {

    scrum.client.DataTransferObject updateEntities(int conversationNumber, java.util.Collection<java.util.Map<String, String>> modified, java.util.Collection<String> deleted);

    scrum.client.DataTransferObject exception(int conversationNumber);

    scrum.client.DataTransferObject changePassword(int conversationNumber, java.lang.String newPassword, java.lang.String oldPassword);

    scrum.client.DataTransferObject logout(int conversationNumber);

    scrum.client.DataTransferObject resetPassword(int conversationNumber, java.lang.String userId);

    scrum.client.DataTransferObject sendTestEmail(int conversationNumber);

    scrum.client.DataTransferObject testLdap(int conversationNumber);

    scrum.client.DataTransferObject updateSystemMessage(int conversationNumber, scrum.client.admin.SystemMessage systemMessage);

    scrum.client.DataTransferObject requestComments(int conversationNumber, java.lang.String parentId);

    scrum.client.DataTransferObject requestForum(int conversationNumber, boolean all);

    scrum.client.DataTransferObject ping(int conversationNumber);

    scrum.client.DataTransferObject startConversation(int conversationNumber);

    scrum.client.DataTransferObject touchLastActivity(int conversationNumber);

    scrum.client.DataTransferObject requestEntity(int conversationNumber, java.lang.String entityId);

    scrum.client.DataTransferObject requestEntityByReference(int conversationNumber, java.lang.String reference);

    scrum.client.DataTransferObject sleep(int conversationNumber, long millis);

    scrum.client.DataTransferObject activateRequirementEstimationVoting(int conversationNumber, java.lang.String requirementId);

    scrum.client.DataTransferObject requestRequirementEstimationVotes(int conversationNumber, java.lang.String requirementId);

    scrum.client.DataTransferObject requestImpediments(int conversationNumber);

    scrum.client.DataTransferObject convertIssueToStory(int conversationNumber, java.lang.String issueId);

    scrum.client.DataTransferObject requestAcceptedIssues(int conversationNumber);

    scrum.client.DataTransferObject requestClosedIssues(int conversationNumber);

    scrum.client.DataTransferObject requestReleaseIssues(int conversationNumber, java.lang.String releaseId);

    scrum.client.DataTransferObject sendIssueReplyEmail(int conversationNumber, java.lang.String issueId, java.lang.String from, java.lang.String to, java.lang.String subject, java.lang.String text);

    scrum.client.DataTransferObject requestChanges(int conversationNumber, java.lang.String parentId);

    scrum.client.DataTransferObject requestProjectEvents(int conversationNumber);

    scrum.client.DataTransferObject closeProject(int conversationNumber);

    scrum.client.DataTransferObject createExampleProject(int conversationNumber);

    scrum.client.DataTransferObject deleteStory(int conversationNumber, java.lang.String storyId);

    scrum.client.DataTransferObject moveRequirementToProject(int conversationNumber, java.lang.String destinationProjectId, java.lang.String requirementId);

    scrum.client.DataTransferObject selectProject(int conversationNumber, java.lang.String projectId);

    scrum.client.DataTransferObject updateProjectHomepage(int conversationNumber);

    scrum.client.DataTransferObject publishRelease(int conversationNumber, java.lang.String releaseId);

    scrum.client.DataTransferObject requestRisks(int conversationNumber);

    scrum.client.DataTransferObject search(int conversationNumber, java.lang.String text);

    scrum.client.DataTransferObject createIssueFromTask(int conversationNumber, java.lang.String taskId);

    scrum.client.DataTransferObject kickStoryFromSprint(int conversationNumber, java.lang.String storyId);

    scrum.client.DataTransferObject pullStoryToSprint(int conversationNumber, java.lang.String storyId);

    scrum.client.DataTransferObject requestHistory(int conversationNumber);

    scrum.client.DataTransferObject requestHistorySprint(int conversationNumber, java.lang.String sprintId);

    scrum.client.DataTransferObject switchToNextSprint(int conversationNumber);

}