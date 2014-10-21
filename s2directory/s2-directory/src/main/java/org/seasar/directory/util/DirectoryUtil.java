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
package org.seasar.directory.util;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.naming.directory.SearchControls;

import org.seasar.directory.digest.Digest;
import org.seasar.directory.digest.DigestFactory;
import org.seasar.framework.util.StringUtil;

/**
 * ディレクトリに関するユーティリティクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public final class DirectoryUtil {

	/**
	 * 指定された識別名からトップの識別名を取得します。
	 * 
	 * @param fullDn
	 *            識別名
	 * @return トップの識別名
	 */
	public static String getFirstDn(String fullDn) {
		int index = fullDn.indexOf(",");
		if (index > 0) {
			return fullDn.substring(0, index).trim();
		}
		return fullDn;
	}

	/**
	 * 指定された識別名から基底の識別名を取得します。
	 * 
	 * @param fullDn
	 *            識別名
	 * @return 基底の識別名
	 */
	public static String getBaseDn(String fullDn) {
		int index = fullDn.indexOf(",");
		if (index > 0) {
			return fullDn.substring(index + 1).trim();
		}
		return fullDn;
	}

	/**
	 * 属性名を取得します。
	 * 
	 * @param valueSet
	 *            属性名と属性値のセット
	 * @return 属性名
	 */
	public static String getAttributeName(String valueSet) {
		valueSet = getFirstDn(valueSet);
		return valueSet.substring(0, valueSet.indexOf("=")).trim();
	}

	/**
	 * 属性値を取得します。
	 * 
	 * @param valueSet
	 *            属性名と属性値のセット
	 * @return 属性値
	 */
	public static String getAttributeValue(String valueSet) {
		valueSet = getFirstDn(valueSet);
		return valueSet.substring(valueSet.indexOf("=") + 1).trim();
	}

	/**
	 * パスワードが正しいか検証します。<br />
	 * パスワードが正しい場合 <code>true</code> 不正な場合は <code>false</code> を返します。
	 * 
	 * @param password
	 * @param hash
	 * @return パスワードが正しい場合 <code>true</code> 不正な場合は <code>false</code>
	 */
	public static boolean verifyPassword(String hash, String password) {
		try {
			Digest digest = DigestFactory.getDigest(hash);
			return digest.verify(hash, password);
		} catch (NoSuchAlgorithmException e) {
			return false;
		}
	}

	/**
	 * パスワードを作成します。
	 * 
	 * @param password
	 *            平文パスワード
	 * @param algorithm
	 *            アルゴリズム名
	 * @param saltLength
	 *            saltの長さ
	 * @return パスワード
	 */
	public static String createPassword(String password, String algorithm,
			int saltLength) {
		try {
			Digest digest = DigestFactory.getDigest(algorithm);
			return digest.create(password, saltLength);
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}

	/**
	 * フィルターを作成します。
	 * 
	 * @param operator
	 *            オペレーター
	 * @param name
	 *            属性名
	 * @param values
	 *            値の配列
	 * @return フィルター
	 */
	public static String createFilter(String operator, String name,
			String[] values) {
		if (values == null) {
			return "";
		}
		if (values.length == 0) {
			return "";
		}
		// aaa
		// objectClass=aaa
		if (values.length == 1) {
			if (StringUtil.isEmpty(values[0])) {
				return "";
			} else {
				return name + "=" + values[0];
			}
		}
		// aaa bbb
		// (& (objectClass=aaa) (objectClass=bbb) )
		// aaa bbb ccc
		// (& (&(objectClass=aaa)(objectClass=bbb)) (objectClass=ccc) )
		final StringBuffer buffer = new StringBuffer();
		buffer.append("(" + name + "=").append(values[0]).append(")");
		for (int i = 1; i < values.length; i++) {
			buffer.insert(0, "(" + operator);
			buffer.append("(" + name + "=").append(values[i]).append(")");
			buffer.append(")");
		}
		return buffer.toString();
	}

	/**
	 * フィルターを追加します。
	 * 
	 * @param operator
	 *            　オペレーター
	 * @param baseFilter
	 *            追加されるフィルター
	 * @param appendFilter
	 *            追加するフィルター
	 * @return 追加されたフィルター
	 */
	public static String addFilter(String operator, String baseFilter,
			String appendFilter) {
		if (StringUtil.isEmpty(appendFilter)) {
			if (StringUtil.isEmpty(baseFilter)) {
				return "";
			} else {
				return baseFilter;
			}
		}
		if (StringUtil.isEmpty(baseFilter)) {
			return appendFilter;
		}

		final StringBuffer buffer = new StringBuffer();
		buffer.append("(&");
		if (baseFilter.charAt(0) == '(') {
			buffer.append(baseFilter);
		} else {
			buffer.append("(").append(baseFilter).append(")");
		}
		if (appendFilter.charAt(0) == '(') {
			buffer.append(appendFilter);
		} else {
			buffer.append("(").append(appendFilter).append(")");
		}
		buffer.append(")");
		return buffer.toString();
	}

	/**
	 * {@link SearchControls} の文字列表現を返します。
	 * 
	 * @param controls
	 *            {@link SearchControls}
	 * @return {@link SearchControls} の文字列表現
	 */
	public static String toStringFromSearchControls(SearchControls controls) {
		if (controls == null) {
			return "";
		}
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("countLimit: ").append(controls.getCountLimit()).append(
			", ");
		buffer.append("derefLinkFlag: ").append(controls.getDerefLinkFlag()).append(
			", ");
		buffer.append("returningAttributes: ");
		String[] attrs = controls.getReturningAttributes();
		buffer.append(attrs != null ? Arrays.asList(attrs).toString() : "null");
		buffer.append(", ");
		buffer.append("searchScope: ");
		switch (controls.getSearchScope()) {
		case SearchControls.OBJECT_SCOPE:
			buffer.append("OBJECT_SCOPE");
			break;
		case SearchControls.ONELEVEL_SCOPE:
			buffer.append("ONELEVEL_SCOPE");
			break;
		case SearchControls.SUBTREE_SCOPE:
			buffer.append("SUBTREE_SCOPE");
			break;
		}
		buffer.append(", ");
		buffer.append("timeLimit: ").append(controls.getTimeLimit());
		buffer.append("}");
		return buffer.toString();
	}

}
