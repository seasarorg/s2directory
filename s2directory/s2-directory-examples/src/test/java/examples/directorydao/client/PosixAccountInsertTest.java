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
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;
import examples.directorydao.common.PosixAccountDtoFactory;
import examples.directorydao.directorydao.PosixAccountDtoDao;
import examples.directorydao.dto.PosixAccountDto;

/**
 * PosixAccountエントリの追加テストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class PosixAccountInsertTest extends TestCase {
	private static final String PATH = "app.dicon";
	private static S2Container container;
	private static PosixAccountDtoDao posixAccountDtoDao;
	private static PosixAccountDto user1;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PosixAccountInsertTest.class);
	}

	protected void setUp() throws Exception {
		container = S2ContainerFactory.create(PATH);
		container.init();
		posixAccountDtoDao = (PosixAccountDtoDao)container
				.getComponent(PosixAccountDtoDao.class);
		PosixAccountDtoFactory posixAccountDtoFactory = new PosixAccountDtoFactory(
				container);
		user1 = posixAccountDtoFactory.getUser("user1");
	}

	protected void tearDown() throws Exception {
		PosixAccountDto account = new PosixAccountDto();
		account.setDn(user1.getDn());
		assertEquals(1, posixAccountDtoDao.delete(account));
		assertEquals(null, posixAccountDtoDao.getUser(account));
	}

	public void testCreate1() {
		assertEquals(null, posixAccountDtoDao.getUser(user1));
		assertEquals(1, posixAccountDtoDao.insert(user1));
		PosixAccountDto account = posixAccountDtoDao.getUser(user1);
		assertEquals(user1.getDn(), account.getDn());
		assertEquals(user1.getCn(), account.getCn());
		assertEquals(user1.getSn(), account.getSn());
		assertEquals(user1.getUidNumber(), account.getUidNumber());
	}
}
