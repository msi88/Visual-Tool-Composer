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

/**
 * The Bean model class.
 * 
 * @author Michael Sieber
 */
public class Bean implements Nameable {
	private final String _name;
	private final Class<?> _targetClass;

	/**
	 * Create a new Bean.
	 * 
	 * @param name The bean name
	 * @param targetClass The target bean class
	 */
	public Bean(String name, Class<?> targetClass) {
		_name = name;
		_targetClass = targetClass;
	}

	/**
	 * Get the bean name.
	 * 
	 * @return The name
	 */
	@Override
	public String getName() {
		return _name;
	}

	/**
	 * Get the target class.
	 * 
	 * @return The targetClass
	 */
	public Class<?> getTargetClass() {
		return _targetClass;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Bean) {
			Bean b = (Bean) obj;
			if (b.getName().equals(getName())
					&& b.getTargetClass().equals(getTargetClass())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return _name.hashCode() * _targetClass.hashCode();
	}

}
