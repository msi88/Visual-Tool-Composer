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

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.vtc.core.model.componentbrowser.BeanModel;
import org.vtc.core.model.componentbrowser.ComponentContentProvider;
import org.vtc.core.model.componentbrowser.ComponentLabelProvider;
import org.vtc.ui.common.commands.listeners.Refreshable;

/**
 * The ComponentExplorer shows all available bean components.
 * 
 * @author Michael Sieber
 */
public class ComponentExplorer extends ViewPart implements Refreshable {
	private final String _dropinDir = "dropin";
	private TreeViewer _viewer;

	/*
	 * {@inheritDoc}
	 */
	@Override
	public void createPartControl(Composite parent) {
		_viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);

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

		refresh();

		// set selection provider
		getSite().setSelectionProvider(_viewer);
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
		if (_viewer != null) {
			_viewer.setContentProvider(new ComponentContentProvider());
			_viewer.setLabelProvider(new ComponentLabelProvider());
			_viewer.setInput(new BeanModel(_dropinDir));
		}
	}
}
