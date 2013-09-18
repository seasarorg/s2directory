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
package org.seasar.directory.dao.impl;

import org.seasar.directory.DirectoryAttributeHandlerFactory;
import org.seasar.directory.DirectoryDataSourceFactory;
import org.seasar.directory.dao.DirectoryCommand;

/**
 * ディレクトリ用処理コマンドの抽象クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public abstract class AbstractDirectoryCommand implements DirectoryCommand {

	/** データソース */
	protected DirectoryDataSourceFactory dataSourceFactory;

	/** ディレクトリ用の値の型ファクトリ */
	protected DirectoryAttributeHandlerFactory attributeHandlerFactory;

	/** フィルタ */
	private String filter;

	/** オブジェクトクラス */
	private String[] objectClasses;

	/**
	 * インスタンスを作成します。
	 * 
	 * @param dataSourceFactory
	 *            データソース
	 * @param attributeHandlerFactory
	 *            属性ハンドラファクトリ
	 */
	public AbstractDirectoryCommand(
			DirectoryDataSourceFactory dataSourceFactory,
			DirectoryAttributeHandlerFactory attributeHandlerFactory) {
		this.dataSourceFactory = dataSourceFactory;
		this.attributeHandlerFactory = attributeHandlerFactory;
	}

	/**
	 * ディレクトリ用の値の型ファクトリを取得します。
	 * 
	 * @return ディレクトリ用の値の型ファクトリ
	 */
	public DirectoryAttributeHandlerFactory getDirectoryAttributeHandlerFactory() {
		return attributeHandlerFactory;
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
