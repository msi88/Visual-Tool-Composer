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
package org.vtc.api.annotation;

/**
 * The Method annotation which marks a method which can be used to connect
 * beans. This annotation should be positioned on the method wich is executing
 * the event (eg. process(Object o)). The VisualToolComposer expects that two
 * additional methods are in the class. This two methods are used to register
 * and deregister beans. This two methods have to follow the bean conventions
 * for adding and removing listeners (eg. addProcessListener,
 * removeProcessListener)
 * 
 * @author Michael Sieber
 */
public @interface Method {

	/**
	 * The optional name of the method. If no name is specified, the method name
	 * itself will be used.
	 * 
	 */
	public String name() default "";
}
