/*
 * Copyright 2005-2007 the Seasar Foundation and the Others.
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

import java.util.Iterator;
import java.util.Set;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.NamingEnumerationHandler;
import org.seasar.directory.dao.DirectoryBeanMetaData;
import org.seasar.directory.types.PropertyType;
import org.seasar.directory.types.ValueType;
import org.seasar.directory.util.DirectoryDataSourceUtil;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.util.CaseInsensitiveSet;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.StringUtil;

/**
 * 検索結果の抽象ハンドラクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public abstract class AbstractBeanMetaDataNamingEnumerationHandler implements
		NamingEnumerationHandler {
	/** メタデータ */
	private DirectoryBeanMetaData beanMetaData;
	/** ディレクトリサーバ接続情報 */
	private DirectoryControlProperty property;

	/**
	 * インスタンスを作成します。
	 * 
	 * @param beanMetaData
	 *            ビーンメタデータ
	 * @param property
	 *            ディレクトリサーバ接続情報
	 */
	public AbstractBeanMetaDataNamingEnumerationHandler(
			DirectoryBeanMetaData beanMetaData,
			DirectoryControlProperty property) {
		this.beanMetaData = beanMetaData;
		this.property = property;
	}

	/**
	 * ビーンメタデータを取得します。
	 * 
	 * @return ビーンメタデータ
	 */
	public DirectoryBeanMetaData getDirectoryBeanMetaData() {
		return beanMetaData;
	}

	/**
	 * エントリを作成します。
	 * 
	 * @param result
	 * @param attributeNameSet
	 * @return
	 * @throws NamingException
	 */
	protected Object createEntry(SearchResult result, Set attributeNameSet)
			throws NamingException {
		Object entry = ClassUtil.newInstance(beanMetaData.getBeanClass());
		for (int i = 0; i < beanMetaData.getPropertyTypeSize(); ++i) {
			PropertyType pt = beanMetaData.getPropertyType(i);
			if (pt.getColumnName().equals("dn")) {
				PropertyDesc pd = pt.getPropertyDesc();
				pd.setValue(entry, result.getName());
				// JDK 5.0 later
				// pd.setValue(entry, result.getNameInNamespace());
			} else if (attributeNameSet.contains(pt.getColumnName())) {
				ValueType valueType = pt.getValueType();
				Object value =
					valueType.getReadValue(result.getAttributes(), pt
						.getColumnName(), property.getMultipleValueDelimiter());
				PropertyDesc pd = pt.getPropertyDesc();
				pd.setValue(entry, value);
			} else if (!pt.isPersistent()) {
				for (Iterator iter = attributeNameSet.iterator(); iter
					.hasNext();) {
					String columnName = (String)iter.next();
					String columnName2 =
						StringUtil.replace(columnName, "_", "");
					if (columnName2.equalsIgnoreCase(pt.getColumnName())) {
						ValueType valueType = pt.getValueType();
						Object value =
							valueType.getReadValue(
								result.getAttributes(),
								columnName,
								property.getMultipleValueDelimiter());
						PropertyDesc pd = pt.getPropertyDesc();
						pd.setValue(entry, value);
						break;
					}
				}
			}
		}
		return entry;
	}

	/**
	 * 属性名の集合を作成します。
	 * 
	 * @param result
	 * @return
	 * @throws NamingException
	 */
	protected Set createAttributesNames(SearchResult result)
			throws NamingException {
		Set columnNames = new CaseInsensitiveSet();
		Attributes attributes = result.getAttributes();
		NamingEnumeration ae = attributes.getAll();
		while (ae.hasMoreElements()) {
			Attribute attribute = (Attribute)ae.next();
			String attributeName = attribute.getID();
			columnNames.add(attributeName);
		}
		DirectoryDataSourceUtil.close(ae);
		return columnNames;
	}
}
