/*
 * Copyright 2005-2006 the Seasar Foundation and the Others.
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
package org.seasar.directory;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * 検索結果を扱うインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface NamingEnumerationHandler {
	/**
	 * 指定された検索結果を処理し、結果オブジェクトを返します。
	 * 
	 * @param results 検索結果
	 * @param baseDn 基底の識別名
	 * @return 結果オブジェクト
	 * @throws NamingException
	 */
	public Object handle(NamingEnumeration results, String baseDn)
			throws NamingException;
}
