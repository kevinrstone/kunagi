// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtServiceCallGenerator










package scrum.client.admin;


@com.google.gwt.user.client.rpc.RemoteServiceRelativePath("scrum")
public class TestLdapServiceCall
            extends ilarkesto.gwt.client.AServiceCall<scrum.client.DataTransferObject> {

    private static scrum.client.ScrumServiceAsync service;


    public TestLdapServiceCall() {
    }

    @Override
    protected synchronized void onExecute(int conversationNumber, com.google.gwt.user.client.rpc.AsyncCallback<scrum.client.DataTransferObject> callback) {
        if (service==null) {
            service = (scrum.client.ScrumServiceAsync) com.google.gwt.core.client.GWT.create(scrum.client.ScrumService.class);
            initializeService(service, "scrum");
        }
        service.testLdap(conversationNumber, callback);
    }

    @Override
    public String toString() {
        return "testLdap";
    }

}