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
package org.vtc.core.model.componentbrowser;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.vtc.core.model.componentbrowser.util.Group;

/**
 * The ComponentContentProvider class.
 * 
 * @author Michael Sieber
 */
public class ComponentContentProvider implements ITreeContentProvider {
	private BeanModel _model;

	/*
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		// nothing to do here
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		_model = (BeanModel) newInput;
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		return _model.getComponents().toArray();
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		return ((Group) parentElement).getSubGroups().toArray();
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public Object getParent(Object element) {
		return null;
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasChildren(Object element) {
		return (element instanceof Group);
	}
}
