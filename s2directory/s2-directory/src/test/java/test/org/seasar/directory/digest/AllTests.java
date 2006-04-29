package test.org.seasar.directory.digest;

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
				"Test for test.org.seasar.directory.digest");
		//$JUnit-BEGIN$
		suite.addTestSuite(DigestFactoryTest.class);
		//$JUnit-END$
		return suite;
	}
}
