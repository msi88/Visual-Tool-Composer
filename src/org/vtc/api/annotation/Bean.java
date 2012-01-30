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

import java.beans.Customizer;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vtc.api.annotation.util.DefaultCustomizer;

/**
 * The Bean annotation which marks a java class as a component bean.
 * 
 * @author Michael Sieber
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Bean {

	/**
	 * The name of the bean or an empty string if no name is specified. If the
	 * name is empty, the class name will be used as the bean name.
	 */
	public String name() default "";

	/**
	 * The class name of the customizer. The customizer has to implemente the
	 * {@link Customizer} interface
	 */
	public Class<? extends Customizer> customizer() default DefaultCustomizer.class;

	/**
	 * The group to which this bean should be added. If no group with this name
	 * exists, a new group will be created.
	 */
	public String group() default "";
}
