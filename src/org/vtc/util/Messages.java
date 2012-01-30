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
package org.vtc.util;

import org.eclipse.osgi.util.NLS;

/**
 * The Messages class.
 * 
 * @author Michael Sieber
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "i18n.messages"; //$NON-NLS-1$

	// @off
	public static String PREFERENCES_DESCRIPTION;
	public static String CHOOSE_WORK_DIR;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	/**
	 * Create a new Messages.
	 */
	private Messages() {
	}
}
