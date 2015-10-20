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
package scrum.client.sprint;

import scrum.client.common.AScrumAction;
import scrum.client.common.TooltipBuilder;
import scrum.client.core.EventBus;

public class LoadSprintHistoryAction extends AScrumAction {

	private Sprint sprint;

	public LoadSprintHistoryAction(Sprint sprint) {
		super();
		this.sprint = sprint;
	}

	@Override
	protected void updateTooltip(TooltipBuilder tb) {
		tb.setText("Request details for this sprints history");
	}

	@Override
	protected void onExecute() {
		new RequestHistorySprintServiceCall(sprint.getId()).execute(new Runnable() {

			@Override
			public void run() {
				sprint.historyLoaded = true;
				EventBus.get().visibleDataChanged();
			}
		});
	}

	@Override
	public String getLabel() {
		return "Load Sprint history details";
	}

}
