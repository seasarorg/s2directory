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

import org.seasar.directory.dao.DirectoryDaoNamingConvention;

/**
 * ディレクトリ命名規約のインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryDaoNamingConventionImpl implements
		DirectoryDaoNamingConvention {
	/** ディレクトリDaoクラスの接尾辞 */
	private String[] daoSuffixes = new String[] { "Dao" };

	/** 認証処理を実行するメソッド名につける接頭辞 */
	protected String[] authenticatePrefixes = new String[] { "auth" };

	/** 新規挿入処理を実行するメソッド名につける接頭辞 */
	protected String[] insertPrefixes =
		new String[] { "insert", "create", "add" };

	/** 更新挿入処理を実行するメソッド名につける接頭辞 */
	protected String[] updatePrefixes =
		new String[] { "update", "modify", "store" };

	/** 削除処理を実行するメソッド名につける接頭辞 */
	protected String[] deletePrefixes = new String[] { "delete", "remove" };

	/**
	 * {@inheritDoc}
	 */
	public String[] getDirectoryDaoSuffixes() {
		return daoSuffixes;
	}

	/**
	 * ディレクトリDaoクラスの接尾辞を設定します。
	 * 
	 * @param daoSuffixes
	 *            ディレクトリDaoクラスの接尾辞
	 */
	public void setDirectoryDaoSuffixes(String[] daoSuffixes) {
		this.daoSuffixes = daoSuffixes;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getAuthenticatePrefixes() {
		return authenticatePrefixes;
	}

	/**
	 * 認証処理を実行するメソッド名につける接頭辞を設定します。
	 * 
	 * @param authenticatePrefixes
	 *            認証処理を実行するメソッド名につける接頭辞
	 */
	public void setAuthenticatePrefixes(String[] authenticatePrefixes) {
		this.authenticatePrefixes = authenticatePrefixes;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getInsertPrefixes() {
		return insertPrefixes;
	}

	/**
	 * 新規挿入処理を実行するメソッド名につける接頭辞を設定します。
	 * 
	 * @param insertPrefixes
	 *            新規挿入処理を実行するメソッド名につける接頭辞
	 */
	public void setInsertPrefixes(String[] insertPrefixes) {
		this.insertPrefixes = insertPrefixes;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getUpdatePrefixes() {
		return updatePrefixes;
	}

	/**
	 * 更新挿入処理を実行するメソッド名につける接頭辞
	 * 
	 * @param updatePrefixes
	 *            更新挿入処理を実行するメソッド名につける接頭辞
	 */
	public void setUpdatePrefixes(String[] updatePrefixes) {
		this.updatePrefixes = updatePrefixes;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getDeletePrefixes() {
		return deletePrefixes;
	}

	/**
	 * 削除処理を実行するメソッド名につける接頭辞を設定します。
	 * 
	 * @param deletePrefixes
	 *            削除処理を実行するメソッド名につける接頭辞
	 */
	public void setDeletePrefixes(String[] deletePrefixes) {
		this.deletePrefixes = deletePrefixes;
	}

}
