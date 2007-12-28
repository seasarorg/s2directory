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
import org.seasar.directory.exception.DirectoryRuntimeException;
import org.seasar.directory.exception.NoSuchEntryRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * PosixAccountエントリの更新テストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class PosixAccountUpdateTest extends DefaultDirectoryInformationTreeTest {
	private static final String PATH = "app.dicon";
	private static S2Container container;
	private static PosixAccountDtoDirectoryDao posixAccountDtoDao;
	private static PosixAccountDto user1;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PosixAccountUpdateTest.class);
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
		user1.setDescription(null);
		assertEquals(null, posixAccountDtoDao.getUser(user1));
		assertEquals(1, posixAccountDtoDao.insert(user1));
	}

	protected void tearDown() throws Exception {
		assertEquals(1, posixAccountDtoDao.delete(user1));
		assertEquals(null, posixAccountDtoDao.getUser(user1));
		super.tearDown();
	}

	public void testUpdate1() {
		// user1を取得します。
		PosixAccountDto account = posixAccountDtoDao.getUser(user1);
		assertEquals(true, account.getCn().equals(user1.getCn()));
		assertEquals(null, account.getDescription());
		// 属性を更新します。
		account.setCn("user2");
		account.setDescription("user2 description");
		account.setSn("user2");
		assertEquals(1, posixAccountDtoDao.update(account));
		// user1を取得し直します。
		account = posixAccountDtoDao.getUser(user1);
		assertEquals("user2", account.getCn());
		assertEquals("user2 description", account.getDescription());
		assertEquals("user2", account.getSn());
		// 初期化します。
		assertEquals(1, posixAccountDtoDao.update(user1));
		account = posixAccountDtoDao.getUser(user1);
		assertEquals("user1", account.getCn());
		assertEquals(null, account.getDescription());
		assertEquals("user1", account.getSn());
	}

	public void testUpdate2() {
		// user1を取得します。
		PosixAccountDto account = posixAccountDtoDao.getUser(user1);
		assertEquals(true, account.getCn().equals(user1.getCn()));
		assertEquals(null, account.getDescription());
		// 存在しないエントリの属性を更新しようとします。
		account.setDn("uid=dummy,dc=seasar,dc=org");
		try {
			assertEquals(1, posixAccountDtoDao.update(account));
			assertTrue(false);
		} catch (NoSuchEntryRuntimeException e) {
			assertTrue(true);
		}
	}

	public void testUpdateWithUserMode() {
		DirectoryControlProperty property =
			(DirectoryControlProperty)container
				.getComponent(DirectoryControlProperty.class);
		property.setUser(user1.getUid());
		property.setPassword(user1.getUserPassword());
		// user1を取得します。
		PosixAccountDto account =
			posixAccountDtoDao.getUserWithUserMode(property, user1);
		assertEquals(user1.getCn(), account.getCn());
		assertEquals(null, account.getDescription());
		// 属性を更新します。
		account.setCn("user2");
		account.setDescription("user2 description");
		account.setSn("user2");
		assertEquals(1, posixAccountDtoDao
			.updateWithUserMode(property, account));
		// user1を取得し直します。
		account = posixAccountDtoDao.getUserWithUserMode(property, user1);
		assertEquals("user2", account.getCn());
		assertEquals("user2 description", account.getDescription());
		assertEquals("user2", account.getSn());
		// 初期化します。
		assertEquals(1, posixAccountDtoDao.update(user1));
		account = posixAccountDtoDao.getUserWithUserMode(property, user1);
		assertEquals("user1", account.getCn());
		assertEquals(null, account.getDescription());
		assertEquals("user1", account.getSn());
	}

	public void testUpdateSchemeViolation() {
		// user1を取得します。
		PosixAccountDto account = posixAccountDtoDao.getUser(user1);
		// 属性を更新します。
		account.setUid("user2");
		try {
			posixAccountDtoDao.update(account);
			assertTrue(false);
		} catch (DirectoryRuntimeException e) {
			assertTrue(true);
		}
		// user1を取得し直します。
		account = posixAccountDtoDao.getUser(user1);
		assertEquals(user1.getUid(), account.getUid());
		// 初期化します。
		assertEquals(0, posixAccountDtoDao.update(user1));
		account = posixAccountDtoDao.getUser(user1);
	}

	public void testUpdateSchemeViolationWithUserMode() {
		DirectoryControlProperty property =
			(DirectoryControlProperty)container
				.getComponent(DirectoryControlProperty.class);
		property.setUser(user1.getUid());
		property.setPassword(user1.getUserPassword());
		// user1を取得します。
		PosixAccountDto account =
			posixAccountDtoDao.getUserWithUserMode(property, user1);
		// 属性を更新します。
		account.setUid("user2");
		try {
			posixAccountDtoDao.updateWithUserMode(property, account);
			assertTrue(false);
		} catch (DirectoryRuntimeException e) {
			assertTrue(true);
		}
		// user1を取得し直します。
		account = posixAccountDtoDao.getUserWithUserMode(property, user1);
		assertEquals(user1.getUid(), account.getUid());
		// 初期化します。
		assertEquals(0, posixAccountDtoDao.update(user1));
	}
}
