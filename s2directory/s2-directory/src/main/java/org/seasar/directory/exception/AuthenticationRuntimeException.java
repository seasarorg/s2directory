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

import org.seasar.directory.DirectoryControlProperty;

/**
 * ネーミングサービスまたはディレクトリサービスにアクセスする際に認証エラーが発生した場合にスローされます。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public final class AuthenticationRuntimeException extends
		DirectoryRuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 指定されたサーバ接続情報を使用して新しいインスタンスを構築します。
	 * 
	 * @param property
	 *            サーバ接続情報
	 */
	public AuthenticationRuntimeException(DirectoryControlProperty property) {
		super("EDIR0003", new Object[] {
			property.getUrl(),
			property.getBindDn(),
			property.getPassword() });
	}

}
