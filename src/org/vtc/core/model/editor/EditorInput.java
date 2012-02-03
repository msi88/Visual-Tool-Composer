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
package org.vtc.core.model.editor;

import java.io.File;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.vtc.Activator;

/**
 * The EditorInput class.
 * 
 * @author Michael Sieber
 */
public class EditorInput implements IEditorInput {
	private final File _file;

	/**
	 * Create a new EditorInput.
	 * 
	 * @param file The underlaying file
	 */
	public EditorInput(File file) {
		_file = file;
	}

	/**
	 * Get the underlaying file.
	 * 
	 * @return The file
	 */
	public File getFile() {
		return _file;
	}

	/*
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists() {
		return _file.exists();
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return Activator.getImageDescriptor("/icons/page.png");
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return _file.getName();
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public String getToolTipText() {
		return _file.getName();
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EditorInput) {
			EditorInput tmp = (EditorInput) obj;
			if (tmp.getFile().equals(getFile())) {
				return true;
			}
		}

		return false;
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return _file.hashCode();
	}
}
