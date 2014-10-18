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
package org.seasar.directory;

import java.util.Hashtable;

import javax.naming.directory.SearchControls;

/**
 * ディレクトリサーバ接続情報を表わすインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public interface DirectoryControlProperty {

	/** 匿名バインドのための認証メカニズム定数です。 */
	public static final String AUTHENTICATION_NONE = "none";

	/** 簡単な認証バインドのための認証メカニズム定数です。 */
	public static final String AUTHENTICATION_SIMPLE = "simple";

	/**
	 * デフォルトのディレクトリ接続情報を取得します。
	 * 
	 * @return デフォルトのディレクトリ接続情報
	 */
	public Hashtable getDefaultEnvironment();

	/**
	 * デフォルトのディレクトリ接続情報を設定します。
	 * 
	 * @param defaultEnvironment
	 *            デフォルトのディレクトリ接続情報
	 */
	public void setDefaultEnvironment(Hashtable defaultEnvironment);

	/**
	 * デフォルトの {@link SearchControls} を取得します。
	 */
	public SearchControls getDefaultSearchControls();

	/**
	 * デフォルトの {@link SearchControls} を設定します。
	 * 
	 * @param defaultSearchControls
	 *            デフォルトの {@link SearchControls}
	 */
	public void setDefaultSearchControls(SearchControls defaultSearchControls);

	/**
	 * 接続に使用するコンテキストファクトリを取得します。
	 * 
	 * @return コンテキストファクトリ
	 */
	public String getInitialContextFactory();

	/**
	 * 接続に使用するコンテキストファクトリを設定します。
	 * 
	 * @param contextFactory
	 *            接続に使用するコンテキストファクトリ
	 */
	public void setInitialContextFactory(String contextFactory);

	/**
	 * 接続に使用するSSLソケットファクトリを取得します。
	 * 
	 * @return SSLソケットファクトリ
	 */
	public String getSslSocketFactory();

	/**
	 * 接続に使用するSSLソケットファクトリを設定します。
	 * 
	 * @param sslSocketFactory
	 *            SSLソケットファクトリ
	 */
	public void setSslSocketFactory(String sslSocketFactory);

	/**
	 * 認証メカニズムを設定します。
	 * 
	 * @return 認証メカニズム
	 */
	public String getAuthentication();

	/**
	 * 認証メカニズムを返します。
	 * 
	 * @param authentication
	 *            認証メカニズム
	 */
	public void setAuthentication(String authentication);

	/**
	 * urlを返します。
	 * 
	 * @return url
	 */
	public String getUrl();

	/**
	 * @param url
	 *            指定されたurlを設定します。
	 */
	public void setUrl(String url);

	/**
	 * 基底となる識別名を返します。
	 * 
	 * @return 基底となる識別名
	 */
	public String getBaseDn();

	/**
	 * @param baseDn
	 *            指定された基底となる識別名を設定します。
	 */
	public void setBaseDn(String baseDn);

	/**
	 * バインド識別名を返します。
	 * 
	 * @return バインド識別名
	 */
	public String getBindDn();

	/**
	 * @param bindDn
	 *            バインド識別名を設定します。
	 */
	public void setBindDn(String bindDn);

	/**
	 * パスワードを返します。
	 * 
	 * @return パスワード
	 */
	public String getPassword();

	/**
	 * @param password
	 *            パスワードを設定します。
	 */
	public void setPassword(String password);

	/**
	 * パスワードの形式を返します。
	 * 
	 * @return パスワードの形式
	 */
	public String getPasswordAlgorithm();

	/**
	 * パスワードの形式を設定します。
	 * 
	 * @param passwordAlgorithm
	 *            パスワードの形式
	 */
	public void setPasswordAlgorithm(String passwordAlgorithm);

	/**
	 * パスワードハッシュ作成時に使用するsaltの長さを返します。
	 * 
	 * @return パスワードハッシュ作成時に使用するsaltの長さ
	 */
	public int getPasswordSaltLength();

	/**
	 * パスワードハッシュ作成時に使用するsaltの長さを設定します。
	 * 
	 * @param passwordSaltLength
	 *            パスワードハッシュ作成時に使用するsaltの長さ
	 */
	public void setPasswordSaltLength(int passwordSaltLength);

	/**
	 * ユーザ名を返します。
	 * 
	 * @return ユーザ名
	 */
	public String getUser();

	/**
	 * @param user
	 *            ユーザ名を設定します。
	 */
	public void setUser(String user);

	/**
	 * ユーザユニットの接尾辞を取得します。
	 * 
	 * @return ユーザユニットの接尾辞
	 */
	public String getUserSuffix();

	/**
	 * ユーザユニットの接尾辞を設定します。
	 * 
	 * @param userSuffix
	 *            ユーザユニットの接尾辞
	 */
	public void setUserSuffix(String userSuffix);

	/**
	 * ユーザを識別するための属性名を取得します。
	 * 
	 * @return ユーザを識別するための属性名
	 */
	public String getUserAttributeName();

	/**
	 * ユーザを識別するための属性名を設定します。
	 * 
	 * @param userAttributeName
	 *            ユーザを識別するための属性名
	 */
	public void setUserAttributeName(String userAttributeName);

	/**
	 * グループユニットの接尾辞を取得します。
	 * 
	 * @return グループユニットの接尾辞
	 */
	public String getGroupSuffix();

	/**
	 * グループユニットの接尾辞を設定します。
	 * 
	 * @param groupSuffix
	 *            グループユニットの接尾辞
	 */
	public void setGroupSuffix(String groupSuffix);

	/**
	 * グループを識別するための属性名を取得します。
	 * 
	 * @return グループを識別するための属性名
	 */
	public String getGroupAttributeName();

	/**
	 * グループを識別するための属性名を設定します。
	 * 
	 * @param groupAttributeName
	 *            グループを識別するための属性名
	 */
	public void setGroupAttributeName(String groupAttributeName);

	/**
	 * 複数の属性値のための区切り文字を取得します。
	 * 
	 * @return multipleValueDelimiter 複数の属性値のための区切り文字
	 */
	public String getMultipleValueDelimiter();

	/**
	 * 複数の属性値のための区切り文字を設定します。
	 * 
	 * @param multipleValueDelimiter
	 *            複数の属性値のための区切り文字
	 */
	public void setMultipleValueDelimiter(String multipleValueDelimiter);

	/**
	 * フィルタを返します。
	 * 
	 * @return フィルタ
	 */
	public String getFilter();

	/**
	 * @param filter
	 *            指定されたフィルタを設定します。
	 */
	public void setFilter(String filter);

	/**
	 * SSL接続が有効かどうか判断します。
	 * 
	 * @return SSL接続が有効化どうか
	 */
	public boolean isEnableSSL();

	/**
	 * SSL接続が有効かどうか設定します。
	 * 
	 * @param enableSSL
	 */
	public void setEnableSSL(boolean enableSSL);

	/**
	 * TLS接続が有効かどうか判断します。
	 * 
	 * @return TLS接続が有効かどうか
	 */
	public boolean isEnableTLS();

	/**
	 * TLS接続が有効かどうか設定します。
	 * 
	 * @param enableTls
	 *            TLS接続を有効かどう
	 */
	public void setEnableTLS(boolean enableTls);

	/**
	 * コネクションプーリング設定を取得します。
	 * 
	 * @return コネクションプーリング設定
	 */
	public DirectoryConnectionPool getDirectoryConnectionPool();

	/**
	 * コネクションプーリング設定を取得します。
	 * 
	 * @param pool
	 *            コネクションプーリング設定
	 */
	public void setDirectoryConnectionPool(DirectoryConnectionPool pool);

	/**
	 * コネクションプーリングを行うかどうか判断します。
	 * 
	 * @return コネクションプーリングを行うかどうか
	 */
	public boolean isEnablePool();

}
