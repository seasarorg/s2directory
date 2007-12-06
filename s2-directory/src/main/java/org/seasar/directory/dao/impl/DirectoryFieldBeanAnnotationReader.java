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

import java.lang.reflect.Field;
import org.seasar.directory.dao.DirectoryBeanAnnotationReader;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.StringUtil;

/**
 * フィールドアノテーションを読み込むクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryFieldBeanAnnotationReader implements
		DirectoryBeanAnnotationReader {
	/** BEANアノテーションの設定名 */
	public String BEAN = "BEAN";
	/** オブジェクトクラスアノテーションの設定名 */
	public String OBJECTCLASSES = "OBJECTCLASSES";
	/** 永続化対象にしない属性の設定名 */
	public String NO_PERSISTENT_PROPS_SUFFIX = "_NO_PERSISTENT_PROPS";
	/** この属性だけ永続化する属性の設定名 */
	public String PERSISTENT_PROPS_SUFFIX = "_PERSISTENT_PROPS";
	/** 属性名の設定名 */
	public String COLUMN_SUFFIX = "_COLUMN";
	/** 値の型の設定名 */
	public String VALUE_TYPE_SUFFIX = "_VALUE_TYPE";
	/** メタ情報 */
	protected BeanDesc beanDesc;

	/**
	 * 指定されたメタ情報を持つインスタンスを作成します。
	 * 
	 * @param beanClass
	 *            ビーンクラス
	 */
	public DirectoryFieldBeanAnnotationReader(Class beanClass) {
		this.beanDesc = BeanDescFactory.getBeanDesc(beanClass);
	}

	/**
	 * {@inheritDoc}
	 */
	public String getColumnAnnotation(PropertyDesc pd) {
		String propertyName = pd.getPropertyName();
		String columnNameKey = propertyName + COLUMN_SUFFIX;
		if (beanDesc.hasField(columnNameKey)) {
			Field field = beanDesc.getField(columnNameKey);
			return (String)FieldUtil.get(field, null);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class getBeanClass() {
		Field beanField = beanDesc.getField(BEAN);
		return (Class)FieldUtil.get(beanField, null);
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getObjectClasses() {
		if (beanDesc.hasField(OBJECTCLASSES)) {
			Field queryField = beanDesc.getField(OBJECTCLASSES);
			String objectClassNames = (String)FieldUtil.get(queryField, null);
			return objectClassNames.split(",");
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getNoPersistentProps(String methodName) {
		return getProps(methodName, methodName + NO_PERSISTENT_PROPS_SUFFIX);
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getPersistentProps(String methodName) {
		return getProps(methodName, methodName + PERSISTENT_PROPS_SUFFIX);
	}

	/**
	 * 指定された関数用のフィールドを取得します。
	 * 
	 * @param methodName
	 * @param fieldName
	 * @return
	 */
	private String[] getProps(String methodName, String fieldName) {
		if (beanDesc.hasField(fieldName)) {
			Field field = beanDesc.getField(fieldName);
			String s = (String)FieldUtil.get(field, null);
			return StringUtil.split(s, ", ");
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getValueType(PropertyDesc pd) {
		String valueTypeKey = pd.getPropertyName() + VALUE_TYPE_SUFFIX;
		if (beanDesc.hasField(valueTypeKey)) {
			Field field = beanDesc.getField(valueTypeKey);
			return (String)FieldUtil.get(field, null);
		}
		return null;
	}
}
