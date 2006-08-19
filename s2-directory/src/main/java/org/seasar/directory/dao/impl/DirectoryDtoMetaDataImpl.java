/*
 * Copyright 2005-2006 the Seasar Foundation and the Others.
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

import org.seasar.directory.dao.DirectoryBeanAnnotationReader;
import org.seasar.directory.dao.DirectoryDtoMetaData;
import org.seasar.directory.dao.DirectoryValueTypeFactory;
import org.seasar.directory.types.PropertyType;
import org.seasar.directory.types.PropertyTypeImpl;
import org.seasar.directory.types.ValueType;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.PropertyNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.CaseInsensitiveMap;

/**
 * DTO用のメタデータを表すクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryDtoMetaDataImpl implements DirectoryDtoMetaData {
	/** ビーンクラスを表わします。 */
	private Class beanClass;
	private CaseInsensitiveMap propertyTypes_ = new CaseInsensitiveMap();
	/** ビーンアノテーションリーダーを表します。 */
	protected DirectoryBeanAnnotationReader directoryBeanAnnotationReader;
	/** ディレクトリ用の値の型ファクトリを表します。 */
	protected DirectoryValueTypeFactory directoryValueTypeFactory;

	/**
	 * インスタンスを作成します。
	 */
	protected DirectoryDtoMetaDataImpl() {}

	public void initialize() {
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(getBeanClass());
		setupPropertyType(beanDesc);
	}

	/**
	 * {@inheritDoc}
	 */
	public Class getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getPropertyTypeSize() {
		return propertyTypes_.size();
	}

	/**
	 * {@inheritDoc}
	 */
	public PropertyType getPropertyType(int index) {
		return (PropertyType)propertyTypes_.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	public PropertyType getPropertyType(String propertyName)
			throws PropertyNotFoundRuntimeException {
		PropertyType propertyType = (PropertyType)propertyTypes_
				.get(propertyName);
		if (propertyType == null) {
			throw new PropertyNotFoundRuntimeException(beanClass, propertyName);
		}
		return propertyType;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasPropertyType(String propertyName) {
		return propertyTypes_.get(propertyName) != null;
	}

	private void setupPropertyType(BeanDesc beanDesc) {
		for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
			PropertyDesc pd = beanDesc.getPropertyDesc(i);
			PropertyType pt = createPropertyType(beanDesc, pd);
			addPropertyType(pt);
		}
	}

	protected PropertyType createPropertyType(BeanDesc beanDesc,
			PropertyDesc propertyDesc) {
		final String columnName = getColumnName(propertyDesc);
		final ValueType valueType = getValueType(propertyDesc);
		PropertyType pt = new PropertyTypeImpl(propertyDesc, valueType,
				columnName);
		return pt;
	}

	private String getColumnName(PropertyDesc propertyDesc) {
		String columnName = directoryBeanAnnotationReader
				.getColumnAnnotation(propertyDesc);
		if (columnName != null) {
			return columnName;
		}
		return propertyDesc.getPropertyName();
	}

	protected ValueType getValueType(PropertyDesc propertyDesc) {
		final String valueTypeName = directoryBeanAnnotationReader
				.getValueType(propertyDesc);
		if (valueTypeName != null) {
			return getDirectoryValueTypeFactory().getValueTypeByName(
					valueTypeName);
		} else {
			return getDirectoryValueTypeFactory().getValueTypeByClass(
					propertyDesc.getPropertyType());
		}
	}

	protected void addPropertyType(PropertyType propertyType) {
		propertyTypes_.put(propertyType.getPropertyName(), propertyType);
	}

	public void setDirectoryBeanAnnotationReader(
			DirectoryBeanAnnotationReader directoryBeanAnnotationReader) {
		this.directoryBeanAnnotationReader = directoryBeanAnnotationReader;
	}

	/**
	 * ディレクトリ用の値の型ファクトリを取得します。
	 * 
	 * @return ディレクトリ用の値の型ファクトリ
	 */
	protected DirectoryValueTypeFactory getDirectoryValueTypeFactory() {
		return directoryValueTypeFactory;
	}

	/**
	 * ディレクトリ用の値の型ファクトリを設定します。
	 * 
	 * @param directoryValueTypeFactory ディレクトリ用の値の型ファクトリ
	 */
	public void setDirectoryValueTypeFactory(
			DirectoryValueTypeFactory directoryValueTypeFactory) {
		this.directoryValueTypeFactory = directoryValueTypeFactory;
	}
}
