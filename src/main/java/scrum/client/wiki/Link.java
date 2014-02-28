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
package scrum.client.wiki;

import ilarkesto.core.base.Str;

public class Link extends AWikiElement {

	private String href;
	private String label;

	public Link(String href, String label) {
		super();
		this.href = enhance(href);
		this.label = label;
	}

	public Link(String href) {
		this(href, Str.formatUrlAsLink(href));
	}

	@Override
	String toHtml(HtmlContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href=\"");
		sb.append(enhance(href));
		sb.append("\" target=\"_blank\">");
		sb.append(escapeHtml(label));
		sb.append("</a>");
		return sb.toString();
	}

	private String enhance(String s) {
		if (s.startsWith("http://")) return s;
		if (s.startsWith("https://")) return s;
		if (s.startsWith("ftp://")) return s;
		if (s.startsWith("mailto://")) return s;
		if (s.startsWith("apt://")) return s;
		if (s.startsWith("file://")) return s;
		if (s.startsWith("#")) return s;
		return "http://" + s;
	}

	public String getHref() {
		return href;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return "EntityReference(" + href + ")";
	}
}
