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

/**
 * ディレクトリDao命名規約のインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface DirectoryDaoNamingConvention {
	/**
	 * ディレクトリDaoクラスの接尾辞を取得します。
	 * 
	 * @return ディレクトリDaoクラスの接尾辞
	 */
	public String[] getDirectoryDaoSuffixes();

	/**
	 * 認証処理を実行するメソッド名につける接頭辞を取得します。
	 * 
	 * @return 認証処理を実行するメソッド名につける接頭辞
	 */
	public String[] getAuthenticatePrefixes();

	/**
	 * 新規挿入処理を実行するメソッド名につける接頭辞を取得します。
	 * 
	 * @return 新規挿入処理を実行するメソッド名につける接頭辞
	 */
	public String[] getInsertPrefixes();

	/**
	 * 更新挿入処理を実行するメソッド名につける接頭辞を取得します。
	 * 
	 * @return 更新挿入処理を実行するメソッド名につける接頭辞
	 */
	public String[] getUpdatePrefixes();

	/**
	 * 削除処理を実行するメソッド名につける接頭辞を取得します。
	 * 
	 * @return 削除処理を実行するメソッド名につける接頭辞
	 */
	public String[] getDeletePrefixes();

}
