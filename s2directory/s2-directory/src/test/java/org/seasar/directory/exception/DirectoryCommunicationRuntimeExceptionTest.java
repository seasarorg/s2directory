/*
 * Copyright 2005-2007 the Seasar Foundation and the Others.
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
import junit.framework.TestCase;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.impl.DirectoryControlPropertyImpl;

/**
 * サーバ接続の例外クラスのテストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryCommunicationRuntimeExceptionTest extends TestCase {
	private Locale defaultLocale;
	private DirectoryControlProperty property;

	protected void setUp() throws Exception {
		super.setUp();
		defaultLocale = Locale.getDefault();
		property = new DirectoryControlPropertyImpl();
		property.setUrl("ldap://localhost:389");
	}

	protected void tearDown() throws Exception {
		Locale.setDefault(defaultLocale);
		super.tearDown();
	}

	public void testErrorMessage_ja() throws Exception {
		// ## Arrange ##
		Locale.setDefault(Locale.JAPANESE);
		DirectoryCommunicationRuntimeException directoryCommunicationRuntimeException =
			new DirectoryCommunicationRuntimeException(property);
		// ## Act ##
		String message = directoryCommunicationRuntimeException.getMessage();
		// ## Assert ##
		assertContains(message, "ldap://localhost:389");
	}

	public void testErrorMessage_en() throws Exception {
		// ## Arrange ##
		Locale.setDefault(Locale.ENGLISH);
		DirectoryCommunicationRuntimeException directoryCommunicationRuntimeException =
			new DirectoryCommunicationRuntimeException(property);
		// ## Act ##
		String message = directoryCommunicationRuntimeException.getMessage();
		// ## Assert ##
		assertContains(message, "ldap://localhost:389");
	}

	private void assertContains(String s, String contained) {
		assertEquals(s, true, s.indexOf(contained) > -1);
	}
}