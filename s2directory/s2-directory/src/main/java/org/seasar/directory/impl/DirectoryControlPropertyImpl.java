/*
 * Copyright 2005-2014 the Seasar Foundation and the Others.
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

import java.util.Hashtable;

import javax.naming.directory.SearchControls;

import org.seasar.directory.DirectoryConnectionPool;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.util.DirectoryUtil;
import org.seasar.framework.util.ArrayUtil;

/**
 * ディレクトリ接続情報を表わす標準的な実装クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class DirectoryControlPropertyImpl implements DirectoryControlProperty {

	/** コネクションプーリング設定のBindingアノテーション */
	public static final String directoryConnectionPool_BINDING =
		"bindingType=may";

	/** デフォルトのディレクトリ接続情報 */
	private Hashtable defaultEnvironment;

	/** デフォルトの {@link SearchControls} */
	private SearchControls defaultSearchControls;

	/** デフォルトのオブジェクトクラスの配列 */
	private String[] abstractObjectClasses = ABSTRACT_OBJECTCLASSES_TOP;

	/** 接続に使用するコンテキストファクトリ */
	private String initialContextFactory = "com.sun.jndi.ldap.LdapCtxFactory";

	/** 接続に使用するSSLソケットファクトリ */
	private String sslSocketFactory = "javax.net.ssl.SSLSocketFactory";

	/** 認証メカニズム */
	private String authentication = AUTHENTICATION_SIMPLE;

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
	private String multipleValueDelimiter = "DEFAULT_MULTIPLE_VALUE_DELIMITER";

	/** フィルタ */
	private String filter;

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
	public Hashtable getDefaultEnvironment() {
		if (defaultEnvironment != null) {
			return defaultEnvironment;
		}
		return new Hashtable();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDefaultEnvironment(Hashtable defaultEnvironment) {
		this.defaultEnvironment = defaultEnvironment;
	}

	/**
	 * {@inheritDoc}
	 */
	public SearchControls getDefaultSearchControls() {
		if (defaultSearchControls != null) {
			return defaultSearchControls;
		}
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		return controls;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDefaultSearchControls(SearchControls defaultSearchControls) {
		this.defaultSearchControls = defaultSearchControls;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getAbstractObjectClasses() {
		return abstractObjectClasses;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAbstractObjectClasses(String[] abstractObjectClasses) {
		this.abstractObjectClasses = abstractObjectClasses;
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
	public String getAuthentication() {
		return authentication;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAuthentication(String authentication) {
		this.authentication = authentication;
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
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("defaultEnvironment: ").append(
			defaultEnvironment != null ? defaultEnvironment.toString() : null).append(
			", ");
		buffer.append("defaultSearchControls: ").append(
			DirectoryUtil.toStringFromSearchControls(defaultSearchControls)).append(
			", ");
		buffer.append("defaultObjectClasses: ").append(
			ArrayUtil.toString(abstractObjectClasses)).append(", ");
		buffer.append("contextFactory: ").append(initialContextFactory).append(
			", ");
		buffer.append("sslSocketFactory: ").append(sslSocketFactory).append(
			", ");
		buffer.append("authentication: ").append(authentication).append(", ");
		buffer.append("url: ").append(url).append(", ");
		buffer.append("baseDn: ").append(baseDn).append(", ");
		buffer.append("bindDn: ").append(bindDn).append(", ");
		buffer.append("user: ").append(user).append(", ");
		buffer.append("password: ").append(password).append(", ");
		buffer.append("filter: ").append(filter).append(", ");
		buffer.append("enableSSL").append(enableSSL).append(", ");
		buffer.append("enableTLS: ").append(enableTLS).append(", ");
		buffer.append("pool: ").append(pool);
		return buffer.toString();
	}

}
