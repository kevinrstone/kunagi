package scrum.server.sprint;

import ilarkesto.testng.ATest;

import org.testng.annotations.Test;

public class SprintHistoryHelperTest extends ATest {

	@Test
	public void parseLines() {
		assertEquals(scrum.client.sprint.SprintHistoryHelper.parseLines("a"), ilarkesto.core.base.Utl.toList("a"));
		assertEquals(scrum.client.sprint.SprintHistoryHelper.parseLines("line1\nline2"), ilarkesto.core.base.Utl.toList("line1", "line2"));
		assertEquals(scrum.client.sprint.SprintHistoryHelper.parseLines("line1\n\nline3"), ilarkesto.core.base.Utl.toList("line1", "", "line3"));
		assertEquals(scrum.client.sprint.SprintHistoryHelper.parseLines(""), ilarkesto.core.base.Utl.toList(""));
	}

}
