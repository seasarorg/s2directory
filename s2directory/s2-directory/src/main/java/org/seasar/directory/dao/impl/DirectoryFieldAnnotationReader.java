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
import org.seasar.directory.dao.DirectoryDaoAnnotationReader;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.StringUtil;

/**
 * フィールドアノテーションを読み込むクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryFieldAnnotationReader implements
		DirectoryDaoAnnotationReader {
	/** BEANアノテーションの設定名を表します。 */
	public String BEAN = "BEAN";
	/** ARGSアノテーションの設定名を表します。 */
	public String ARGS_SUFFIX = "_ARGS";
	/** FILTERアノテーションの設定名を表します。 */
	public String FILTER_SUFFIX = "_FILTER";
	/** QUERYアノテーションの設定名を表します。 */
	public String QUERY_SUFFIX = "_QUERY";
	/** 永続化対象にしない属性の設定名を表します。 */
	public String NO_PERSISTENT_PROPS_SUFFIX = "_NO_PERSISTENT_PROPS";
	/** この属性だけ永続化する属性の設定名を表します。 */
	public String PERSISTENT_PROPS_SUFFIX = "_PERSISTENT_PROPS";
	/** メタ情報を表わします。 */
	protected BeanDesc daoBeanDesc;

	/**
	 * 指定されたメタ情報を持つインスタンスを作成します。
	 * 
	 * @param daoBeanDesc
	 */
	public DirectoryFieldAnnotationReader(BeanDesc daoBeanDesc) {
		this.daoBeanDesc = daoBeanDesc;
	}

	/**
	 * ARGSアノテーションを取得します。
	 * 
	 * @param methodName
	 * @return
	 * @see org.seasar.directory.dao.DirectoryDaoAnnotationReader#getArgNames(java.lang.String)
	 */
	public String[] getArgNames(String methodName) {
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
	 * QUERYアノテーションを取得します。
	 * 
	 * @param methodName
	 * @return
	 * @see org.seasar.directory.dao.DirectoryDaoAnnotationReader#getQuery(java.lang.String)
	 */
	public String getQuery(String methodName) {
		String key = methodName + QUERY_SUFFIX;
		if (daoBeanDesc.hasField(key)) {
			Field queryField = daoBeanDesc.getField(key);
			return (String)FieldUtil.get(queryField, null);
		} else {
			return null;
		}
	}

	/**
	 * メタ情報のクラスを取得します。
	 * 
	 * @return
	 * @see org.seasar.directory.dao.DirectoryDaoAnnotationReader#getBeanClass()
	 */
	public Class getBeanClass() {
		Field beanField = daoBeanDesc.getField(BEAN);
		return (Class)FieldUtil.get(beanField, null);
	}

	/**
	 * 永続化対象にしない属性を取得します。
	 * 
	 * @param methodName
	 * @return
	 * @see org.seasar.directory.dao.DirectoryDaoAnnotationReader#getNoPersistentProps(java.lang.String)
	 */
	public String[] getNoPersistentProps(String methodName) {
		return getProps(methodName, methodName + NO_PERSISTENT_PROPS_SUFFIX);
	}

	/**
	 * この属性だけ永続化する属性を取得します。
	 * 
	 * @param methodName
	 * @return
	 * @see org.seasar.directory.dao.DirectoryDaoAnnotationReader#getPersistentProps(java.lang.String)
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
		if (daoBeanDesc.hasField(fieldName)) {
			Field field = daoBeanDesc.getField(fieldName);
			String s = (String)FieldUtil.get(field, null);
			return StringUtil.split(s, ", ");
		}
		return null;
	}

	/**
	 * FILTERアノテーションを取得します。
	 * 
	 * @param methodName
	 * @return
	 * @see org.seasar.directory.dao.DirectoryDaoAnnotationReader#getFilter(java.lang.String)
	 */
	public String getFilter(String methodName) {
		String key = methodName + FILTER_SUFFIX;
		if (daoBeanDesc.hasField(key)) {
			Field queryField = daoBeanDesc.getField(key);
			return (String)FieldUtil.get(queryField, null);
		}
		return null;
	}
}
