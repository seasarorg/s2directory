/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
package examples.directorydao.directorydao;

import java.util.Iterator;
import java.util.List;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;
import examples.directorydao.dto.PosixAccountDto;

/**
 * PosixAccountDaoのサンプルクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class PosixAccountDaoSample {
	private static final String PATH = "app.dicon";
	private static S2Container container;

	public static void main(String[] args) {
		// 初期化します。
		container = S2ContainerFactory.create(PATH);
		container.init();
		// authenticate();
		// authenticateByUser1();
		// getUserByUidWithUser();
		// getUserByFilter();
		selectAndSelect();
		// selectAndUpdateUser();
		// selectAndUpdateUserWithUser();
		// selectAndDeleteUser();
	}

	/**
	 * 初期設定での認証処理を実行します。
	 */
	public static void authenticate() {
		System.out.println("# authenticate()");
		// Daoクラスを取得します。
		PosixAccountDtoDao dao = (PosixAccountDtoDao)container
				.getComponent(PosixAccountDtoDao.class);
		if (dao.authenticate()) {
			System.out.println("ok");
		} else {
			System.out.println("false");
		}
	}

	/**
	 * 指定したユーザ情報による認証処理を実行します。
	 */
	public static void authenticateByUser1() {
		System.out.println("# authenticateByUser1()");
		// ユーザによる接続情報を設定します。
		DirectoryControlProperty property = (DirectoryControlProperty)container
				.getComponent(DirectoryControlProperty.class);
		property.setUser("user1");
		property.setPassword("user1pass");
		// property.setUser(null);
		// property.setPassword(null);
		// Daoクラスを取得します。
		PosixAccountDtoDao dao = (PosixAccountDtoDao)container
				.getComponent(PosixAccountDtoDao.class);
		if (dao.authenticateByUserMode(property)) {
			System.out.println("ok");
		} else {
			System.out.println("false");
		}
	}

	public static void getUserByUidWithUser() {
		System.out.println("# getUserByUidWithUser()");
		// ユーザによる接続情報を設定します。
		DirectoryControlProperty property = (DirectoryControlProperty)container
				.getComponent(DirectoryControlProperty.class);
		property.setUser("user1");
		property.setPassword("user1pass");
		// Daoクラスを取得します。
		PosixAccountDtoDao dao = (PosixAccountDtoDao)container
				.getComponent(PosixAccountDtoDao.class);
		// user1を取得します。
		PosixAccountDto account = dao.getUserByUidWithUserMode(property, "user1");
		// 表示します。
		System.out.println("DEBUG: " + account);
		// user2を取得します。
		account = dao.getUserByUidWithUserMode(property, "user2");
		// 表示します。
		System.out.println("DEBUG: " + account);
	}

	public static void getUserByFilter() {
		System.out.println("# getUserByFilter()");
		// Daoクラスを取得します。
		PosixAccountDtoDao dao = (PosixAccountDtoDao)container
				.getComponent(PosixAccountDtoDao.class);
		List list = dao.getUserByFilter();
		// 表示します。
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			System.out.println("DEBUG: " + iter.next());
		}
	}

	/**
	 * TODO: filter条件からDNを除くか、DNがある場合ベースDNを設定し挙動を変える
	 */
	public static void selectAndSelect() {
		System.out.println("# selectAndSelect()");
		// Daoクラスを取得します。
		PosixAccountDtoDao dao = (PosixAccountDtoDao)container
				.getComponent(PosixAccountDtoDao.class);
		// user2を取得します。
		PosixAccountDto account = dao.getUserByUid("user2");
		// 表示します。
		System.out.println("DEBUG: " + account);
		// user2を更新
		// account.setUserPassword(null);
		// account.setDn(null);
		PosixAccountDto account2 = new PosixAccountDto();
		account2.setDn(account.getDn());
		account = dao.getUser(account2);
		System.out.println("DEBUG: " + account);
	}

	public static void selectAndUpdateUser() {
		System.out.println("# selectAndUpdateUser()");
		// Daoクラスを取得します。
		PosixAccountDtoDao dao = (PosixAccountDtoDao)container
				.getComponent(PosixAccountDtoDao.class);
		// user2を取得します。
		PosixAccountDto account = dao.getUserByUid("user2");
		// 表示します。
		System.out.println("DEBUG: " + account);
		account.setLoginShell("/bin/sh");
		account.setGecos("Rename User");
		account.setDescription("");
		System.out.println("userPassword: " + account.getUserPassword());
		account.setUserPassword("user2pass");
		// user2を更新
		int amount = dao.update(account);
		System.out.println("UPDATE AMOUNT: " + amount);
	}

	public static void selectAndUpdateUserWithUser() {
		System.out.println("# selectAndUpdateUser()");
		// ユーザによる接続情報を設定します。
		DirectoryControlProperty property = (DirectoryControlProperty)container
				.getComponent(DirectoryControlProperty.class);
		property.setUser("user2");
		property.setPassword("user2pass");
		// Daoクラスを取得します。
		PosixAccountDtoDao dao = (PosixAccountDtoDao)container
				.getComponent(PosixAccountDtoDao.class);
		// user2を取得します。
		PosixAccountDto account = dao.getUserByUid("user2");
		account.setUserPassword("test");
		int amount = dao.updateWithUserMode(property, account);
		property.setPassword("test");
		account = dao.getUserByUid("user2");
		account.setUserPassword("user2pass");
		amount += dao.updateWithUserMode(property, account);
		System.out.println("UPDATE AMOUNT: " + amount);
	}

	public static void selectAndDeleteUser() {
		System.out.println("# selectAndDeleteUser()");
		// Daoクラスを取得します。
		PosixAccountDtoDao dao = (PosixAccountDtoDao)container
				.getComponent(PosixAccountDtoDao.class);
		// user2を取得します。
		PosixAccountDto account = dao.getUserByUid("user2");
		// 表示します。
		System.out.println("DEBUG: " + account);
		// user2を削除します。
		int amount = dao.delete(account);
		System.out.println("DELETE AMOUNT: " + amount);
	}
}