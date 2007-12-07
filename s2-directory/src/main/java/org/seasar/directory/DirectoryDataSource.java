/*
 * Copyright 2005-2007 the Seasar Foundation and the Others.
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
 * このDirectoryDataSourceオブジェクトが表す物理LDAPソースへの接続に対するファクトリインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface DirectoryDataSource {
	/**
	 * 接続情報を設定します。
	 * 
	 * @param property
	 *            接続情報
	 */
	public void setDirectoryControlProperty(DirectoryControlProperty property);

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

	/**
	 * 保持している接続情報を使用してサーバに接続できるか調べます。
	 * 
	 * @return 接続可能ならば true
	 * @throws NamingException
	 */
	public boolean authenticate() throws NamingException;

	/**
	 * 指定された接続情報を使用してサーバに接続できるか調べます。
	 * 
	 * @return 接続可能ならば true
	 * @throws NamingException
	 */
	public boolean authenticate(DirectoryControlProperty property)
			throws NamingException;
}
