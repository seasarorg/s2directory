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
package org.seasar.directory.attribute;

import java.util.Arrays;
import java.util.List;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.DirectoryValueTypeFactory;
import org.seasar.directory.types.ValueType;
import org.seasar.framework.util.StringUtil;

/**
 * 属性ハンドラの抽象クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public abstract class AbstractAttributeHandler implements AttributeHandler {
	/** ディレクトリ用の値の型ファクトリ */
	private DirectoryValueTypeFactory valueTypeFactory;
	/** 属性ハンドラが扱う属性名のリスト */
	private List attributeNameList;

	/**
	 * 指定された現在の値と新しい値が属性値として等しいか比較します。
	 * 
	 * @param property
	 *            ディレクトリサーバ接続情報
	 * @param currentValue
	 *            現在の値
	 * @param newValue
	 *            新しい値
	 * @return 等しい場合 true 等しくない場合 false
	 */
	public abstract boolean equalAttributeValue(
			DirectoryControlProperty property, Object currentValue,
			Object newValue);

	/**
	 * 指定された値を属性値に変換します。
	 * 
	 * @param property
	 *            ディレクトリサーバ接続情報
	 * @param value
	 *            値
	 * @return 属性値
	 */
	public abstract Object toAttributeValue(DirectoryControlProperty property,
			Object value);

	/**
	 * {@inheritDoc}
	 */
	public DirectoryValueTypeFactory getDirectoryValueTypeFactory() {
		return valueTypeFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDirectoryValueTypeFactory(
			DirectoryValueTypeFactory valueTypeFactory) {
		this.valueTypeFactory = valueTypeFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAttributeNameList(List attributeNameList) {
		this.attributeNameList = attributeNameList;
	}

	/**
	 * {@inheritDoc}
	 */
	public List getAttributeNameList() {
		return attributeNameList;
	}

	/**
	 * {@inheritDoc}
	 */
	public Attribute getAddAttribute(DirectoryControlProperty property,
			String attributeName, Object value, Class valueClass) {
		// 追加する値を属性名に適した値に変換します。
		value = toAttributeValue(property, value);
		if (value == null)
			return null;
		DirectoryValueTypeFactory valueTypeFactory =
			getDirectoryValueTypeFactory();
		ValueType type = valueTypeFactory.getValueTypeByClass(valueClass);
		String stringValue = String.valueOf(value);
		ValueType stringValueType = valueTypeFactory.getStringValueType();
		if (type == stringValueType
			&& stringValue.indexOf(property.getMultipleValueDelimiter()) != -1) {
			// String型で定義されていて、多重属性を持つ場合List型に変換します。
			value =
				Arrays.asList(stringValue.split(property
					.getMultipleValueDelimiter()));
			type = valueTypeFactory.getListValueType();
		}
		return type.getWriteValue(attributeName, value, property
			.getMultipleValueDelimiter());
	}

	/**
	 * {@inheritDoc}
	 */
	public ModificationItem getModificationItem(
			DirectoryControlProperty property, Attribute currentAttribute,
			String attributeName, Object newValue, Class newValueClass)
			throws NamingException {
		if (currentAttribute != null) {
			// 属性がある場合
			DirectoryValueTypeFactory valueTypeFactory =
				getDirectoryValueTypeFactory();
			ValueType stringValueType = valueTypeFactory.getStringValueType();
			String stringValue = String.valueOf(newValue);
			Object currentValue =
				stringValueType.getReadValue(currentAttribute, property
					.getMultipleValueDelimiter());
			if (newValue == null || StringUtil.isEmpty(stringValue)) {
				// 値が空の場合、削除します。
				return new ModificationItem(
					DirContext.REMOVE_ATTRIBUTE,
					currentAttribute);
			} else if (!equalAttributeValue(property, currentValue, newValue)) {
				// 値があり、現在の値と異なる場合、更新します。
				Attribute attribute =
					getAddAttribute(
						property,
						attributeName,
						newValue,
						newValueClass);
				return new ModificationItem(
					DirContext.REPLACE_ATTRIBUTE,
					attribute);
			}
		} else {
			Attribute attribute =
				getAddAttribute(
					property,
					attributeName,
					newValue,
					newValueClass);
			if (attribute != null) {
				return new ModificationItem(DirContext.ADD_ATTRIBUTE, attribute);
			}
		}
		return null;
	}
}
