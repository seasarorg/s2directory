package org.seasar.directory.exception;

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
			new TestSuite("Test for org.seasar.directory.exception");
		// $JUnit-BEGIN$
		suite.addTestSuite(DirectoryNameAlreadyBoundRuntimeExceptionTest.class);
		suite.addTestSuite(DirectoryCommunicationRuntimeExceptionTest.class);
		suite.addTestSuite(DirectoryAuthenticationRuntimeExceptionTest.class);
		// $JUnit-END$
		return suite;
	}
}
