// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtActionGenerator










package scrum.client.issues;


public abstract class GRejectFixIssueAction
            extends scrum.client.common.AScrumAction {

    protected scrum.client.issues.Issue issue;

    public GRejectFixIssueAction(scrum.client.issues.Issue issue) {
        this.issue = issue;
    }

    @Override
    public boolean isExecutable() {
        return true;
    }

    @Override
    public String getId() {
        return ilarkesto.core.base.Str.getSimpleName(getClass()) + '_' + ilarkesto.core.base.Str.toHtmlId(issue);
    }

}