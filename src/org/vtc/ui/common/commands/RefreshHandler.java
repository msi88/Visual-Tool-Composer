/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.vtc.ui.common.commands;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.vtc.ui.common.commands.listeners.Refreshable;

/**
 * The RefreshHandler class.
 * 
 * @author Michael Sieber
 */
public class RefreshHandler extends AbstractHandler {
	private static Logger LOGGER = Logger.getLogger(RefreshHandler.class);

	/*
	 * {@inheritDoc}
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		LOGGER.debug("Refresh called.");

		// get the selected view
		IWorkbenchPart active = HandlerUtil.getActivePart(event);

		// refresh if the active part is a refreshable object
		if (active instanceof Refreshable) {
			LOGGER.debug("Refreshing " + active);
			((Refreshable) active).refresh();
		}

		return null;
	}
}
