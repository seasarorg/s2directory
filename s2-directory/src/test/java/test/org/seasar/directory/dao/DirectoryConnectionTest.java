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
package test.org.seasar.directory.dao;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import junit.framework.TestCase;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.impl.DirectoryControlPropertyImpl;
import org.seasar.directory.impl.DirectoryDataSourceImpl;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * 接続テストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryConnectionTest extends TestCase {
	private static final String PATH = "directory.dicon";
	/** Directory接続ファクトリを表わします。 */
	private DirectoryDataSourceImpl directoryDataSource;

	/**
	 * テストの初期設定を行います。
	 */
	public void setUp() {
		S2Container container = S2ContainerFactory.create(PATH);
		container.init();
		DirectoryControlProperty defaultProperty = (DirectoryControlProperty)container
				.getComponent(DirectoryControlPropertyImpl.class);
		directoryDataSource = new DirectoryDataSourceImpl(defaultProperty);
	}

	/**
	 * DirectoryControlPropertyのテストを行います。
	 */
	public void testDirectoryControlProperty() {
		DirectoryControlProperty property = directoryDataSource
				.getDirectoryControlProperty();
		assertEquals("com.sun.jndi.ldap.LdapCtxFactory", property
				.getInitialContextFactory());
		assertEquals("ldap://localhost:30390", property.getUrl());
		assertEquals("cn=Manager", property.getUser());
		assertEquals("secret", property.getPassword());
		assertEquals("uid", property.getUserAttributeName());
	}

	/**
	 * 接続テストを行います。
	 */
	public void testAdminConnection() {
		try {
			assertEquals(true, directoryDataSource.authenticate());
			DirContext ctx = directoryDataSource.getConnection();
			ctx.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 接続テストを行います。
	 */
	public void testUserConnection() {
		DirectoryControlProperty property = directoryDataSource
				.getDirectoryControlProperty();
		try {
			// 1
			assertEquals(true, directoryDataSource.authenticate());
			DirContext ctx = directoryDataSource.getConnection();
			ctx.close();
			// 2
			property.setUser("user1");
			property.setPassword("user1pass");
			assertEquals(true, directoryDataSource.authenticate(property));
			ctx = directoryDataSource.getConnection();
			// 3
			property.setUser("uid=user1");
			property.setPassword("user1pass");
			assertEquals(true, directoryDataSource.authenticate(property));
			ctx = directoryDataSource.getConnection();
			// 4
			property.setUser("uid=user1,ou=Users");
			property.setPassword("user1pass");
			assertEquals(true, directoryDataSource.authenticate(property));
			ctx = directoryDataSource.getConnection();
		} catch (NamingException e) {
			System.out.println(property.getUser());
			e.printStackTrace();
		}
	}
}