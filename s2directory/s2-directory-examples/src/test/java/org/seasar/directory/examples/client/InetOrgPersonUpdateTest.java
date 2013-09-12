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
package org.seasar.directory.examples.client;

import org.seasar.directory.examples.client.common.PosixAccountDtoFactory;
import org.seasar.directory.examples.directorydao.InetOrgPersonDirectoryDao;
import org.seasar.directory.examples.directorydao.PosixAccountDtoDirectoryDao;
import org.seasar.directory.examples.dto.PosixAccountDto;
import org.seasar.directory.examples.entity.InetOrgPerson;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * InetOrgPersonエントリの更新テストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date:: 2008-01-13 16:59:44 +0900#$
 */
public class InetOrgPersonUpdateTest extends
		DefaultDirectoryInformationTreeTest {
	private static final String PATH = "app.dicon";
	private static S2Container container;
	private static PosixAccountDtoDirectoryDao posixAccountDtoDao;
	private static PosixAccountDto user1;
	private static InetOrgPersonDirectoryDao inetOrgPersonDao;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(InetOrgPersonUpdateTest.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (container == null) {
			container = S2ContainerFactory.create(PATH);
			container.init();
			posixAccountDtoDao =
				(PosixAccountDtoDirectoryDao)container.getComponent(PosixAccountDtoDirectoryDao.class);
			inetOrgPersonDao =
				(InetOrgPersonDirectoryDao)container.getComponent(InetOrgPersonDirectoryDao.class);
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
	 * プロバイダの非文字列属性の組み込みリストにある場合
	 */
	public void testBinaryData1() {
		byte[] data1 = "'10'B".getBytes();
		byte[] data2 = "'11'B".getBytes();
		// user1を取得します。
		InetOrgPerson search = new InetOrgPerson();
		search.setUid("user1");
		InetOrgPerson account = inetOrgPersonDao.getInetOrgPerson(search);
		assertEquals(null, account.getX500UniqueIdentifier());
		// 更新1
		account.setX500UniqueIdentifier(data1);
		inetOrgPersonDao.updateInetOrgPerson(account);
		account = inetOrgPersonDao.getInetOrgPerson(search);
		assertEquals(new String(data1), new String(account.getX500UniqueIdentifier()));
		// 更新2
		account.setX500UniqueIdentifier(data2);
		inetOrgPersonDao.updateInetOrgPerson(account);
		account = inetOrgPersonDao.getInetOrgPerson(search);
		assertEquals(new String(data2), new String(account.getX500UniqueIdentifier()));
		// 削除
		account.setX500UniqueIdentifier(null);
		inetOrgPersonDao.updateInetOrgPerson(account);
		account = inetOrgPersonDao.getInetOrgPerson(search);
		assertEquals(null, account.getX500UniqueIdentifier());
	}

	/**
	 * プロバイダの非文字列属性の組み込みリストにない場合
	 */
	public void testBinaryData2() {
		byte[] data1 = "test string 1".getBytes();
		byte[] data2 = "test string 2".getBytes();
		// user1を取得します。
		InetOrgPerson search = new InetOrgPerson();
		search.setUid("user1");
		InetOrgPerson account = inetOrgPersonDao.getInetOrgPerson(search);
		assertEquals(null, account.getMail());
		// 更新1
		account.setMail(data1);
		inetOrgPersonDao.updateInetOrgPerson(account);
		account = inetOrgPersonDao.getInetOrgPerson(search);
		assertEquals(new String(data1), new String(account.getMail()));
		// 更新2
		account.setMail(data2);
		inetOrgPersonDao.updateInetOrgPerson(account);
		account = inetOrgPersonDao.getInetOrgPerson(search);
		assertEquals(new String(data2), new String(account.getMail()));
		// 削除
		account.setMail(null);
		inetOrgPersonDao.updateInetOrgPerson(account);
		account = inetOrgPersonDao.getInetOrgPerson(search);
		assertEquals(null, account.getMail());
	}
}
