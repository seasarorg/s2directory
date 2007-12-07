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

import org.seasar.directory.examples.common.PosixAccountDtoFactory;
import org.seasar.directory.examples.directorydao.PosixAccountDtoDirectoryDao;
import org.seasar.directory.examples.dto.PosixAccountDto;
import org.seasar.directory.exception.DirectoryNameAlreadyBoundRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * PosixAccountエントリの追加テストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class PosixAccountInsertTest extends DefaultDirectoryInformationTreeTest {
	private static final String PATH = "app.dicon";
	private static S2Container container;
	private static PosixAccountDtoDirectoryDao posixAccountDtoDao;
	private static PosixAccountDto user1;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PosixAccountInsertTest.class);
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

	protected void tearDown() throws Exception {
		PosixAccountDto account = new PosixAccountDto();
		account.setDn(user1.getDn());
		assertEquals(1, posixAccountDtoDao.delete(account));
		assertEquals(null, posixAccountDtoDao.getUser(account));
		super.tearDown();
	}

	public void testCreate1() {
		assertEquals(null, posixAccountDtoDao.getUser(user1));
		assertEquals(1, posixAccountDtoDao.insert(user1));
		PosixAccountDto account = posixAccountDtoDao.getUser(user1);
		assertEquals(user1.getDn(), account.getDn());
		assertEquals(user1.getCn(), account.getCn());
		assertEquals(user1.getSn(), account.getSn());
		assertEquals(user1.getUidNumber(), account.getUidNumber());
		// 検索1
		PosixAccountDto search = new PosixAccountDto();
		search.setUid(user1.getUid());
		account = posixAccountDtoDao.getUser(search);
		assertEquals(user1.getDn(), account.getDn());
		assertEquals(user1.getCn(), account.getCn());
		assertEquals(user1.getSn(), account.getSn());
		assertEquals(user1.getUidNumber(), account.getUidNumber());
		// 検索2
		search = new PosixAccountDto();
		search.setUid(user1.getUid());
		search.setSn(user1.getSn());
		account = posixAccountDtoDao.getUser(search);
		assertEquals(user1.getDn(), account.getDn());
		assertEquals(user1.getCn(), account.getCn());
		assertEquals(user1.getSn(), account.getSn());
		assertEquals(user1.getUidNumber(), account.getUidNumber());
		// 検索3
		search = new PosixAccountDto();
		search.setUid(user1.getUid());
		search.setSn(user1.getSn());
		search.setGidNumber(user1.getGidNumber());
		account = posixAccountDtoDao.getUser(search);
		assertEquals(user1.getDn(), account.getDn());
		assertEquals(user1.getCn(), account.getCn());
		assertEquals(user1.getSn(), account.getSn());
		assertEquals(user1.getUidNumber(), account.getUidNumber());
	}

	public void testCreateAlreadyBound() {
		assertEquals(1, posixAccountDtoDao.insert(user1));
		try {
			posixAccountDtoDao.insert(user1);
			assertFalse(true);
		} catch (DirectoryNameAlreadyBoundRuntimeException e) {
			assertTrue(true);
		}
	}
}
