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
package scrum.client.project;

import ilarkesto.core.base.Args;
import ilarkesto.core.base.Utl;
import ilarkesto.core.scope.Scope;
import ilarkesto.gwt.client.HyperlinkWidget;

import java.util.Comparator;

import scrum.client.ScrumGwt;
import scrum.client.admin.Auth;
import scrum.client.collaboration.ForumSupport;
import scrum.client.common.LabelSupport;
import scrum.client.common.ReferenceSupport;
import scrum.client.common.ShowEntityAction;
import scrum.client.issues.Issue;

import com.google.gwt.user.client.ui.Widget;

public class Quality extends GQuality implements ReferenceSupport, LabelSupport, ForumSupport, Comparable<Quality> {

	public static final String REFERENCE_PREFIX = "qlt";

	public static Quality post(Project project) {
		Args.assertNotNull(project, "project");

		Quality quality = new Quality();
		quality.setLabel("");
		quality.setProject(project);

		quality.persist();
		return quality;
	}

	public static Quality post(Issue issue) {
		Quality quality = post(issue.getProject());
		quality.setLabel(issue.getLabel());
		quality.setDescription(issue.getDescription());
		return quality;
	}

	@Override
	public String getReference() {
		return REFERENCE_PREFIX + getNumber();
	}

	@Override
	public String toHtml() {
		return ScrumGwt.toHtml(this, getLabel());
	}

	@Override
	public String asString() {
		return getReference() + " " + getLabel();
	}

	@Override
	public int compareTo(Quality o) {
		return Utl.compare(getLabel(), o.getLabel());
	}

	@Override
	public boolean isEditable() {
		if (!getProject().isProductOwner(Scope.get().getComponent(Auth.class).getUser())) return false;
		return true;
	}

	@Override
	public Widget createForumItemWidget() {
		return new HyperlinkWidget(new ShowEntityAction(QualityBacklogWidget.class, this, getLabel()));
	}

	public static final Comparator<Quality> LABEL_COMPARATOR = new Comparator<Quality>() {

		@Override
		public int compare(Quality a, Quality b) {
			return Utl.compare(a.getLabel(), b.getLabel());
		}
	};

	@Override
	protected LabelModel createLabelModel() {
		return new LabelModel() {

			@Override
			public boolean isSwitchToEditModeIfNull() {
				return true;
			}
		};

	}

}
