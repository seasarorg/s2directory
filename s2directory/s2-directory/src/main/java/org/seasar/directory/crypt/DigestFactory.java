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

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ダイジェストの生成クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public final class DigestFactory {
	/** 暗号方式を形式を表します。 */
	private static final Pattern pattern = Pattern.compile("^\\{.+\\}");
	/** ダイジェストのマップを現します。 */
	private static Map digests = Collections.synchronizedMap(new HashMap());
	/**
	 * 初期化します。
	 */
	static {
		registerDigest(MD5Digest.LABEL, new MD5Digest());
		registerDigest(SMD5Digest.LABEL, new SMD5Digest());
		registerDigest(SHADigest.LABEL, new SHADigest());
		registerDigest(SSHADigest.LABEL, new SSHADigest());
	}

	/**
	 * ダイジェストを登録します。
	 * 
	 * @param algorithm
	 * @param digest
	 */
	public static void registerDigest(String algorithm, Digest digest) {
		digests.put(algorithm, digest);
	}

	/**
	 * ダイジェストを取得します。
	 * 
	 * @param hash
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static Digest getDigest(String hash) throws NoSuchAlgorithmException {
		String algorithm = "";
		Matcher matcher = pattern.matcher(hash);
		if (matcher.find()) {
			algorithm = matcher.group(0);
		} else {
			algorithm = "{" + hash + "}";
		}
		Digest digest = (Digest)digests.get(algorithm);
		if (digest == null)
			throw new NoSuchAlgorithmException(algorithm
					+ " algorithm is not suported.");
		return digest;
	}
}
