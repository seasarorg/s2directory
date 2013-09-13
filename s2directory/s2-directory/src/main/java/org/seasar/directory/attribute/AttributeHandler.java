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
package org.seasar.directory.attribute;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.ModificationItem;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.DirectoryValueTypeFactory;

/**
 * 属性ハンドラのインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface AttributeHandler {
	/**
	 * ディレクトリ用の値の型ファクトリを取得します。
	 * 
	 * @return ディレクトリ用の値の型ファクトリ
	 */
	public DirectoryValueTypeFactory getDirectoryValueTypeFactory();

	/**
	 * ディレクトリ用の値の型ファクトリを設定します。
	 * 
	 * @param valueTypeFactory
	 *            ディレクトリ用の値の型ファクトリ
	 */
	public void setDirectoryValueTypeFactory(
			DirectoryValueTypeFactory valueTypeFactory);

	/**
	 * 属性ハンドラが扱う属性名のリストを設定します。
	 * 
	 * @param attributeNameList
	 *            属性ハンドラが扱う属性名のリスト
	 */
	public void setAttributeNameList(List attributeNameList);

	/**
	 * 属性ハンドラが扱う属性名のリストを取得します。
	 * 
	 * @return 属性ハンドラが扱う属性名のリスト
	 */
	public List getAttributeNameList();

	/**
	 * 指定された属性名に従った適当な属性を取得します。
	 * 
	 * @param property
	 *            ディレクトリサーバ接続情報
	 * @param attributeName
	 *            属性名
	 * @param value
	 *            値
	 * @param valueClass
	 *            値の型
	 * @return 属性
	 */
	public Attribute getAddAttribute(DirectoryControlProperty property,
			String attributeName, Object value, Class valueClass);

	/**
	 * 指定された属性名に従った適当な更新アイテムを取得します。
	 * 
	 * @param property
	 *            ディレクトリサーバ接続情報
	 * @param currentAttribute
	 *            現在の属性
	 * @param attributeName
	 *            属性名
	 * @param newValue
	 *            新しい値
	 * @param newValueClass
	 *            新しい型の型
	 * @return 更新アイテム
	 * @throws NamingException
	 */
	public ModificationItem getModificationItem(
			DirectoryControlProperty property, Attribute currentAttribute,
			String attributeName, Object newValue, Class newValueClass)
			throws NamingException;
}
