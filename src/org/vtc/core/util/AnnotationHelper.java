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
package org.vtc.core.util;

import org.apache.log4j.Logger;
import org.vtc.api.annotation.Bean;

/**
 * The AnnotationHelper class which provides functions for annotation
 * processing.
 * 
 * @author Michael Sieber
 */
public class AnnotationHelper {
	private static Logger LOGGER = Logger.getLogger(AnnotationHelper.class);

	/**
	 * Util class.
	 */
	private AnnotationHelper() {
		// nothing to do here
	}

	/**
	 * Get the name for a bean.
	 * 
	 * @param bean The bean class from which the name should be generated
	 * @return The name of the bean
	 */
	public static String getBeanName(Class<?> bean) {
		LOGGER.debug("Loading name for " + bean);

		// check if the class is annotated with the bean annotation
		if (bean.isAnnotationPresent(Bean.class)
				&& !bean.getAnnotation(Bean.class).name().equals("")) {
			return bean.getAnnotation(Bean.class).name();
		}

		return ClassUtils.getClassName(bean);
	}

	/**
	 * Get the group names for a bean.
	 * 
	 * @param bean The bean class from which the groups should be loaded
	 * @return A list of groups for this bean
	 */
	public static String[] getGroups(Class<?> bean) {
		if (bean.isAnnotationPresent(Bean.class)) {
			return bean.getAnnotation(Bean.class).group();
		}

		return new String[] { "" };
	}
}
