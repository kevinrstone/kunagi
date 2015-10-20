package scrum.server.sprint;

import ilarkesto.core.fp.Predicate;

import java.util.Set;

import scrum.server.project.Project;

public class SprintReportDao extends GSprintReportDao {

	public SprintReport postSprintReport(Sprint sprint) {
		SprintReport report = newEntityInstance();
		report.setSprint(sprint);

		persist(report);
		return report;
	}

	public Set<SprintReport> getSprintReportsByProject(final Project project) {
		return getEntities(new Predicate<SprintReport>() {

			@Override
			public boolean test(SprintReport e) {
				return e.getSprint().isProject(project);
			}
		});
	}

}