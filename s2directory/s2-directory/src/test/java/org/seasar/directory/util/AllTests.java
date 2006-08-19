/*
 * Copyright 2005-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.directory.util;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * テストスイートクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class AllTests {
	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for test.org.seasar.directory.util");
		//$JUnit-BEGIN$
		suite.addTestSuite(DirectoryUtilTest.class);
		suite.addTestSuite(DirectoryDataSourceUtilTest.class);
		//$JUnit-END$
		return suite;
	}
}
