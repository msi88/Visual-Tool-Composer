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
package org.vtc.core.model.filebrowser;

import java.io.File;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * The FileContentProvider class which provides the content for all file
 * browsers.
 * 
 * @author Michael Sieber
 */
public class FileContentProvider implements ITreeContentProvider {

	/*
	 * {@inheritDoc}
	 */
	@Override
	public Object[] getChildren(Object parent) {
		File file = (File) parent;
		return file.listFiles();
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		return (Object[]) inputElement;
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public Object getParent(Object element) {
		File file = (File) element;
		return file.getParentFile();
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasChildren(Object parent) {
		File file = (File) parent;
		return file.isDirectory();
	}

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
	public void inputChanged(Viewer view, Object oldInput, Object newInput) {
		// nothing to do here
	}

}
