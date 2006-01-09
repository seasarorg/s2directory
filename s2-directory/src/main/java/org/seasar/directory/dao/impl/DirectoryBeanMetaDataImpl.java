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
import java.sql.DatabaseMetaData;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.seasar.directory.dao.DirectoryBeanMetaData;
import org.seasar.directory.types.PropertyType;
import org.seasar.extension.jdbc.ColumnNotFoundRuntimeException;
import org.seasar.extension.jdbc.util.DatabaseMetaDataUtil;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.PropertyNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.CaseInsensitiveMap;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.StringUtil;

/**
 * ビーンメタデータを表すクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryBeanMetaDataImpl extends DirectoryDtoMetaDataImpl
		implements DirectoryBeanMetaData {
	private static Logger logger_ = Logger
			.getLogger(DirectoryBeanMetaDataImpl.class);
	private String objectClassName;
	private Map propertyTypesByColumnName_ = new CaseInsensitiveMap();
	private String versionNoPropertyName_ = "versionNo";

	public DirectoryBeanMetaDataImpl(Class beanClass) {
		this(beanClass, false);
	}

	public DirectoryBeanMetaDataImpl(Class beanClass, boolean relation) {
		setBeanClass(beanClass);
		// relation_ = relation;
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(beanClass);
		setupObjectClassName(beanDesc);
		setupVersionNoPropertyName(beanDesc);
		setupProperty(beanDesc);
	}

	/**
	 * @see org.seasar.dao.BeanMetaData#getObjectClassName()
	 */
	public String getObjectClassName() {
		return objectClassName;
	}

	/**
	 * @see org.seasar.dao.BeanMetaData#getVersionNoPropertyType()
	 */
	public PropertyType getVersionNoPropertyType()
			throws PropertyNotFoundRuntimeException {
		return getPropertyType(versionNoPropertyName_);
	}

	/**
	 * @see org.seasar.dao.BeanMetaData#getPropertyTypeByColumnName(java.lang.String)
	 */
	public PropertyType getPropertyTypeByColumnName(String columnName)
			throws ColumnNotFoundRuntimeException {
		PropertyType propertyType = (PropertyType)propertyTypesByColumnName_
				.get(columnName);
		if (propertyType == null) {
			throw new ColumnNotFoundRuntimeException(objectClassName,
					columnName);
		}
		return propertyType;
	}

	/**
	 * @see org.seasar.dao.BeanMetaData#hasPropertyTypeByColumnName(java.lang.String)
	 */
	public boolean hasPropertyTypeByColumnName(String columnName) {
		return propertyTypesByColumnName_.get(columnName) != null;
	}

	/**
	 * @see org.seasar.dao.BeanMetaData#hasVersionNoPropertyType()
	 */
	public boolean hasVersionNoPropertyType() {
		return hasPropertyType(versionNoPropertyName_);
	}

	/**
	 * @see org.seasar.dao.BeanMetaData#convertFullColumnName(java.lang.String)
	 */
	public String convertFullColumnName(String alias) {
		if (hasPropertyTypeByColumnName(alias)) {
			return objectClassName + "." + alias;
		}
		int index = alias.lastIndexOf('_');
		if (index < 0) {
			throw new ColumnNotFoundRuntimeException(objectClassName, alias);
		}
		String columnName = alias.substring(0, index);
		return columnName;
	}

	protected void setupObjectClassName(BeanDesc beanDesc) {
		if (beanDesc.hasField(OBJECTCLASS)) {
			Field field = beanDesc.getField(OBJECTCLASS);
			objectClassName = (String)FieldUtil.get(field, null);
		}
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
			if (beanDesc.hasField(relnoKey)) {} else {
				pt = createPropertyType(beanDesc, pd);
				addPropertyType(pt);
			}
		}
	}

	protected void setupPropertyPersistentAndColumnName(BeanDesc beanDesc,
			DatabaseMetaData dbMetaData) {
		Set columnSet = DatabaseMetaDataUtil.getColumnSet(dbMetaData,
				objectClassName);
		if (columnSet.isEmpty()) {
			logger_.log("WDAO0002", new Object[] { objectClassName });
		}
		for (Iterator i = columnSet.iterator(); i.hasNext();) {
			String columnName = (String)i.next();
			String columnName2 = StringUtil.replace(columnName, "_", "");
			for (int j = 0; j < getPropertyTypeSize(); ++j) {
				PropertyType pt = getPropertyType(j);
				if (pt.getColumnName().equalsIgnoreCase(columnName2)) {
					pt.setColumnName(columnName);
					break;
				}
			}
		}
		if (beanDesc.hasField(NO_PERSISTENT_PROPS)) {
			Field field = beanDesc.getField(NO_PERSISTENT_PROPS);
			String str = (String)FieldUtil.get(field, null);
			String[] props = StringUtil.split(str, ", ");
			for (int i = 0; i < props.length; ++i) {
				PropertyType pt = getPropertyType(props[i].trim());
				pt.setPersistent(false);
			}
		} else {
			for (int i = 0; i < getPropertyTypeSize(); ++i) {
				PropertyType pt = getPropertyType(i);
				if (!columnSet.contains(pt.getColumnName())) {
					pt.setPersistent(false);
				}
			}
		}
	}
}
