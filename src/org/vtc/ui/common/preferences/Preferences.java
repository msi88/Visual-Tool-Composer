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
package org.vtc.ui.common.preferences;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.vtc.Activator;
import org.vtc.util.Messages;

/**
 * The Preferences class which sets up the preferences page.
 * 
 * @author Michael Sieber
 */
public class Preferences extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	/*
	 * {@inheritDoc}
	 */
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.PREFERENCES_DESCRIPTION);
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	protected void createFieldEditors() {

		// add a field for choosing the working dir
		addField(new DirectoryFieldEditor(PreferenceName.WORK_DIR,
				Messages.CHOOSE_WORK_DIR,
				getFieldEditorParent()));
	}
}
