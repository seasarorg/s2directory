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
package org.seasar.directory.digest;

/**
 * SHAによるハッシュを扱うクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class SHADigest extends AbstractMessageDigest {

	/** 暗号形式 */
	public static final String LABEL = "{SHA}";

	/**
	 * インスタンスを生成します。
	 */
	public SHADigest() {
		super("SHA");
	}

	/**
	 * {@inheritDoc}
	 */
	public String getLabel() {
		return LABEL;
	}

	/**
	 * {@inheritDoc}
	 */
	public String create(String password) {
		return create(password, -1);
	}

	/**
	 * {@inheritDoc}
	 */
	public String create(String password, int saltLenght) {
		return LABEL + super.createHash(new byte[0], password);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean verify(String digest, String password) {
		return super.verifyPassword(digest.substring(LABEL.length()), password);
	}

}
