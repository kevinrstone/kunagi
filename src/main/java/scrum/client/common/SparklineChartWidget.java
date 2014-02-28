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
package scrum.client.common;

import ilarkesto.core.base.Str;
import ilarkesto.gwt.client.AWidget;
import ilarkesto.gwt.client.Gwt;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class SparklineChartWidget extends AWidget {

	private int height;
	private int barWidth;
	private List<Item> items = new ArrayList<SparklineChartWidget.Item>();
	private String barBackgroundColor = "#D6E4E1";
	private String barColor = "#667B99";
	private float heightFactor;
	private String suffix;
	private String suffixTitle;

	public SparklineChartWidget(int height, int barWidth) {
		super();
		this.height = height;
		this.barWidth = barWidth;
	}

	public void addItem(float value, String text) {
		items.add(new Item(value, text));
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void setSuffixTitle(String suffixTitle) {
		this.suffixTitle = suffixTitle;
	}

	@Override
	protected Widget onInitialization() {
		FlowPanel container = new FlowPanel();

		heightFactor = computeFactor();

		for (Item item : items) {
			container.add(createBar(item));
		}

		if (!Str.isBlank(suffix)) {
			SimplePanel spacer = Gwt.createSpacer(10, height);
			spacer.getElement().getStyle().setFloat(Style.Float.LEFT);
			container.add(spacer);

			Label label = new Label(suffix);
			label.setTitle(suffixTitle);

			container.add(label);
		}

		return container;
	}

	private Widget createBar(Item item) {
		FlowPanel bar = new FlowPanel();
		int barheight = (int) (item.value * heightFactor);
		// if (barheight < 3) barheight = 3;
		bar.setHeight(barheight + "px");
		bar.setWidth(barWidth + "px");
		bar.getElement().getStyle().setBackgroundColor(barColor);
		bar.getElement().getStyle().setMarginTop(height - barheight, Unit.PX);

		FlowPanel frame = new FlowPanel();
		frame.add(bar);
		frame.setHeight(height + "px");
		frame.setWidth(barWidth + "px");
		frame.getElement().setTitle(item.text);
		frame.getElement().getStyle().setMarginRight(2, Unit.PX);
		frame.getElement().getStyle().setFloat(Style.Float.LEFT);
		frame.getElement().getStyle().setBackgroundColor(barBackgroundColor);

		return frame;
	}

	private float computeFactor() {
		float max = 0;
		for (Item item : items) {
			if (item.value > max) max = item.value;
		}
		return height / max;
	}

	public static class Item {

		private float value;
		private String text;

		public Item(float value, String text) {
			super();
			this.value = value;
			this.text = text;
		}

	}

}
