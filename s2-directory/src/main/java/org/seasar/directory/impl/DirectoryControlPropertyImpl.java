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

import javax.naming.directory.SearchControls;
import org.seasar.directory.DirectoryControlProperty;

/**
 * ディレクトリ接続情報を表わすインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryControlPropertyImpl implements DirectoryControlProperty,
		Cloneable {
	/** 接続に使用するコンテキストファクトリ */
	private String initialContextFactory = "com.sun.jndi.ldap.LdapCtxFactory";
	/** URL */
	private String url;
	/** ユーザ名 */
	private String user;
	/** パスワード */
	private String password;
	/** パスワード形式 */
	private String passwordAlgorithm = "SSHA";
	/** 基底となる識別名 */
	private String baseDn;
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

	/**
	 * Directory接続情報のインスタンスを作成します。
	 */
	public DirectoryControlPropertyImpl() {
		super();
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
	public String getUrl() {
		return url;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setUrl(String url) {
		this.url = url;
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
	public boolean hasAuthentication() {
		return (user != null) && (password != null);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("contextFactory: ").append(initialContextFactory).append(
			", ");
		buffer.append("url: ").append(url).append(", ");
		buffer.append("user: ").append(user).append(", ");
		buffer.append("password: ").append(password).append(", ");
		buffer.append("dn: ").append(baseDn).append(", ");
		buffer.append("filter: ").append(filter).append(", ");
		buffer.append("searchControls: ").append(searchControls).append(", ");
		buffer.append("allowAnonymous: ").append(allowAnonymous);
		return buffer.toString();
	}
}
