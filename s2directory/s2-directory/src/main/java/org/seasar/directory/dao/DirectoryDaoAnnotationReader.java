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
package org.seasar.directory.dao;

import java.lang.reflect.Method;

/**
 * アノテーションを読み込むインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public interface DirectoryDaoAnnotationReader {

	/**
	 * QUERYアノテーションを取得します。
	 * 
	 * @param method
	 *            関数
	 * @return QUERYアノテーションの値
	 */
	public String getQuery(Method method);

	/**
	 * ARGSアノテーションを取得します。
	 * 
	 * @param method
	 *            関数名
	 * @return ARGSアノテーションの値
	 */
	public String[] getArgNames(Method method);

	/**
	 * メタ情報のクラスを取得します。
	 * 
	 * @return メタ情報のクラス
	 */
	public Class getBeanClass();

	/**
	 * オブジェクトクラスアノテーションからオブジェクトクラスの配列を取得します。
	 * 
	 * @param beanObjectClasses
	 *            ビーンクラスのオブジェクトクラス
	 * @return オブジェクトクラスアノテーションの値の配列
	 */
	public String[] getObjectClasses(String[] beanObjectClasses);

	/**
	 * 永続化対象にしない属性を取得します。
	 * 
	 * @param method
	 *            関数名
	 * @return 永続対象にしない属性の配列
	 */
	public String[] getNoPersistentProps(Method method);

	/**
	 * この属性だけ永続化する属性を取得します。
	 * 
	 * @param method
	 *            関数名
	 * @return 永続化する属性の配列
	 */
	public String[] getPersistentProps(Method method);

	/**
	 * FILTERアノテーションを取得します。
	 * 
	 * @param method
	 *            関数名
	 * @return FILTERアノテーションの値
	 */
	public String getFilter(Method method);

}
