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
package org.seasar.directory.dao.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchResult;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.dao.DirectoryBeanMetaData;
import org.seasar.directory.util.DirectoryDataSourceUtil;

/**
 * 検索結果のList型ハンドラクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class BeanListMetaDataNamingEnumerationHandler extends
		AbstractBeanMetaDataNamingEnumerationHandler {
	/**
	 * インスタンスを作成します。
	 * 
	 * @param beanMetaData
	 *            ビーンメタデータ
	 * @param property
	 *            ディレクトリサーバ接続情報
	 */
	public BeanListMetaDataNamingEnumerationHandler(
			DirectoryBeanMetaData beanMetaData,
			DirectoryControlProperty property) {
		super(beanMetaData, property);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * リスト型にして返却します。
	 * </p>
	 */
	public Object handle(NamingEnumeration results, String baseDn)
			throws NamingException {
		List list = new ArrayList();
		while (results.hasMore()) {
			SearchResult result = (SearchResult)results.next();
			result.setName(result.getName() + "," + baseDn);
			// attributeNameSetはエントリごとに持つ属性が異なるので
			// キャッシュしてはいけない
			Set attributeNameSet = createAttributesNames(result);
			list.add(createEntry(result, attributeNameSet));
		}
		DirectoryDataSourceUtil.close(results);
		return list;
	}
}
