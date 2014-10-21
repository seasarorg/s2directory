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
package org.seasar.directory.impl;

import java.util.Hashtable;

import javax.naming.directory.SearchControls;

import junit.framework.TestCase;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * 接続テストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class DirectoryControlPropertyImplTest extends TestCase {

	private static final String PATH = "directory.dicon";

	private DirectoryControlProperty property1;

	private DirectoryControlProperty property2;

	/** Directory接続ファクトリを表わします。 */
	private DirectoryDataSourceImpl dataSource;

	/**
	 * テストの初期設定を行います。
	 */
	public void setUp() {
		S2Container container = S2ContainerFactory.create(PATH);
		container.init();
		property1 =
			(DirectoryControlProperty)container.getComponent(DirectoryControlPropertyImpl.class);
		property2 =
			(DirectoryControlProperty)container.getComponent(DirectoryControlPropertyImpl.class);
		dataSource = new DirectoryDataSourceImpl(property1);
	}

	/**
	 * DirectoryControlPropertyのテストを行います。
	 */
	public void testDirectoryControlProperty() {
		DirectoryControlProperty property =
			dataSource.getDirectoryControlProperty();
		assertEquals(
			"com.sun.jndi.ldap.LdapCtxFactory",
			property.getInitialContextFactory());
		assertEquals(
			"org.seasar.directory.impl.PermissiveSSLSocketFactory",
			property.getSslSocketFactory());
		// assertEquals("ldap://localhost:389", property.getUrl());
		assertEquals("cn=Manager,dc=seasar,dc=org", property.getBindDn());
		assertEquals(null, property.getUser());
		assertEquals("secret", property.getPassword());
		assertEquals("uid", property.getUserAttributeName());
		assertEquals("SHA", property.getPasswordAlgorithm());
		assertEquals(
			"DEFAULT_MULTIPLE_VALUE_DELIMITER",
			property.getMultipleValueDelimiter());
		assertEquals(false, property.isEnableSSL());
	}

	public void testLdaps() {
		DirectoryControlProperty property =
			dataSource.getDirectoryControlProperty();
		property.setUrl("ldaps://localhost:389");
		assertEquals(true, property.isEnableSSL());
	}

	/**
	 * defaultEnvironmentのテストを行います。
	 */
	public void testDefaultEnvironment() {
		Hashtable environment1 = property1.getDefaultEnvironment();
		assertEquals(
			"500",
			environment1.get("com.sun.jndi.ldap.connect.timeout"));
		assertEquals("5000", environment1.get("com.sun.jndi.ldap.read.timeout"));
		assertNull(environment1.get("dummy.not.found"));

		// instance=prototype を確認します。
		environment1.put("com.sun.jndi.ldap.connect.timeout", "1000");
		assertEquals(
			"1000",
			environment1.get("com.sun.jndi.ldap.connect.timeout"));

		Hashtable environmen2 = property2.getDefaultEnvironment();
		assertEquals(
			"500",
			environmen2.get("com.sun.jndi.ldap.connect.timeout"));
		assertEquals("5000", environmen2.get("com.sun.jndi.ldap.read.timeout"));
		assertNull(environmen2.get("dummy.not.found"));

		try {
			property1.setDefaultEnvironment(null);
			property1.toString();
			assertTrue(true);
		} catch (NullPointerException e) {
			fail();
		}
	}

	/**
	 * defaultSearchControlsのテストを行います。
	 */
	public void testDefaultSearchControls() {
		SearchControls controls1 = property1.getDefaultSearchControls();
		assertEquals(1000, controls1.getCountLimit());
		assertEquals(false, controls1.getDerefLinkFlag());
		assertEquals(null, controls1.getReturningAttributes());
		assertEquals(SearchControls.SUBTREE_SCOPE, controls1.getSearchScope());
		assertEquals(5000, controls1.getTimeLimit());

		// instance=prototype を確認します。
		controls1.setCountLimit(2000);
		assertEquals(2000, controls1.getCountLimit());

		SearchControls controls2 = property2.getDefaultSearchControls();
		assertEquals(1000, controls2.getCountLimit());
		assertEquals(false, controls2.getDerefLinkFlag());
		assertEquals(null, controls2.getReturningAttributes());
		assertEquals(SearchControls.SUBTREE_SCOPE, controls2.getSearchScope());
		assertEquals(5000, controls2.getTimeLimit());

		try {
			property1.setDefaultSearchControls(null);
			property1.toString();
			assertTrue(true);
		} catch (NullPointerException e) {
			fail();
		}
	}

	/**
	 * defaultObjectClassesのテストを行います。
	 */
	public void testDefaultObjectClasses() {
		String[] objectClasses1 = property1.getAbstractObjectClasses();
		assertEquals(2, objectClasses1.length);

		// instance=prototype を確認します。
		property1.setAbstractObjectClasses(new String[] { "aaa", "bbb", "ccc" });
		assertEquals(3, property1.getAbstractObjectClasses().length);

		assertEquals(2, property2.getAbstractObjectClasses().length);

		try {
			property1.setAbstractObjectClasses(null);
			property1.toString();
			assertTrue(true);
		} catch (NullPointerException e) {
			fail();
		}
	}

}
