/*
 * Copyright 2005-2007 the Seasar Foundation and the Others.
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
package org.seasar.directory.digest;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.seasar.framework.util.Base64Util;

/**
 * メッセージダイジェストの抽象クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public abstract class AbstractMessageDigest implements Digest {
	/** メッセージダイジェストアルゴリズム */
	private MessageDigest md;
	/** エンコーディング */
	private final static String ENCODING = "UTF-8";

	/**
	 * インスタンスを作成します。
	 * 
	 * @param algorithm
	 *            暗号アルゴリズム
	 */
	public AbstractMessageDigest(String algorithm) {
		try {
			md = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(algorithm
				+ " algorithm is not suported.");
		}
	}

	/**
	 * 指定された乱数を用いてパスワードをハッシュ化します。
	 * 
	 * @param salt
	 *            乱数
	 * @param password
	 *            ハッシュ化するパスワード
	 */
	public String create(byte[] salt, String password) {
		md.reset();
		try {
			md.update(password.getBytes(ENCODING));
			md.update(salt);
			byte[] pwhash = md.digest();
			return Base64Util.encode(DigestUtils.concat(pwhash, salt));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}

	/**
	 * 指定されたハッシュ値とパスワードのハッシュが同じか判別します。
	 * 
	 * @param digest
	 *            ハッシュ化されたパスワード
	 * @param password
	 *            確認するパスワード
	 */
	public boolean verify(String digest, String password, int size) {
		if (digest.length() < 4) {
			return false;
		}
		byte[][] hs = DigestUtils.split(Base64Util.decode(digest), size);
		byte[] hash = hs[0];
		byte[] salt = hs[1];
		md.reset();
		try {
			md.update(password.getBytes(ENCODING));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		md.update(salt);
		byte[] pwhash = md.digest();
		boolean valid = true;
		if (!MessageDigest.isEqual(hash, pwhash)) {
			valid = false;
		}
		return valid;
	}
}
