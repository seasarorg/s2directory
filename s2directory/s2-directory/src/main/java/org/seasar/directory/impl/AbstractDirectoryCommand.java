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
package org.seasar.directory.impl;

import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.dao.DirectoryCommand;

/**
 * ディレクトリ用処理コマンドの抽象クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public abstract class AbstractDirectoryCommand implements DirectoryCommand {
	/** データソースを表わします。 */
	private DirectoryDataSource directoryDataSource;
	/** フィルタを表します。 */
	private String filter;
	/** オブジェクトクラスを表します。 */
	private String[] objectClasses;

	/**
	 * インスタンスを作成します。
	 * 
	 * @param directoryDataSource データソース
	 */
	public AbstractDirectoryCommand(DirectoryDataSource directoryDataSource) {
		this.directoryDataSource = directoryDataSource;
	}

	/**
	 * データソースを取得します。
	 * 
	 * @return データソース
	 */
	public DirectoryDataSource getDirectoryDataSource() {
		return directoryDataSource;
	}

	/**
	 * フィルタを取得します。
	 * 
	 * @return filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * フィルタを設定します。
	 * 
	 * @param filter
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * オブジェクトクラスを取得します。
	 * 
	 * @return objectClasses
	 */
	public String[] getObjectClasses() {
		return objectClasses;
	}

	/**
	 * オブジェクトクラスを設定します。
	 * 
	 * @param objectClasses
	 */
	public void setObjectClasses(String[] objectClasses) {
		this.objectClasses = objectClasses;
	}
}
