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

import java.util.List;

import org.seasar.directory.examples.client.common.PosixAccountDtoFactory;
import org.seasar.directory.examples.directorydao.PosixAccountDtoDirectoryDao;
import org.seasar.directory.examples.dto.PosixAccountDto;
import org.seasar.directory.exception.DirectoryRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * PosixAccountエントリの更新テストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date:: 2007-12-08 03:35:41 +0900#$
 */
public class PosixAccountSelectTest extends DefaultDirectoryInformationTreeTest {
	private static final String PATH = "app.dicon";
	private static S2Container container;
	private static PosixAccountDtoDirectoryDao posixAccountDtoDao;
	private static PosixAccountDto user1;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PosixAccountSelectTest.class);
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

	public void testSelect1() {
		// user1を取得します。
		PosixAccountDto account = posixAccountDtoDao.getUser(user1);
		assertEquals(true, account.getCn().equals(user1.getCn()));
		assertEquals(null, account.getDescription());
	}

	public void testSelect2() {
		// user1を取得します。
		PosixAccountDto search = new PosixAccountDto();
		search.setDn("uid=user1,ou=Users,dc=seasar,dc=org");
		PosixAccountDto account = posixAccountDtoDao.getUser(search);
		assertEquals(true, account.getCn().equals(user1.getCn()));
		assertEquals(null, account.getDescription());
	}

	public void testSelect3() {
		// 存在しないユーザを取得します。
		PosixAccountDto search = new PosixAccountDto();
		search.setDn("uid=user1,dc=seasar,dc=org");
		PosixAccountDto account = posixAccountDtoDao.getUser(search);
		if (account == null) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
		// 存在しないユーザを取得します。
		search.setDn("uid=user100,ou=Users,dc=seasar,dc=org");
		account = posixAccountDtoDao.getUser(search);
		if (account == null) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}

	}

	public void testSelect4() {
		PosixAccountDto search = new PosixAccountDto();
		// 存在しないBaseDNへアクセスします。
		search.setDn("uid=user1,dc=example,dc=org");
		try {
			posixAccountDtoDao.getUser(search);
			assertTrue(false);
		} catch (DirectoryRuntimeException e) {
			assertTrue(true);
		}
	}

	public void testSelectList() {
		List accounts = posixAccountDtoDao.getAllUser();
		assertNotNull(accounts);
	}

	public void testSelectNull() {
		// 検索に使用するエンティティの値がすべて空の場合、
		// 検索結果はnullになります。
		PosixAccountDto search = new PosixAccountDto();
		search = posixAccountDtoDao.getUser(search);
		assertNull(search);
	}

	public void testSelectManyTimes() {
		// user1を取得します。
		PosixAccountDto account = null;
		for (int i = 0; i < 100; i++) {
			account = posixAccountDtoDao.getUser(user1);
		}
		assertEquals(true, account.getCn().equals(user1.getCn()));
		assertEquals(null, account.getDescription());
	}
}
