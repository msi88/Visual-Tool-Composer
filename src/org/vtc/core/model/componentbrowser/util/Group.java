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
package org.vtc.core.model.componentbrowser.util;

import java.util.ArrayList;
import java.util.List;

/**
 * The Group model class.
 * 
 * @author Michael Sieber
 */
public class Group {
	private final String _name;
	private List<Group> _subGroups;
	private List<Bean> _beans;

	/**
	 * Create a new group.
	 * 
	 * @param name The group name
	 */
	public Group(String name) {
		_name = name;
		_subGroups = new ArrayList<Group>();
		_beans = new ArrayList<Bean>();
	}

	/**
	 * Get the group name.
	 * 
	 * @return The name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Get a list with all sub groups.
	 * 
	 * @return The subGroups
	 */
	public List<Group> getSubGroups() {
		return _subGroups;
	}

	/**
	 * Get a list with all beans.
	 * 
	 * @return The beans
	 */
	public List<Bean> getBeans() {
		return _beans;
	}

}
