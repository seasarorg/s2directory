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
package org.seasar.directory.util;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.exception.DirectoryRuntimeException;

/**
 * Directoryコネクションを作成クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public final class DirectoryDataSourceUtils {
	/**
	 * 指定されたDirectory接続データソースからDirectoryコネクションを作成します。
	 * Directoryコネクション作成時に例外が発生した場合、LDAPランタイム例外が発生します。
	 * 
	 * @param DirectoryDataSource Directory接続データソース
	 * @return Directoryコネクション
	 * @throws DirectoryRuntimeException Directoryコネクション作成例外
	 */
	public static DirContext getConnection(
			DirectoryDataSource DirectoryDataSource) {
		try {
			return DirectoryDataSource.getConnection();
		} catch (NamingException ex) {
			throw new DirectoryRuntimeException(ex);
		}
	}

	/**
	 * ディレクトリサーバ接続情報をセットアップします。
	 * 
	 * @param property - ディレクトリサーバ接続情報
	 */
	public static void setupDirectoryControlProperty(
			DirectoryControlProperty property) {
		if (property.getUser() != null) {
			property.setUser(property.getUser().replaceAll("[ \t]", ""));
			property.setBaseDn(property.getBaseDn().replaceAll("[ \t]", ""));
			property.setUser(getFullUserDn(property));
		}
	}

	/**
	 * ディレクトリコネクションを閉じます。
	 * 
	 * @param context ディレクトリコネクション
	 */
	public static void close(DirContext context) {
		if (context == null) {
			return;
		}
		try {
			context.close();
		} catch (NamingException e) {
			throw new DirectoryRuntimeException(e);
		}
	}

	/**
	 * 完全なユーザDnを取得します。
	 * 
	 * @param property - ディレクトリサーバ接続情報
	 * @return 完全なユーザDn
	 */
	private static String getFullUserDn(DirectoryControlProperty property) {
		String user = property.getUser();
		// トップ識別子を取得します。
		String userFirstAttribute = user;
		int index = user.indexOf(",");
		if (index >= 0) {
			userFirstAttribute = user.substring(0, index);
		}
		// トップ識別子に "ユーザ識別属性名=" が入っていない場合、
		// 省略ユーザ表記とみなし、完全なユーザ表記に補完します。
		if (userFirstAttribute.indexOf("=") == -1) {
			String userAttributeName = property.getUserAttributeName();
			index = userFirstAttribute.indexOf(userAttributeName + "=");
			if (index == -1) {
				user = userAttributeName + "=" + user;
			}
		}
		// ユーザDNが "ユーザ識別属性名=" で始まっている場合、
		// ユーザユニットの接尾辞を補完します。
		if (user.startsWith(property.getUserAttributeName())) {
			String userSuffix = property.getUserSuffix();
			index = user.indexOf(userSuffix);
			if (index == -1) {
				user = user + "," + userSuffix;
			}
		}
		// ベースDNを補完します。
		String baseDn = property.getBaseDn();
		index = user.indexOf(baseDn);
		if (index == -1) {
			user = user + "," + baseDn;
		}
		return user;
	}
}