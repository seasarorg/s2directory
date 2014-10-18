/*
 * Copyright 2005-2014 the Seasar Foundation and the Others.
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
package org.seasar.directory.tiger.examples.client;

import org.seasar.directory.tiger.examples.client.common.PosixAccountDtoFactory;
import org.seasar.directory.tiger.examples.client.common.PosixAccountWithAnnotationDtoFactory;
import org.seasar.directory.tiger.examples.directorydao.PosixAccountDtoDirectoryDao;
import org.seasar.directory.tiger.examples.directorydao.PosixAccountWithAnnocationDtoDirectoryDao;
import org.seasar.directory.tiger.examples.dto.PosixAccountDto;
import org.seasar.directory.tiger.examples.dto.PosixAccountWithAnnotationDto;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * PosixAccountエントリの更新テストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date:: 2007-12-12 13:13:09 +0900#$
 */
public class PosixAccountWithAnnocationTest extends
		DefaultDirectoryInformationTreeTest {
	private static final String PATH = "app.dicon";
	private static S2Container container;
	private static PosixAccountDtoDirectoryDao posixAccountDtoDao;
	private static PosixAccountWithAnnocationDtoDirectoryDao posixAccountWithAnnocationDtoDirectoryDao;
	private static PosixAccountDto user1;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PosixAccountWithAnnocationTest.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (container == null) {
			container = S2ContainerFactory.create(PATH);
			container.init();
			posixAccountDtoDao =
				(PosixAccountDtoDirectoryDao)container.getComponent(PosixAccountDtoDirectoryDao.class);
			posixAccountWithAnnocationDtoDirectoryDao =
				(PosixAccountWithAnnocationDtoDirectoryDao)container.getComponent(PosixAccountWithAnnocationDtoDirectoryDao.class);
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

	public void testInsert1() {
		PosixAccountWithAnnotationDtoFactory posixAccountDtoFactory =
			new PosixAccountWithAnnotationDtoFactory(container);
		PosixAccountWithAnnotationDto user1 =
			posixAccountDtoFactory.getUser("newuser1");
		assertEquals(
			null,
			posixAccountWithAnnocationDtoDirectoryDao.getUser(user1));
		try {
			assertEquals(
				1,
				posixAccountWithAnnocationDtoDirectoryDao.insert(user1));
		} finally {
			assertEquals(
				1,
				posixAccountWithAnnocationDtoDirectoryDao.delete(user1));
		}
	}

	public void testSelect1() {
		PosixAccountWithAnnotationDto account = null;
		PosixAccountWithAnnotationDto search =
			new PosixAccountWithAnnotationDto();
		// DN指定で検索
		search.setDn("uid=user1,ou=Users,dc=seasar,dc=org");
		account = posixAccountWithAnnocationDtoDirectoryDao.getUser(search);
		assertEquals("user1", account.getCn());
		// アノテーションの指定してある属性を使用して検索
		search = new PosixAccountWithAnnotationDto();
		search.setHome("/home/users/user1");
		account = posixAccountWithAnnocationDtoDirectoryDao.getUser(search);
		assertEquals("user1", account.getCn());
	}

	public void testUpdate1() {
		PosixAccountWithAnnotationDto account = null;
		PosixAccountWithAnnotationDto search =
			new PosixAccountWithAnnotationDto();
		// DN指定で検索
		search.setDn("uid=user1,ou=Users,dc=seasar,dc=org");
		account = posixAccountWithAnnocationDtoDirectoryDao.getUser(search);
		// ATTRIBUTEアノテーションの指定してある属性を使用して更新
		account.setHome("/foo/bar");
		posixAccountWithAnnocationDtoDirectoryDao.update(account);
		account = posixAccountWithAnnocationDtoDirectoryDao.getUser(search);
		assertEquals("/foo/bar", account.getHome());
		account.setHome("/home/users/user1");
		posixAccountWithAnnocationDtoDirectoryDao.update(account);
		account = posixAccountWithAnnocationDtoDirectoryDao.getUser(search);
		assertEquals("/home/users/user1", account.getHome());
	}

	public void testUpdate2() {
		PosixAccountWithAnnotationDto account = null;
		PosixAccountWithAnnotationDto search =
			new PosixAccountWithAnnotationDto();
		// DN指定で検索
		search.setDn("uid=user1,ou=Users,dc=seasar,dc=org");
		account = posixAccountWithAnnocationDtoDirectoryDao.getUser(search);
		// ATTRIBUTEアノテーションとCOLUMNアノテーションの
		// 両方が指定してある属性を使用して更新
		account.setGid("9999");
		posixAccountWithAnnocationDtoDirectoryDao.update(account);
		account = posixAccountWithAnnocationDtoDirectoryDao.getUser(search);
		assertEquals("9999", account.getGid());
		account.setGid("1000");
		posixAccountWithAnnocationDtoDirectoryDao.update(account);
		account = posixAccountWithAnnocationDtoDirectoryDao.getUser(search);
		assertEquals("1000", account.getGid());
	}

}
