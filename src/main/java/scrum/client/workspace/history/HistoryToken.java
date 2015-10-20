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
package scrum.client.workspace.history;

import ilarkesto.core.base.Utl;
import ilarkesto.core.logging.Log;
import ilarkesto.gwt.client.AGwtEntity;
import ilarkesto.gwt.client.Gwt;

import java.util.Map;

import scrum.client.workspace.Navigator;

import com.google.gwt.user.client.History;

public class HistoryToken {

	private static Log log = Log.get(HistoryToken.class);

	public static final String START_PAGE = "Dashboard";
	public static final char SEPARATOR = Gwt.HISTORY_TOKEN_SEPARATOR;

	private Navigator observer; // Navigator

	private String projectId;
	private String page;
	private String entityId;
	private int toggle;

	public HistoryToken(Navigator observer) {
		this.observer = observer;
	}

	public void evalHistoryToken(String token) {
		log.debug("Evaluating history token:", token, "| current state:", projectId, page, entityId);
		Map<String, String> props = Gwt.parseHistoryToken(token);

		String oldProjectId = projectId;
		String oldPage = page;
		String oldEntityId = entityId;
		int oldToggle = 0;

		projectId = props.get("project");
		page = props.get("page");
		entityId = props.get("entity");
		String sToggle = props.get("toggle");
		toggle = sToggle != null ? Integer.parseInt(sToggle) : 0;

		if (projectId == null || !Utl.equals(oldProjectId, projectId)) {
			observer.onProjectChanged(projectId);
			return;
		} else if (!Utl.equals(oldPage, page) || !Utl.equals(oldEntityId, entityId) || toggle != oldToggle) {
			observer.onPageOrEntityChanged();
		} else {
			log.debug("Nothing changed", oldProjectId, oldPage, oldEntityId);
		}
	}

	// TODO das muss weg
	public void updatePageAndEntity(String page, AGwtEntity entity, boolean issueEvent) {
		this.page = page;
		entityId = entity == null ? null : entity.getId();
		String token = Navigator.createToken(projectId, page, entityId);
		log.debug("updatePageAndEntity", page, entity, issueEvent, "->", token);
		if (token.equals(History.getToken())) return;
		History.newItem(token, issueEvent);
	}

	public boolean isProjectIdSet() {
		return projectId != null;
	}

	public String getPage() {
		return page;
	}

	public String getEntityId() {
		return entityId;
	}

	public boolean isToggle() {
		return toggle != 0;
	}

}
