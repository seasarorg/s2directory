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
package org.seasar.directory.impl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * すべてのホスト名が有効なものであると見なします。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date:: 2006-04-30 02:34:00 +0900#$
 */
public class PermissiveHostnameVerifier implements HostnameVerifier {
	/**
	 * すべてのホスト名の検証を有効であると返します。
	 * 
	 * @param hostname
	 *            ホスト名
	 * @param session
	 *            ホストへの接続に使用された SSLSession
	 * @return 常に true
	 */
	public boolean verify(String hostname, SSLSession session) {
		return true;
	}
}
