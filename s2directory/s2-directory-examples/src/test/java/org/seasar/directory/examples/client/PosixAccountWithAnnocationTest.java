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

import org.seasar.directory.examples.client.common.PosixAccountDtoFactory;
import org.seasar.directory.examples.directorydao.PosixAccountDtoDirectoryDao;
import org.seasar.directory.examples.directorydao.PosixAccountWithAnnocationDirectoryDao;
import org.seasar.directory.examples.dto.PosixAccountDto;
import org.seasar.directory.examples.entity.PosixAccountWithAnnotation;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * PosixAccountエントリの更新テストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class PosixAccountWithAnnocationTest extends
		DefaultDirectoryInformationTreeTest {

	private static final String PATH = "app.dicon";

	private static S2Container container;

	private static PosixAccountDtoDirectoryDao posixAccountDtoDao;

	private static PosixAccountWithAnnocationDirectoryDao posixAccountWithAnnocationDirectoryDao;

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
			posixAccountWithAnnocationDirectoryDao =
				(PosixAccountWithAnnocationDirectoryDao)container.getComponent(PosixAccountWithAnnocationDirectoryDao.class);
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
		PosixAccountWithAnnotation account = null;
		PosixAccountWithAnnotation search = new PosixAccountWithAnnotation();
		// DN指定で検索
		search.setDn("uid=user1,ou=Users,dc=seasar,dc=org");
		account = posixAccountWithAnnocationDirectoryDao.getUser(search);
		assertEquals("user1", account.getCn());
		// アノテーションの指定してある属性を使用して検索
		search = new PosixAccountWithAnnotation();
		search.setHome("/home/users/user1");
		account = posixAccountWithAnnocationDirectoryDao.getUser(search);
		assertEquals("user1", account.getCn());
	}

	public void testUpdate1() {
		PosixAccountWithAnnotation account = null;
		PosixAccountWithAnnotation search = new PosixAccountWithAnnotation();
		// DN指定で検索
		search.setDn("uid=user1,ou=Users,dc=seasar,dc=org");
		account = posixAccountWithAnnocationDirectoryDao.getUser(search);
		// ATTRIBUTEアノテーションの指定してある属性を使用して更新
		account.setHome("/foo/bar");
		posixAccountWithAnnocationDirectoryDao.update(account);
		account = posixAccountWithAnnocationDirectoryDao.getUser(search);
		assertEquals("/foo/bar", account.getHome());
		account.setHome("/home/users/user1");
		posixAccountWithAnnocationDirectoryDao.update(account);
		account = posixAccountWithAnnocationDirectoryDao.getUser(search);
		assertEquals("/home/users/user1", account.getHome());
	}

	public void testUpdate2() {
		PosixAccountWithAnnotation account = null;
		PosixAccountWithAnnotation search = new PosixAccountWithAnnotation();
		// DN指定で検索
		search.setDn("uid=user1,ou=Users,dc=seasar,dc=org");
		account = posixAccountWithAnnocationDirectoryDao.getUser(search);
		// ATTRIBUTEアノテーションとCOLUMNアノテーションの
		// 両方が指定してある属性を使用して更新
		account.setGid("9999");
		posixAccountWithAnnocationDirectoryDao.update(account);
		account = posixAccountWithAnnocationDirectoryDao.getUser(search);
		assertEquals("9999", account.getGid());
		account.setGid("1000");
		posixAccountWithAnnocationDirectoryDao.update(account);
		account = posixAccountWithAnnocationDirectoryDao.getUser(search);
		assertEquals("1000", account.getGid());
	}

}
