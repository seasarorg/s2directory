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
package org.seasar.directory.examples.client;

import java.util.ArrayList;
import java.util.List;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.examples.client.common.PosixAccountDtoFactory;
import org.seasar.directory.examples.client.common.PosixGroupDtoFactory;
import org.seasar.directory.examples.directorydao.PosixAccountDtoDirectoryDao;
import org.seasar.directory.examples.directorydao.PosixGroupDtoDirectoryDao;
import org.seasar.directory.examples.dto.PosixAccountDto;
import org.seasar.directory.examples.dto.PosixGroupDto;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * PosixAccountエントリの追加テストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class PosixGroupDtoTest extends DefaultDirectoryInformationTreeTest {

	private static final String PATH = "app.dicon";

	private static S2Container container;

	private static PosixAccountDtoDirectoryDao posixAccountDtoDao;

	private static PosixAccountDto user1, user2;

	private static PosixGroupDtoDirectoryDao posixGroupDtoDao;

	private static PosixGroupDto group1, group2;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PosixGroupDtoTest.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (container == null) {
			container = S2ContainerFactory.create(PATH);
			container.init();
			posixAccountDtoDao =
				(PosixAccountDtoDirectoryDao)container.getComponent(PosixAccountDtoDirectoryDao.class);
			posixGroupDtoDao =
				(PosixGroupDtoDirectoryDao)container.getComponent(PosixGroupDtoDirectoryDao.class);
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
		PosixGroupDtoFactory posixGroupDtoFactory =
			new PosixGroupDtoFactory(container);
		// グループ1を追加します。
		List memberUid = new ArrayList();
		memberUid.add(user1.getUid());
		group1 = posixGroupDtoFactory.getGroup("group1", memberUid);
		group1.setDescription(null);
		assertEquals(null, posixGroupDtoDao.getPosixGroup(group1));
		assertEquals(1, posixGroupDtoDao.insertPosixGroup(group1));
		// グループ2を追加します。
		memberUid.add(user2.getUid());
		group2 = posixGroupDtoFactory.getGroup("group2", memberUid);
		group2.setDescription(null);
		assertEquals(null, posixGroupDtoDao.getPosixGroup(group2));
		assertEquals(1, posixGroupDtoDao.insertPosixGroup(group2));
	}

	protected void tearDown() throws Exception {
		// グループを削除します。
		assertEquals(1, posixGroupDtoDao.deletePosixGroup(group2));
		assertEquals(null, posixGroupDtoDao.getPosixGroup(group2));
		assertEquals(1, posixGroupDtoDao.deletePosixGroup(group1));
		assertEquals(null, posixGroupDtoDao.getPosixGroup(group1));
		// ユーザを削除します。
		assertEquals(1, posixAccountDtoDao.delete(user1));
		assertEquals(null, posixAccountDtoDao.getUser(user1));
		assertEquals(1, posixAccountDtoDao.delete(user2));
		assertEquals(null, posixAccountDtoDao.getUser(user2));
		super.tearDown();
	}

	public void testFilter1() {
		List list = posixGroupDtoDao.getPosixGroupList();
		assertEquals(2, list.size());
		PosixGroupDto group = (PosixGroupDto)list.get(0);
		assertEquals("group1", group.getCn());
		assertEquals(null, group.getDescription());
		assertEquals("user1", group.getMemberUid());
		group = (PosixGroupDto)list.get(1);
		assertEquals("group2", group.getCn());
		assertEquals(null, group.getDescription());
		assertEquals("user1__user2", group.getMemberUid());
	}

	public void testInsert1() {
		DirectoryControlProperty property =
			(DirectoryControlProperty)container.getComponent(DirectoryControlProperty.class);

		// 作成します。
		PosixGroupDto newgroup1 = posixGroupDtoDao.getPosixGroup(group1);
		newgroup1.setDn("cn=newgroup1,ou=Groups," + property.getBaseDn());
		newgroup1.setCn("newgroup1");
		newgroup1.setDescription("New Group Description");
		newgroup1.setField1("dummy");
		newgroup1.publicField1 = "dummy";
		assertEquals(1, posixGroupDtoDao.insertPosixGroup(newgroup1));

		// 検索します。
		PosixGroupDto search = new PosixGroupDto();
		search.setCn("newgroup1");
		PosixGroupDto dbNewgroup1 = posixGroupDtoDao.getPosixGroup(search);
		assertEquals("newgroup1", dbNewgroup1.getCn());

		// 削除します。
		posixGroupDtoDao.deletePosixGroup(dbNewgroup1);
	}

	public void testUpdate1() {
		PosixGroupDto group = posixGroupDtoDao.getPosixGroup(group1);
		group.setDescription("New Group Description");
		group.setField1("dummy");
		group.publicField1 = "dummy";
		assertEquals(1, posixGroupDtoDao.updatePosixGroup(group));
		group = posixGroupDtoDao.getPosixGroup(group1);
		assertEquals("New Group Description", group.getDescription());
		// 初期化します。
		assertEquals(1, posixGroupDtoDao.updatePosixGroup(group1));
		group = posixGroupDtoDao.getPosixGroup(group1);
		assertEquals("group1", group.getCn());
		assertEquals(null, group.getDescription());
	}

	public void testUpdate2() {
		PosixGroupDto group = posixGroupDtoDao.getPosixGroup(group1);
		String newMemberUid = group1.getMemberUid() + ",user3";
		group.setMemberUid(newMemberUid);
		assertEquals(1, posixGroupDtoDao.updatePosixGroup(group));
		group = posixGroupDtoDao.getPosixGroup(group1);
		assertEquals(newMemberUid, group.getMemberUid());
		// 初期化します。
		assertEquals(1, posixGroupDtoDao.updatePosixGroup(group1));
	}

}
