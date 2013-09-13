/*
 * Copyright 2005-2013 the Seasar Foundation and the Others.
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
package org.seasar.directory.examples.client;

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
		TestSuite suite =
			new TestSuite("Test for org.seasar.directory.examples.client");
		// $JUnit-BEGIN$
		suite.addTestSuite(InetOrgPersonUpdateTest.class);
		suite.addTestSuite(PosixGroupUpdateTest.class);
		suite.addTestSuite(PosixGroupInsertTest.class);
		suite.addTestSuite(PosixAccountPasswordUpdateTest.class);
		suite.addTestSuite(PosixAccountSelectTest.class);
		suite.addTestSuite(PosixAccountInsertTest.class);
		suite.addTestSuite(PosixAccountWithAnnocationTest.class);
		suite.addTestSuite(PosixGroupDtoTest.class);
		suite.addTestSuite(PosixAccountDeleteTest.class);
		suite.addTestSuite(PosixAccountConnectionTest.class);
		suite.addTestSuite(PosixAccountUpdateTest.class);
		suite.addTestSuite(PosixGroupWithListInsertTest.class);
		// $JUnit-END$
		return suite;
	}
}
