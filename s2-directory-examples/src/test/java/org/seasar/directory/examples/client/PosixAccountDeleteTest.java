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
package org.seasar.directory.examples.client;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.examples.client.common.PosixAccountDtoFactory;
import org.seasar.directory.examples.directorydao.PosixAccountDtoDirectoryDao;
import org.seasar.directory.examples.dto.PosixAccountDto;
import org.seasar.directory.exception.AuthenticationRuntimeException;
import org.seasar.directory.exception.DirectoryRuntimeException;
import org.seasar.directory.exception.NoSuchEntryRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * PosixAccountエントリの削除テストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class PosixAccountDeleteTest extends DefaultDirectoryInformationTreeTest {
	private static final String PATH = "app.dicon";
	private static S2Container container;
	private static PosixAccountDtoDirectoryDao posixAccountDtoDao;
	private static PosixAccountDto user1;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PosixAccountDeleteTest.class);
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
		assertEquals(null, posixAccountDtoDao.getUser(user1));
		assertEquals(1, posixAccountDtoDao.insert(user1));
	}

	protected void tearDown() throws Exception {
		PosixAccountDto account = new PosixAccountDto();
		account.setDn(user1.getDn());
		assertEquals(1, posixAccountDtoDao.delete(account));
		assertEquals(null, posixAccountDtoDao.getUser(account));
		super.tearDown();
	}

	public void testDelete1() {
		// user1を取得します。
		PosixAccountDto account = posixAccountDtoDao.getUser(user1);
		// 削除します。
		assertEquals(1, posixAccountDtoDao.delete(account));
		assertEquals(null, posixAccountDtoDao.getUser(account));
		// 初期化します。
		assertEquals(1, posixAccountDtoDao.insert(user1));
	}

	public void testDelete2() {
		// user1を取得します。
		PosixAccountDto account = posixAccountDtoDao.getUser(user1);
		// 削除します。
		assertEquals(1, posixAccountDtoDao.delete(account));
		// 既に存在しないエントリを削除しようとすると例外が発生します。
		try {
			posixAccountDtoDao.delete(account);
			assertTrue(false);
		} catch (NoSuchEntryRuntimeException e) {
			assertTrue(true);
		}
		// 初期化します。
		assertEquals(1, posixAccountDtoDao.insert(user1));
	}

	public void testDeleteWithUserMode() {
		DirectoryControlProperty property =
			(DirectoryControlProperty)container
				.getComponent(DirectoryControlProperty.class);
		property.setUser(user1.getUid());
		property.setPassword(user1.getUserPassword());
		// user1を取得します。
		PosixAccountDto account =
			posixAccountDtoDao.getUserWithUserMode(property, user1);
		// 削除します。
		try {
			// ApacheDSの場合、自分自身を削除できる
			assertEquals(1, posixAccountDtoDao.deleteWithUserMode(
				property,
				account));
		} catch (DirectoryRuntimeException e) {
			// OpenLDAPの場合、自分自身を削除できない
			assertEquals(1, posixAccountDtoDao.delete(account));
		}
		try {
			// 存在しないユーザで取得します。
			assertEquals(null, posixAccountDtoDao.getUserWithUserMode(
				property,
				account));
			assertTrue(false);
		} catch (AuthenticationRuntimeException e) {
			assertTrue(true);
		}
		try {
			// 存在しないユーザで初期化します。
			assertEquals(1, posixAccountDtoDao.insertWithUserMode(
				property,
				user1));
			assertTrue(false);
		} catch (AuthenticationRuntimeException e) {
			assertTrue(true);
		}
		// 初期化します。
		assertEquals(1, posixAccountDtoDao.insert(user1));
	}
}
