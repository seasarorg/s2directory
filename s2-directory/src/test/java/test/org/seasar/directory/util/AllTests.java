package test.org.seasar.directory.util;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * TODO: クラスです。
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
