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
package scrum.client.collaboration;

import ilarkesto.core.scope.Scope;
import ilarkesto.core.time.DateAndTime;

import java.util.Comparator;

import scrum.client.admin.User;
import scrum.client.common.AScrumGwtEntity;
import scrum.client.project.Project;

public class Comment extends GComment {

	public static Comment post(AScrumGwtEntity parent, User author, String text) {
		Comment comment = new Comment();

		comment.setParent(parent);
		comment.setAuthor(author);
		comment.setAuthorName(author.getName());
		comment.setText(text);
		comment.setDateAndTime(DateAndTime.now());

		comment.persist();
		return comment;
	}

	@Override
	public boolean isEditable() {
		User currentUser = Scope.get().getComponent(User.class);
		if (isAuthorSet()) {
			if (!isAuthor(currentUser)) return false;
			// MGI edit. We don't care about limitting the edit time for comments. Make them editable forever.
			// if (getDateAndTime().getPeriodFromNow().abs().toHours() > 6) return false;
			// AScrumGwtEntity parent = (AScrumGwtEntity) getParent();
			// if (parent.getLatestComment() != this) return false;
		} else {
			// public comment
			Project project = Scope.get().getComponent(Project.class);
			if (!project.isScrumTeamMember(currentUser)) return false;
		}
		return true;
	}

	public static final Comparator<Comment> DATEANDTIME_COMPARATOR = new Comparator<Comment>() {

		@Override
		public int compare(Comment a, Comment b) {
			return a.getDateAndTime().compareTo(b.getDateAndTime());
		}
	};

	public static final Comparator<Comment> REVERSE_DATEANDTIME_COMPARATOR = new Comparator<Comment>() {

		@Override
		public int compare(Comment a, Comment b) {
			return b.getDateAndTime().compareTo(a.getDateAndTime());
		}
	};

}
