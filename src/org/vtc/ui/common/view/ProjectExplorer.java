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

import java.io.File;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.vtc.Activator;
import org.vtc.core.model.editor.EditorInput;
import org.vtc.core.model.filebrowser.FileContentProvider;
import org.vtc.core.model.filebrowser.FileLabelProvider;
import org.vtc.ui.common.commands.listeners.Refreshable;
import org.vtc.ui.common.preferences.PreferenceName;

/**
 * The ProjectExplorer which shows all stored projects.
 * 
 * @author Michael Sieber
 */
public class ProjectExplorer extends ViewPart implements Refreshable {
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

		refresh();

		// listener for expanding an element on double click
		_viewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection s =
						(IStructuredSelection) event.getSelection();
				Object element = s.getFirstElement();
				if (_viewer.isExpandable(element)) {
					_viewer.setExpandedState(element,
							!_viewer.getExpandedState(element));
				}

			}
		});

		// setup listener for tree traversal
		_viewer.addOpenListener(new IOpenListener() {

			@Override
			public void open(OpenEvent event) {
				IStructuredSelection selection =
						(IStructuredSelection) event
								.getSelection();

				// open all selected files
				Iterator<?> it = selection.iterator();
				while (it.hasNext()) {
					Object tmp = it.next();
					if (tmp instanceof File) {
						File file = (File) tmp;

						// only open files, not directories
						if (!file.isDirectory()) {
							IWorkbenchPage page =
									PlatformUI.getWorkbench()
											.getActiveWorkbenchWindow()
											.getActivePage();
							try {
								page.openEditor(new EditorInput(file),
										"org.vtc.ui.common.editor.VTCEditor");
							} catch (PartInitException e) {
								LOGGER.error("Unable to open file.", e);
							}
						}
					}
				}
			}
		});

		// set selection provider
		getSite().setSelectionProvider(_viewer);

		// setup the preference change listener for the working directory
		// property
		Activator.getDefault().getPreferenceStore()
				.addPropertyChangeListener(new IPropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent event) {
						if (event.getProperty() == PreferenceName.WORK_DIR) {
							String dir = event.getNewValue().toString();
							update(dir);
						}
					}
				});
	}

	/**
	 * Update the project directory tree.
	 * 
	 * @param dir The current directory
	 */
	protected void update(String dir) {
		// only show tree if a working dir is defined
		if (dir != null && !dir.equals("")) {

			// get the dir as file
			File work = new File(dir);

			if (work != null) {

				// initialize the tree viewer
				_viewer.setContentProvider(new FileContentProvider());
				_viewer.setLabelProvider(new FileLabelProvider());
				_viewer.setInput(work.listFiles());
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

	/*
	 * {@inheritDoc}
	 */
	@Override
	public void refresh() {
		// get the working directory and update the tree
		String workDir =
				Activator.getDefault().getPreferenceStore()
						.getString(PreferenceName.WORK_DIR);

		update(workDir);
	}
}
