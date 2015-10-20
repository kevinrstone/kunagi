// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtServiceCallGenerator










package scrum.client.project;

import java.util.*;

@com.google.gwt.user.client.rpc.RemoteServiceRelativePath("scrum")
public class MoveRequirementToProjectServiceCall
            extends ilarkesto.gwt.client.AServiceCall<scrum.client.DataTransferObject> {

    private static scrum.client.ScrumServiceAsync service;

    java.lang.String destinationProjectId;
    java.lang.String requirementId;

    public MoveRequirementToProjectServiceCall(java.lang.String destinationProjectId, java.lang.String requirementId) {
        this.destinationProjectId = destinationProjectId;
        this.requirementId = requirementId;
    }

    @Override
    protected synchronized void onExecute(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback) {
        if (service==null) {
            service = (scrum.client.ScrumServiceAsync) com.google.gwt.core.client.GWT.create(scrum.client.ScrumService.class);
            initializeService(service, "scrum");
        }
        service.moveRequirementToProject(conversationNumber, destinationProjectId, requirementId, callback);
    }

    @Override
    public String toString() {
        return "moveRequirementToProject";
    }

}