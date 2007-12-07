/*
 * Copyright 2005-2007 the Seasar Foundation and the Others.
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
package org.seasar.directory;

import java.lang.reflect.Method;

/**
 * ディレクトリ命名規約のインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface DirectoryDaoNamingConvention {
	/**
	 * 認証関数の命名規則に沿っているかどうかを返します。
	 * 
	 * @param method
	 *            関数
	 * @return 認証関数の命名規則に沿っているかどうか
	 */
	public boolean isAuthenticateMethod(Method method);

	/**
	 * 挿入関数の命名規則に沿っているかどうかを返します。
	 * 
	 * @param method
	 *            関数
	 * @return 挿入関数の命名規則に沿っているかどうか
	 */
	public boolean isInsertMethod(Method method);

	/**
	 * 削除関数の命名規則に沿っているかどうかを返します。
	 * 
	 * @param method
	 *            関数
	 * @return 削除関数の命名規則に沿っているかどうか
	 */
	public boolean isDeleteMethod(Method method);

	/**
	 * 検索関数の命名規則に沿っているかどうかを返します。
	 * 
	 * @param method
	 *            関数
	 * @return 検索関数の命名規則に沿っているかどうか
	 */
	public boolean isSelectMethod(Method method);

	/**
	 * 更新関数の命名規則に沿っているかどうかを返します。
	 * 
	 * @param method
	 *            関数
	 * @return 更新関数の命名規則に沿っているかどうか
	 */
	public boolean isUpdateMethod(Method method);

	/**
	 * DirectoryDaoクラスの接頭辞を取得します。
	 * 
	 * @return DirectoryDaoクラスの接頭辞
	 */
	public String[] getDirectoryDaoSuffixes();
}