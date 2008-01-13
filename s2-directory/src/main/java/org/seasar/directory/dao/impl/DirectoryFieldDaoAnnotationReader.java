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
import java.lang.reflect.Method;

import org.seasar.directory.dao.DirectoryDaoAnnotationReader;
import org.seasar.directory.dao.util.DaoUtil;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.StringUtil;

/**
 * フィールドアノテーションを読み込むクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryFieldDaoAnnotationReader implements
		DirectoryDaoAnnotationReader {
	/** BEANアノテーションの設定名 */
	public String BEAN = "BEAN";
	/** オブジェクトクラスアノテーション */
	public String OBJECTCLASSES = "OBJECTCLASSES";
	/** ARGSアノテーションの設定名 */
	public String ARGS_SUFFIX = "_ARGS";
	/** FILTERアノテーションの設定名 */
	public String FILTER_SUFFIX = "_FILTER";
	/** QUERYアノテーションの設定名 */
	public String QUERY_SUFFIX = "_QUERY";
	/** 永続化対象にしない属性の設定名 */
	public String NO_PERSISTENT_PROPS_SUFFIX = "_NO_PERSISTENT_PROPS";
	/** この属性だけ永続化する属性の設定名 */
	public String PERSISTENT_PROPS_SUFFIX = "_PERSISTENT_PROPS";
	/** Daoクラスのメタ情報 */
	protected BeanDesc daoBeanDesc;
	/** 基底オブジェクトクラス */
	private final static String BASE_OBJECTCLASS = "top";

	/**
	 * 指定されたメタ情報を持つインスタンスを作成します。
	 * 
	 * @param daoBeanDesc
	 */
	public DirectoryFieldDaoAnnotationReader(BeanDesc daoBeanDesc) {
		this.daoBeanDesc = daoBeanDesc;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getArgNames(Method method) {
		String methodName = method.getName();
		String argsKey = methodName + ARGS_SUFFIX;
		if (daoBeanDesc.hasField(argsKey)) {
			Field argNamesField = daoBeanDesc.getField(argsKey);
			String argNames = (String)FieldUtil.get(argNamesField, null);
			return StringUtil.split(argNames, " ,");
		} else {
			return new String[0];
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String getQuery(Method method) {
		String methodName = method.getName();
		String key = methodName + QUERY_SUFFIX;
		if (daoBeanDesc.hasField(key)) {
			Field queryField = daoBeanDesc.getField(key);
			return (String)FieldUtil.get(queryField, null);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Class getBeanClass() {
		Field beanField = daoBeanDesc.getField(BEAN);
		return (Class)FieldUtil.get(beanField, null);
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getObjectClasses(String[] beanObjectClasses) {
		String[] tmpObjectClasses, objectClasses;
		if (daoBeanDesc.hasField(OBJECTCLASSES)) {
			Field queryField = daoBeanDesc.getField(OBJECTCLASSES);
			String objectClassNames = (String)FieldUtil.get(queryField, null);
			tmpObjectClasses = objectClassNames.split(",");
		} else {
			if (beanObjectClasses != null) {
				// ビーンクラスにOBJECTCLASSESアノテーションがある場合
				tmpObjectClasses = beanObjectClasses;
			} else {
				// ビーンクラスにOBJECTCLASSESアノテーションがない場合
				tmpObjectClasses = new String[1];
				tmpObjectClasses[0] =
					DaoUtil.getSimpleClassName(this.getBeanClass());
			}
		}
		// top オブジェクトクラスを持っていない場合、追加します。
		boolean hasTopObjectClass = false;
		for (int i = 0; i < tmpObjectClasses.length; i++) {
			tmpObjectClasses[i] = tmpObjectClasses[i].trim();
			if (tmpObjectClasses[i].equals(BASE_OBJECTCLASS)) {
				hasTopObjectClass = true;
			}
		}
		if (hasTopObjectClass) {
			objectClasses = tmpObjectClasses;
		} else {
			int length = tmpObjectClasses.length;
			objectClasses = new String[length + 1];
			System.arraycopy(tmpObjectClasses, 0, objectClasses, 0, length);
			objectClasses[length] = BASE_OBJECTCLASS;
		}
		return objectClasses;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getNoPersistentProps(Method method) {
		String methodName = method.getName();
		return getProps(methodName, methodName + NO_PERSISTENT_PROPS_SUFFIX);
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getPersistentProps(Method method) {
		String methodName = method.getName();
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
		if (daoBeanDesc.hasField(fieldName)) {
			Field field = daoBeanDesc.getField(fieldName);
			String s = (String)FieldUtil.get(field, null);
			return StringUtil.split(s, ", ");
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getFilter(Method method) {
		String methodName = method.getName();
		String key = methodName + FILTER_SUFFIX;
		if (daoBeanDesc.hasField(key)) {
			Field queryField = daoBeanDesc.getField(key);
			return (String)FieldUtil.get(queryField, null);
		}
		return null;
	}
}
