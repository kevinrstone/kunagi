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
package scrum.client.journal;

import ilarkesto.core.base.Args;
import ilarkesto.core.time.DateAndTime;

import java.util.Comparator;

import scrum.client.collaboration.Wiki;
import scrum.client.project.Project;

public class ProjectEvent extends GProjectEvent {

	public static ProjectEvent post(Project project, String label) {
		Args.assertNotNull(project, "project");
		Args.assertNotBlank(label, "label");

		ProjectEvent event = new ProjectEvent();
		event.setDateAndTime(DateAndTime.now());
		event.setProject(project);
		event.setLabel(label);

		event.persist();
		return event;
	}

	@Override
	public boolean isEditable() {
		return false;
	}

	@Override
	public String toHtml() {
		return Wiki.toHtml(getLabel());
	}

	@Override
	public String asString() {
		return getLabel();
	}

	public static final Comparator<ProjectEvent> DATE_AND_TIME_COMPARATOR = new Comparator<ProjectEvent>() {

		@Override
		public int compare(ProjectEvent a, ProjectEvent b) {
			return b.getDateAndTime().compareTo(a.getDateAndTime());
		}
	};

}
