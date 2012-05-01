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
package scrum.client.project;

import java.util.List;

import scrum.client.common.AScrumWidget;
import scrum.client.common.SparklineChartWidget;

import com.google.gwt.user.client.ui.Widget;

public class VelocityHistoryWidget extends AScrumWidget {

	@Override
	protected Widget onInitialization() {
		List<Float> velocities = getCurrentProject().getVelocitiesFromPreviousSprints(12);
		return new SparklineChartWidget(14, 5, velocities);
	}

}
