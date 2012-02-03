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
package org.vtc.core.condition;

/**
 * The ICondition interface for dynamic condition checking.
 * 
 * @param <T> The Object type to check
 * 
 * @author Michael Sieber
 */
public interface ICondition<T> {

	/**
	 * The check method. In this method the checks will be performed.
	 * 
	 * @param check The object to check
	 * @return True if the check passed successfully
	 */
	public boolean check(T check);
}
