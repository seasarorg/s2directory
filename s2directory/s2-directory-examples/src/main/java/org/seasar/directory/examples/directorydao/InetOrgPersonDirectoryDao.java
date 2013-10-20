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
package org.seasar.directory.examples.directorydao;

import java.util.List;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.examples.entity.InetOrgPerson;

/**
 * InetOrgPersonのDaoクラスです。
 * 
 * @author Jun Futagawa
 */
public interface InetOrgPersonDirectoryDao {

	/** BEANアノテーション */
	public static final Class BEAN = InetOrgPerson.class;

	/**
	 * 指定したInetOrgPersonを追加します。
	 * 
	 * @param inetOrgPerson
	 *            InetOrgPerson
	 * @return 追加したエントリ数
	 */
	public int insertInetOrgPerson(InetOrgPerson inetOrgPerson);

	/**
	 * 指定したディレクトリ接続情報を使用して、指定したInetOrgPersonを追加します。
	 * 
	 * @param property
	 *            ディレクトリ接続情報
	 * @param inetOrgPerson
	 *            InetOrgPerson
	 * @return 追加したエントリ数
	 */
	public int insertInetOrgPersonWithUserMode(
			DirectoryControlProperty property, InetOrgPerson inetOrgPerson);

	/**
	 * 指定したInetOrgPersonを更新します。
	 * 
	 * @param inetOrgPerson
	 *            InetOrgPerson
	 * @return 更新したエントリ数
	 */
	public int updateInetOrgPerson(InetOrgPerson inetOrgPerson);

	/**
	 * 指定したInetOrgPersonを削除します。
	 * 
	 * @param inetOrgPerson
	 *            InetOrgPerson
	 * @return 削除したエントリ数
	 */
	public int deleteInetOrgPerson(InetOrgPerson inetOrgPerson);

	/**
	 * 指定したInetOrgPersonを検索します。
	 * 
	 * @param inetOrgPerson
	 *            InetOrgPerson
	 * @return
	 */
	public InetOrgPerson getInetOrgPerson(InetOrgPerson inetOrgPerson);

	/**
	 * 指定したディレクトリ接続情報を使用して、指定したInetOrgPersonを検索します。
	 * 
	 * @param property
	 *            ディレクトリ接続情報
	 * @param inetOrgPerson
	 *            InetOrgPerson
	 * @return
	 */
	public InetOrgPerson getInetOrgPersonWithUserMode(
			DirectoryControlProperty property, InetOrgPerson inetOrgPerson);

	/**
	 * すべてのInetOrgPersonを取得します。
	 * 
	 * @return
	 */
	public List getInetOrgPersonList();

}
