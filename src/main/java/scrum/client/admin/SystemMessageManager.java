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
package scrum.client.admin;

import ilarkesto.core.time.DateAndTime;
import ilarkesto.gwt.client.editor.ADateAndTimeEditorModel;
import ilarkesto.gwt.client.editor.ATextEditorModel;
import scrum.client.core.EventBus;

public class SystemMessageManager extends GSystemMessageManager {

	private SystemMessage systemMessage = new SystemMessage();

	public void updateMessage(SystemMessage systemMessage) {
		if (systemMessage == null) return;
		this.systemMessage = systemMessage;
		log.info("SystemMessage received:", systemMessage);
		EventBus.get().visibleDataChanged();
	}

	public void activateSystemMessage() {
		systemMessage.setActive(true);
		new UpdateSystemMessageServiceCall(systemMessage).execute();
		EventBus.get().visibleDataChanged();
	}

	public void deactivateSystemMessage() {
		systemMessage.setActive(false);
		new UpdateSystemMessageServiceCall(systemMessage).execute();
		EventBus.get().visibleDataChanged();
	}

	public SystemMessage getSystemMessage() {
		return systemMessage;
	}

	public ATextEditorModel systemMessageTextModel = new ATextEditorModel() {

		@Override
		public void setValue(String value) {
			systemMessage.setText(value);
		}

		@Override
		public String getValue() {
			return systemMessage.getText();
		}
	};

	public ADateAndTimeEditorModel systemMessageExpiresModel = new ADateAndTimeEditorModel() {

		@Override
		public DateAndTime getValue() {
			return systemMessage.getExpires();
		}

		@Override
		public void setValue(DateAndTime value) {
			systemMessage.setExpires(value);
		}

	};

}