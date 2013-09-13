/*
 * Copyright 2005-2013 the Seasar Foundation and the Others.
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
 * 不正なスキーマの例外クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class ParseException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 例外を生成します。
	 * 
	 * @param message
	 *            メッセージ
	 */
	public ParseException(String message) {
		super(message);
	}

	/**
	 * この例外の元になった例外を指定して例外を生成します。
	 * 
	 * @param message
	 *            メッセージ
	 * @param cause
	 *            元になった例外
	 */
	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
