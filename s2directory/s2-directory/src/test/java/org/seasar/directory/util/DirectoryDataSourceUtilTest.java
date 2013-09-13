/*
 * Copyright 2005-2013 the Seasar Foundation and the Others.
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
package org.seasar.directory.util;

import junit.framework.TestCase;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.impl.DirectoryControlPropertyImpl;
import org.seasar.directory.impl.DirectoryDataSourceImpl;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * データソース用ユーティリティクラスのテストクラスです。
 * 
 * @author Jun Futagawa
 * @version $Revision$ $Date$
 */
public class DirectoryDataSourceUtilTest extends TestCase {
	private static final String PATH = "directory.dicon";
	/** Directory接続ファクトリを表わします。 */
	private DirectoryDataSource directoryDataSource;

	/**
	 * テストの初期設定を行います。
	 */
	public void setUp() {
		S2Container container = S2ContainerFactory.create(PATH);
		DirectoryControlProperty defaultProperty =
			(DirectoryControlProperty)container.getComponent(DirectoryControlPropertyImpl.class);
		directoryDataSource = new DirectoryDataSourceImpl(defaultProperty);
	}

	public void testGetFullUserDn() {
		DirectoryControlProperty property =
			directoryDataSource.getDirectoryControlProperty();
		//
		// bindDnが定義されている場合
		//
		DirectoryDataSourceUtil.setupDirectoryControlProperty(property);
		assertEquals("cn=Manager,dc=seasar,dc=org", property.getBindDn());
		//
		// bindDnが定義されていない場合
		//
		// ユーザ名のみの形式のテストを行います。
		property.setUser("user1 ");
		DirectoryDataSourceUtil.setupDirectoryControlProperty(property);
		assertEquals(
			"uid=user1 ,ou=Users,dc=seasar,dc=org",
			property.getBindDn());
		// ユーザ名のみの形式のテストを行います。(半角スペースあり)
		property.setUser("user 1 ");
		DirectoryDataSourceUtil.setupDirectoryControlProperty(property);
		assertEquals(
			"uid=user 1 ,ou=Users,dc=seasar,dc=org",
			property.getBindDn());
		// ユーザ識別子が付与した形式のテストを行います。
		property.setUser("uid=user1  ");
		DirectoryDataSourceUtil.setupDirectoryControlProperty(property);
		assertEquals(
			"uid=user1  ,ou=Users,dc=seasar,dc=org",
			property.getBindDn());
		// ユーザ識別子(uid)が含まれている形式のテストを行います。
		property.setUser("uiduser1,  ou=Users");
		DirectoryDataSourceUtil.setupDirectoryControlProperty(property);
		assertEquals(
			"uid=uiduser1,  ou=Users,dc=seasar,dc=org",
			property.getBindDn());
		// ユーザ識別子(uid)が含まれている形式のテストを行います。
		property.setUser("uid= uiduser1 ,	ou=Users,dc=ju");
		DirectoryDataSourceUtil.setupDirectoryControlProperty(property);
		assertEquals(
			"uid= uiduser1 ,	ou=Users,dc=ju,dc=seasar,dc=org",
			property.getBindDn());
		// ユーザ識別子が完全に指定された形式のテストを行います。
		property.setUser("uid=user1,dc=seasar,dc=org");
		DirectoryDataSourceUtil.setupDirectoryControlProperty(property);
		assertEquals("uid=user1,dc=seasar,dc=org", property.getBindDn());
		// ユーザ識別子が完全に指定された形式のテストを行います。(半角スペースあり)
		property.setBaseDn("dc= seasar, dc=org");
		property.setUser("uid=user1,dc= seasar, dc=org");
		DirectoryDataSourceUtil.setupDirectoryControlProperty(property);
		assertEquals("uid=user1,dc= seasar, dc=org", property.getBindDn());
	}
}
