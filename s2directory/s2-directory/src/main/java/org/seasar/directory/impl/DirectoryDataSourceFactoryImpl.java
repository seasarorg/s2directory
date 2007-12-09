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
package org.seasar.directory.impl;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.DirectoryDataSourceFactory;

/**
 * ディレクトリデータソースを作成する標準的な実装クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryDataSourceFactoryImpl implements
		DirectoryDataSourceFactory {
	/** インスタンス作成時の接続情報 */
	private DirectoryControlProperty defaultProperty;

	/**
	 * インスタンスを作成します。
	 * 
	 * @param property
	 */
	public DirectoryDataSourceFactoryImpl(DirectoryControlProperty property) {
		this.defaultProperty = property;
	}

	/**
	 * {@inheritDoc}
	 */
	public DirectoryControlProperty getDefaultDirectoryControlProperty() {
		return defaultProperty;
	}

	/**
	 * {@inheritDoc}
	 */
	public DirectoryDataSource getDirectoryDataSource() {
		return getDirectoryDataSource(defaultProperty);
	}

	/**
	 * {@inheritDoc}
	 */
	public DirectoryDataSource getDirectoryDataSource(
			DirectoryControlProperty property) {
		DirectoryDataSource dataSource = new DirectoryDataSourceImpl(property);
		return dataSource;
	}
}
