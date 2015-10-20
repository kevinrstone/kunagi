/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package scrum.client.core;

import ilarkesto.core.scope.Scope;

import scrum.client.collaboration.UsersStatus;
import scrum.client.communication.Pinger;
import scrum.client.files.File;
import scrum.client.files.Uploader;
import scrum.client.workspace.Ui;

public class EventBus {

	private static final EventBus INSTANCE = new EventBus();

	public static EventBus get() {
		return INSTANCE;
	}

	public void blockCollapsed(Object object) {
		Pinger pinger = Scope.get().getComponent(Pinger.class);
		if (pinger != null) pinger.onBlockCollapsed(object);

		UsersStatus userStatus = Scope.get().getComponent(UsersStatus.class);
		if (userStatus != null) userStatus.onBlockCollapsed(object);
	}

	public void blockExpanded(Object object) {
		Pinger pinger = Scope.get().getComponent(Pinger.class);
		if (pinger != null) pinger.onBlockExpanded(object);

		UsersStatus usersStatus = Scope.get().getComponent(UsersStatus.class);
		if (usersStatus != null) usersStatus.onBlockExpanded(object);
	}

	public void visibleDataChanged() {
		Scope.get().getComponent(Ui.class).onVisibleDataChanged();
	}

	public void fileReceived(File file) {
		Uploader uploader = Scope.get().getComponent(Uploader.class);
		if (uploader != null) uploader.onFileReceived(file);
	}

}
