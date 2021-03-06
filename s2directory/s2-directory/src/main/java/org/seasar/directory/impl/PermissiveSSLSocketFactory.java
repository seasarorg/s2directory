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
package org.seasar.directory.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * すべてのホスト名が有効なものであると見なします。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class PermissiveSSLSocketFactory extends SSLSocketFactory {

	/** 自分自身のインスタンス */
	private SSLSocketFactory factory;

	/**
	 * インスタンスを作成します。
	 */
	public PermissiveSSLSocketFactory() {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(
				null,
				new TrustManager[] { new PermissiveTrustManager() },
				null);
			factory = (SSLSocketFactory)sslContext.getSocketFactory();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Unable to get SSL context: "
				+ e.getMessage());
		} catch (KeyManagementException e) {
			throw new IllegalStateException("Unable to initialize ctx: "
				+ e.getMessage());
		}
	}

	/**
	 * デフォルトのSSLソケットファクトリを返します。
	 */
	public static SocketFactory getDefault() {
		return new PermissiveSSLSocketFactory();
	}

	/**
	 * {@inheritDoc}
	 */
	public Socket createSocket(Socket s, String host, int port,
			boolean autoClose) throws IOException {
		return factory.createSocket(s, host, port, autoClose);
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getDefaultCipherSuites() {
		return factory.getDefaultCipherSuites();
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getSupportedCipherSuites() {
		return factory.getSupportedCipherSuites();
	}

	/**
	 * {@inheritDoc}
	 */
	public Socket createSocket(InetAddress host, int port) throws IOException {
		return factory.createSocket(host, port);
	}

	/**
	 * {@inheritDoc}
	 */
	public Socket createSocket(InetAddress address, int port,
			InetAddress localAddress, int localPort) throws IOException {
		return factory.createSocket(address, port, localAddress, localPort);
	}

	/**
	 * {@inheritDoc}
	 */
	public Socket createSocket(String host, int port) throws IOException,
			UnknownHostException {
		return factory.createSocket(host, port);
	}

	/**
	 * {@inheritDoc}
	 */
	public Socket createSocket(String host, int port, InetAddress localHost,
			int localPort) throws IOException, UnknownHostException {
		return factory.createSocket(host, port, localHost, localPort);
	}

}
