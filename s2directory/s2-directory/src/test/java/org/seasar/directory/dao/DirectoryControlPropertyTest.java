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
package org.seasar.directory.dao;

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
public class DirectoryControlPropertyTest extends TestCase {
	private static final String PATH = "directory.dicon";
	/** Directory接続ファクトリを表わします。 */
	private DirectoryDataSourceImpl dataSource;

	/**
	 * テストの初期設定を行います。
	 */
	public void setUp() {
		S2Container container = S2ContainerFactory.create(PATH);
		container.init();
		DirectoryControlProperty defaultProperty =
			(DirectoryControlProperty)container
				.getComponent(DirectoryControlPropertyImpl.class);
		dataSource = new DirectoryDataSourceImpl(defaultProperty);
	}

	/**
	 * DirectoryControlPropertyのテストを行います。
	 */
	public void testDirectoryControlProperty() {
		DirectoryControlProperty property =
			dataSource.getDirectoryControlProperty();
		assertEquals("com.sun.jndi.ldap.LdapCtxFactory", property
			.getInitialContextFactory());
		assertEquals(
			"org.seasar.directory.impl.PermissiveSSLSocketFactory",
			property.getSslSocketFactory());
		// assertEquals("ldap://localhost:389", property.getUrl());
		assertEquals("cn=Manager,dc=seasar,dc=org", property.getBindDn());
		assertEquals(null, property.getUser());
		assertEquals("secret", property.getPassword());
		assertEquals("uid", property.getUserAttributeName());
		assertEquals("SHA", property.getPasswordAlgorithm());
		assertEquals("__", property.getMultipleValueDelimiter());
		assertEquals(false, property.isEnableSSL());
	}

	public void testLdaps() {
		DirectoryControlProperty property =
			dataSource.getDirectoryControlProperty();
		property.setUrl("ldaps://localhost:389");
		assertEquals(true, property.isEnableSSL());
		// property.setUrl("ldap://localhost:389");
		// System.out.println("property: " + property.getUrl());
		// System.out.println("property: " + property.isUseSsl());
		// assertEquals(false, property.isUseSsl());
	}
}
