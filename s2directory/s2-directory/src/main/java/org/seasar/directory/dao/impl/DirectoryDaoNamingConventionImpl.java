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

import java.lang.reflect.Method;

import org.seasar.directory.DirectoryDaoNamingConvention;

/**
 * ディレクトリ命名規約のインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryDaoNamingConventionImpl implements
		DirectoryDaoNamingConvention {

	/** 認証処理を実行するメソッド名につけるprefix */
	protected String[] authenticatePrefixes = new String[] { "auth" };

	public void setAuthenticatePrefixes(String[] prefixes) {
		this.authenticatePrefixes = prefixes;
	}

	public boolean isAuthenticateMethod(Method method) {
		for (int i = 0; i < authenticatePrefixes.length; ++i) {
			if (method.getName().startsWith(authenticatePrefixes[i])) {
				return true;
			}
		}
		return false;
	}

	/** 新規挿入処理を実行するメソッド名につけるprefix */
	protected String[] insertPrefixes =
		new String[] { "insert", "create", "add" };

	public void setInsertPrefixes(String[] prefixes) {
		this.insertPrefixes = prefixes;
	}

	public boolean isInsertMethod(Method method) {
		for (int i = 0; i < insertPrefixes.length; ++i) {
			if (method.getName().startsWith(insertPrefixes[i])) {
				return true;
			}
		}
		return false;
	}

	/** 削除挿入処理を実行するメソッド名につけるprefix */
	protected String[] deletePrefixes = new String[] { "delete", "remove" };

	public void setDeletePrefixes(String[] prefixes) {
		this.deletePrefixes = prefixes;
	}

	public boolean isDeleteMethod(Method method) {
		for (int i = 0; i < deletePrefixes.length; ++i) {
			if (method.getName().startsWith(deletePrefixes[i])) {
				return true;
			}
		}
		return false;
	}

	/** 更新挿入処理を実行するメソッド名につけるprefix */
	protected String[] updatePrefixes =
		new String[] { "update", "modify", "store" };

	public void setUpdatePrefixes(String[] prefixes) {
		this.updatePrefixes = prefixes;
	}

	public boolean isUpdateMethod(Method method) {
		for (int i = 0; i < updatePrefixes.length; ++i) {
			if (method.getName().startsWith(updatePrefixes[i])) {
				return true;
			}
		}
		return false;
	}

	public boolean isSelectMethod(Method method) {
		if (isDeleteMethod(method) || isInsertMethod(method)
			|| isUpdateMethod(method)) {
			return false;
		}
		return true;
	}

	private String[] directoryDaoSuffixes;

	public void setDirectoryDaoSuffixes(String[] daoSuffixes) {
		this.directoryDaoSuffixes = daoSuffixes;
	}

	public String[] getDirectoryDaoSuffixes() {
		return directoryDaoSuffixes;
	}
}
