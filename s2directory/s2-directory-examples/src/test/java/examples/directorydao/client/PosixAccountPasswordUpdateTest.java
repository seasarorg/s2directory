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
package examples.directorydao.client;

import junit.framework.TestCase;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.exception.DirectoryAuthenticationRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;
import examples.directorydao.common.PosixAccountDtoFactory;
import examples.directorydao.directorydao.PosixAccountDtoDao;
import examples.directorydao.dto.PosixAccountDto;

/**
 * パスワード機能のテストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class PosixAccountPasswordUpdateTest extends TestCase {
	private static final String PATH = "app.dicon";
	private static S2Container container;
	private static PosixAccountDtoDao posixAccountDtoDao;
	private static PosixAccountDto user1;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PosixAccountPasswordUpdateTest.class);
	}

	protected void setUp() throws Exception {
		container = S2ContainerFactory.create(PATH);
		container.init();
		posixAccountDtoDao = (PosixAccountDtoDao)container
				.getComponent(PosixAccountDtoDao.class);
		PosixAccountDtoFactory posixAccountDtoFactory = new PosixAccountDtoFactory(
				container);
		user1 = posixAccountDtoFactory.getUser("user1");
		assertEquals(null, posixAccountDtoDao.getUser(user1));
		assertEquals(1, posixAccountDtoDao.insert(user1));
	}

	protected void tearDown() throws Exception {
		assertEquals(1, posixAccountDtoDao.delete(user1));
		assertEquals(null, posixAccountDtoDao.getUser(user1));
	}

	public void testAuthenticateByUser() {
		// ユーザによる接続情報を設定します。
		DirectoryControlProperty property = (DirectoryControlProperty)container
				.getComponent(DirectoryControlProperty.class);
		property.setUser(user1.getUid());
		property.setPassword(user1.getUserPassword());
		assertEquals(true, posixAccountDtoDao.authenticateByUserMode(property));
		property.setPassword("invalid_pass");
		assertEquals(false, posixAccountDtoDao.authenticateByUserMode(property));
	}

	public void testSelectAndUpdatePassword() {
		// ユーザによる接続情報を設定します。
		DirectoryControlProperty property = (DirectoryControlProperty)container
				.getComponent(DirectoryControlProperty.class);
		// user1を取得します。
		PosixAccountDto account = posixAccountDtoDao.getUser(user1);
		// パスワードを更新します。
		account.setUserPassword("newpass");
		assertEquals(1, posixAccountDtoDao.update(account));
		property.setUser(account.getUid());
		property.setPassword(account.getUserPassword());
		assertEquals(true, posixAccountDtoDao.authenticateByUserMode(property));
		// パスワードを更新します。
		account.setUserPassword(user1.getUserPassword());
		assertEquals(1, posixAccountDtoDao.update(account));
		property.setUser(account.getUid());
		property.setPassword(account.getUserPassword());
		assertEquals(true, posixAccountDtoDao.authenticateByUserMode(property));
	}

	public void testSelectAndUpdatePasswordWithUserMode() {
		DirectoryControlProperty property = (DirectoryControlProperty)container
				.getComponent(DirectoryControlProperty.class);
		property.setUser(user1.getUid());
		property.setPassword(user1.getUserPassword());
		// user1を取得します。
		PosixAccountDto account = posixAccountDtoDao.getUser(user1);
		// パスワードを更新します。
		account.setUserPassword("newpass");
		assertEquals(1, posixAccountDtoDao
				.updateWithUserMode(property, account));
		try {
			// パスワードが変更されたため更新時に例外が発生します。
			posixAccountDtoDao.updateWithUserMode(property, account);
			assertTrue(false);
		} catch (DirectoryAuthenticationRuntimeException e) {
			assertTrue(true);
		}
		property.setUser(account.getUid());
		property.setPassword(account.getUserPassword());
		assertEquals(true, posixAccountDtoDao.authenticateByUserMode(property));
		// パスワードを更新します。
		account.setUserPassword(user1.getUserPassword());
		assertEquals(1, posixAccountDtoDao
				.updateWithUserMode(property, account));
		property.setUser(account.getUid());
		property.setPassword(account.getUserPassword());
		assertEquals(true, posixAccountDtoDao.authenticateByUserMode(property));
	}
}
