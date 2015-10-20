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
package scrum.server.admin;

import ilarkesto.auth.AuthenticationContext;
import ilarkesto.core.base.Utl;
import ilarkesto.core.logging.Log;

import java.util.Set;

import scrum.server.ScrumWebApplication;

public class KunagiAuthenticationContext implements AuthenticationContext<User> {

	private static final Log log = Log.get(KunagiAuthenticationContext.class);

	@Override
	public String getPasswordHash(User user) {
		return user.getPassword();
	}

	@Override
	public String getPasswordSalt(User user) {
		return user.getPasswordSalt();
	}

	@Override
	public void setPasswordSalt(User user, String passwordSalt) {
		user.setPasswordSalt(passwordSalt);
	}

	@Override
	public void setPasswordHash(User user, String passwordHash) {
		user.setPassword(passwordHash);
	}

	@Override
	public void passwordReset(User user) {
		log.info("Password reset:", user);
	}

	@Override
	public void passwordChanged(User user) {
		log.info("Password changed:", user);
	}

	@Override
	public Set<String> getUsersKnownStrings(User user) {
		return Utl.toSet(user.getName(), user.getFullName(), user.getEmail());
	}

	@Override
	public String getDefaultPassword(User user) {
		String defaultPassword = ScrumWebApplication.get().getSystemConfig().getDefaultUserPassword();
		return defaultPassword;
	}

	@Override
	public String getNewPasswordVeto(User user, String password) {
		return null;
	}

}
