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

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;
import examples.directorydao.dto.PosixAccountDto;

/**
 * 削除のサンプルです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DeleteDaoSample {
	private static final String PATH = "app.dicon";
	private static S2Container container;

	public static void main(String[] args) {
		// 初期化します。
		container = S2ContainerFactory.create(PATH);
		container.init();
		selectAndUpdateUser();
		// selectAndUpdateUserWithUser();
	}

	public static void selectAndUpdateUser() {
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

	public static void selectAndUpdateUserWithUser() {
		// ユーザによる接続情報を設定します。
		DirectoryControlProperty property = (DirectoryControlProperty)container
				.getComponent(DirectoryControlProperty.class);
		property.setUser("user2");
		property.setPassword("user2pass");
		// Daoクラスを取得します。
		PosixAccountDtoDao dao = (PosixAccountDtoDao)container
				.getComponent(PosixAccountDtoDao.class);
		// user2を取得します。
		PosixAccountDto account = dao.getUserByUidWithUserMode(property, "user2");
		// 表示します。
		System.out.println("DEBUG: " + account);
		// user2を削除します。
		int amount = dao.deleteWithUserMode(property, account);
		System.out.println("DELETE AMOUNT: " + amount);
	}
}