// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.legacy.generator.GwtActionGenerator










package scrum.client.admin;


public abstract class GEnableUserAction
            extends scrum.client.common.AScrumAction {

    protected scrum.client.admin.User user;

    public GEnableUserAction(scrum.client.admin.User user) {
        this.user = user;
    }

    @Override
    public boolean isExecutable() {
        return true;
    }

    @Override
    public String getId() {
        return ilarkesto.core.base.Str.getSimpleName(getClass()) + '_' + ilarkesto.core.base.Str.toHtmlId(user);
    }

}