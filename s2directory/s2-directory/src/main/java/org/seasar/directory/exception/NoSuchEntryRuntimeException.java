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
package org.seasar.directory.exception;

/**
 * 指定したDNにエントリが存在しないことを通知します。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public final class NoSuchEntryRuntimeException extends
		DirectoryRuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 指定された例外を使用して新しいインスタンスを構築します。
	 * 
	 * @param message
	 *            この例外に関する詳細情報
	 */
	public NoSuchEntryRuntimeException(String message) {
		super("EDIR0007", new Object[] { message });
	}

}
