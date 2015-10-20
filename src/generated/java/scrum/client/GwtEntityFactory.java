// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtEntityFactoryGenerator










package scrum.client;

import java.util.*;

public class GwtEntityFactory
            extends ilarkesto.gwt.client.persistence.AGwtEntityFactory {

    @Override
    public ilarkesto.core.persistance.AEntity createEntity(String type, String id) {
        if (type.equals("BlogEntry")) return new scrum.client.pr.BlogEntry().setId(id);
        if (type.equals("Change")) return new scrum.client.journal.Change().setId(id);
        if (type.equals("ChatMessage")) return new scrum.client.collaboration.ChatMessage().setId(id);
        if (type.equals("Comment")) return new scrum.client.collaboration.Comment().setId(id);
        if (type.equals("Emoticon")) return new scrum.client.collaboration.Emoticon().setId(id);
        if (type.equals("File")) return new scrum.client.files.File().setId(id);
        if (type.equals("Impediment")) return new scrum.client.impediments.Impediment().setId(id);
        if (type.equals("Issue")) return new scrum.client.issues.Issue().setId(id);
        if (type.equals("Project")) return new scrum.client.project.Project().setId(id);
        if (type.equals("ProjectEvent")) return new scrum.client.journal.ProjectEvent().setId(id);
        if (type.equals("ProjectSprintSnapshot")) return new scrum.client.project.ProjectSprintSnapshot().setId(id);
        if (type.equals("ProjectUserConfig")) return new scrum.client.admin.ProjectUserConfig().setId(id);
        if (type.equals("Quality")) return new scrum.client.project.Quality().setId(id);
        if (type.equals("Release")) return new scrum.client.release.Release().setId(id);
        if (type.equals("Requirement")) return new scrum.client.project.Requirement().setId(id);
        if (type.equals("RequirementEstimationVote")) return new scrum.client.estimation.RequirementEstimationVote().setId(id);
        if (type.equals("Risk")) return new scrum.client.risks.Risk().setId(id);
        if (type.equals("SimpleEvent")) return new scrum.client.calendar.SimpleEvent().setId(id);
        if (type.equals("Sprint")) return new scrum.client.sprint.Sprint().setId(id);
        if (type.equals("SprintDaySnapshot")) return new scrum.client.sprint.SprintDaySnapshot().setId(id);
        if (type.equals("SprintReport")) return new scrum.client.sprint.SprintReport().setId(id);
        if (type.equals("Subject")) return new scrum.client.collaboration.Subject().setId(id);
        if (type.equals("Subscription")) return new scrum.client.pr.Subscription().setId(id);
        if (type.equals("SystemConfig")) return new scrum.client.admin.SystemConfig().setId(id);
        if (type.equals("Task")) return new scrum.client.sprint.Task().setId(id);
        if (type.equals("User")) return new scrum.client.admin.User().setId(id);
        if (type.equals("Wikipage")) return new scrum.client.collaboration.Wikipage().setId(id);
        throw new IllegalStateException("Unsupported entity: " + type);
    }

    @Override
    public Class<? extends ilarkesto.core.persistance.AEntity> getType(String type) {
        if (type.equals("BlogEntry")) return  scrum.client.pr.BlogEntry.class;
        if (type.equals("Change")) return  scrum.client.journal.Change.class;
        if (type.equals("ChatMessage")) return  scrum.client.collaboration.ChatMessage.class;
        if (type.equals("Comment")) return  scrum.client.collaboration.Comment.class;
        if (type.equals("Emoticon")) return  scrum.client.collaboration.Emoticon.class;
        if (type.equals("File")) return  scrum.client.files.File.class;
        if (type.equals("Impediment")) return  scrum.client.impediments.Impediment.class;
        if (type.equals("Issue")) return  scrum.client.issues.Issue.class;
        if (type.equals("Project")) return  scrum.client.project.Project.class;
        if (type.equals("ProjectEvent")) return  scrum.client.journal.ProjectEvent.class;
        if (type.equals("ProjectSprintSnapshot")) return  scrum.client.project.ProjectSprintSnapshot.class;
        if (type.equals("ProjectUserConfig")) return  scrum.client.admin.ProjectUserConfig.class;
        if (type.equals("Quality")) return  scrum.client.project.Quality.class;
        if (type.equals("Release")) return  scrum.client.release.Release.class;
        if (type.equals("Requirement")) return  scrum.client.project.Requirement.class;
        if (type.equals("RequirementEstimationVote")) return  scrum.client.estimation.RequirementEstimationVote.class;
        if (type.equals("Risk")) return  scrum.client.risks.Risk.class;
        if (type.equals("SimpleEvent")) return  scrum.client.calendar.SimpleEvent.class;
        if (type.equals("Sprint")) return  scrum.client.sprint.Sprint.class;
        if (type.equals("SprintDaySnapshot")) return  scrum.client.sprint.SprintDaySnapshot.class;
        if (type.equals("SprintReport")) return  scrum.client.sprint.SprintReport.class;
        if (type.equals("Subject")) return  scrum.client.collaboration.Subject.class;
        if (type.equals("Subscription")) return  scrum.client.pr.Subscription.class;
        if (type.equals("SystemConfig")) return  scrum.client.admin.SystemConfig.class;
        if (type.equals("Task")) return  scrum.client.sprint.Task.class;
        if (type.equals("User")) return  scrum.client.admin.User.class;
        if (type.equals("Wikipage")) return  scrum.client.collaboration.Wikipage.class;
        throw new IllegalStateException("Unsupported entity: " + type);
    }

}