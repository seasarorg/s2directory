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
package org.seasar.directory;

/**
 * ディレクトリコネクションプーリングの設定情報を表すインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public interface DirectoryConnectionPool {

	/**
	 * プールできる接続のスペース区切りの認証タイプのリストを取得します。
	 * 
	 * @return プールできる接続のスペース区切りの認証タイプのリスト
	 */
	public String getAuthentication();

	/**
	 * プールできる接続のスペース区切りの認証タイプのリストを設定します。
	 * 
	 * @param authentication
	 *            プールできる接続のスペース区切りの認証タイプのリスト
	 */
	public void setAuthentication(String authentication);

	/**
	 * 生成されるデバッグ出力のレベルを示す文字列を取得します。
	 * 
	 * @return 生成されるデバッグ出力のレベルを示す文字列
	 */
	public String getDebug();

	/**
	 * 生成されるデバッグ出力のレベルを示す文字列を設定します。
	 * 
	 * @param debug
	 *            生成されるデバッグ出力のレベルを示す文字列
	 */
	public void setDebug(String debug);

	/**
	 * 接続IDに対して接続を最初に作成するときの接続数を取得します。
	 * 
	 * @return 接続IDに対して接続を最初に作成するときの接続数
	 */
	public int getInitSize();

	/**
	 * 接続IDに対して接続を最初に作成するときの接続数を取得します。
	 * 
	 * @param initSize
	 *            接続IDに対して接続を最初に作成するときの接続数
	 */
	public void setInitSize(int initSize);

	/**
	 * 同時に維持できる接続ID別の最大接続数を取得します。
	 * 
	 * @return 同時に維持できる接続ID別の最大接続数
	 */
	public int getMaxSize();

	/**
	 * 同時に維持できる接続ID別の最大接続数を設定します。
	 * 
	 * @param maxSize
	 *            同時に維持できる接続ID別の最大接続数
	 */
	public void setMaxSize(int maxSize);

	/**
	 * 同時に維持しなければならない接続ID別の優先的接続数を取得します。
	 * 
	 * @return 同時に維持しなければならない接続ID別の優先的接続数
	 */
	public int getPrefSize();

	/**
	 * 同時に維持しなければならない接続ID別の優先的接続数を設定します。
	 * 
	 * @param prefSize
	 *            同時に維持しなければならない接続ID別の優先的接続数
	 */
	public void setPrefSize(int prefSize);

	/**
	 * プールできる接続の、スペース区切りのプロトコルタイプのリストを取得します。
	 * 
	 * @return プールできる接続の、スペース区切りのプロトコルタイプのリスト
	 */
	public String getProtocol();

	/**
	 * プールできる接続の、スペース区切りのプロトコルタイプのリストを設定します。
	 * 
	 * @param protocol
	 *            プールできる接続の、スペース区切りのプロトコルタイプのリスト
	 */
	public void setProtocol(String protocol);

	/**
	 * アイドル状態の接続が切断されず、プールから除外されることなくプール内に存在できる期間 (ミリ秒) を取得します。
	 * 
	 * @return プール内に存在できる期間 (ミリ秒)
	 */
	public int getTimeout();

	/**
	 * アイドル状態の接続が切断されず、プールから除外されることなくプール内に存在できる期間 (ミリ秒) を設定します。
	 * 
	 * @param timeout
	 *            プール内に存在できる期間 (ミリ秒)
	 */
	public void setTimeout(int timeout);

}
