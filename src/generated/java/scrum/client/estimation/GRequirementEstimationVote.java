// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: scrum.mda.KunagiModelApplication$1










package scrum.client.estimation;

import java.util.*;
import ilarkesto.core.base.Utl;
import ilarkesto.core.logging.Log;
import ilarkesto.core.base.Str;
import ilarkesto.core.persistance.AEntity;
import ilarkesto.core.persistance.EntityDoesNotExistException;

public abstract class GRequirementEstimationVote
            extends scrum.client.common.AScrumGwtEntity
            implements java.lang.Comparable<RequirementEstimationVote> {

    protected static final ilarkesto.core.logging.Log log = ilarkesto.core.logging.Log.get(RequirementEstimationVote.class);

    private static transient ilarkesto.core.persistance.AEntitySetBackReferenceHelper<RequirementEstimationVote> requirementBackReferencesCache = new ilarkesto.core.persistance.AEntitySetBackReferenceHelper<RequirementEstimationVote>() {
    @Override
        protected Set<RequirementEstimationVote> loadById(final String id) {
        return new ARequirementEstimationVoteQuery() {
            @Override
            public boolean test(RequirementEstimationVote entity) {
                return id.equals(entity.getRequirementId());
            }
            @Override
            public String toString() {
                return "RequirementEstimationVote:byRequirement";
            }
        }.list();
        }
    };

    public static Set< RequirementEstimationVote> listByRequirement(final scrum.client.project.Requirement requirement) {
        if (requirement == null) return new HashSet<RequirementEstimationVote>();
        return requirementBackReferencesCache.getById(requirement.getId());
    }

    private static transient ilarkesto.core.persistance.AEntitySetBackReferenceHelper<RequirementEstimationVote> userBackReferencesCache = new ilarkesto.core.persistance.AEntitySetBackReferenceHelper<RequirementEstimationVote>() {
    @Override
        protected Set<RequirementEstimationVote> loadById(final String id) {
        return new ARequirementEstimationVoteQuery() {
            @Override
            public boolean test(RequirementEstimationVote entity) {
                return id.equals(entity.getUserId());
            }
            @Override
            public String toString() {
                return "RequirementEstimationVote:byUser";
            }
        }.list();
        }
    };

    public static Set< RequirementEstimationVote> listByUser(final scrum.client.admin.User user) {
        if (user == null) return new HashSet<RequirementEstimationVote>();
        return userBackReferencesCache.getById(user.getId());
    }

    public static Set< RequirementEstimationVote> listByEstimatedWork(final java.lang.Float estimatedWork) {
        return new ARequirementEstimationVoteQuery() {
            @Override
            public boolean test(RequirementEstimationVote entity) {
                return entity.isEstimatedWork(estimatedWork);
            }
            @Override
            public String toString() {
                return "RequirementEstimationVote:byEstimatedWork";
            }
        }.list();
    }

    @Override
    protected void onAfterPersist() {
        super.onAfterPersist();
        requirementBackReferencesCache.clear(getRequirementId());
        userBackReferencesCache.clear(getUserId());
    }

    public abstract static class ARequirementEstimationVoteQuery extends ilarkesto.core.persistance.AEntityQuery<RequirementEstimationVote> {
    @Override
        public Class<RequirementEstimationVote> getType() {
            return RequirementEstimationVote.class;
        }
    }

    public static Set<RequirementEstimationVote> listAll() {
        return new ilarkesto.core.persistance.AllByTypeQuery(RequirementEstimationVote.class).list();
    }

    public static RequirementEstimationVote getById(String id) {
        return (RequirementEstimationVote) AEntity.getById(id);
    }

    @Override
    public Set<ilarkesto.core.persistance.Entity> getReferencedEntities() {
        Set<ilarkesto.core.persistance.Entity> ret = super.getReferencedEntities();
    // --- references ---
        try { Utl.addIfNotNull(ret, getRequirement()); } catch(EntityDoesNotExistException ex) {}
        try { Utl.addIfNotNull(ret, getUser()); } catch(EntityDoesNotExistException ex) {}
        return ret;
    }

    @Override
    public void storeProperties(Map<String, String> properties) {
        super.storeProperties(properties);
        properties.put("requirementId", ilarkesto.core.persistance.Persistence.propertyAsString(this.requirementId));
        properties.put("userId", ilarkesto.core.persistance.Persistence.propertyAsString(this.userId));
        properties.put("estimatedWork", ilarkesto.core.persistance.Persistence.propertyAsString(this.estimatedWork));
    }

    @Override
    public int compareTo(RequirementEstimationVote other) {
        return ilarkesto.core.localization.GermanComparator.INSTANCE.compare(toString(), other.toString());
    }

    private static final ilarkesto.core.logging.Log LOG = ilarkesto.core.logging.Log.get(GRequirementEstimationVote.class);

    public static final String TYPE = "RequirementEstimationVote";

    // -----------------------------------------------------------
    // - requirement
    // -----------------------------------------------------------

    private String requirementId;

    public final String getRequirementId() {
        return this.requirementId;
    }

    public final scrum.client.project.Requirement getRequirement() {
        try {
            return this.requirementId == null ? null : (scrum.client.project.Requirement) AEntity.getById(this.requirementId);
        } catch (ilarkesto.core.persistance.EntityDoesNotExistException ex) {
            throw ex.setCallerInfo("RequirementEstimationVote.requirement");
        }
    }

    public final void setRequirement(scrum.client.project.Requirement requirement) {
        requirement = prepareRequirement(requirement);
        if (isRequirement(requirement)) return;
        setRequirementId(requirement == null ? null : requirement.getId());
    }

    public final void setRequirementId(String id) {
        if (Utl.equals(requirementId, id)) return;
        clearRequirementBackReferenceCache(id, this.requirementId);
        this.requirementId = id;
            updateLastModified();
            fireModified("requirementId", ilarkesto.core.persistance.Persistence.propertyAsString(this.requirementId));
    }

    private void clearRequirementBackReferenceCache(String oldId, String newId) {
        requirementBackReferencesCache.clear(oldId);
        requirementBackReferencesCache.clear(newId);
    }

    private final void updateRequirementId(String id) {
        setRequirementId(id);
    }

    protected scrum.client.project.Requirement prepareRequirement(scrum.client.project.Requirement requirement) {
        return requirement;
    }

    protected void repairDeadRequirementReference(String entityId) {
        if (!isPersisted()) return;
        if (this.requirementId == null || entityId.equals(this.requirementId)) {
            repairMissingMaster();
        }
    }

    public final boolean isRequirementSet() {
        return this.requirementId != null;
    }

    public final boolean isRequirement(scrum.client.project.Requirement requirement) {
        if (this.requirementId == null && requirement == null) return true;
        return requirement != null && requirement.getId().equals(this.requirementId);
    }


    // -----------------------------------------------------------
    // - user
    // -----------------------------------------------------------

    private String userId;

    public final String getUserId() {
        return this.userId;
    }

    public final scrum.client.admin.User getUser() {
        try {
            return this.userId == null ? null : (scrum.client.admin.User) AEntity.getById(this.userId);
        } catch (ilarkesto.core.persistance.EntityDoesNotExistException ex) {
            throw ex.setCallerInfo("RequirementEstimationVote.user");
        }
    }

    public final void setUser(scrum.client.admin.User user) {
        user = prepareUser(user);
        if (isUser(user)) return;
        setUserId(user == null ? null : user.getId());
    }

    public final void setUserId(String id) {
        if (Utl.equals(userId, id)) return;
        clearUserBackReferenceCache(id, this.userId);
        this.userId = id;
            updateLastModified();
            fireModified("userId", ilarkesto.core.persistance.Persistence.propertyAsString(this.userId));
    }

    private void clearUserBackReferenceCache(String oldId, String newId) {
        userBackReferencesCache.clear(oldId);
        userBackReferencesCache.clear(newId);
    }

    private final void updateUserId(String id) {
        setUserId(id);
    }

    protected scrum.client.admin.User prepareUser(scrum.client.admin.User user) {
        return user;
    }

    protected void repairDeadUserReference(String entityId) {
        if (!isPersisted()) return;
        if (this.userId == null || entityId.equals(this.userId)) {
            repairMissingMaster();
        }
    }

    public final boolean isUserSet() {
        return this.userId != null;
    }

    public final boolean isUser(scrum.client.admin.User user) {
        if (this.userId == null && user == null) return true;
        return user != null && user.getId().equals(this.userId);
    }


    // -----------------------------------------------------------
    // - estimatedWork
    // -----------------------------------------------------------

    private java.lang.Float estimatedWork;

    public final java.lang.Float getEstimatedWork() {
        return estimatedWork;
    }

    public final void setEstimatedWork(java.lang.Float estimatedWork) {
        estimatedWork = prepareEstimatedWork(estimatedWork);
        if (isEstimatedWork(estimatedWork)) return;
        this.estimatedWork = estimatedWork;
            updateLastModified();
            fireModified("estimatedWork", ilarkesto.core.persistance.Persistence.propertyAsString(this.estimatedWork));
    }

    private final void updateEstimatedWork(java.lang.Float estimatedWork) {
        if (isEstimatedWork(estimatedWork)) return;
        this.estimatedWork = estimatedWork;
            updateLastModified();
            fireModified("estimatedWork", ilarkesto.core.persistance.Persistence.propertyAsString(this.estimatedWork));
    }

    protected java.lang.Float prepareEstimatedWork(java.lang.Float estimatedWork) {
        return estimatedWork;
    }

    public final boolean isEstimatedWorkSet() {
        return this.estimatedWork != null;
    }

    public final boolean isEstimatedWork(java.lang.Float estimatedWork) {
        if (this.estimatedWork == null && estimatedWork == null) return true;
        return this.estimatedWork != null && this.estimatedWork.equals(estimatedWork);
    }

    protected final void updateEstimatedWork(Object value) {
        setEstimatedWork((java.lang.Float)value);
    }

    public void updateProperties(Map<String, String> properties) {
        super.updateProperties(properties);
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            String property = entry.getKey();
            if (property.equals("id")) continue;
            String value = entry.getValue();
            if (property.equals("requirementId")) updateRequirementId(ilarkesto.core.persistance.Persistence.parsePropertyReference(value));
            if (property.equals("userId")) updateUserId(ilarkesto.core.persistance.Persistence.parsePropertyReference(value));
            if (property.equals("estimatedWork")) updateEstimatedWork(ilarkesto.core.persistance.Persistence.parsePropertyFloat(value));
        }
    }

    // --- ensure integrity ---
    @Override
    public void onEnsureIntegrity() {
        super.onEnsureIntegrity();
        if (!isRequirementSet()) {
            repairMissingMaster();
        }
        try {
            getRequirement();
        } catch (ilarkesto.core.persistance.EntityDoesNotExistException ex) {
            LOG.info("Repairing dead requirement reference");
            repairDeadRequirementReference(this.requirementId);
        }
        if (!isUserSet()) {
            repairMissingMaster();
        }
        try {
            getUser();
        } catch (ilarkesto.core.persistance.EntityDoesNotExistException ex) {
            LOG.info("Repairing dead user reference");
            repairDeadUserReference(this.userId);
        }
    }

    // --- PLUGIN: GwtEntityPropertyEditorClassGeneratorPlugin ---

    private transient EstimatedWorkModel estimatedWorkModel;

    public EstimatedWorkModel getEstimatedWorkModel() {
        if (estimatedWorkModel == null) estimatedWorkModel = createEstimatedWorkModel();
        return estimatedWorkModel;
    }

    protected EstimatedWorkModel createEstimatedWorkModel() { return new EstimatedWorkModel(); }

    protected class EstimatedWorkModel extends ilarkesto.gwt.client.editor.AFloatEditorModel {

        @Override
        public String getId() {
            return "RequirementEstimationVote_estimatedWork";
        }

        @Override
        public java.lang.Float getValue() {
            return getEstimatedWork();
        }

        @Override
        public void setValue(java.lang.Float value) {
            setEstimatedWork(value);
        }

        @Override
        protected void onChangeValue(java.lang.Float oldValue, java.lang.Float newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

}