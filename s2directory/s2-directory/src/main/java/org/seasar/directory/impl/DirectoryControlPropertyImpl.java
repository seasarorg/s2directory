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

import javax.naming.directory.SearchControls;
import org.seasar.directory.DirectoryControlProperty;

/**
 * Directory接続情報を表わすインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryControlPropertyImpl implements DirectoryControlProperty,
		Cloneable {
	/** 接続に使用するコンテキスト生成器を表わします。 */
	private String initialContextFactory = "com.sun.jndi.ldap.LdapCtxFactory";
	/** URLを表わします。 */
	private String url;
	/** ユーザ名を表します。 */
	private String user;
	/** パスワードを表します。 */
	private String password;
	/** パスワードのハッシュ形式を表します。 */
	private String passwordType = "SSHA";
	/** 基底となる識別名を表わします。 */
	private String baseDn;
	/** ユーザユニットの接尾辞を表します。 */
	private String userSuffix = "ou=Users";
	/** ユーザを識別するための属性名を表します。 */
	private String userAttributeName = "uid";
	/** グループユニットの接尾辞を表します。 */
	private String groupSuffix = "ou=Groups";
	/** グループを識別するための属性名を表します。 */
	private String groupAttributeName = "memberUid";
	/** フィルタを表わします。 */
	private String filter;
	/** 検索コントロールを表わします。 */
	private int searchControls = SearchControls.SUBTREE_SCOPE;
	/** 匿名接続を許可するかを表わします。 */
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
	 * @param user ユーザ名
	 * @param password パスワード
	 */
	public DirectoryControlPropertyImpl(String user, String password) {
		this.user = user;
		this.password = password;
	}

	/**
	 * クローンを生成します。
	 * 
	 * @return クローン
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		try {
			return (super.clone());
		} catch (CloneNotSupportedException e) {
			throw (new InternalError(e.getMessage()));
		}
	}

	/**
	 * コンテキスト生成器を返します。
	 * 
	 * @return コンテキスト生成器
	 */
	public String getInitialContextFactory() {
		return initialContextFactory;
	}

	/**
	 * 指定されたコンテキスト生成器を設定します。
	 * 
	 * @param initialContextFactory コンテキスト生成器
	 */
	public void setInitialContextFactory(String initialContextFactory) {
		this.initialContextFactory = initialContextFactory;
	}

	/**
	 * urlを返します。
	 * 
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 指定されたURLを設定します。
	 * 
	 * @param url url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * パスワードを返します。
	 * 
	 * @return パスワード
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * パスワードを設定します。
	 * 
	 * @param password パスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * パスワードのハッシュ形式を返します。
	 * 
	 * @return パスワードのハッシュ形式
	 */
	public String getPasswordType() {
		return passwordType;
	}

	/**
	 * パスワードのハッシュ形式を設定します。
	 * 
	 * @param passwordType パスワードのハッシュ形式
	 */
	public void setPasswordType(String passwordType) {
		this.passwordType = passwordType;
	}

	/**
	 * ユーザ名を返します。
	 * 
	 * @return ユーザ名
	 */
	public String getUser() {
		return user;
	}

	/**
	 * ユーザ名を設定します。
	 * 
	 * @param user ユーザ名
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * ユーザユニットの接尾辞を取得します。
	 * 
	 * @return ユーザユニットの接尾辞
	 */
	public String getUserSuffix() {
		return userSuffix;
	}

	/**
	 * ユーザユニットの接尾辞を設定します。
	 * 
	 * @param userSuffix ユーザユニットの接尾辞
	 */
	public void setUserSuffix(String userSuffix) {
		this.userSuffix = userSuffix;
	}

	/**
	 * ユーザを識別するための属性名を取得します。
	 * 
	 * @return ユーザを識別するための属性名
	 */
	public String getUserAttributeName() {
		return userAttributeName;
	}

	/**
	 * ユーザを識別するための属性名を設定します。
	 * 
	 * @param userAttributeName ユーザを識別するための属性名
	 */
	public void setUserAttributeName(String userAttributeName) {
		this.userAttributeName = userAttributeName;
	}

	/**
	 * グループユニットの接尾辞を取得します。
	 * 
	 * @return グループユニットの接尾辞
	 */
	public String getGroupSuffix() {
		return groupSuffix;
	}

	/**
	 * グループユニットの接尾辞を設定します。
	 * 
	 * @param groupSuffix グループユニットの接尾辞
	 */
	public void setGroupSuffix(String groupSuffix) {
		this.groupSuffix = groupSuffix;
	}

	/**
	 * グループを識別するための属性名を取得します。
	 * 
	 * @return グループを識別するための属性名
	 */
	public String getGroupAttributeName() {
		return groupAttributeName;
	}

	/**
	 * グループを識別するための属性名を設定します。
	 * 
	 * @param groupAttributeName グループを識別するための属性名
	 */
	public void setGroupAttributeName(String groupAttributeName) {
		this.groupAttributeName = groupAttributeName;
	}

	/**
	 * 基底となる識別名を返します。
	 * 
	 * @return 基底となる識別名
	 */
	public String getBaseDn() {
		return baseDn;
	}

	/**
	 * 指定された基底となる識別名を設定します。
	 * 
	 * @param baseDn 基底となる識別名
	 */
	public void setBaseDn(String baseDn) {
		this.baseDn = baseDn;
	}

	/**
	 * フィルタを返します。
	 * 
	 * @return フィルタ
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * 指定されたフィルタを設定します。
	 * 
	 * @param filter フィルタ
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * 検索コントロールを返します。
	 * 
	 * @return 検索コントロール
	 */
	public int getSearchControls() {
		return searchControls;
	}

	/**
	 * 検索コントロールを設定します。
	 * 
	 * @param searchControls 検索コントロール
	 */
	public void setSearchControls(int searchControls) {
		this.searchControls = searchControls;
	}

	/**
	 * 匿名接続を許可するか判断します。
	 * 
	 * @return allowAnonymous
	 */
	public boolean isAllowAnonymous() {
		return allowAnonymous;
	}

	/**
	 * 匿名接続を許可するか設定します。
	 * 
	 * @param allowAnonymous 匿名接続を許可するかどうか
	 */
	public void setAllowAnonymous(boolean allowAnonymous) {
		this.allowAnonymous = allowAnonymous;
	}

	/**
	 * 認証可能な情報を保持しているか調べます。
	 * 
	 * @return 認証可能な情報を保持している場合 true
	 * @see org.seasar.directory.DirectoryControlProperty#hasAuthentication()
	 */
	public boolean hasAuthentication() {
		return (user != null) && (password != null);
	}

	/**
	 * このオブジェクトの文字列表現を返します。
	 * 
	 * @return このオブジェクトの文字列表現
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