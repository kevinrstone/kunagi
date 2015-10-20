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
package scrum.server.files;

import ilarkesto.base.PermissionDeniedException;
import ilarkesto.core.base.Str;
import ilarkesto.gwt.server.AUploadServlet;
import ilarkesto.io.IO;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import scrum.server.GwtConversation;
import scrum.server.ScrumWebApplication;
import scrum.server.WebSession;
import scrum.server.project.Project;

public class FileUploadServlet extends AUploadServlet {

	@Override
	protected String handleFiles(HttpServletRequest req, List<FileItem> sessionFiles) throws IOException {
		String projectId = req.getParameter("projectId");
		if (projectId == null) throw new RuntimeException("projectId == null");

		if (sessionFiles.size() != 1) throw new IllegalStateException("sessionFiles.size() == " + sessionFiles.size());

		ScrumWebApplication webApp = ScrumWebApplication.get();
		WebSession session = (WebSession) webApp.getWebSession(req);

		Project project = (Project) ScrumWebApplication.get().getDaoService().getById(projectId);
		if (!project.isVisibleFor(session.getUser())) throw new PermissionDeniedException();

		FileItem item = sessionFiles.get(0);
		String filename = getFilename(item.getName());
		java.io.File f = new java.io.File(project.getFileRepositoryPath() + "/" + filename);
		int count = 0;
		while (f.exists()) {
			count++;
			f = new java.io.File(project.getFileRepositoryPath() + "/" + insertSuffix(filename, count));
		}
		IO.copyDataToFile(item.getInputStream(), f);

		File file = webApp.getFileDao().postFile(f, project);
		for (GwtConversation conversation : webApp.getConversationsByProject(project, null)) {
			conversation.sendToClient(file);
		}
		return file.getReference();
	}

	private String insertSuffix(String name, int count) {
		int idx = name.lastIndexOf('.');
		if (idx > 0) return name.substring(0, idx) + "_" + count + name.substring(idx);
		return name + "_" + count;
	}

	private String getFilename(String name) {
		if (name == null) return "unnamed.bin";
		name = name.replace('\\', '/');
		name = Str.toFileCompatibleString(name);
		int idx = name.lastIndexOf('/');
		if (idx >= 0) return name.substring(idx + 1);
		return name;
	}

	@Override
	public void checkRequest(HttpServletRequest request) {
		ScrumWebApplication webApp = ScrumWebApplication.get();
		Integer maxFileSize = webApp.getSystemConfig().getMaxFileSize();
		maxSize = maxFileSize == null ? DEFAULT_REQUEST_LIMIT_KB : maxFileSize * 1024 * 1024;
		super.checkRequest(request);
	}

}
