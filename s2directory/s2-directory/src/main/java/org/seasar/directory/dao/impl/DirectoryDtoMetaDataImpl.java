/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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

import java.lang.reflect.Field;
import org.seasar.directory.dao.DirectoryDtoMetaData;
import org.seasar.directory.types.PropertyType;
import org.seasar.directory.types.PropertyTypeImpl;
import org.seasar.directory.types.ValueType;
import org.seasar.directory.types.ValueTypes;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.PropertyNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.CaseInsensitiveMap;
import org.seasar.framework.util.FieldUtil;

/**
 * DTO用のメタデータを表すクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryDtoMetaDataImpl implements DirectoryDtoMetaData {
	private Class beanClass_;
	private CaseInsensitiveMap propertyTypes_ = new CaseInsensitiveMap();

	protected DirectoryDtoMetaDataImpl() {}

	public DirectoryDtoMetaDataImpl(Class beanClass) {
		beanClass_ = beanClass;
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(beanClass);
		setupPropertyType(beanDesc);
	}

	/**
	 * @see org.seasar.dao.DirectoryDtoMetaData#getBeanClass()
	 */
	public Class getBeanClass() {
		return beanClass_;
	}

	protected void setBeanClass(Class beanClass) {
		beanClass_ = beanClass;
	}

	/**
	 * @see org.seasar.dao.DirectoryDtoMetaData#getPropertyTypeSize()
	 */
	public int getPropertyTypeSize() {
		return propertyTypes_.size();
	}

	/**
	 * @see org.seasar.dao.DirectoryDtoMetaData#getPropertyType(int)
	 */
	public PropertyType getPropertyType(int index) {
		return (PropertyType)propertyTypes_.get(index);
	}

	/**
	 * @see org.seasar.dao.DirectoryDtoMetaData#getPropertyType(java.lang.String)
	 */
	public PropertyType getPropertyType(String propertyName)
			throws PropertyNotFoundRuntimeException {
		PropertyType propertyType = (PropertyType)propertyTypes_
				.get(propertyName);
		if (propertyType == null) {
			throw new PropertyNotFoundRuntimeException(beanClass_, propertyName);
		}
		return propertyType;
	}

	/**
	 * @see org.seasar.dao.DirectoryDtoMetaData#hasPropertyType(java.lang.String)
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
		String columnNameKey = propertyDesc.getPropertyName()
				+ COLUMN_KEY_SUFFIX;
		String columnName = propertyDesc.getPropertyName();
		if (beanDesc.hasField(columnNameKey)) {
			Field field = beanDesc.getField(columnNameKey);
			columnName = (String)FieldUtil.get(field, null);
		}
		ValueType valueType = ValueTypes.getValueType(propertyDesc
				.getPropertyType());
		PropertyType pt = new PropertyTypeImpl(propertyDesc, valueType,
				columnName);
		return pt;
	}

	protected void addPropertyType(PropertyType propertyType) {
		propertyTypes_.put(propertyType.getPropertyName(), propertyType);
	}
}