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

import javax.naming.directory.SearchControls;

import org.seasar.directory.DirectoryConnectionPool;
import org.seasar.directory.DirectoryControlProperty;

/**
 * ディレクトリ接続情報を表わす標準的な実装クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryControlPropertyImpl implements DirectoryControlProperty,
		Cloneable {
	/** コネクションプーリング設定のBindingアノテーション */
	public static final String directoryConnectionPool_BINDING =
		"bindingType=may";

	/** 接続に使用するコンテキストファクトリ */
	private String initialContextFactory = "com.sun.jndi.ldap.LdapCtxFactory";
	/** 接続に使用するSSLソケットファクトリ */
	private String sslSocketFactory = "javax.net.ssl.SSLSocketFactory";
	/** URL */
	private String url;
	/** 基底となる識別名 */
	private String baseDn;
	/** バインドDn */
	private String bindDn;
	/** ユーザ名 */
	private String user;
	/** パスワード */
	private String password;
	/** パスワード形式 */
	private String passwordAlgorithm = "SSHA";
	/** パスワードハッシュ作成時に使用するsaltの長さ */
	private int passwordSaltLength = 4;
	/** ユーザユニットの接尾辞 */
	private String userSuffix = "ou=Users";
	/** ユーザを識別するための属性名 */
	private String userAttributeName = "uid";
	/** グループユニットの接尾辞 */
	private String groupSuffix = "ou=Groups";
	/** グループを識別するための属性名 */
	private String groupAttributeName = "memberUid";
	/** 複数値のための区切り文字 */
	private String multipleValueDelimiter = ",";
	/** フィルタ */
	private String filter;
	/** 検索コントロール */
	private int searchControls = SearchControls.SUBTREE_SCOPE;
	/** 匿名接続を許可するかどうか */
	private boolean allowAnonymous = false;
	/** SSL接続するかどうか */
	private boolean enableSSL = false;
	/** TLS接続するかどうか */
	private boolean enableTLS = false;
	/** コネクションプーリング設定 */
	private DirectoryConnectionPool pool;

	/**
	 * ディレクトリ接続情報のインスタンスを作成します。
	 */
	public DirectoryControlPropertyImpl() {
	}

	/**
	 * 指定されたユーザ情報を保持したDirectory接続情報のインスタンスを作成します。
	 * 
	 * @param user
	 *            ユーザ名
	 * @param password
	 *            パスワード
	 */
	public DirectoryControlPropertyImpl(String user, String password) {
		this.user = user;
		this.password = password;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object clone() {
		try {
			return (super.clone());
		} catch (CloneNotSupportedException e) {
			throw (new InternalError(e.getMessage()));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String getInitialContextFactory() {
		return initialContextFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setInitialContextFactory(String initialContextFactory) {
		this.initialContextFactory = initialContextFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getSslSocketFactory() {
		return sslSocketFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSslSocketFactory(String sslSocketFactory) {
		this.sslSocketFactory = sslSocketFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setUrl(String url) {
		if (url.startsWith("ldaps://")) {
			setEnableSSL(true);
		} else {
			setEnableSSL(false);
		}
		this.url = url;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getBaseDn() {
		return baseDn;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setBaseDn(String baseDn) {
		this.baseDn = baseDn;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getBindDn() {
		return bindDn;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setBindDn(String bindDn) {
		this.bindDn = bindDn;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getPasswordAlgorithm() {
		return passwordAlgorithm;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setPasswordAlgorithm(String passwordAlgorithm) {
		this.passwordAlgorithm = passwordAlgorithm;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getPasswordSaltLength() {
		return passwordSaltLength;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setPasswordSaltLength(int passwordSaltLength) {
		this.passwordSaltLength = passwordSaltLength;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getUser() {
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getUserSuffix() {
		return userSuffix;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setUserSuffix(String userSuffix) {
		this.userSuffix = userSuffix;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getUserAttributeName() {
		return userAttributeName;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setUserAttributeName(String userAttributeName) {
		this.userAttributeName = userAttributeName;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getGroupSuffix() {
		return groupSuffix;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setGroupSuffix(String groupSuffix) {
		this.groupSuffix = groupSuffix;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getGroupAttributeName() {
		return groupAttributeName;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setGroupAttributeName(String groupAttributeName) {
		this.groupAttributeName = groupAttributeName;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getMultipleValueDelimiter() {
		return multipleValueDelimiter;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMultipleValueDelimiter(String multipleValueDelimiter) {
		this.multipleValueDelimiter = multipleValueDelimiter;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getSearchControls() {
		return searchControls;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSearchControls(int searchControls) {
		this.searchControls = searchControls;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAllowAnonymous() {
		return allowAnonymous;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAllowAnonymous(boolean allowAnonymous) {
		this.allowAnonymous = allowAnonymous;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isEnableSSL() {
		return enableSSL;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setEnableSSL(boolean useSSL) {
		this.enableSSL = useSSL;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isEnableTLS() {
		return enableTLS;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setEnableTLS(boolean enableTLS) {
		this.enableTLS = enableTLS;
	}

	/**
	 * {@inheritDoc}
	 */
	public DirectoryConnectionPool getDirectoryConnectionPool() {
		return pool;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDirectoryConnectionPool(DirectoryConnectionPool pool) {
		this.pool = pool;
	}

	public boolean isEnablePool() {
		return (pool != null);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasAuthentication() {
		return (bindDn != null) && (password != null);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("contextFactory: ").append(initialContextFactory).append(
			", ");
		buffer.append("sslSocketFactory: ").append(sslSocketFactory).append(
			", ");
		buffer.append("url: ").append(url).append(", ");
		buffer.append("baseDn: ").append(baseDn).append(", ");
		buffer.append("bindDn: ").append(baseDn).append(", ");
		buffer.append("user: ").append(user).append(", ");
		buffer.append("password: ").append(password).append(", ");
		buffer.append("filter: ").append(filter).append(", ");
		buffer.append("searchControls: ").append(searchControls).append(", ");
		buffer.append("allowAnonymous: ").append(allowAnonymous).append(", ");
		buffer.append("enableSSL").append(enableSSL).append(", ");
		buffer.append("enableTLS: ").append(enableTLS).append(", ");
		buffer.append("pool: ").append(pool);
		return buffer.toString();
	}
}
