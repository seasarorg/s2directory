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
package org.seasar.directory.exception;

import javax.naming.NameAlreadyBoundException;

/**
 * 名前がすでに他のオブジェクトにバインドされているために、バインディングを追加できないことを示すメソッドによってスローされます。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public final class DirectoryNameAlreadyBoundRuntimeException extends
		DirectoryRuntimeException {
	/**
	 * 指定された例外を使用して新しいインスタンスを構築します。
	 * 
	 * @param cause NamingException この例外に関する詳細情報
	 */
	public DirectoryNameAlreadyBoundRuntimeException(
			NameAlreadyBoundException cause) {
		super("EDIRECTORY0004", new Object[] { cause.getRemainingName() });
	}
}
