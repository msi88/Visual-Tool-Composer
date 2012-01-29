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
package org.vtc.perspective.view;

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
	private Text _console;

	/*
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(Composite parent) {
		// create the console view
		_console = new Text(parent, SWT.V_SCROLL);
		_console.setEditable(false);
		_console.setBackground(parent.getDisplay().getSystemColor(
				SWT.COLOR_WHITE));

		// setup streams
		setUp();
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	/**
	 * Set up the console streams.
	 */
	private void setUp() {
		PrintStream outStream =
				new PrintStream(new ConsoleStream(new ByteArrayOutputStream()));

		// configure System.out and System.err
		System.setOut(outStream);
		System.setErr(outStream);
	}

	/**
	 * The ConsoleStream which prints the console output to the console window.
	 * 
	 * @author Michael Sieber
	 */
	private class ConsoleStream extends FilterOutputStream {

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
			_console.append(out);
		}

	}
}
