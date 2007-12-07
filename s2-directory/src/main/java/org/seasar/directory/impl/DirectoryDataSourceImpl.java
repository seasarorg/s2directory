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

import java.io.IOException;
import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.StartTlsRequest;
import javax.naming.ldap.StartTlsResponse;
import javax.net.ssl.SSLSocketFactory;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.util.DirectoryDataSourceUtil;

/**
 * サーバに接続するためのリソースを提供するデータソースクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryDataSourceImpl implements DirectoryDataSource {
	/** 接続情報 */
	private DirectoryControlProperty property;
	/** SSLソケットファクトリのための設定名 */
	private final static String SSL_SOCKET_FACTORY =
		"java.naming.ldap.factory.socket";

	/**
	 * 指定された接続情報を保持したデータソースのインスタンスを作成します。
	 * 
	 * @param property
	 *            接続情報
	 */
	public DirectoryDataSourceImpl(DirectoryControlProperty property) {
		this.property = property;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDirectoryControlProperty(DirectoryControlProperty property) {
		this.property = property;
	}

	/**
	 * {@inheritDoc}
	 */
	public DirectoryControlProperty getDirectoryControlProperty() {
		return property;
	}

	/**
	 * {@inheritDoc}
	 */
	public DirContext getConnection() throws NamingException {
		return getConnection(property);
	}

	/**
	 * {@inheritDoc}
	 */
	public DirContext getConnection(DirectoryControlProperty property)
			throws NamingException {
		DirectoryDataSourceUtil.setupDirectoryControlProperty(property);
		if (property.isAllowAnonymous()) {
			// 匿名接続を許可する場合、認証情報の null を空に置き換えます。
			if (property.getUser() == null)
				property.setUser("");
			if (property.getPassword() == null)
				property.setPassword("");
		} else if (!property.hasAuthentication()) {
			// 匿名接続が許可されていないのに、認証情報が null の場合
			// TODO: エラーハンドル
			throw new NamingException("匿名接続は許可されていません。");
		}
		String url = property.getUrl();
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, property
			.getInitialContextFactory());
		env.put(Context.PROVIDER_URL, url);
		env.put(Context.SECURITY_PRINCIPAL, property.getUser());
		env.put(Context.SECURITY_CREDENTIALS, property.getPassword());
		if (property.isEnableSSL() && property.isEnableTLS()) {
			// TODO: エラーハンドル
			throw new NamingException("SSL接続とTLS接続の併用はできません。");
		}
		if (property.isEnableSSL()) {
			// SSL接続を行う
			// TLS利用時にこの設定をすると最初からSSLで暗号化通信してしまうため、
			// この設定を行ってはいけない
			env.put(SSL_SOCKET_FACTORY, property.getSslSocketFactory());
			env.put(Context.SECURITY_PROTOCOL, "ssl");
		}
		if (property.isEnableTLS()) {
			// TLS接続を行う
			LdapContext context = new InitialLdapContext(env, null);
			StartTlsResponse tls =
				(StartTlsResponse)((LdapContext)context)
					.extendedOperation(new StartTlsRequest());
			try {
				tls.setHostnameVerifier(new PermissiveHostnameVerifier());
				tls.negotiate((SSLSocketFactory)PermissiveSSLSocketFactory
					.getDefault());
			} catch (IOException e) {
				// TODO: エラーハンドル
				e.printStackTrace();
			}
		}
		return new InitialLdapContext(env, null);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean authenticate() throws NamingException {
		return authenticate(property);
	}

	/**
	 * {@inheritDoc}
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
