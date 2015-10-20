/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package scrum.client.common;

import ilarkesto.core.base.Str;
import ilarkesto.core.html.ToHtmlSupport;
import ilarkesto.core.scope.Scope;
import ilarkesto.core.time.DateAndTime;
import ilarkesto.gwt.client.AGwtEntity;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.HtmlLabelSupport;
import ilarkesto.gwt.client.editor.AFieldModel;
import ilarkesto.gwt.client.editor.AOptionEditorModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import scrum.client.admin.Auth;
import scrum.client.admin.User;
import scrum.client.calendar.SimpleEvent;
import scrum.client.collaboration.Comment;
import scrum.client.collaboration.Emoticon;
import scrum.client.collaboration.ForumSupport;
import scrum.client.collaboration.Subject;
import scrum.client.collaboration.Wikipage;
import scrum.client.files.File;
import scrum.client.impediments.Impediment;
import scrum.client.issues.Issue;
import scrum.client.pr.BlogEntry;
import scrum.client.project.Quality;
import scrum.client.project.Requirement;
import scrum.client.release.Release;
import scrum.client.risks.Risk;
import scrum.client.sprint.Sprint;
import scrum.client.sprint.Task;

public abstract class AScrumGwtEntity extends AGwtEntity implements ToHtmlSupport, HtmlLabelSupport {

	public AScrumGwtEntity() {}

	public Set<Comment> getComments() {
		return Comment.listByParent(this);
	}

	public Comment getLatestComment() {
		Comment latest = null;
		for (Comment comment : getComments()) {
			if (latest == null || comment.getDateAndTime().isAfter(latest.getDateAndTime())) latest = comment;
		}
		return latest;
	}

	public DateAndTime getLatestCommentDateAndTime() {
		Comment latest = getLatestComment();
		return latest == null ? null : latest.getDateAndTime();
	}

	public Set<Emoticon> getEmoticons() {
		return Emoticon.listByParent(this);
	}

	public void setCurrentUserEmoticon(String emotion) {
		updateLastModified();
		boolean delete = Str.isBlank(emotion);
		Emoticon emoticon = getCurrentUserEmoticon();
		if (emoticon == null) {
			if (!delete) {
				emoticon = Emoticon.post(this, emotion);
				return;
			}
		} else {
			if (delete) {
				emoticon.delete();
			} else {
				emoticon.setEmotion(emotion);
			}
		}
	}

	public Emoticon getCurrentUserEmoticon() {
		User currentUser = Scope.get().getComponent(Auth.class).getUser();
		for (Emoticon emoticon : getEmoticons()) {
			if (emoticon.isOwner(currentUser)) return emoticon;
		}
		return null;
	}

	public AOptionEditorModel<String> getCurrentUserEmotionModel() {
		return new AOptionEditorModel<String>() {

			@Override
			public List<String> getOptions() {
				List<String> options = new ArrayList<String>(Emoticon.EMOTIONS_LEGACY);
				options.add(0, "");
				return options;
			}

			@Override
			public void setValue(String value) {
				setCurrentUserEmoticon(value);
			}

			@Override
			public String getValue() {
				Emoticon emoticon = getCurrentUserEmoticon();
				return emoticon == null ? null : emoticon.getEmotion();
			}
		};
	}

	@Override
	public String getHtmlLabel() {
		StringBuilder sb = new StringBuilder();
		if (this instanceof ReferenceSupport) {
			sb.append("<span class='reference'>").append(((ReferenceSupport) this).getReference()).append("</span> ");
		}
		String label;
		if (this instanceof ForumSupport) {
			label = ((ForumSupport) this).getLabel();
		} else {
			label = toString();
		}
		sb.append(Gwt.escapeHtml(label));
		return sb.toString();
	}

	private transient AFieldModel<String> labelModel;

	public AFieldModel<String> getLabelModel() {
		if (labelModel == null) {
			labelModel = new AFieldModel<String>() {

				@Override
				public String getValue() {
					if (AScrumGwtEntity.this instanceof LabelSupport)
						return ((LabelSupport) AScrumGwtEntity.this).getLabel();
					return toString();
				}
			};
		}
		return labelModel;
	}

	private transient AFieldModel<String> lastCommentAgoModel;

	public AFieldModel<String> getLastCommentAgoModel() {
		if (lastCommentAgoModel == null) lastCommentAgoModel = new AFieldModel<String>() {

			@Override
			public String getValue() {
				Comment comment = (AScrumGwtEntity.this).getLatestComment();
				return comment != null ? comment.getDateAndTime().getPeriodToNow().toShortestString() + " ago by "
						+ comment.getAuthorName() : null;
			}
		};
		return lastCommentAgoModel;
	}

	@Override
	public String toHtml() {
		return Gwt.escapeHtml(toString());
	}

	public static AScrumGwtEntity getEntityByReference(String reference) {

		if (reference.length() > 4 && reference.startsWith("[[")) {
			String pageName = reference.substring(2, reference.length() - 2);
			for (Wikipage e : Wikipage.listAll()) {
				if (e.isName(pageName)) return e;
			}
			return null;
		}

		int number;
		try {
			number = Integer.parseInt(reference.substring(Requirement.REFERENCE_PREFIX.length()));
		} catch (NumberFormatException ex) {
			return null;
		}
		if (reference.startsWith(Requirement.REFERENCE_PREFIX)) {
			for (Requirement e : Requirement.listAll()) {
				if (e.isNumber(number)) return e;
			}
			return null;
		} else if (reference.startsWith(Task.REFERENCE_PREFIX)) {
			for (Task e : Task.listAll()) {
				if (e.isNumber(number)) return e;
			}
			return null;
		} else if (reference.startsWith(Quality.REFERENCE_PREFIX)) {
			for (Quality e : Quality.listAll()) {
				if (e.isNumber(number)) return e;
			}
			return null;
		} else if (reference.startsWith(Issue.REFERENCE_PREFIX)) {
			for (Issue e : Issue.listAll()) {
				if (e.isNumber(number)) return e;
			}
			return null;
		} else if (reference.startsWith(Sprint.REFERENCE_PREFIX)) {
			for (Sprint e : Sprint.listAll()) {
				if (e.isNumber(number)) return e;
			}
			return null;
		} else if (reference.startsWith(Risk.REFERENCE_PREFIX)) {
			for (Risk e : Risk.listAll()) {
				if (e.isNumber(number)) return e;
			}
			return null;
		} else if (reference.startsWith(Impediment.REFERENCE_PREFIX)) {
			for (Impediment e : Impediment.listAll()) {
				if (e.isNumber(number)) return e;
			}
			return null;
		} else if (reference.startsWith(File.REFERENCE_PREFIX)) {
			for (File e : File.listAll()) {
				if (e.isNumber(number)) return e;
			}
			return null;
		} else if (reference.startsWith(Subject.REFERENCE_PREFIX)) {
			for (Subject e : Subject.listAll()) {
				if (e.isNumber(number)) return e;
			}
			return null;
		} else if (reference.startsWith(SimpleEvent.REFERENCE_PREFIX)) {
			for (SimpleEvent e : SimpleEvent.listAll()) {
				if (e.isNumber(number)) return e;
			}
			return null;
		} else if (reference.startsWith(Release.REFERENCE_PREFIX)) {
			for (Release e : Release.listAll()) {
				if (e.isNumber(number)) return e;
			}
			return null;
		} else if (reference.startsWith(BlogEntry.REFERENCE_PREFIX)) {
			for (BlogEntry e : BlogEntry.listAll()) {
				if (e.isNumber(number)) return e;
			}
			return null;
		} else {
			throw new RuntimeException("Unsupported entity reference: " + reference);
		}
	}

}
