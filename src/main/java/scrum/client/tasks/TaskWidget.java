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
package scrum.client.tasks;

import ilarkesto.gwt.client.AFieldValueWidget;
import ilarkesto.gwt.client.AIntegerViewEditWidget;
import ilarkesto.gwt.client.AMultiSelectionViewEditWidget;
import ilarkesto.gwt.client.Gwt;
import ilarkesto.gwt.client.TableBuilder;

import java.util.List;

import scrum.client.ScrumGwt;
import scrum.client.collaboration.CommentsWidget;
import scrum.client.common.AScrumWidget;
import scrum.client.impediments.Impediment;
import scrum.client.journal.ChangeHistoryWidget;
import scrum.client.sprint.Task;

import com.google.gwt.user.client.ui.Widget;

public class TaskWidget extends AScrumWidget {

	private Task task;

	public TaskWidget(Task task) {
		this.task = task;
	}

	@Override
	protected Widget onInitialization() {
		boolean inCurrentSprint = task.isInCurrentSprint();

		TableBuilder tb = ScrumGwt.createFieldTable();

		if (inCurrentSprint) tb.addFieldRow("Label", task.getLabelModel(), 3);

		tb.addFieldRow("Description", task.getDescriptionModel(), 3);

		if (inCurrentSprint) appendCurrentSprintFields(tb);

		Widget comments = inCurrentSprint ? ScrumGwt.createEmoticonsAndComments(task) : new CommentsWidget(task);
		ChangeHistoryWidget changeHistory = new ChangeHistoryWidget(task);
		return Gwt.createFlowPanel(tb.createTable(), comments, Gwt.createSpacer(1, 10), changeHistory);
	}

	private void appendCurrentSprintFields(TableBuilder tb) {
		tb.addField("Burned", new AIntegerViewEditWidget() {

			@Override
			protected void onIntegerViewerUpdate() {
				setViewerValue(task.getBurnedWork());
			}

			@Override
			protected void onEditorUpdate() {
				setEditorValue(task.getBurnedWork());
			}

			@Override
			protected void onEditorSubmit() {
				Integer value = getEditorValue(0);
				if (value == null) value = 0;
				int previous = task.getBurnedWork();
				int diff = value - previous;
				task.setBurnedWork(value);
				task.adjustRemainingWork(diff);
			}

			@Override
			protected void onMinusClicked() {
				boolean decreased = task.decrementBurnedWork();
				if (!decreased) return;
				task.adjustRemainingWork(-1);
			}

			@Override
			protected void onPlusClicked() {
				task.incrementBurnedWork();
				task.adjustRemainingWork(1);
			}

			@Override
			public boolean isEditable() {
				return task.isEditable();
			}
		});

		tb.addFieldRow("Remaining", new TaskRemainingWorkWidget(task));

		tb.addFieldRow("Owner", new AFieldValueWidget() {

			@Override
			protected void onUpdate() {
				setText(task.getOwner() == null ? null : task.getOwner().getName());
			}
		}, 3);

		tb.addFieldRow("Impediments", new AMultiSelectionViewEditWidget<Impediment>() {

			@Override
			protected void onViewerUpdate() {
				setViewerItemsAsHtml(task.getImpediments());
			}

			@Override
			protected void onEditorUpdate() {
				List<Impediment> impediments = task.getProject().getOpenImpediments();
				setEditorItems(impediments);
				setEditorSelectedItems(task.getImpediments());
			}

			@Override
			protected void onEditorSubmit() {
				task.setImpediments(getEditorSelectedItems());
			}

			@Override
			public boolean isEditable() {
				return task.isEditable();
			}

			@Override
			public String getTooltip() {
				return "Impediments, which impede this task.";
			}
		});

	}
}
