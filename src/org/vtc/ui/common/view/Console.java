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
package org.vtc.ui.common.view;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

/**
 * The Console which shows all outputs from System.out and System.err.
 * 
 * @author Michael Sieber
 */
public class Console extends ViewPart {
	private static final PrintStream OUTSTREAM;
	private static Text CONSOLE;

	static {
		OUTSTREAM =
				new PrintStream(new ConsoleStream(new ByteArrayOutputStream()));
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(Composite parent) {
		// create the console view
		CONSOLE = new Text(parent, SWT.V_SCROLL);
		CONSOLE.setEditable(false);
		CONSOLE.setBackground(parent.getDisplay().getSystemColor(
				SWT.COLOR_WHITE));

		// setup streams
		setUp();
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
		CONSOLE.setFocus();
	}

	/**
	 * Set up the console streams.
	 */
	private void setUp() {

		// configure System.out and System.err
		System.setOut(OUTSTREAM);
		System.setErr(OUTSTREAM);
	}

	/**
	 * Clear the console window.
	 */
	public void clear() {
		if (CONSOLE != null) {
			CONSOLE.setText("");
		}
	}

	/**
	 * Determine wether the console is empty or not.
	 * 
	 * @return True if the console is empty and therefore has no text in it
	 */
	public boolean isEmpty() {
		if (CONSOLE != null) {
			return CONSOLE.getText().equals("");
		}

		return true;
	}

	/**
	 * Get the output stream to which all messages will be written.
	 * 
	 * @return The print stream for all messages
	 */
	public static PrintStream getOutputStream() {
		return OUTSTREAM;
	}

	/**
	 * The ConsoleStream which prints the console output to the console window.
	 * 
	 * @author Michael Sieber
	 */
	private static class ConsoleStream extends FilterOutputStream {

		/**
		 * Create a new ConsoleStream.
		 * 
		 * @param stream The output stream
		 */
		public ConsoleStream(OutputStream stream) {
			super(stream);
		}

		/*
		 * {@inheritDoc}
		 */
		@Override
		public void write(byte[] b) throws IOException {
			write(b, 0, b.length);
		}

		/*
		 * {@inheritDoc}
		 */
		@Override
		public void write(byte[] b, int off, int len) throws IOException {

			// get the string and append it to the console view
			String out = new String(b, off, len);

			if (CONSOLE != null) {
				CONSOLE.append(out);
			}
		}

	}
}
