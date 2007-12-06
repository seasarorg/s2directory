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
package org.seasar.directory.examples.client;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.examples.common.PosixAccountDtoFactory;
import org.seasar.directory.examples.directorydao.PosixAccountDtoDirectoryDao;
import org.seasar.directory.examples.dto.PosixAccountDto;
import org.seasar.directory.exception.DirectoryAuthenticationRuntimeException;
import org.seasar.directory.exception.DirectoryCommunicationRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * コネクション接続の追加テストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class PosixAccountConnectionTest extends
		DefaultDirectoryInformationTreeTest {
	private static final String PATH = "app.dicon";
	private static S2Container container;
	private static PosixAccountDtoDirectoryDao posixAccountDtoDao;
	private static PosixAccountDto user1;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PosixAccountConnectionTest.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (container == null) {
			container = S2ContainerFactory.create(PATH);
			container.init();
			posixAccountDtoDao =
				(PosixAccountDtoDirectoryDao)container
					.getComponent(PosixAccountDtoDirectoryDao.class);
		}
		PosixAccountDtoFactory posixAccountDtoFactory =
			new PosixAccountDtoFactory(container);
		user1 = posixAccountDtoFactory.getUser("user1");
	}

	public void testInvalidHost() {
		DirectoryControlProperty property =
			(DirectoryControlProperty)container
				.getComponent(DirectoryControlProperty.class);
		property.setUrl("ldap://localhost:12345");
		try {
			posixAccountDtoDao.insertWithUserMode(property, user1);
			assertFalse(true);
		} catch (DirectoryCommunicationRuntimeException e) {
			assertTrue(true);
		}
	}

	public void testInvalidConnectionUser() {
		DirectoryControlProperty property =
			(DirectoryControlProperty)container
				.getComponent(DirectoryControlProperty.class);
		property.setUser("cn=dummyuser,dc=seasar,dc=org");
		try {
			posixAccountDtoDao.insertWithUserMode(property, user1);
			assertFalse(true);
		} catch (DirectoryAuthenticationRuntimeException e) {
			assertTrue(true);
		}
	}
}
