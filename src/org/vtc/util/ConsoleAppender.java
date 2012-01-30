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
package org.vtc.util;

import java.io.IOException;
import java.io.PrintStream;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.vtc.ui.common.view.Console;

/**
 * The ConsoleAppender which appends all log4j messages to the {@link Console}.
 * 
 * @author Michael Sieber
 */
public class ConsoleAppender extends AppenderSkeleton {
	private static Logger LOGGER = Logger.getLogger(ConsoleAppender.class);
	private static PrintStream OUT;

	static {

		// get the output stream from the console view
		OUT = Console.getOutputStream();
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		OUT.close();
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public boolean requiresLayout() {
		return false;
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	protected void append(LoggingEvent event) {
		try {
			// format the message
			String message = layout.format(event);

			// write the message
			OUT.write(message.getBytes());
		} catch (IOException e) {
			LOGGER.debug("Unable to write message to output stream.", e);
		}
	}
}
