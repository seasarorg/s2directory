/*
 * Copyright 2005-2008 the Seasar Foundation and the Others.
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
package org.seasar.directory.exception;

import java.util.Locale;
import javax.naming.CompositeName;
import javax.naming.NameAlreadyBoundException;
import junit.framework.TestCase;

/**
 * エントリが既に存在する例外クラスのテストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryNameAlreadyBoundRuntimeExceptionTest extends TestCase {
	private Locale defaultLocale;

	protected void setUp() throws Exception {
		super.setUp();
		defaultLocale = Locale.getDefault();
	}

	protected void tearDown() throws Exception {
		Locale.setDefault(defaultLocale);
		super.tearDown();
	}

	public void testErrorMessage_ja() throws Exception {
		// ## Arrange ##
		Locale.setDefault(Locale.JAPANESE);
		NameAlreadyBoundException exception = new NameAlreadyBoundException();
		exception.setRemainingName(new CompositeName(
			"uid=user1,ou=Users,dc=seasar,dc=org"));
		NameAlreadyBoundRuntimeException directoryNameAlreadyBoundRuntimeException =
			new NameAlreadyBoundRuntimeException(exception);
		// ## Act ##
		String message = directoryNameAlreadyBoundRuntimeException.getMessage();
		// ## Assert ##
		assertContains(message, "DN: uid=user1,ou=Users,dc=seasar,dc=org");
	}

	public void testErrorMessage_en() throws Exception {
		// ## Arrange ##
		Locale.setDefault(Locale.ENGLISH);
		NameAlreadyBoundException exception = new NameAlreadyBoundException();
		exception.setRemainingName(new CompositeName(
			"uid=user1,ou=Users,dc=seasar,dc=org"));
		NameAlreadyBoundRuntimeException directoryNameAlreadyBoundRuntimeException =
			new NameAlreadyBoundRuntimeException(exception);
		// ## Act ##
		String message = directoryNameAlreadyBoundRuntimeException.getMessage();
		// ## Assert ##
		assertContains(message, "DN: uid=user1,ou=Users,dc=seasar,dc=org");
	}

	private void assertContains(String s, String contained) {
		assertEquals(s, true, s.indexOf(contained) > -1);
	}
}