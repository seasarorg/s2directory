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
package org.seasar.directory.exception;

import javax.naming.NamingException;

import org.seasar.framework.exception.SRuntimeException;

/**
 * Context および DirContext インタフェースでの操作によってスローされるすべての例外のスーパークラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryRuntimeException extends SRuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 指定された NamingException で例外を作成します。
	 * 
	 * @param cause
	 *            NamingException
	 */
	public DirectoryRuntimeException(NamingException cause) {
		super("DIR0001", new Object[] { cause }, cause);
	}

	/**
	 * 指定された NamingException で例外を作成します。
	 * 
	 * @param message
	 */
	public DirectoryRuntimeException(String message) {
		super("DIR0001", new Object[] { message });
	}

	/**
	 * 指定されたメッセージコードと埋め込みオブジェクトを使用して新しいインスタンスを構築します。
	 * 
	 * @param messageCode
	 *            メッセージコード
	 * @param args
	 *            埋め込みオブジェクト
	 */
	public DirectoryRuntimeException(String messageCode, Object[] args) {
		super(messageCode, args);
	}
}
