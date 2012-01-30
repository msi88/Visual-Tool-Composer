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

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * The FileLabelProvider class which provides the labels and names for files.
 * 
 * @author Michael Sieber
 */
public class FileLabelProvider extends LabelProvider {
	private static final Image FOLDER = AbstractUIPlugin
			.imageDescriptorFromPlugin("org.vtc",
					"icons/folder.png").createImage();
	private static final Image FILE = AbstractUIPlugin
			.imageDescriptorFromPlugin("org.vtc",
					"icons/page.png").createImage();

	@Override
	public Image getImage(Object element) {
		File file = (File) element;

		// check if the file is a directory or not
		if (file.isDirectory()) {
			return FOLDER;
		}
		return FILE;
	}

	@Override
	public String getText(Object element) {

		// get the file name
		String fileName = ((File) element).getName();
		if (fileName.length() > 0) {
			return fileName;
		}
		return ((File) element).getPath();
	}
}
