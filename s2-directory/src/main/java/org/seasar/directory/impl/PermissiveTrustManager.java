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
package org.seasar.directory.impl;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date:: 2006-04-30 02:34:00 +0900#$
 */
public class PermissiveTrustManager implements X509TrustManager {
	public void checkClientTrusted(X509Certificate[] chain, String authType) {
		// do nothing
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) {
		// do nothing
	}

	/**
	 * required for jdk 1.3/jsse 1.0.3_01
	 * 
	 * @param chain
	 * @return
	 */
	public boolean isClientTrusted(X509Certificate[] chain) {
		return true;
	}

	/**
	 * required for jdk 1.3/jsse 1.0.3_01
	 * 
	 * @param chain
	 * @return
	 */
	public boolean isServerTrusted(X509Certificate[] chain) {
		return true;
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}
