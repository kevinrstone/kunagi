// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtActionGenerator










package scrum.client.sprint;


public abstract class GConvertTaskToIssueAction
            extends scrum.client.common.AScrumAction {

    protected scrum.client.sprint.Task task;

    public GConvertTaskToIssueAction(scrum.client.sprint.Task task) {
        this.task = task;
    }

    @Override
    public boolean isExecutable() {
        return true;
    }

    @Override
    public String getId() {
        return ilarkesto.core.base.Str.getSimpleName(getClass()) + '_' + ilarkesto.core.base.Str.toHtmlId(task);
    }

}