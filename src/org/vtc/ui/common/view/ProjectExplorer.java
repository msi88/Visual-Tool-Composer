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

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.vtc.Activator;
import org.vtc.core.model.filebrowser.FileContentProvider;
import org.vtc.core.model.filebrowser.FileLabelProvider;
import org.vtc.ui.common.preferences.PreferenceName;

/**
 * The ProjectExplorer which shows all stored projects.
 * 
 * @author Michael Sieber
 */
public class ProjectExplorer extends ViewPart {
	private static Logger LOGGER = Logger.getLogger(ProjectExplorer.class);
	private TreeViewer _viewer;

	/*
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(Composite parent) {
		_viewer =
				new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
						| SWT.V_SCROLL);

		// get the working directory
		String workDir =
				Activator.getDefault().getPreferenceStore()
						.getString(PreferenceName.WORK_DIR);

		// only show tree if a working dir is defined
		if (workDir != null && !workDir.equals("")) {

			// get the dir as file
			File work = new File(workDir);

			if (work != null) {

				// initialize the tree viewer
				_viewer.setContentProvider(new FileContentProvider());
				_viewer.setLabelProvider(new FileLabelProvider());
				_viewer.setInput(work.listFiles());

				// setup listener for tree traversal
				_viewer.addOpenListener(new IOpenListener() {

					@Override
					public void open(OpenEvent event) {
						IStructuredSelection selection =
								(IStructuredSelection) event
										.getSelection();

						File file = (File) selection.getFirstElement();
						if (Desktop.isDesktopSupported()) {
							Desktop desktop = Desktop.getDesktop();
							if (desktop.isSupported(Desktop.Action.OPEN)) {
								try {
									desktop.open(file);
								} catch (IOException e) {
									LOGGER.info("Error opening project tree.",
											e);
								}
							}
						}
					}
				});
			}
		}
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
		if (_viewer != null) {
			_viewer.getControl().setFocus();
		}
	}
}
