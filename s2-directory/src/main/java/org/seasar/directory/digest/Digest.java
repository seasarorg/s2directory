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
 * ダイジェストインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface Digest {
	/**
	 * 指定されたパスワードをハッシュ化します。
	 * 
	 * @param password
	 * @return ハッシュ化された文字列
	 */
	public String create(String password);

	/**
	 * 指定されたハッシュ値とパスワードのハッシュが同じか判別します。<br />
	 * パスワードが正しい場合 <code>true</code> 不正な場合 <code>false</code> を返します。
	 * 
	 * @param digest
	 * @param password
	 * @return 検証結果
	 */
	public boolean verify(String digest, String password);
}
