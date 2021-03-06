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
package org.seasar.directory.generater.parser;

/**
 * パーサ用のユーティリティクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class ParserUtil {
	/** indent string */
	public static final String INDENT_STRING = ParserConstant.INDENT_STRING;

	public static String getIndent(int amount) {
		StringBuffer sb = new StringBuffer();
		for (; amount != 0; amount--) {
			sb.append(INDENT_STRING);
		}
		return sb.toString();
	}

	public static StringBuffer deleteLast(StringBuffer sb, int amount) {
		if (sb.length() < amount)
			return sb;
		sb.delete(sb.length() - amount, sb.length());
		return sb;
	}
}
