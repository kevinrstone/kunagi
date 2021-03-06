// ----------> GENERATED FILE - DON'T TOUCH! <----------

// generator: scrum.mda.KunagiModelApplication$1










package scrum.client.pr;

import ilarkesto.core.base.Utl;
import ilarkesto.core.persistance.AEntity;
import ilarkesto.core.persistance.EntityDoesNotExistException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class GBlogEntry
            extends scrum.client.common.AScrumGwtEntity
            implements java.lang.Comparable<BlogEntry> {

    protected static final ilarkesto.core.logging.Log log = ilarkesto.core.logging.Log.get(BlogEntry.class);

    private static transient ilarkesto.core.persistance.AEntitySetBackReferenceHelper<BlogEntry> projectBackReferencesCache = new ilarkesto.core.persistance.AEntitySetBackReferenceHelper<BlogEntry>() {
    @Override
        protected Set<BlogEntry> loadById(final String id) {
        return new ABlogEntryQuery() {
            @Override
            public boolean test(BlogEntry entity) {
                return id.equals(entity.getProjectId());
            }
            @Override
            public String toString() {
                return "BlogEntry:byProject";
            }
        }.list();
        }
    };

    public static Set< BlogEntry> listByProject(final scrum.client.project.Project project) {
        if (project == null) return new HashSet<BlogEntry>();
        return projectBackReferencesCache.getById(project.getId());
    }

    public static Set< BlogEntry> listByNumber(final int number) {
        return new ABlogEntryQuery() {
            @Override
            public boolean test(BlogEntry entity) {
                return entity.isNumber(number);
            }
            @Override
            public String toString() {
                return "BlogEntry:byNumber";
            }
        }.list();
    }

    private static transient ilarkesto.core.persistance.AEntitySetBackReferenceHelper<BlogEntry> authorsBackReferencesCache = new ilarkesto.core.persistance.AEntitySetBackReferenceHelper<BlogEntry>() {
    @Override
        protected Set<BlogEntry> loadById(final String id) {
        return new ABlogEntryQuery() {
            @Override
            public boolean test(BlogEntry entity) {
                return entity.getAuthorsIds().contains(id);
            }
            @Override
            public String toString() {
                return "BlogEntry:byAuthors";
            }
        }.list();
        }
    };

    public static Set< BlogEntry> listByAuthor(final scrum.client.admin.User author) {
        if (author == null) return new HashSet<BlogEntry>();
        return authorsBackReferencesCache.getById(author.getId());
    }

    public static Set< BlogEntry> listByTitle(final java.lang.String title) {
        return new ABlogEntryQuery() {
            @Override
            public boolean test(BlogEntry entity) {
                return entity.isTitle(title);
            }
            @Override
            public String toString() {
                return "BlogEntry:byTitle";
            }
        }.list();
    }

    public static Set< BlogEntry> listByText(final java.lang.String text) {
        return new ABlogEntryQuery() {
            @Override
            public boolean test(BlogEntry entity) {
                return entity.isText(text);
            }
            @Override
            public String toString() {
                return "BlogEntry:byText";
            }
        }.list();
    }

    public static Set< BlogEntry> listByDateAndTime(final ilarkesto.core.time.DateAndTime dateAndTime) {
        return new ABlogEntryQuery() {
            @Override
            public boolean test(BlogEntry entity) {
                return entity.isDateAndTime(dateAndTime);
            }
            @Override
            public String toString() {
                return "BlogEntry:byDateAndTime";
            }
        }.list();
    }

    private static transient ilarkesto.core.persistance.AEntitySetBackReferenceHelper<BlogEntry> releasesBackReferencesCache = new ilarkesto.core.persistance.AEntitySetBackReferenceHelper<BlogEntry>() {
    @Override
        protected Set<BlogEntry> loadById(final String id) {
        return new ABlogEntryQuery() {
            @Override
            public boolean test(BlogEntry entity) {
                return entity.getReleasesIds().contains(id);
            }
            @Override
            public String toString() {
                return "BlogEntry:byReleases";
            }
        }.list();
        }
    };

    public static Set< BlogEntry> listByRelease(final scrum.client.release.Release release) {
        if (release == null) return new HashSet<BlogEntry>();
        return releasesBackReferencesCache.getById(release.getId());
    }

    public static Set< BlogEntry> listByPublished(final boolean published) {
        return new ABlogEntryQuery() {
            @Override
            public boolean test(BlogEntry entity) {
                return entity.isPublished(published);
            }
            @Override
            public String toString() {
                return "BlogEntry:byPublished";
            }
        }.list();
    }

    @Override
    protected void onAfterPersist() {
        super.onAfterPersist();
        projectBackReferencesCache.clear(getProjectId());
        authorsBackReferencesCache.clear(getAuthorsIds());
        releasesBackReferencesCache.clear(getReleasesIds());
    }

    public abstract static class ABlogEntryQuery extends ilarkesto.core.persistance.AEntityQuery<BlogEntry> {
    @Override
        public Class<BlogEntry> getType() {
            return BlogEntry.class;
        }
    }

    public static Set<BlogEntry> listAll() {
        return new ilarkesto.core.persistance.AllByTypeQuery(BlogEntry.class).list();
    }

    public static BlogEntry getById(String id) {
        return (BlogEntry) AEntity.getById(id);
    }

    @Override
    public Set<ilarkesto.core.persistance.Entity> getReferencedEntities() {
        Set<ilarkesto.core.persistance.Entity> ret = super.getReferencedEntities();
    // --- references ---
        try { Utl.addIfNotNull(ret, getProject()); } catch(EntityDoesNotExistException ex) {}
        if (authorsIds!=null) for (String id : authorsIds) {
            try { ret.add(AEntity.getById(id)); } catch(EntityDoesNotExistException ex) {}
        }
        if (releasesIds!=null) for (String id : releasesIds) {
            try { ret.add(AEntity.getById(id)); } catch(EntityDoesNotExistException ex) {}
        }
        return ret;
    }

    @Override
    public void storeProperties(Map<String, String> properties) {
        super.storeProperties(properties);
        properties.put("projectId", ilarkesto.core.persistance.Persistence.propertyAsString(this.projectId));
        properties.put("number", ilarkesto.core.persistance.Persistence.propertyAsString(this.number));
        properties.put("authorsIds", ilarkesto.core.persistance.Persistence.propertyAsString(this.authorsIds));
        properties.put("title", ilarkesto.core.persistance.Persistence.propertyAsString(this.title));
        properties.put("text", ilarkesto.core.persistance.Persistence.propertyAsString(this.text));
        properties.put("dateAndTime", ilarkesto.core.persistance.Persistence.propertyAsString(this.dateAndTime));
        properties.put("releasesIds", ilarkesto.core.persistance.Persistence.propertyAsString(this.releasesIds));
        properties.put("published", ilarkesto.core.persistance.Persistence.propertyAsString(this.published));
    }

    @Override
    public int compareTo(BlogEntry other) {
        return ilarkesto.core.localization.GermanComparator.INSTANCE.compare(toString(), other.toString());
    }

    private static final ilarkesto.core.logging.Log LOG = ilarkesto.core.logging.Log.get(GBlogEntry.class);

    public static final String TYPE = "BlogEntry";


    // -----------------------------------------------------------
    // - Searchable
    // -----------------------------------------------------------

    @Override
    public boolean matches(ilarkesto.core.search.SearchText search) {
         return search.matches(getTitle(), getText());
    }

    // -----------------------------------------------------------
    // - project
    // -----------------------------------------------------------

    private String projectId;

    public final String getProjectId() {
        return this.projectId;
    }

    public final scrum.client.project.Project getProject() {
        try {
            return this.projectId == null ? null : (scrum.client.project.Project) AEntity.getById(this.projectId);
        } catch (ilarkesto.core.persistance.EntityDoesNotExistException ex) {
            throw ex.setCallerInfo("BlogEntry.project");
        }
    }

    public final void setProject(scrum.client.project.Project project) {
        project = prepareProject(project);
        if (isProject(project)) return;
        setProjectId(project == null ? null : project.getId());
    }

    public final void setProjectId(String id) {
        if (Utl.equals(projectId, id)) return;
        clearProjectBackReferenceCache(id, this.projectId);
        this.projectId = id;
            updateLastModified();
            fireModified("projectId", ilarkesto.core.persistance.Persistence.propertyAsString(this.projectId));
    }

    private void clearProjectBackReferenceCache(String oldId, String newId) {
        projectBackReferencesCache.clear(oldId);
        projectBackReferencesCache.clear(newId);
    }

    private final void updateProjectId(String id) {
        setProjectId(id);
    }

    protected scrum.client.project.Project prepareProject(scrum.client.project.Project project) {
        return project;
    }

    protected void repairDeadProjectReference(String entityId) {
        if (!isPersisted()) return;
        if (this.projectId == null || entityId.equals(this.projectId)) {
            repairMissingMaster();
        }
    }

    public final boolean isProjectSet() {
        return this.projectId != null;
    }

    public final boolean isProject(scrum.client.project.Project project) {
        if (this.projectId == null && project == null) return true;
        return project != null && project.getId().equals(this.projectId);
    }


    // -----------------------------------------------------------
    // - number
    // -----------------------------------------------------------

    private int number;

    public final int getNumber() {
        return number;
    }

    public final void setNumber(int number) {
        number = prepareNumber(number);
        if (isNumber(number)) return;
        this.number = number;
            updateLastModified();
            fireModified("number", ilarkesto.core.persistance.Persistence.propertyAsString(this.number));
    }

    private final void updateNumber(int number) {
        if (isNumber(number)) return;
        this.number = number;
            updateLastModified();
            fireModified("number", ilarkesto.core.persistance.Persistence.propertyAsString(this.number));
    }

    protected int prepareNumber(int number) {
        return number;
    }

    public final boolean isNumber(int number) {
        return this.number == number;
    }

    protected final void updateNumber(Object value) {
        setNumber((Integer)value);
    }

    // -----------------------------------------------------------
    // - authors
    // -----------------------------------------------------------

    private java.util.Set<String> authorsIds = new java.util.HashSet<String>();

    public final Collection<String> getAuthorsIds() {
        return java.util.Collections .unmodifiableCollection(this.authorsIds);
    }

    public final java.util.Set<scrum.client.admin.User> getAuthors() {
        try {
            return (java.util.Set) AEntity.getByIdsAsSet(this.authorsIds);
        } catch (ilarkesto.core.persistance.EntityDoesNotExistException ex) {
            throw ex.setCallerInfo("BlogEntry.authors");
        }
    }

    public final void setAuthors(Collection<scrum.client.admin.User> authors) {
        authors = prepareAuthors(authors);
        if (authors == null) authors = Collections.emptyList();
        java.util.Set<String> ids = ilarkesto.core.persistance.Persistence.getIdsAsSet(authors);
        setAuthorsIds(ids);
    }

    public final void setAuthorsIds(java.util.Set<String> ids) {
        if (Utl.equals(authorsIds, ids)) return;
        clearAuthorsBackReferenceCache(ids, authorsIds);
        authorsIds = ids;
            updateLastModified();
            fireModified("authorsIds", ilarkesto.core.persistance.Persistence.propertyAsString(this.authorsIds));
    }

    private void clearAuthorsBackReferenceCache(Collection<String> oldId, Collection<String> newId) {
        authorsBackReferencesCache.clear(oldId);
        authorsBackReferencesCache.clear(newId);
    }

    private final void updateAuthorsIds(java.util.Set<String> ids) {
        setAuthorsIds(ids);
    }

    protected Collection<scrum.client.admin.User> prepareAuthors(Collection<scrum.client.admin.User> authors) {
        return authors;
    }

    protected void repairDeadAuthorReference(String entityId) {
        if (!isPersisted()) return;
        if (this.authorsIds == null ) return;
        if (this.authorsIds.remove(entityId)) {
            updateLastModified();
            fireModified("authorsIds", ilarkesto.core.persistance.Persistence.propertyAsString(this.authorsIds));
        }
    }

    public final boolean containsAuthor(scrum.client.admin.User author) {
        if (author == null) return false;
        if (this.authorsIds == null) return false;
        return this.authorsIds.contains(author.getId());
    }

    public final int getAuthorsCount() {
        if (this.authorsIds == null) return 0;
        return this.authorsIds.size();
    }

    public final boolean isAuthorsEmpty() {
        if (this.authorsIds == null) return true;
        return this.authorsIds.isEmpty();
    }

    public final boolean addAuthor(scrum.client.admin.User author) {
        if (author == null) throw new IllegalArgumentException("author == null");
        if (this.authorsIds == null) this.authorsIds = new java.util.HashSet<String>();
        boolean added = this.authorsIds.add(author.getId());
        if (added) authorsBackReferencesCache.clear(author.getId());
        if (added) {
            updateLastModified();
            fireModified("authorsIds", ilarkesto.core.persistance.Persistence.propertyAsString(this.authorsIds));
        }
        return added;
    }

    public final boolean addAuthors(Collection<scrum.client.admin.User> authors) {
        if (authors == null) throw new IllegalArgumentException("authors == null");
        if (this.authorsIds == null) this.authorsIds = new java.util.HashSet<String>();
        boolean added = false;
        for (scrum.client.admin.User author : authors) {
            added = added | this.authorsIds.add(author.getId());
        }
        if (added) authorsBackReferencesCache.clear(this.authorsIds);
        if (added) {
            updateLastModified();
            fireModified("authorsIds", ilarkesto.core.persistance.Persistence.propertyAsString(this.authorsIds));
        }
        return added;
    }

    public final boolean removeAuthor(scrum.client.admin.User author) {
        if (author == null) return false;
        if (this.authorsIds == null) return false;
        boolean removed = this.authorsIds.remove(author.getId());
        if (removed) authorsBackReferencesCache.clear(author.getId());
        if (removed) {
            updateLastModified();
            fireModified("authorsIds", ilarkesto.core.persistance.Persistence.propertyAsString(this.authorsIds));
        }
        return removed;
    }

    public final boolean removeAuthors(Collection<scrum.client.admin.User> authors) {
        if (authors == null) return false;
        if (authors.isEmpty()) return false;
        if (this.authorsIds == null) return false;
        boolean removed = false;
        for (scrum.client.admin.User _element: authors) {
            removed = removed | this.authorsIds.remove(_element);
        }
        if (removed) authorsBackReferencesCache.clear(this.authorsIds);
        if (removed) {
            updateLastModified();
            fireModified("authorsIds", ilarkesto.core.persistance.Persistence.propertyAsString(this.authorsIds));
        }
        return removed;
    }

    public final boolean clearAuthors() {
        if (this.authorsIds == null) return false;
        if (this.authorsIds.isEmpty()) return false;
        authorsBackReferencesCache.clear(this.authorsIds);
        this.authorsIds.clear();
            updateLastModified();
            fireModified("authorsIds", ilarkesto.core.persistance.Persistence.propertyAsString(this.authorsIds));
        return true;
    }

    // -----------------------------------------------------------
    // - title
    // -----------------------------------------------------------

    private java.lang.String title;

    public final java.lang.String getTitle() {
        return title;
    }

    public final void setTitle(java.lang.String title) {
        title = prepareTitle(title);
        if (isTitle(title)) return;
        if (title == null) throw new IllegalArgumentException("Mandatory field can not be set to null: title");
        this.title = title;
            updateLastModified();
            fireModified("title", ilarkesto.core.persistance.Persistence.propertyAsString(this.title));
    }

    private final void updateTitle(java.lang.String title) {
        if (isTitle(title)) return;
        if (title == null) throw new IllegalArgumentException("Mandatory field can not be set to null: title");
        this.title = title;
            updateLastModified();
            fireModified("title", ilarkesto.core.persistance.Persistence.propertyAsString(this.title));
    }

    protected java.lang.String prepareTitle(java.lang.String title) {
        // title = Str.removeUnreadableChars(title);
        return title;
    }

    public final boolean isTitleSet() {
        return this.title != null;
    }

    public final boolean isTitle(java.lang.String title) {
        if (this.title == null && title == null) return true;
        return this.title != null && this.title.equals(title);
    }

    protected final void updateTitle(Object value) {
        setTitle((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - text
    // -----------------------------------------------------------

    private java.lang.String text;

    public final java.lang.String getText() {
        return text;
    }

    public final void setText(java.lang.String text) {
        text = prepareText(text);
        if (isText(text)) return;
        this.text = text;
            updateLastModified();
            fireModified("text", ilarkesto.core.persistance.Persistence.propertyAsString(this.text));
    }

    private final void updateText(java.lang.String text) {
        if (isText(text)) return;
        this.text = text;
            updateLastModified();
            fireModified("text", ilarkesto.core.persistance.Persistence.propertyAsString(this.text));
    }

    protected java.lang.String prepareText(java.lang.String text) {
        // text = Str.removeUnreadableChars(text);
        return text;
    }

    public final boolean isTextSet() {
        return this.text != null;
    }

    public final boolean isText(java.lang.String text) {
        if (this.text == null && text == null) return true;
        return this.text != null && this.text.equals(text);
    }

    protected final void updateText(Object value) {
        setText((java.lang.String)value);
    }

    // -----------------------------------------------------------
    // - dateAndTime
    // -----------------------------------------------------------

    private ilarkesto.core.time.DateAndTime dateAndTime;

    public final ilarkesto.core.time.DateAndTime getDateAndTime() {
        return dateAndTime;
    }

    public final void setDateAndTime(ilarkesto.core.time.DateAndTime dateAndTime) {
        dateAndTime = prepareDateAndTime(dateAndTime);
        if (isDateAndTime(dateAndTime)) return;
        this.dateAndTime = dateAndTime;
            updateLastModified();
            fireModified("dateAndTime", ilarkesto.core.persistance.Persistence.propertyAsString(this.dateAndTime));
    }

    private final void updateDateAndTime(ilarkesto.core.time.DateAndTime dateAndTime) {
        if (isDateAndTime(dateAndTime)) return;
        this.dateAndTime = dateAndTime;
            updateLastModified();
            fireModified("dateAndTime", ilarkesto.core.persistance.Persistence.propertyAsString(this.dateAndTime));
    }

    protected ilarkesto.core.time.DateAndTime prepareDateAndTime(ilarkesto.core.time.DateAndTime dateAndTime) {
        return dateAndTime;
    }

    public final boolean isDateAndTimeSet() {
        return this.dateAndTime != null;
    }

    public final boolean isDateAndTime(ilarkesto.core.time.DateAndTime dateAndTime) {
        if (this.dateAndTime == null && dateAndTime == null) return true;
        return this.dateAndTime != null && this.dateAndTime.equals(dateAndTime);
    }

    protected final void updateDateAndTime(Object value) {
        value = value == null ? null : new ilarkesto.core.time.DateAndTime((String)value);
        setDateAndTime((ilarkesto.core.time.DateAndTime)value);
    }

    // -----------------------------------------------------------
    // - releases
    // -----------------------------------------------------------

    private java.util.Set<String> releasesIds = new java.util.HashSet<String>();

    public final Collection<String> getReleasesIds() {
        return java.util.Collections .unmodifiableCollection(this.releasesIds);
    }

    public final java.util.Set<scrum.client.release.Release> getReleases() {
        try {
            return (java.util.Set) AEntity.getByIdsAsSet(this.releasesIds);
        } catch (ilarkesto.core.persistance.EntityDoesNotExistException ex) {
            throw ex.setCallerInfo("BlogEntry.releases");
        }
    }

    public final void setReleases(Collection<scrum.client.release.Release> releases) {
        releases = prepareReleases(releases);
        if (releases == null) releases = Collections.emptyList();
        java.util.Set<String> ids = ilarkesto.core.persistance.Persistence.getIdsAsSet(releases);
        setReleasesIds(ids);
    }

    public final void setReleasesIds(java.util.Set<String> ids) {
        if (Utl.equals(releasesIds, ids)) return;
        clearReleasesBackReferenceCache(ids, releasesIds);
        releasesIds = ids;
            updateLastModified();
            fireModified("releasesIds", ilarkesto.core.persistance.Persistence.propertyAsString(this.releasesIds));
    }

    private void clearReleasesBackReferenceCache(Collection<String> oldId, Collection<String> newId) {
        releasesBackReferencesCache.clear(oldId);
        releasesBackReferencesCache.clear(newId);
    }

    private final void updateReleasesIds(java.util.Set<String> ids) {
        setReleasesIds(ids);
    }

    protected Collection<scrum.client.release.Release> prepareReleases(Collection<scrum.client.release.Release> releases) {
        return releases;
    }

    protected void repairDeadReleaseReference(String entityId) {
        if (!isPersisted()) return;
        if (this.releasesIds == null ) return;
        if (this.releasesIds.remove(entityId)) {
            updateLastModified();
            fireModified("releasesIds", ilarkesto.core.persistance.Persistence.propertyAsString(this.releasesIds));
        }
    }

    public final boolean containsRelease(scrum.client.release.Release release) {
        if (release == null) return false;
        if (this.releasesIds == null) return false;
        return this.releasesIds.contains(release.getId());
    }

    public final int getReleasesCount() {
        if (this.releasesIds == null) return 0;
        return this.releasesIds.size();
    }

    public final boolean isReleasesEmpty() {
        if (this.releasesIds == null) return true;
        return this.releasesIds.isEmpty();
    }

    public final boolean addRelease(scrum.client.release.Release release) {
        if (release == null) throw new IllegalArgumentException("release == null");
        if (this.releasesIds == null) this.releasesIds = new java.util.HashSet<String>();
        boolean added = this.releasesIds.add(release.getId());
        if (added) releasesBackReferencesCache.clear(release.getId());
        if (added) {
            updateLastModified();
            fireModified("releasesIds", ilarkesto.core.persistance.Persistence.propertyAsString(this.releasesIds));
        }
        return added;
    }

    public final boolean addReleases(Collection<scrum.client.release.Release> releases) {
        if (releases == null) throw new IllegalArgumentException("releases == null");
        if (this.releasesIds == null) this.releasesIds = new java.util.HashSet<String>();
        boolean added = false;
        for (scrum.client.release.Release release : releases) {
            added = added | this.releasesIds.add(release.getId());
        }
        if (added) releasesBackReferencesCache.clear(this.releasesIds);
        if (added) {
            updateLastModified();
            fireModified("releasesIds", ilarkesto.core.persistance.Persistence.propertyAsString(this.releasesIds));
        }
        return added;
    }

    public final boolean removeRelease(scrum.client.release.Release release) {
        if (release == null) return false;
        if (this.releasesIds == null) return false;
        boolean removed = this.releasesIds.remove(release.getId());
        if (removed) releasesBackReferencesCache.clear(release.getId());
        if (removed) {
            updateLastModified();
            fireModified("releasesIds", ilarkesto.core.persistance.Persistence.propertyAsString(this.releasesIds));
        }
        return removed;
    }

    public final boolean removeReleases(Collection<scrum.client.release.Release> releases) {
        if (releases == null) return false;
        if (releases.isEmpty()) return false;
        if (this.releasesIds == null) return false;
        boolean removed = false;
        for (scrum.client.release.Release _element: releases) {
            removed = removed | this.releasesIds.remove(_element);
        }
        if (removed) releasesBackReferencesCache.clear(this.releasesIds);
        if (removed) {
            updateLastModified();
            fireModified("releasesIds", ilarkesto.core.persistance.Persistence.propertyAsString(this.releasesIds));
        }
        return removed;
    }

    public final boolean clearReleases() {
        if (this.releasesIds == null) return false;
        if (this.releasesIds.isEmpty()) return false;
        releasesBackReferencesCache.clear(this.releasesIds);
        this.releasesIds.clear();
            updateLastModified();
            fireModified("releasesIds", ilarkesto.core.persistance.Persistence.propertyAsString(this.releasesIds));
        return true;
    }

    // -----------------------------------------------------------
    // - published
    // -----------------------------------------------------------

    private boolean published;

    public final boolean isPublished() {
        return published;
    }

    public final void setPublished(boolean published) {
        published = preparePublished(published);
        if (isPublished(published)) return;
        this.published = published;
            updateLastModified();
            fireModified("published", ilarkesto.core.persistance.Persistence.propertyAsString(this.published));
    }

    private final void updatePublished(boolean published) {
        if (isPublished(published)) return;
        this.published = published;
            updateLastModified();
            fireModified("published", ilarkesto.core.persistance.Persistence.propertyAsString(this.published));
    }

    protected boolean preparePublished(boolean published) {
        return published;
    }

    public final boolean isPublished(boolean published) {
        return this.published == published;
    }

    protected final void updatePublished(Object value) {
        setPublished((Boolean)value);
    }

    public void updateProperties(Map<String, String> properties) {
        super.updateProperties(properties);
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            String property = entry.getKey();
            if (property.equals("id")) continue;
            String value = entry.getValue();
            if (property.equals("projectId")) updateProjectId(ilarkesto.core.persistance.Persistence.parsePropertyReference(value));
            if (property.equals("number")) updateNumber(ilarkesto.core.persistance.Persistence.parsePropertyint(value));
            if (property.equals("authorsIds")) updateAuthorsIds(ilarkesto.core.persistance.Persistence.parsePropertyReferenceSet(value));
            if (property.equals("title")) updateTitle(ilarkesto.core.persistance.Persistence.parsePropertyString(value));
            if (property.equals("text")) updateText(ilarkesto.core.persistance.Persistence.parsePropertyString(value));
            if (property.equals("dateAndTime")) updateDateAndTime(ilarkesto.core.persistance.Persistence.parsePropertyDateAndTime(value));
            if (property.equals("releasesIds")) updateReleasesIds(ilarkesto.core.persistance.Persistence.parsePropertyReferenceSet(value));
            if (property.equals("published")) updatePublished(ilarkesto.core.persistance.Persistence.parsePropertyboolean(value));
        }
    }

    // --- ensure integrity ---
    @Override
    public void onEnsureIntegrity() {
        super.onEnsureIntegrity();
        if (!isProjectSet()) {
            repairMissingMaster();
        }
        try {
            getProject();
        } catch (ilarkesto.core.persistance.EntityDoesNotExistException ex) {
            LOG.info("Repairing dead project reference");
            repairDeadProjectReference(this.projectId);
        }
        if (this.authorsIds == null) this.authorsIds = new java.util.HashSet<String>();
        Set<String> authors = new HashSet<String>(this.authorsIds);
        for (String entityId : authors) {
            try {
                AEntity.getById(entityId);
            } catch (ilarkesto.core.persistance.EntityDoesNotExistException ex) {
                LOG.info("Repairing dead author reference");
                repairDeadAuthorReference(entityId);
            }
        }
        if (this.releasesIds == null) this.releasesIds = new java.util.HashSet<String>();
        Set<String> releases = new HashSet<String>(this.releasesIds);
        for (String entityId : releases) {
            try {
                AEntity.getById(entityId);
            } catch (ilarkesto.core.persistance.EntityDoesNotExistException ex) {
                LOG.info("Repairing dead release reference");
                repairDeadReleaseReference(entityId);
            }
        }
    }

    // --- PLUGIN: GwtEntityPropertyEditorClassGeneratorPlugin ---

    private transient NumberModel numberModel;

    public NumberModel getNumberModel() {
        if (numberModel == null) numberModel = createNumberModel();
        return numberModel;
    }

    protected NumberModel createNumberModel() { return new NumberModel(); }

    protected class NumberModel extends ilarkesto.gwt.client.editor.AIntegerEditorModel {

        @Override
        public String getId() {
            return "BlogEntry_number";
        }

        @Override
        public java.lang.Integer getValue() {
            return getNumber();
        }

        @Override
        public void setValue(java.lang.Integer value) {
            setNumber(value);
        }

            @Override
            public void increment() {
                setNumber(getNumber() + 1);
            }

            @Override
            public void decrement() {
                setNumber(getNumber() - 1);
            }

        @Override
        public boolean isMandatory() { return true; }

        @Override
        protected void onChangeValue(java.lang.Integer oldValue, java.lang.Integer newValue) {
            super.onChangeValue(oldValue, newValue);
            if (oldValue == null) return;
            addUndo(this, oldValue);
        }

    }

    private transient TitleModel titleModel;

    public TitleModel getTitleModel() {
        if (titleModel == null) titleModel = createTitleModel();
        return titleModel;
    }

    protected TitleModel createTitleModel() { return new TitleModel(); }

    protected class TitleModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "BlogEntry_title";
        }

        @Override
        public java.lang.String getValue() {
            return getTitle();
        }

        @Override
        public void setValue(java.lang.String value) {
            setTitle(value);
        }

        @Override
        public boolean isMandatory() { return true; }
        @Override
        public String getTooltip() { return "The title that will appear in the blog."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            if (oldValue == null) return;
            addUndo(this, oldValue);
        }

    }

    private transient TextModel textModel;

    public TextModel getTextModel() {
        if (textModel == null) textModel = createTextModel();
        return textModel;
    }

    protected TextModel createTextModel() { return new TextModel(); }

    protected class TextModel extends ilarkesto.gwt.client.editor.ATextEditorModel {

        @Override
        public String getId() {
            return "BlogEntry_text";
        }

        @Override
        public java.lang.String getValue() {
            return getText();
        }

        @Override
        public void setValue(java.lang.String value) {
            setText(value);
        }

        @Override
        public boolean isRichtext() { return true; }
        @Override
        public String getTooltip() { return "The text that will appear in the blog."; }

        @Override
        protected void onChangeValue(java.lang.String oldValue, java.lang.String newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    private transient DateAndTimeModel dateAndTimeModel;

    public DateAndTimeModel getDateAndTimeModel() {
        if (dateAndTimeModel == null) dateAndTimeModel = createDateAndTimeModel();
        return dateAndTimeModel;
    }

    protected DateAndTimeModel createDateAndTimeModel() { return new DateAndTimeModel(); }

    protected class DateAndTimeModel extends ilarkesto.gwt.client.editor.ADateAndTimeEditorModel {

        @Override
        public String getId() {
            return "BlogEntry_dateAndTime";
        }

        @Override
        public ilarkesto.core.time.DateAndTime getValue() {
            return getDateAndTime();
        }

        @Override
        public void setValue(ilarkesto.core.time.DateAndTime value) {
            setDateAndTime(value);
        }
        @Override
        public String getTooltip() { return "The time that indicates when the blog entry was released."; }

        @Override
        protected void onChangeValue(ilarkesto.core.time.DateAndTime oldValue, ilarkesto.core.time.DateAndTime newValue) {
            super.onChangeValue(oldValue, newValue);
            addUndo(this, oldValue);
        }

    }

    private transient PublishedModel publishedModel;

    public PublishedModel getPublishedModel() {
        if (publishedModel == null) publishedModel = createPublishedModel();
        return publishedModel;
    }

    protected PublishedModel createPublishedModel() { return new PublishedModel(); }

    protected class PublishedModel extends ilarkesto.gwt.client.editor.ABooleanEditorModel {

        @Override
        public String getId() {
            return "BlogEntry_published";
        }

        @Override
        public java.lang.Boolean getValue() {
            return isPublished();
        }

        @Override
        public void setValue(java.lang.Boolean value) {
            setPublished(value);
        }

        @Override
        public boolean isMandatory() { return true; }

        @Override
        protected void onChangeValue(java.lang.Boolean oldValue, java.lang.Boolean newValue) {
            super.onChangeValue(oldValue, newValue);
            if (oldValue == null) return;
            addUndo(this, oldValue);
        }

    }

}