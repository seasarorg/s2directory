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

import java.util.ArrayList;
import java.util.List;
import org.seasar.directory.examples.common.PosixAccountDtoFactory;
import org.seasar.directory.examples.common.PosixGroupFactory;
import org.seasar.directory.examples.directorydao.PosixAccountDtoDirectoryDao;
import org.seasar.directory.examples.directorydao.PosixGroupWithListDirectoryDao;
import org.seasar.directory.examples.dto.PosixAccountDto;
import org.seasar.directory.examples.entity.PosixGroupWithList;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * PosixAccountエントリの追加テストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class PosixGroupWithListInsertTest extends
		DefaultDirectoryInformationTreeTest {
	private static final String PATH = "app.dicon";
	private static S2Container container;
	private static PosixAccountDtoDirectoryDao posixAccountDtoDao;
	private static PosixAccountDto user1, user2;
	private static PosixGroupWithListDirectoryDao posixGroupWithListDao;
	private static PosixGroupWithList group1;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PosixGroupWithListInsertTest.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (container == null) {
			container = S2ContainerFactory.create(PATH);
			container.init();
			posixAccountDtoDao =
				(PosixAccountDtoDirectoryDao)container
					.getComponent(PosixAccountDtoDirectoryDao.class);
			posixGroupWithListDao =
				(PosixGroupWithListDirectoryDao)container
					.getComponent(PosixGroupWithListDirectoryDao.class);
		}
		// ユーザを追加します。
		PosixAccountDtoFactory posixAccountDtoFactory =
			new PosixAccountDtoFactory(container);
		user1 = posixAccountDtoFactory.getUser("user1");
		user2 = posixAccountDtoFactory.getUser("user2");
		assertEquals(null, posixAccountDtoDao.getUser(user1));
		assertEquals(1, posixAccountDtoDao.insert(user1));
		assertEquals(null, posixAccountDtoDao.getUser(user2));
		assertEquals(1, posixAccountDtoDao.insert(user2));
		// グループを追加します。
		PosixGroupFactory posixGroupFactory = new PosixGroupFactory(container);
		List memberUid = new ArrayList();
		memberUid.add(user1.getUid());
		memberUid.add(user2.getUid());
		group1 = posixGroupFactory.getGroupWithList("group1", memberUid);
		assertEquals(null, posixGroupWithListDao.getPosixGroup(group1));
		assertEquals(1, posixGroupWithListDao.insert(group1));
	}

	protected void tearDown() throws Exception {
		// グループを削除します。
		assertEquals(1, posixGroupWithListDao.delete(group1));
		assertEquals(null, posixGroupWithListDao.getPosixGroup(group1));
		// ユーザを削除します。
		assertEquals(1, posixAccountDtoDao.delete(user1));
		assertEquals(null, posixAccountDtoDao.getUser(user1));
		assertEquals(1, posixAccountDtoDao.delete(user2));
		assertEquals(null, posixAccountDtoDao.getUser(user2));
		super.tearDown();
	}

	public void testGetPosixGroup() {
		// グループで検索
		PosixGroupWithList group = posixGroupWithListDao.getPosixGroup(group1);
		List list = group.getMemberUid();
		assertEquals("user1", String.valueOf(list.get(0)));
		assertEquals("user2", String.valueOf(list.get(1)));
		// 所属メンバーをList型で検索
		List search = new ArrayList();
		search.add("user1");
		list = posixGroupWithListDao.getPosixGroupListByMemberUidList(search);
		group = (PosixGroupWithList)list.get(0);
		assertEquals("group1", group.getCn());
	}
}
