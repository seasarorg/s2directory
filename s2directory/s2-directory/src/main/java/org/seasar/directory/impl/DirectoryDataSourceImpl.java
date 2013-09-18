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
package org.seasar.directory.impl;

import java.io.IOException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.StartTlsRequest;
import javax.naming.ldap.StartTlsResponse;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.exception.DirectoryRuntimeException;
import org.seasar.directory.util.DirectoryDataSourceUtil;
import org.seasar.framework.util.ClassUtil;

/**
 * サーバに接続するためのリソースを提供するデータソースクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class DirectoryDataSourceImpl implements DirectoryDataSource {

	/** 接続情報 */
	private DirectoryControlProperty defaultProperty;

	/**
	 * 指定された接続情報を保持したデータソースのインスタンスを作成します。
	 * 
	 * @param property
	 *            接続情報
	 */
	public DirectoryDataSourceImpl(DirectoryControlProperty property) {
		this.defaultProperty = property;
	}

	/**
	 * {@inheritDoc}
	 */
	public DirectoryControlProperty getDirectoryControlProperty() {
		return defaultProperty;
	}

	/**
	 * {@inheritDoc}
	 */
	public DirContext getConnection() throws NamingException {
		return getConnection(defaultProperty);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NamingException
	 */
	public DirContext getConnection(DirectoryControlProperty property)
			throws NamingException {
		// 接続設定を準備します。
		setupDirectoryControlProperty(property);
		Properties env = new Properties();
		env.put(
			Context.INITIAL_CONTEXT_FACTORY,
			property.getInitialContextFactory());
		env.put(Context.PROVIDER_URL, property.getUrl());
		env.put(Context.SECURITY_AUTHENTICATION, property.getAuthentication());
		if (DirectoryControlProperty.AUTHENTICATION_NONE.equals(property.getAuthentication()) == false) {
			// 匿名接続以外の場合のみ認証情報を設定します。
			env.put(Context.SECURITY_PRINCIPAL, property.getBindDn());
			env.put(Context.SECURITY_CREDENTIALS, property.getPassword());
		}

		// コネクションプーリング
		// Connection pooling is supported only on the Java 2 SDK, v 1.4.1, and
		// later releases.
		// http://java.sun.com/products/jndi/tutorial/ldap/connect/config.html
		if (property.isEnablePool()) {
			env.put(CONNECTION_POOL_KEY, "true");
		}

		// デバッグ用、通信内容が16進で出力されます。
		// env.put("com.sun.jndi.ldap.trace.ber", System.out);

		// 接続処理
		if (property.isEnableSSL()) {
			// SSL接続
			return getSSLConnection(env, property);
		}
		if (property.isEnableTLS()) {
			// TLS接続
			return getTLSConnection(env, property);
		}
		// 通常接続
		return getConnection(env);
	}

	/**
	 * 指定された接続情報を使用して作成したコネクションを返します。
	 * 
	 * @param env
	 *            接続情報
	 * @return コネクション
	 * @throws NamingException
	 */
	protected DirContext getConnection(Properties env) throws NamingException {
		return new InitialDirContext(env);
	}

	/**
	 * @param env
	 *            接続情報
	 * @param property
	 *            接続情報
	 * @return TLSコネクション
	 * @throws NamingException
	 */
	protected DirContext getTLSConnection(Properties env,
			DirectoryControlProperty property) throws NamingException {
		SSLSocketFactory sslSocketFactory = null;
		HostnameVerifier hostnameVerifier = null;
		String sslSocketFactoryClassName = property.getSslSocketFactory();
		if (property.getSslSocketFactory().equals(
			"org.seasar.directory.impl.PermissiveSSLSocketFactory")) {
			sslSocketFactory = new PermissiveSSLSocketFactory();
			hostnameVerifier = new PermissiveHostnameVerifier();
		} else {
			sslSocketFactory =
				(SSLSocketFactory)ClassUtil.newInstance(sslSocketFactoryClassName);
		}
		// TLS接続を行う
		LdapContext context = new InitialLdapContext(env, null);
		StartTlsResponse tls =
			(StartTlsResponse)((LdapContext)context).extendedOperation(new StartTlsRequest());
		try {
			if (hostnameVerifier != null) {
				tls.setHostnameVerifier(hostnameVerifier);
			}
			tls.negotiate(sslSocketFactory);
		} catch (IOException e) {
			if (tls != null) {
				try {
					tls.close();
				} catch (IOException e1) {
					// do nothing.
				}
			}
			DirectoryDataSourceUtil.close(context);
			// TODO: 専用の例外ハンドラ作成
			throw new DirectoryRuntimeException("このサーバはTLS接続をサポートしていません。");
		}
		return context;
	}

	/**
	 * @param env
	 *            接続情報
	 * @param property
	 *            接続情報
	 * @return SSLコネクション
	 * @throws NamingException
	 */
	protected DirContext getSSLConnection(Properties env,
			DirectoryControlProperty property) throws NamingException {
		// SSL接続を行う
		// TLS利用時にこの設定をすると最初からSSLで暗号化通信してしまうため通信に失敗します。
		env.put(SSL_SOCKET_FACTORY_KEY, property.getSslSocketFactory());
		env.put(Context.SECURITY_PROTOCOL, "ssl");
		return getConnection(env);
	}

	/**
	 * ディレクトリサーバ接続情報をセットアップします。
	 * 
	 * @param property
	 *            接続情報
	 */
	protected void setupDirectoryControlProperty(
			DirectoryControlProperty property) {
		if (property.isEnableSSL() && property.isEnableTLS()) {
			// TODO: 専用の例外ハンドラ作成
			throw new DirectoryRuntimeException("SSL接続とTLS接続の併用はできません。");
		}
		DirectoryDataSourceUtil.setupDirectoryControlProperty(property);
	}

}
