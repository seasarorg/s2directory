package test.org.seasar.directory.dao;

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
				"Test for test.org.seasar.directory.dao");
		//$JUnit-BEGIN$
		suite.addTestSuite(BeanListMetaDataNamingEnumerationHandlerTest.class);
		suite.addTestSuite(DirectoryConnectionTest.class);
		//$JUnit-END$
		return suite;
	}
}
