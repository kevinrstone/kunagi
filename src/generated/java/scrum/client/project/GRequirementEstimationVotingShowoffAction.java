// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtActionGenerator










package scrum.client.project;


public abstract class GRequirementEstimationVotingShowoffAction
            extends scrum.client.common.AScrumAction {

    protected scrum.client.project.Requirement requirement;

    public GRequirementEstimationVotingShowoffAction(scrum.client.project.Requirement requirement) {
        this.requirement = requirement;
    }

    @Override
    public boolean isExecutable() {
        return true;
    }

    @Override
    public String getId() {
        return ilarkesto.core.base.Str.getSimpleName(getClass()) + '_' + ilarkesto.core.base.Str.toHtmlId(requirement);
    }

}