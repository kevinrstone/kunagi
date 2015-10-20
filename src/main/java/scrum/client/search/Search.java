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
package scrum.client.search;

import ilarkesto.core.base.Str;
import ilarkesto.core.search.SearchText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import scrum.client.common.AScrumGwtEntity;

public class Search extends GSearch {

	private SearchResults results = new SearchResults();
	private SearchResultsWidget resultsWidget;
	private String searchText;

	public void search(String text) {
		if (text != null) text = text.toLowerCase();
		this.searchText = text;
		log.info("Searching:", searchText);
		results.clear();
		if (Str.isBlank(searchText)) return;

		new SearchServiceCall(searchText).execute(new Runnable() {

			@Override
			public void run() {
				searchClient(searchText);
				updateResultsWidget();
			}
		});

		navigator.gotoCurrentProjectSearch();

		searchClient(searchText);
	}

	private void searchClient(String text) {
		SearchText searchText = new SearchText(text);

		log.info(project.getRequirements().size(), " stories to search for", searchText.toString());

		results.addEntities(getMatching(project.getRequirements(), searchText));
		results.addEntities(getMatching(project.getQualitys(), searchText));
		results.addEntities(getMatching(project.getTasks(), searchText));
		results.addEntities(getMatching(project.getWikipages(), searchText));
		results.addEntities(getMatching(project.getFiles(), searchText));
		results.addEntities(getMatching(project.getIssues(), searchText));
		results.addEntities(getMatching(project.getImpediments(), searchText));
		results.addEntities(getMatching(project.getRisks(), searchText));

		log.info("Client search results:", results.getCount());
	}

	private <T extends AScrumGwtEntity> List<T> getMatching(Collection entities, SearchText searchText) {
		return searchText.collectMatching(new ArrayList(), entities);
	}

	public SearchResults getResults() {
		return results;
	}

	public void updateResultsWidget() {
		getResultsWidget().update();
	}

	public SearchResultsWidget getResultsWidget() {
		if (resultsWidget == null) resultsWidget = new SearchResultsWidget();
		return resultsWidget;
	}

}