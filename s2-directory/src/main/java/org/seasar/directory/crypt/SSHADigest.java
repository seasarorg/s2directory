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

/**
 * SSHAによるハッシュを扱うクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class SSHADigest extends SHADigest {
	/** 暗号形式を表します。 */
	public static final String LABEL = "{SSHA}";

	/**
	 * 指定された乱数を用いてパスワードをハッシュ化します。
	 * 
	 * @param salt
	 * @param password
	 * @return
	 * @see org.seasar.directory.crypt.Digest#create(byte[], java.lang.String)
	 */
	public String create(String password) {
		return LABEL + super.create(DigestUtils.getRandomSalt(), password);
	}
}
