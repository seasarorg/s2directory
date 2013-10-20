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

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.examples.client.common.PosixAccountDtoFactory;
import org.seasar.directory.examples.directorydao.PosixAccountDtoDirectoryDao;
import org.seasar.directory.examples.dto.PosixAccountDto;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * PosixAccountエントリの更新テストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class PosixAccountSelectAnonymousTest extends
		DefaultDirectoryInformationTreeTest {

	private static final String PATH = "app.dicon";

	private static S2Container container;

	private static PosixAccountDtoDirectoryDao posixAccountDtoDao;

	private static PosixAccountDto user1;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PosixAccountSelectAnonymousTest.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (container == null) {
			container = S2ContainerFactory.create(PATH);
			container.init();
			posixAccountDtoDao =
				(PosixAccountDtoDirectoryDao)container.getComponent(PosixAccountDtoDirectoryDao.class);
		}
		PosixAccountDtoFactory posixAccountDtoFactory =
			new PosixAccountDtoFactory(container);
		user1 = posixAccountDtoFactory.getUser("user1");
		user1.setDescription(null);
		assertEquals(null, posixAccountDtoDao.getUser(user1));
		assertEquals(1, posixAccountDtoDao.insert(user1));
	}

	protected void tearDown() throws Exception {
		assertEquals(1, posixAccountDtoDao.delete(user1));
		assertEquals(null, posixAccountDtoDao.getUser(user1));
		super.tearDown();
	}

	/**
	 * userPassword は匿名接続では取得できない設定にしておく必要があります。
	 * 
	 * <pre>
	 * # OpenLDAP の設定例
	 * access to attrs=userPassword,sambaLMPassword,sambaNTPassword
	 *     by anonymous auth
	 *     by self write
	 *     by * none
	 * 
	 * </pre>
	 */
	public void testSelectByAnonymouseException2() {
		DirectoryControlProperty property;
		PosixAccountDto account;

		property =
			(DirectoryControlProperty)container.getComponent(DirectoryControlProperty.class);
		property.setAuthentication(DirectoryControlProperty.AUTHENTICATION_NONE);
		account = posixAccountDtoDao.getUserWithUserMode(property, user1);
		assertEquals(true, account.getCn().equals(user1.getCn()));
		assertEquals(null, account.getUserPassword());
		assertEquals(null, account.getDescription());

		property =
			(DirectoryControlProperty)container.getComponent(DirectoryControlProperty.class);
		property.setAuthentication(DirectoryControlProperty.AUTHENTICATION_NONE);
		property.setUser(null);
		property.setBindDn(null);
		property.setPassword(null);
		account = posixAccountDtoDao.getUserWithUserMode(property, user1);
		assertEquals(true, account.getCn().equals(user1.getCn()));
		assertEquals(null, account.getUserPassword());
		assertEquals(null, account.getDescription());
	}

}
