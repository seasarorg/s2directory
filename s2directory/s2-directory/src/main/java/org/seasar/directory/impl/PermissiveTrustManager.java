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
 * X.509証明書を寛容に管理します。<br />
 * 使用される証明書は、信頼されない証明書発行局発行の証明書、 期限の切れた証明書であっても利用できます。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date:: 2006-04-30 02:34:00 +0900#$
 */
public class PermissiveTrustManager implements X509TrustManager {
	/**
	 * 常に信頼できる証明書であると返します。<br />
	 * jdk 1.3/jsse 1.0.3_01用の関数です。
	 * 
	 * @param chain
	 *            ピアの証明書チェーン
	 * @return 常に true
	 */
	public boolean isClientTrusted(X509Certificate[] chain) {
		return true;
	}

	/**
	 * 常に信頼できる証明書であると返します。<br />
	 * jdk 1.3/jsse 1.0.3_01用の関数です。
	 * 
	 * @param chain
	 *            ピアの証明書チェーン
	 * @return 常に true
	 */
	public boolean isServerTrusted(X509Certificate[] chain) {
		return true;
	}

	/**
	 * 常に信頼できる証明書であると返します。
	 * 
	 * @param chain
	 *            ピアの証明書チェーン
	 * @param authType
	 *            使用される鍵交換アルゴリズム
	 */
	public void checkClientTrusted(X509Certificate[] chain, String authType) {
		return;
	}

	/**
	 * 常に信頼できる証明書であると返します。
	 * 
	 * @param chain
	 *            ピアの証明書チェーン
	 * @param authType
	 *            使用される鍵交換アルゴリズム
	 */
	public void checkServerTrusted(X509Certificate[] chain, String authType) {
		return;
	}

	/**
	 * {@inheritDoc}
	 */
	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	}
}
