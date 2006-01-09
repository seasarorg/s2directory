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
package org.seasar.directory.dao;

/**
 * アノテーションを読み込むインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface DirectoryDaoAnnotationReader {
	/**
	 * QUERYアノテーションを取得します。
	 * 
	 * @param methodName
	 * @return
	 */
	public String getQuery(String methodName);

	/**
	 * ARGSアノテーションを取得します。
	 * 
	 * @param methodName
	 * @return
	 */
	public String[] getArgNames(String name);

	/**
	 * メタ情報のクラスを取得します。
	 * 
	 * @return
	 */
	public Class getBeanClass();

	/**
	 * 永続化対象にしない属性を取得します。
	 * 
	 * @param methodName
	 * @return
	 */
	public String[] getNoPersistentProps(String methodName);

	/**
	 * この属性だけ永続化する属性を取得します。
	 * 
	 * @param methodName
	 * @return
	 */
	public String[] getPersistentProps(String methodName);

	/**
	 * FILTERアノテーションを取得します。
	 * 
	 * @param name
	 * @return
	 */
	public String getFilter(String name);
}
