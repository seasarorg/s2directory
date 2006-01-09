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
package org.seasar.directory.impl;

import java.util.Properties;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.util.DirectoryDataSourceUtils;

/**
 * サーバに接続するためのリソースを提供するデータソースクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryDataSourceImpl implements DirectoryDataSource {
	/** 接続情報を表わします。 */
	private DirectoryControlProperty directoryControlProperty;

	/**
	 * インスタンスを作成します。
	 */
	public DirectoryDataSourceImpl() {
		super();
	}

	/**
	 * 指定された接続情報を保持したデータソースのインスタンスを作成します。
	 * 
	 * @param directoryControlProperty - 接続情報
	 */
	public DirectoryDataSourceImpl(
			DirectoryControlProperty directoryControlProperty) {
		this.directoryControlProperty = directoryControlProperty;
	}

	/**
	 * 接続情報を設定します。
	 * 
	 * @param directoryControlProperty - 接続情報
	 */
	public void setDirectoryControlProperty(
			DirectoryControlProperty directoryControlProperty) {
		this.directoryControlProperty = directoryControlProperty;
	}

	/**
	 * 接続情報を返します。
	 * 
	 * @return 接続情報
	 */
	public DirectoryControlProperty getDirectoryControlProperty() {
		return directoryControlProperty;
	}

	/**
	 * 保持している接続情報を使用して作成したコネクションを返します。
	 * 
	 * @return コネクション
	 * @throws NamingException
	 */
	public DirContext getConnection() throws NamingException {
		return getConnection(directoryControlProperty);
	}

	/**
	 * 指定された接続情報を使用して作成したコネクションを返します。
	 * 
	 * @param directoryControlProperty - 接続情報
	 * @return コネクション
	 * @throws NamingException
	 */
	public DirContext getConnection(
			DirectoryControlProperty directoryControlProperty)
			throws NamingException {
		DirectoryDataSourceUtils
				.setupDirectoryControlProperty(directoryControlProperty);
		if (directoryControlProperty.isAllowAnonymous()) {
			// 匿名接続を許可する場合、認証情報の null を 空 に置き換えます。
			if (directoryControlProperty.getUser() == null)
				directoryControlProperty.setUser("");
			if (directoryControlProperty.getPassword() == null)
				directoryControlProperty.setPassword("");
		} else if (!directoryControlProperty.hasAuthentication()) {
			// 匿名接続が許可されていないのに、認証情報が null の場合
			throw new NamingException("匿名接続は許可されていません。");
		}
		String url = directoryControlProperty.getUrl();
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, directoryControlProperty
				.getInitialContextFactory());
		env.put(Context.PROVIDER_URL, url);
		env.put(Context.SECURITY_PRINCIPAL, directoryControlProperty.getUser());
		env.put(Context.SECURITY_CREDENTIALS, directoryControlProperty
				.getPassword());
		return new InitialDirContext(env);
	}

	/**
	 * 保持している接続情報を使用してサーバに接続できるか調べます。
	 * 
	 * @return 接続可能ならば true
	 * @throws NamingException
	 */
	public boolean authenticate() throws NamingException {
		return authenticate(directoryControlProperty);
	}

	/**
	 * 指定された接続情報を使用してサーバに接続できるか調べます。
	 * 
	 * @return 接続可能ならば true
	 * @throws NamingException
	 */
	public boolean authenticate(
			DirectoryControlProperty directoryControlProperty)
			throws NamingException {
		try {
			getConnection(directoryControlProperty);
			return true;
		} catch (AuthenticationException ae) {
			ae.printStackTrace();
			return false;
		}
	}
}
