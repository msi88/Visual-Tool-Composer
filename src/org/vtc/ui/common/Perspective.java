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
package org.vtc.ui.common;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * 
 * The Perspective which initializes all views and editors.
 * 
 * @author Michael Sieber
 */
public class Perspective implements IPerspectiveFactory {
	private final float _ratioLeft = 0.3f;
	private final float _ratioBottom = 0.7f;
	private final float _ratioRight = 0.7f;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createInitialLayout(IPageLayout layout) {
		// left folder & views
		IFolderLayout leftFolder =
				layout.createFolder("org.vtc.ui.common.folder.left",
						IPageLayout.LEFT, _ratioLeft, layout.getEditorArea());

		leftFolder.addView("org.vtc.ui.common.view.ComponentExplorer");
		leftFolder.addView("org.vtc.ui.common.view.ProjectExplorer");

		// bottom folder & views
		IFolderLayout bottomFolder =
				layout.createFolder("org.vtc.ui.common.folder.bottom",
						IPageLayout.BOTTOM, _ratioBottom,
						layout.getEditorArea());

		bottomFolder.addView("org.vtc.ui.common.view.Console");

		// right folder & views
		IFolderLayout rightFolder =
				layout.createFolder("org.vtc.ui.common.folder.right",
						IPageLayout.RIGHT, _ratioRight, layout.getEditorArea());

		rightFolder.addView("org.vtc.ui.common.view.PropertyEditor");
	}
}
