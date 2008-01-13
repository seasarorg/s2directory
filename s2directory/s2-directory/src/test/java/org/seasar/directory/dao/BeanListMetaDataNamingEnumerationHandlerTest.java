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

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import junit.framework.TestCase;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.dao.DirectoryBeanMetaData;
import org.seasar.directory.dao.handler.BeanListMetaDataNamingEnumerationHandler;
import org.seasar.directory.impl.DirectoryControlPropertyImpl;
import org.seasar.directory.impl.DirectoryDataSourceImpl;
import org.seasar.directory.util.DirectoryDataSourceUtil;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * List型ハンドラのテストクラスです。
 * 
 * @author Jun Futagawa
 * @version $Revision$ $Date$
 */
public class BeanListMetaDataNamingEnumerationHandlerTest extends TestCase {
	private static final String PATH = "directory.dicon";
	/** Directory接続ファクトリを表わします。 */
	private DirectoryDataSourceImpl dataSource;
	private DirectoryBeanMetaData beanMetaData;

	/**
	 * テストの初期設定を行います。
	 */
	public void setUp() {
		S2Container container = S2ContainerFactory.create(PATH);
		DirectoryControlProperty defaultProperty =
			(DirectoryControlProperty)container
				.getComponent(DirectoryControlPropertyImpl.class);
		dataSource = new DirectoryDataSourceImpl(defaultProperty);
	}

	public void testHandle() {
		BeanListMetaDataNamingEnumerationHandler handler =
			new BeanListMetaDataNamingEnumerationHandler(
				beanMetaData,
				dataSource.getDirectoryControlProperty());
		DirContext context = null;
		NamingEnumeration ne = null;
		try {
			context = dataSource.getConnection();
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			ne =
				context.search(
					"dc=seasar,dc=org",
					"objectClass=posixGroup",
					controls);
			handler.handle(ne, "dc=seasar,dc=org");
		} catch (NamingException e) {
			assertTrue(true);
		} finally {
			DirectoryDataSourceUtil.close(ne);
			DirectoryDataSourceUtil.close(context);
		}
	}
}
