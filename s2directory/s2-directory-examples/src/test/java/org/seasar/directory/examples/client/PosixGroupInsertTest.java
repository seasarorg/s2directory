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
package org.seasar.directory.examples.client;

import java.util.ArrayList;
import java.util.List;
import org.seasar.directory.examples.common.PosixAccountDtoFactory;
import org.seasar.directory.examples.common.PosixGroupFactory;
import org.seasar.directory.examples.directorydao.PosixAccountDtoDirectoryDao;
import org.seasar.directory.examples.directorydao.PosixGroupDirectoryDao;
import org.seasar.directory.examples.dto.PosixAccountDto;
import org.seasar.directory.examples.entity.PosixGroup;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * PosixAccountエントリの追加テストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class PosixGroupInsertTest extends DefaultDirectoryInformationTreeTest {
	private static final String PATH = "app.dicon";
	private static S2Container container;
	private static PosixAccountDtoDirectoryDao posixAccountDtoDao;
	private static PosixAccountDto user1, user2;
	private static PosixGroupDirectoryDao posixGroupDao;
	private static PosixGroup group1;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PosixGroupInsertTest.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (container == null) {
			container = S2ContainerFactory.create(PATH);
			container.init();
			posixAccountDtoDao =
				(PosixAccountDtoDirectoryDao)container
					.getComponent(PosixAccountDtoDirectoryDao.class);
			posixGroupDao =
				(PosixGroupDirectoryDao)container
					.getComponent(PosixGroupDirectoryDao.class);
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
		group1 = posixGroupFactory.getGroup("group1", memberUid);
		assertEquals(null, posixGroupDao.getPosixGroup(group1));
		assertEquals(1, posixGroupDao.insert(group1));
	}

	protected void tearDown() throws Exception {
		// グループを削除します。
		assertEquals(1, posixGroupDao.delete(group1));
		assertEquals(null, posixGroupDao.getPosixGroup(group1));
		// ユーザを削除します。
		assertEquals(1, posixAccountDtoDao.delete(user1));
		assertEquals(null, posixAccountDtoDao.getUser(user1));
		assertEquals(1, posixAccountDtoDao.delete(user2));
		assertEquals(null, posixAccountDtoDao.getUser(user2));
		super.tearDown();
	}

	public void testInsertCheck() {
		PosixGroup group = posixGroupDao.getPosixGroup(group1);
		assertEquals("user1__user2", group.getMemberUid());
	}
}
