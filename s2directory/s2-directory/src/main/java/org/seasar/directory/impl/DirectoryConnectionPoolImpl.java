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

import org.seasar.directory.DirectoryConnectionPool;

/**
 * ディレクトリコネクションプーリングの設定情報の標準的な実装クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class DirectoryConnectionPoolImpl implements DirectoryConnectionPool {

	/** プールできる接続のスペース区切りの認証タイプのリスト */
	private String authentication;

	/** 生成されるデバッグ出力のレベルを示す文字列 */
	private String debug;

	/** 接続IDに対して接続を最初に作成するときの接続数 */
	private int initSize = -1;

	/** 同時に維持できる接続ID別の最大接続数 */
	private int maxSize = -1;

	/** 同時に維持しなければならない接続ID別の優先的接続数 */
	private int prefSize = -1;

	/** プールできる接続の、スペース区切りのプロトコルタイプのリスト */
	private String protocol;

	/** アイドル状態の接続が切断されず、プールから除外されることなくプール内に存在できる期間 (ミリ秒) */
	private int timeout = -1;

	/**
	 * {@inheritDoc}
	 */
	public String getAuthentication() {
		return authentication;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getDebug() {
		return debug;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDebug(String debug) {
		this.debug = debug;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getInitSize() {
		return initSize;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setInitSize(int initSize) {
		this.initSize = initSize;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getPrefSize() {
		return prefSize;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setPrefSize(int prefSize) {
		this.prefSize = prefSize;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * {@inheritDoc}
	 */

	public int getTimeout() {
		return timeout;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("authentication: ").append(authentication).append(", ");
		buffer.append("debug: ").append(debug).append(", ");
		buffer.append("initSize: ").append(initSize).append(", ");
		buffer.append("maxSize: ").append(maxSize).append(", ");
		buffer.append("prefSize: ").append(prefSize).append(", ");
		buffer.append("protocol: ").append(protocol).append(", ");
		buffer.append("timeout: ").append(timeout);
		return buffer.toString();
	}

}
