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
import org.vtc.core.model.componentbrowser.util.Bean;
import org.vtc.core.model.componentbrowser.util.Group;
import org.vtc.core.model.componentbrowser.util.Nameable;
import org.vtc.core.util.AnnotationHelper;
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
	 * Get the component content.
	 * 
	 * @return A list of components
	 */
	public List<Nameable> getComponents() {
		List<Nameable> out = new ArrayList<Nameable>();
		try {
			List<Class<?>> classes =
					ClassUtils.getClassesWithAnnotation(_dir,
							org.vtc.api.annotation.Bean.class);
			out = createComponentTree(classes);
		} catch (Exception e) {
			LOGGER.fatal("Error loading classes from directory: " + _dir, e);
		}

		return out;
	}

	/**
	 * Create the component tree with groups and beans.
	 * 
	 * @param classes The list of classes from which the tree will be generated
	 * @return A list of all beans in hirarchical tree order
	 */
	private List<Nameable> createComponentTree(List<Class<?>> classes) {
		Group root = new Group("root");

		if (classes != null) {
			for (Class<?> clazz : classes) {
				String[] groups = AnnotationHelper.getGroups(clazz);

				// if the bean is in no group add it to root
				if (groups[0].equals("")) {
					String name = AnnotationHelper.getBeanName(clazz);
					root.getContent().add(new Bean(name, clazz));
				} else {
					Bean toAdd =
							new Bean(AnnotationHelper.getBeanName(clazz),
									clazz);
					addToGroup(root, groups, 0, toAdd);
				}
			}
		}

		return root.getContent();
	}

	/**
	 * Add a bean to a group or subgroup.
	 * 
	 * @param curr The current group
	 * @param groups The list of groups in which the bean should be added
	 * @param index The group index. Determines which group is currently
	 *            processed
	 * @param toAdd The bean which should be added to a group
	 */
	private void addToGroup(Group curr, String[] groups,
			int index, Bean toAdd) {
		if (index < groups.length) {
			String group = groups[index];

			for (Nameable nameable : curr.getContent()) {
				if (nameable instanceof Group
						&& nameable.getName().equals(group)) {
					Group tmp = (Group) nameable;
					addToGroup(tmp, groups,
							++index, toAdd);
					return;
				}
			}

			// no group name found, create it
			Group g = new Group(group);
			curr.getContent().add(g);
			addToGroup(g, groups, ++index, toAdd);
		} else {
			curr.getContent().add(toAdd);
		}
	}
}
