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

import java.security.NoSuchAlgorithmException;
import org.seasar.directory.digest.Digest;
import org.seasar.directory.digest.DigestFactory;

/**
 * ディレクトリに関するユーティリティクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public final class DirectoryUtils {
	/**
	 * 指定された識別名からトップの識別名を取得します。
	 * 
	 * @param fullDn
	 * @return
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
	 * @return
	 */
	public static String getBaseDn(String fullDn) {
		int index = fullDn.indexOf(",");
		if (index > 0) {
			return fullDn.substring(index + 1).trim();
		}
		return fullDn;
	}

	public static String getAttributeName(String valueSet) {
		valueSet = getFirstDn(valueSet);
		return valueSet.substring(0, valueSet.indexOf("=")).trim();
	}

	public static String getAttributeValue(String valueSet) {
		valueSet = getFirstDn(valueSet);
		return valueSet.substring(valueSet.indexOf("=") + 1).trim();
	}

	/**
	 * パスワードが等しいか比較します。
	 * 
	 * @param password
	 * @param hash
	 * @return
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
	 * 新しいパスワードを作成します。
	 * 
	 * @param plain
	 * @param algorithm
	 * @return
	 */
	public static String createPassword(String password, String algorithm) {
		try {
			Digest digest = DigestFactory.getDigest(algorithm);
			return digest.create(password);
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
	}
}
