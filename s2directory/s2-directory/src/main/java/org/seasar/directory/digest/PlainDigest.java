/*
 * Copyright 2005-2008 the Seasar Foundation and the Others.
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

/**
 * 平文パスワードを扱うクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class PlainDigest implements Digest {
	/** 暗号形式 */
	public static final String LABEL = "{PLAIN}";

	/**
	 * インスタンスを生成します。
	 */
	public PlainDigest() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	public String create(String password) {
		return create(password);
	}

	/**
	 * {@inheritDoc}
	 */
	public String create(String password, int saltLenght) {
		return password;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean verify(String digest, String password) {
		return digest.equals(password);
	}
}
