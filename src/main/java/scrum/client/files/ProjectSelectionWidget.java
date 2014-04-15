/*
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
package scrum.client.files;

import ilarkesto.core.base.Str;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import scrum.client.ScrumScopeManager;
import scrum.client.common.AScrumWidget;
import scrum.client.project.Project;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProjectSelectionWidget extends AScrumWidget {

	private Button button = new Button();

	private DialogBox dialog;

	protected Project selectedProject;

	private Callback callback;

	/*
	 * Provide a new project for a requirement
	 */

	public ProjectSelectionWidget(Callback callback) {
		this.callback = callback;
	}

	@Override
	protected Widget onInitialization() {
		CaptionPanel outerPanel = new CaptionPanel("Select destination Project");
		Panel panel = new VerticalPanel();
		outerPanel.add(panel);

		List<Project> projects = getDao().getProjects();
		Collections.sort(projects, new Comparator<Project>() {

			@Override
			public int compare(Project project1, Project project2) {
				return project1.getLabel().compareTo(project2.getLabel());
			}
		});

		ListBox projectList = new ListBox();
		projectList.setVisibleItemCount(10);
		projectList.addStyleName("ProjectList");

		projectList.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				ListBox lb = (ListBox) event.getSource();
				String projectId = lb.getValue((lb.getSelectedIndex()));
				if (Str.isBlank(projectId)) return;
				selectedProject = getDao().getProject(projectId);
			}

		});

		projectList.addStyleName("listAvailableProjects");
		for (Project project : projects) {
			if (project.equals(ScrumScopeManager.getProject())) continue;
			// if (!project.isProductOwner(getCurrentUser())) continue;
			projectList.addItem(project.getLabel(), project.getId());
		}

		Button btnSelect = new Button("Apply", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (selectedProject != null) {
					callback.onProjectSelected(selectedProject);
				}

				dialog.hide();
			}
		});
		btnSelect.setStyleName("btnSelectProject");

		Button btnCancel = new Button("Cancel", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				dialog.hide();
			}
		});
		btnCancel.setStyleName("btnCancelSelectProject");

		Panel pnlButtons = new HorizontalPanel();
		pnlButtons.setStyleName("pnlSelectProject");
		pnlButtons.add(btnSelect);
		pnlButtons.add(btnCancel);

		panel.add(projectList);
		panel.add(pnlButtons);

		return outerPanel;
	}

	@Override
	protected void onUpdate() {
		super.onUpdate();
		button.setVisible(false);
	}

	public void showDialog() {

		dialog = new DialogBox(true, true);
		dialog.setAnimationEnabled(true);
		dialog.setWidget(update());

		dialog.center();
		dialog.show();

	}

	public DialogBox getDialog() {
		return dialog;
	}

	public static interface Callback {

		void onProjectSelected(Project project);

	}
}
