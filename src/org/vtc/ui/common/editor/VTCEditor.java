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
package org.vtc.ui.common.editor;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.vtc.core.model.editor.EditorInput;

/**
 * The VTCEditor class represents the multi page editor.
 * 
 * @author Michael Sieber
 */
public class VTCEditor extends MultiPageEditorPart {
	private static Logger LOGGER = Logger.getLogger(VTCEditor.class);
	private boolean _isDirty;

	/*
	 * {@inheritDoc}
	 */
	@Override
	protected void createPages() {
		// http://www.ralfebert.de/eclipse_rcp/editors/
		// http://docs.jboss.org/jbosside/cookbook/build/en/html/Example5.html
		// TODO Auto-generated method stub

	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Change the isDirty property and update listeners.
	 * 
	 * @param dirty Set if the editor's file is dirty
	 */
	private void setDirty(boolean dirty) {
		_isDirty = dirty;
		firePropertyChange(MultiPageEditorPart.PROP_DIRTY);
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public EditorInput getEditorInput() {
		return (EditorInput) super.getEditorInput();
	}
}
