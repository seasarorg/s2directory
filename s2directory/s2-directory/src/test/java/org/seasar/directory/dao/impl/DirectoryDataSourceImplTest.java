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
package org.seasar.directory.dao.impl;

import javax.naming.NamingException;

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
 */
public class DirectoryDataSourceImplTest extends TestCase {

	private static final String PATH = "directory.dicon";

	private S2Container container;

	/**
	 * テストの初期設定を行います。
	 */
	public void setUp() {
		container = S2ContainerFactory.create(PATH);
		container.init();
	}

	/**
	 * DirectoryControlPropertyのテストを行います。
	 */
	public void testGetConnection() {
		DirectoryControlProperty property =
			(DirectoryControlProperty)container.getComponent(DirectoryControlPropertyImpl.class);
		DirectoryDataSourceImpl directoryDataSource =
			new DirectoryDataSourceImpl(property);
		try {
			directoryDataSource.getConnection(property);
			assertTrue(true);
		} catch (NamingException e) {
			// for ApacheDS
			property = getSuperUserPropertyOfApachDS();
			try {
				directoryDataSource.getConnection(property);
				assertTrue(true);
			} catch (NamingException e1) {
				e1.printStackTrace();
				assertTrue(false);
			}
		}
	}

	/** ApacheDSのSSL環境を用意して動作確認 */
	// public void testGetSSLConnection() {
	// DirectoryControlProperty property =
	// (DirectoryControlProperty)container
	// .getComponent(DirectoryControlPropertyImpl.class);
	// property.setUrl("ldaps://localhost:636");
	// DirectoryDataSourceImpl directoryDataSource =
	// new DirectoryDataSourceImpl(property);
	// try {
	// directoryDataSource.getConnection(property);
	// assertTrue(true);
	// } catch (NamingException e) {
	// e.printStackTrace();
	// property = getSuperUserPropertyOfApachDS();
	// assertTrue(false);
	// }
	// }
	//
	// public void testGetTLSConnection() {
	// DirectoryControlProperty property =
	// (DirectoryControlProperty)container
	// .getComponent(DirectoryControlPropertyImpl.class);
	// property.setUrl("ldap://localhost:389");
	// property.setEnableTLS(true);
	// DirectoryDataSourceImpl directoryDataSource =
	// new DirectoryDataSourceImpl(property);
	// try {
	// directoryDataSource.getConnection(property);
	// assertTrue(true);
	// } catch (NamingException e) {
	// assertTrue(false);
	// }
	// }
	//
	// public void testGetSSLTLSConnection() {
	// DirectoryControlProperty property =
	// (DirectoryControlProperty)container
	// .getComponent(DirectoryControlPropertyImpl.class);
	// property.setUrl("ldaps://localhost:389");
	// property.setEnableTLS(true);
	// DirectoryDataSourceImpl directoryDataSource =
	// new DirectoryDataSourceImpl(property);
	// try {
	// directoryDataSource.getConnection(property);
	// assertTrue(false);
	// } catch (NamingException e) {
	// assertTrue(true);
	// }
	// }

	private DirectoryControlProperty getSuperUserPropertyOfApachDS() {
		DirectoryControlProperty property =
			(DirectoryControlProperty)container.getComponent(DirectoryControlProperty.class);
		property.setUser("uid=admin");
		property.setPassword("secret");
		property.setUserSuffix("");
		property.setBaseDn("ou=system");
		return property;
	}

}
