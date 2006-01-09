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
package org.seasar.directory.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;

/**
 * メッセージダイジェストの抽象クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public abstract class AbstractMessageDigest implements Digest {
	/** メッセージダイジェストアルゴリズムを表します。 */
	private MessageDigest md;

	/**
	 * インスタンスを作成します。
	 * 
	 * @param algorithm
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
	 * @param salt 乱数
	 * @param password ハッシュ化するパスワード
	 */
	public String create(byte[] salt, String password) {
		md.reset();
		md.update(password.getBytes());
		md.update(salt);
		byte[] pwhash = md.digest();
		return new String(Base64.encodeBase64(DigestUtils.concat(pwhash, salt)));
	}

	/**
	 * 指定されたハッシュ値とパスワードのハッシュが同じか判別します。
	 * 
	 * @param digest ハッシュ化されたパスワード
	 * @param password 確認するパスワード
	 */
	public boolean verify(String digest, String password, int size) {
		byte[][] hs = DigestUtils.split(Base64.decodeBase64(digest.getBytes()),
				size);
		byte[] hash = hs[0];
		byte[] salt = hs[1];
		md.reset();
		md.update(password.getBytes());
		md.update(salt);
		byte[] pwhash = md.digest();
		boolean valid = true;
		if (!MessageDigest.isEqual(hash, pwhash)) {
			valid = false;
		}
		return valid;
	}
}
