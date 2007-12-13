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

import org.seasar.directory.DirectoryConnectionPool;
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
	/** 初期化フラグ */
	protected boolean initialized = false;

	/**
	 * インスタンスを作成します。
	 * 
	 * @param property
	 */
	public DirectoryDataSourceFactoryImpl(DirectoryControlProperty property) {
		this.defaultProperty = property;
		setupConnectionPool();
	}

	/**
	 * コネクションーリングの設定を行います。<br />
	 * この設定は起動時に一度しか行えません。
	 */
	protected void setupConnectionPool() {
		if (!initialized && defaultProperty.isEnablePool()) {
			DirectoryConnectionPool pool =
				defaultProperty.getDirectoryConnectionPool();
			/*
			 * See
			 * http://java.sun.com/products/jndi/tutorial/ldap/connect/config.html
			 * http://java.sun.com/j2se/1.5.0/ja/docs/ja/guide/jndi/jndi-ldap.html#POOL
			 */
			if (pool.getAuthentication() != null) {
				System.setProperty(
					"com.sun.jndi.ldap.connect.pool.authentication",
					pool.getAuthentication());
			}
			if (pool.getDebug() != null) {
				System.setProperty("com.sun.jndi.ldap.connect.pool.debug", pool
					.getDebug());
			}
			if (pool.getInitSize() != -1) {
				System.setProperty(
					"com.sun.jndi.ldap.connect.pool.initsize",
					String.valueOf(pool.getInitSize()));
			}
			if (pool.getMaxSize() != -1) {
				System.setProperty(
					"com.sun.jndi.ldap.connect.pool.maxsize",
					String.valueOf(pool.getMaxSize()));
			}
			if (pool.getPrefSize() != -1) {
				System.setProperty(
					"com.sun.jndi.ldap.connect.pool.prefsize",
					String.valueOf(pool.getPrefSize()));
			}
			if (pool.getTimeout() != -1) {
				System.setProperty(
					"com.sun.jndi.ldap.connect.pool.timeout",
					String.valueOf(pool.getTimeout()));
			}
			if (pool.getProtocol() != null) {
				System.setProperty(
					"com.sun.jndi.ldap.connect.pool.protocol",
					String.valueOf(pool.getProtocol()));
			}
			initialized = true;
		}
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
