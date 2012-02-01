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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.vtc.core.model.componentbrowser.util.Group;
import org.vtc.core.util.ClassUtils;

/**
 * The BeanModel class which loads the beans and creates an abstract syntax tree
 * for further processing.
 * 
 * @author Michael Sieber
 */
public class BeanModel {
	private static Logger LOGGER = Logger.getLogger(BeanModel.class);
	private final String _dir;

	/**
	 * Create a new BeanModel.
	 * 
	 * @param dir The directory name in which the beans are stored
	 */
	public BeanModel(String dir) {
		_dir = dir;
	}

	/**
	 * Get the group list.
	 * 
	 * @return A list of groups
	 */
	public List<Group> getGroups() {
		List<Group> out = new ArrayList<Group>();
		try {
			List<Class<?>> classes = ClassUtils.getClassNamesFromFolder(_dir);

			for (Class<?> clazz : classes) {
				out.add(new Group(clazz.toString()));
			}
		} catch (Exception e) {
			LOGGER.fatal("Error loading classes from directory: " + _dir, e);
		}

		return out;
	}
}
