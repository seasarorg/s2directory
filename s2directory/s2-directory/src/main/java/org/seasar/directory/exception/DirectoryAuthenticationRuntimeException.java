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

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.framework.exception.SRuntimeException;

/**
 * サーバ接続認証の例外クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public final class DirectoryAuthenticationRuntimeException extends
		SRuntimeException {
	/**
	 * 指定されたサーバ接続情報で例外を作成します。
	 * 
	 * @param property サーバ接続情報
	 */
	public DirectoryAuthenticationRuntimeException(
			DirectoryControlProperty property) {
		super("EDIRECTORY0003", new Object[] { property.getUrl(),
				property.getUser(), property.getPassword() });
	}
}
