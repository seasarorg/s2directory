/*
 * Copyright 2005-2014 the Seasar Foundation and the Others.
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
import org.seasar.directory.dao.util.DaoUtil;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.StringUtil;

/**
 * ビーンクラスに定義されたフィールドアノテーションを読み込みます。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class DirectoryFieldBeanAnnotationReader implements
		DirectoryBeanAnnotationReader {

	/** オブジェクトクラスアノテーションの設定名 */
	public String OBJECTCLASSES = "OBJECTCLASSES";

	/** 永続化対象にしない属性の設定名 */
	public String NO_PERSISTENT_PROPS = "NO_PERSISTENT_PROPS";

	/** 優先度の高い属性名の設定名 */
	public String ATTRIBUTE_SUFFIX = "_ATTRIBUTE";

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
		String attributeNameKey = propertyName + ATTRIBUTE_SUFFIX;
		String columnNameKey = propertyName + COLUMN_SUFFIX;
		// ATTRIBUTEアノテーションがある場合、COLUMNアノテーションに優先して利用します。
		if (beanDesc.hasField(attributeNameKey)) {
			Field field = beanDesc.getField(attributeNameKey);
			return (String)FieldUtil.get(field, null);
		}
		// ATTRIBUTEアノテーションがなく、COLUMNアノテーションがある場合は利用します。
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
		return beanDesc.getBeanClass();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 優先度
	 * </p>
	 * <ul>
	 * <li>ビーンクラスの OBJECTCLASSES フィールドアノテーション</li>
	 * <li>ビーンクラスのクラス名</li>
	 * </ul>
	 */
	public String[] getObjectClasses() {
		String[] objectClasses = getObjectClassesFromFieldAnnotation();
		if (objectClasses != null) {
			return objectClasses;
		}

		// OBJECTCLASSES フィールドアノテーションが存在しない場合は、
		// クラス名をオブジェクトクラスとみなします。
		objectClasses = new String[1];
		objectClasses[0] = DaoUtil.getSimpleClassName(this.getBeanClass());
		return objectClasses;
	}

	/**
	 * ビーンクラスの OBJECTCLASSES フィールドアノテーションから値の配列を返します。
	 * ビーンクラスは親クラスを辿って探します。
	 * 存在しない場合は、null を返します。
	 * 
	 * @return オブジェクトクラスアノテーションの値の配列
	 */
	protected String[] getObjectClassesFromFieldAnnotation() {
		Class beanClass = getBeanClass();
		for (Class superClass = beanClass; superClass != Object.class; superClass =
			superClass.getSuperclass()) {
			BeanDesc daoBeanBeanDesc = BeanDescFactory.getBeanDesc(superClass);
			if (daoBeanBeanDesc.hasField(OBJECTCLASSES)) {
				Field queryField = daoBeanBeanDesc.getField(OBJECTCLASSES);
				String objectClassNames =
					(String)FieldUtil.get(queryField, null);
				return setupObjectClass(objectClassNames.split(","));
			}
		}
		return null;
	}

	/**
	 * オブジェクトクラスを整形して返します。
	 * 
	 * @param objectClasses
	 *            オブジェクトクラスアノテーションの値の配列
	 * @return セットアップ済みのオブジェクトクラスアノテーションの値の配列
	 */
	protected String[] setupObjectClass(String[] objectClasses) {
		if (objectClasses == null) {
			return objectClasses;
		}
		for (int i = 0; i < objectClasses.length; i++) {
			objectClasses[i] = objectClasses[i].trim();
		}
		return objectClasses;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getNoPersistentProps() {
		if (beanDesc.hasField(NO_PERSISTENT_PROPS)) {
			Field field = beanDesc.getField(NO_PERSISTENT_PROPS);
			String str = (String)FieldUtil.get(field, null);
			return StringUtil.split(str, ", ");
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
