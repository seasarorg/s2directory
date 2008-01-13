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
package org.seasar.directory.dao.impl;

import java.lang.reflect.Field;
import java.util.Map;
import org.seasar.directory.dao.DirectoryAnnotationReaderFactory;
import org.seasar.directory.dao.DirectoryBeanMetaData;
import org.seasar.directory.types.PropertyType;
import org.seasar.extension.jdbc.ColumnNotFoundRuntimeException;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.PropertyNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.CaseInsensitiveMap;
import org.seasar.framework.util.FieldUtil;

/**
 * ビーンメタデータを表すクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryBeanMetaDataImpl extends DirectoryDtoMetaDataImpl
		implements DirectoryBeanMetaData {
	private String[] objectClasses;
	private Map propertyTypesByColumnName_ = new CaseInsensitiveMap();
	private String versionNoPropertyName_ = "versionNo";
	private DirectoryAnnotationReaderFactory annotationReaderFactory;

	public DirectoryBeanMetaDataImpl() {
	}

	public void initialize() {
		beanAnnotationReader =
			getDirectoryAnnotationReaderFactory()
				.createDirectoryBeanAnnotationReader(getBeanClass());
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(getBeanClass());
		setupObjectClasses(beanDesc);
		setupVersionNoPropertyName(beanDesc);
		setupProperty(beanDesc);
		super.initialize();
	}

	protected DirectoryAnnotationReaderFactory getDirectoryAnnotationReaderFactory() {
		return annotationReaderFactory;
	}

	public void setDirectoryAnnotationReaderFactory(
			DirectoryAnnotationReaderFactory annotationReaderFactory) {
		this.annotationReaderFactory = annotationReaderFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getObjectClasses() {
		return objectClasses;
	}

	/**
	 * {@inheritDoc}
	 */
	public PropertyType getVersionNoPropertyType()
			throws PropertyNotFoundRuntimeException {
		return getPropertyType(versionNoPropertyName_);
	}

	/**
	 * {@inheritDoc}
	 */
	public PropertyType getPropertyTypeByColumnName(String columnName)
			throws ColumnNotFoundRuntimeException {
		PropertyType propertyType =
			(PropertyType)propertyTypesByColumnName_.get(columnName);
		if (propertyType == null) {
			throw new ColumnNotFoundRuntimeException(super
				.getBeanClass()
				.getName(), columnName);
		}
		return propertyType;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasPropertyTypeByColumnName(String columnName) {
		return propertyTypesByColumnName_.get(columnName) != null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String convertFullColumnName(String alias) {
		if (hasPropertyTypeByColumnName(alias)) {
			return super.getBeanClass().getName() + "." + alias;
		}
		int index = alias.lastIndexOf('_');
		if (index < 0) {
			throw new ColumnNotFoundRuntimeException(super
				.getBeanClass()
				.getName(), alias);
		}
		String columnName = alias.substring(0, index);
		return columnName;
	}

	protected void setupObjectClasses(BeanDesc beanDesc) {
		objectClasses = beanAnnotationReader.getObjectClasses();
	}

	protected void setupVersionNoPropertyName(BeanDesc beanDesc) {
		if (beanDesc.hasField(VERSION_NO_PROPERTY)) {
			Field field = beanDesc.getField(VERSION_NO_PROPERTY);
			versionNoPropertyName_ = (String)FieldUtil.get(field, null);
		}
	}

	protected void setupProperty(BeanDesc beanDesc) {
		for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
			PropertyDesc pd = beanDesc.getPropertyDesc(i);
			PropertyType pt = null;
			String relnoKey = pd.getPropertyName() + RELNO_SUFFIX;
			if (beanDesc.hasField(relnoKey)) {
			} else {
				pt = createPropertyType(beanDesc, pd);
				addPropertyType(pt);
			}
		}
	}

	public boolean isBeanClassAssignable(Class clazz) {
		return getBeanClass().isAssignableFrom(clazz)
			|| clazz.isAssignableFrom(getBeanClass());
	}
}
