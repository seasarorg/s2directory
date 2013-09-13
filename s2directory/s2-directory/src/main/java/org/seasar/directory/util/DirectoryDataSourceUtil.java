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
package org.seasar.directory.util;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.exception.DirectoryRuntimeException;

/**
 * ディレクトリコネクションを作成するユーティリティです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public final class DirectoryDataSourceUtil {

	/**
	 * ディレクトリサーバ接続情報をセットアップします。
	 * 
	 * @param property
	 *            ディレクトリサーバ接続情報
	 */
	public static void setupDirectoryControlProperty(
			DirectoryControlProperty property) {
		// ユーザ名が設定されている場合、バインドDNにユーザ名から求めたユーザを使用します。
		if (property.getUser() != null) {
			property.setBindDn(getFullUserDn(property));
		}
	}

	/**
	 * 完全なユーザDnを取得します。
	 * 
	 * @param property
	 *            ディレクトリサーバ接続情報
	 * @return 完全なユーザDn
	 */
	private static String getFullUserDn(DirectoryControlProperty property) {
		String user = property.getUser();
		// ユーザDNにベースDNが含まれている場合、フルDNで指定されていると見なします。
		int index = user.indexOf(property.getBaseDn());
		if (index >= 0) {
			return user;
		}
		// トップ識別子を取得します。
		String userFirstAttribute = user;
		index = user.indexOf(",");
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

	/**
	 * ディレクトリコネクションを閉じます。
	 * 
	 * @param context
	 *            ディレクトリコネクション
	 */
	public static void close(DirContext context) {
		try {
			if (context != null) {
				context.close();
			}
		} catch (NamingException e) {
			throw new DirectoryRuntimeException(e);
		}
	}

	/**
	 * ディレクトリ列挙を閉じます。
	 * 
	 * @param results
	 *            ディレクトリ列挙
	 */
	public static void close(NamingEnumeration results) {
		try {
			if (results != null) {
				results.close();
			}
		} catch (NamingException e) {
			throw new DirectoryRuntimeException(e);
		}
	}
}
