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
package org.seasar.directory;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

/**
 * このディレクトリデータソースが表す物理LDAPソースへの接続に対するファクトリインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public interface DirectoryDataSource {

	/** SSLソケットファクトリのための設定名 */
	public final static String SSL_SOCKET_FACTORY_KEY =
		"java.naming.ldap.factory.socket";

	/** LDAPコネクションプーリングのための設定名 */
	public final static String CONNECTION_POOL_KEY =
		"com.sun.jndi.ldap.connect.pool";

	/**
	 * 接続情報を返します。
	 * 
	 * @return 接続情報
	 */
	public DirectoryControlProperty getDirectoryControlProperty();

	/**
	 * 保持している接続情報を使用して作成したコネクションを返します。
	 * 
	 * @return コネクション
	 * @throws NamingException
	 */
	public DirContext getConnection() throws NamingException;

	/**
	 * 指定された接続情報を使用して作成したコネクションを返します。
	 * 
	 * @param property
	 *            接続情報
	 * @return コネクション
	 * @throws NamingException
	 */
	public DirContext getConnection(DirectoryControlProperty property)
			throws NamingException;

}
