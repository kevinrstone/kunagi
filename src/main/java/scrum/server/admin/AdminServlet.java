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

import ilarkesto.core.base.Bytes;
import ilarkesto.ui.web.HtmlBuilder;
import ilarkesto.webapp.ApplicationStatusInfoBuilder;
import ilarkesto.webapp.RequestWrapper;

import java.io.File;
import java.io.IOException;

import scrum.server.WebSession;
import scrum.server.common.AKunagiServlet;

public class AdminServlet extends AKunagiServlet {

	@Override
	protected void onRequest(RequestWrapper<WebSession> req) throws IOException {
		tokenLogin(req);

		User user = req.getSession().getUser();
		if (user == null) {
			redirectToLogin(req);
			return;
		}

		if (!user.isAdmin()) {
			req.sendErrorForbidden();
			return;
		}

		HtmlBuilder html = createDefaultHtmlWithHeader(req, "Administration");

		html.startBODY().setStyle("font-size: 10px");

		adminLinks(html, req);
		version(html);

		new ApplicationStatusInfoBuilder().buildAll(html);

		actions(html);

		html.BR();
		html.BR();

		adminLinks(html, req);

		html.endBODY();

		html.endHTML();
		html.flush();

	}

	private void version(HtmlBuilder html) {
		sectionHeader(html, "Application");
		startTABLE(html);
		keyValueRow(html, "Release", applicationInfo.getRelease());
		keyValueRow(html, "Build", applicationInfo.getBuild());
		keyValueRow(html, "DataPath", applicationInfo.getDataPath());
		File dir = new File(applicationInfo.getDataPath());
		keyValueRow(html, "Free disk space", new Bytes(dir.getFreeSpace()).toRoundedString());

		endTABLE(html);
	}

	private void actions(HtmlBuilder html) {
		sectionHeader(html, "Functions");
		html.text("[ ");
		html.A("backup", "Create backup");
		html.text(" ]");

		html.text("[ ");
		html.A("shutdown", "Shutdown application now");
		html.text(" ]");

		html.text("[ ");
		html.A("shutdown?delay=5", "Shutdown application in 5 minutes");
		html.text(" ]");
	}

}
