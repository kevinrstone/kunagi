package scrum.server.sprint;

import ilarkesto.base.Tm;
import ilarkesto.pdf.APdfContainerElement;
import ilarkesto.pdf.ARow;
import ilarkesto.pdf.ATable;
import ilarkesto.pdf.FieldList;
import ilarkesto.pdf.FontStyle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import scrum.server.project.Project;
import scrum.server.project.Requirement;

/*
 * A custom PDF report specifically for MGI (team scrum dog)
 */
public class SprintBacklogPdfCreatorPlus extends SprintBacklogPdfCreator {

	public SprintBacklogPdfCreatorPlus(Project project) {
		super(project);
	}

	@Override
	protected void build(APdfContainerElement pdf) {
		Sprint sprint = project.getCurrentSprint();

		reportHeader(pdf, "Sprint Backlog", project.getLabel());

		pdf.nl();
		FieldList fields = pdf.fieldList().setLabelFontStyle(fieldLabelFont);
		fields.field("Sprint").paragraph().text(sprint.getReference() + " ", referenceFont).text(sprint.getLabel());
		fields.field("Period").text(
			Tm.FORMAT_SHORTWEEKDAY_SHORTMONTH_DAY.format(sprint.getBegin()) + "  -  "
					+ Tm.FORMAT_SHORTWEEKDAY_SHORTMONTH_DAY.format(sprint.getEnd()) + "   (" + sprint.getLengthInDays()
					+ " days)");
		// add retro notes
		fields.field("Retrospective Notes").text(sprint.getRetrospectiveNote());

		pdf.nl();
		List<Requirement> requirements = new ArrayList<Requirement>(sprint.getRequirements());
		Collections.sort(requirements, sprint.getRequirementsOrderComparator());
		for (Requirement req : requirements) {
			requirement(pdf, req, req.getOpenTasksAsList(), req.getClosedTasksAsList());
		}
	}

	@Override
	protected void requirement(APdfContainerElement pdf, Requirement req, Collection<Task> openTasks,
			Collection<Task> closedTasks, Sprint pastSprint) {
		pdf.nl();

		ATable table = pdf.table(6, 20);

		ARow rowHeader = table.row().setDefaultBackgroundColor(Color.LIGHT_GRAY);
		rowHeader.cell().setFontStyle(referenceFont)
				.text(req.getReference() + " - " + req.getEstimatedWorkAsString() + "pts");
		rowHeader.cell().setFontStyle(new FontStyle(defaultFont).setBold(true)).text(req.getHistoryLabel(pastSprint));

		richtextRow(table, "Story description", req.getDescription());
		richtextRow(table, "Acceptance tests", req.getTestDescription());

		table.createCellBorders(Color.GRAY, 0.2f);
	}
}
