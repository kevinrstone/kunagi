// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: ilarkesto.mda.gen.EntityGenerator










package scrum.server.admin;

import java.util.*;
import ilarkesto.persistence.*;
import ilarkesto.logging.*;
import ilarkesto.base.*;
import ilarkesto.base.time.*;
import ilarkesto.auth.*;

public abstract class GProjectUserConfig
            extends AEntity
            implements java.lang.Comparable<ProjectUserConfig> {

    // --- AEntity ---

    public final ProjectUserConfigDao getDao() {
        return projectUserConfigDao;
    }

    protected void repairDeadDatob(ADatob datob) {
    }

    @Override
    public void storeProperties(Map properties) {
        super.storeProperties(properties);
        properties.put("projectId", this.projectId);
        properties.put("userId", this.userId);
        properties.put("color", this.color);
        properties.put("selectedEntitysIds", this.selectedEntitysIds);
    }

    public int compareTo(ProjectUserConfig other) {
        return toString().toLowerCase().compareTo(other.toString().toLowerCase());
    }

    private static final Logger LOG = Logger.get(GProjectUserConfig.class);

    public static final String TYPE = "projectUserConfig";

    // -----------------------------------------------------------
    // - project
    // -----------------------------------------------------------

    private String projectId;
    private transient scrum.server.project.Project projectCache;

    private void updateProjectCache() {
        projectCache = this.projectId == null ? null : (scrum.server.project.Project)projectDao.getById(this.projectId);
    }

    public final scrum.server.project.Project getProject() {
        if (projectCache == null) updateProjectCache();
        return projectCache;
    }

    public final void setProject(scrum.server.project.Project project) {
        project = prepareProject(project);
        if (isProject(project)) return;
        this.projectId = project == null ? null : project.getId();
        projectCache = project;
        fireModified();
    }

    protected scrum.server.project.Project prepareProject(scrum.server.project.Project project) {
        return project;
    }

    protected void repairDeadProjectReference(String entityId) {
        if (entityId.equals(this.projectId)) {
            repairMissingMaster();
        }
    }

    public final boolean isProjectSet() {
        return this.projectId != null;
    }

    public final boolean isProject(scrum.server.project.Project project) {
        if (this.projectId == null && project == null) return true;
        return project != null && project.getId().equals(this.projectId);
    }

    protected final void updateProject(Object value) {
        setProject(value == null ? null : (scrum.server.project.Project)projectDao.getById((String)value));
    }

    // -----------------------------------------------------------
    // - user
    // -----------------------------------------------------------

    private String userId;
    private transient scrum.server.admin.User userCache;

    private void updateUserCache() {
        userCache = this.userId == null ? null : (scrum.server.admin.User)userDao.getById(this.userId);
    }

    public final scrum.server.admin.User getUser() {
        if (userCache == null) updateUserCache();
        return userCache;
    }

    public final void setUser(scrum.server.admin.User user) {
        user = prepareUser(user);
        if (isUser(user)) return;
        this.userId = user == null ? null : user.getId();
        userCache = user;
        fireModified();
    }

    protected scrum.server.admin.User prepareUser(scrum.server.admin.User user) {
        return user;
    }

    protected void repairDeadUserReference(String entityId) {
        if (entityId.equals(this.userId)) {
            repairMissingMaster();
        }
    }

    public final boolean isUserSet() {
        return this.userId != null;
    }

    public final boolean isUser(scrum.server.admin.User user) {
        if (this.userId == null && user == null) return true;
        return user != null && user.getId().equals(this.userId);
    }

    protected final void updateUser(Object value) {
        setUser(value == null ? null : (scrum.server.admin.User)userDao.getById((String)value));
    }

    // -----------------------------------------------------------
    // - color
    // -----------------------------------------------------------

    private java.lang.String color;

    public final java.lang.String getColor() {
        return color;
    }

    public final void setColor(java.lang.String color) {
        color = prepareColor(color);
        if (isColor(color)) return;
        this.color = color;
        fireModified();
    }

    protected java.lang.String prepareColor(java.lang.String color) {
        color = Str.removeUnreadableChars(color);
        return color;
    }

    public final boolean isColorSet() {
        return this.color != null;
    }

    public final boolean isColor(java.lang.String color) {
        if (this.color == null && color == null) return true;
        return this.color != null && this.color.equals(color);
    }

    protected final void updateColor(Object value) {
        setColor((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - selectedEntitysIds
    // -----------------------------------------------------------

    private java.util.Set<java.lang.String> selectedEntitysIds = new java.util.HashSet<java.lang.String>();

    public final java.util.Set<java.lang.String> getSelectedEntitysIds() {
        return new java.util.HashSet<java.lang.String>(selectedEntitysIds);
    }

    public final void setSelectedEntitysIds(Collection<java.lang.String> selectedEntitysIds) {
        selectedEntitysIds = prepareSelectedEntitysIds(selectedEntitysIds);
        if (selectedEntitysIds == null) selectedEntitysIds = Collections.emptyList();
        if (this.selectedEntitysIds.equals(selectedEntitysIds)) return;
        this.selectedEntitysIds = new java.util.HashSet<java.lang.String>(selectedEntitysIds);
        fireModified();
    }

    protected Collection<java.lang.String> prepareSelectedEntitysIds(Collection<java.lang.String> selectedEntitysIds) {
        return selectedEntitysIds;
    }

    public final boolean containsSelectedEntitysId(java.lang.String selectedEntitysId) {
        if (selectedEntitysId == null) return false;
        return this.selectedEntitysIds.contains(selectedEntitysId);
    }

    public final int getSelectedEntitysIdsCount() {
        return this.selectedEntitysIds.size();
    }

    public final boolean isSelectedEntitysIdsEmpty() {
        return this.selectedEntitysIds.isEmpty();
    }

    public final boolean addSelectedEntitysId(java.lang.String selectedEntitysId) {
        if (selectedEntitysId == null) throw new IllegalArgumentException("selectedEntitysId == null");
        boolean added = this.selectedEntitysIds.add(selectedEntitysId);
        if (added) fireModified();
        return added;
    }

    public final boolean addSelectedEntitysIds(Collection<java.lang.String> selectedEntitysIds) {
        if (selectedEntitysIds == null) throw new IllegalArgumentException("selectedEntitysIds == null");
        boolean added = false;
        for (java.lang.String selectedEntitysId : selectedEntitysIds) {
            added = added | this.selectedEntitysIds.add(selectedEntitysId);
        }
        if (added) fireModified();
        return added;
    }

    public final boolean removeSelectedEntitysId(java.lang.String selectedEntitysId) {
        if (selectedEntitysId == null) throw new IllegalArgumentException("selectedEntitysId == null");
        if (this.selectedEntitysIds == null) return false;
        boolean removed = this.selectedEntitysIds.remove(selectedEntitysId);
        if (removed) fireModified();
        return removed;
    }

    public final boolean removeSelectedEntitysIds(Collection<java.lang.String> selectedEntitysIds) {
        if (selectedEntitysIds == null) return false;
        if (selectedEntitysIds.isEmpty()) return false;
        boolean removed = false;
        for (java.lang.String _element: selectedEntitysIds) {
            removed = removed | removeSelectedEntitysId(_element);
        }
        return removed;
    }

    public final boolean clearSelectedEntitysIds() {
        if (this.selectedEntitysIds.isEmpty()) return false;
        this.selectedEntitysIds.clear();
        fireModified();
        return true;
    }

    public final String getSelectedEntitysIdsAsCommaSeparatedString() {
        if (this.selectedEntitysIds.isEmpty()) return null;
        return Str.concat(this.selectedEntitysIds,", ");
    }

    public final void setSelectedEntitysIdsAsCommaSeparatedString(String selectedEntitysIds) {
        this.selectedEntitysIds = new java.util.HashSet(Str.parseCommaSeparatedString(selectedEntitysIds));
    }

    protected final void updateSelectedEntitysIds(Object value) {
        setSelectedEntitysIds((java.util.Set<java.lang.String>) value);
    }

    public void updateProperties(Map<?, ?> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            String property = (String) entry.getKey();
            if (property.equals("id")) continue;
            Object value = entry.getValue();
            if (property.equals("projectId")) updateProject(value);
            if (property.equals("userId")) updateUser(value);
            if (property.equals("color")) updateColor(value);
            if (property.equals("selectedEntitysIds")) updateSelectedEntitysIds(value);
        }
    }

    protected void repairDeadReferences(String entityId) {
        super.repairDeadReferences(entityId);
        repairDeadProjectReference(entityId);
        repairDeadUserReference(entityId);
        if (this.selectedEntitysIds == null) this.selectedEntitysIds = new java.util.HashSet<java.lang.String>();
    }

    // --- ensure integrity ---

    public void ensureIntegrity() {
        super.ensureIntegrity();
        if (!isProjectSet()) {
            repairMissingMaster();
            return;
        }
        try {
            getProject();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead project reference");
            repairDeadProjectReference(this.projectId);
        }
        if (!isUserSet()) {
            repairMissingMaster();
            return;
        }
        try {
            getUser();
        } catch (EntityDoesNotExistException ex) {
            LOG.info("Repairing dead user reference");
            repairDeadUserReference(this.userId);
        }
        if (this.selectedEntitysIds == null) this.selectedEntitysIds = new java.util.HashSet<java.lang.String>();
    }


    // -----------------------------------------------------------
    // - dependencies
    // -----------------------------------------------------------

    protected static scrum.server.project.ProjectDao projectDao;

    public static final void setProjectDao(scrum.server.project.ProjectDao projectDao) {
        GProjectUserConfig.projectDao = projectDao;
    }

    protected static ProjectUserConfigDao projectUserConfigDao;

    public static final void setProjectUserConfigDao(ProjectUserConfigDao projectUserConfigDao) {
        GProjectUserConfig.projectUserConfigDao = projectUserConfigDao;
    }

}