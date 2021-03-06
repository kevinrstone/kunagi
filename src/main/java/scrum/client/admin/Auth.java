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
package scrum.client.admin;

import scrum.client.ScrumScopeManager;

public class Auth extends GAuth {

	private User user;

	public void setUser(String userId) {
		user = GUser.getById(userId);
		log.info("User logged in:", user);
		ScrumScopeManager.createUserScope(user);
	}

	public void logout() {
		user = null;
	}

	public boolean isUserLoggedIn() {
		return user != null;
	}

	public User getUser() {
		return user;
	}

}
