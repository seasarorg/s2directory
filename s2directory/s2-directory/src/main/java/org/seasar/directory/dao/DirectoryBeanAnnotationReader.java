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
package org.seasar.directory.dao;

import org.seasar.framework.beans.PropertyDesc;

/**
 * アノテーションを読み込むインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface DirectoryBeanAnnotationReader {
	/**
	 * 属性を取得します。
	 * 
	 * @param pd
	 *            属性定義
	 * @return 属性
	 */
	public String getColumnAnnotation(PropertyDesc pd);

	/**
	 * メタ情報のクラスを取得します。
	 * 
	 * @return メタ情報のクラス
	 */
	public Class getBeanClass();

	/**
	 * オブジェクトクラスアノテーションからオブジェクトクラスの配列を取得します。
	 * 
	 * @return オブジェクトクラスアノテーションの値の配列
	 */
	public String[] getObjectClasses();

	/**
	 * 永続化対象にしない属性を取得します。
	 * 
	 * @param methodName
	 *            関数名
	 * @return 永続対象にしない属性の配列
	 */
	public String[] getNoPersistentProps(String methodName);

	/**
	 * この属性だけ永続化する属性を取得します。
	 * 
	 * @param methodName
	 *            関数名
	 * @return 永続化する属性の配列
	 */
	public String[] getPersistentProps(String methodName);

	/**
	 * 値の型を取得します。
	 * 
	 * @param pd
	 *            属性定義
	 * @return 値の型
	 */
	public String getValueType(PropertyDesc pd);
}
