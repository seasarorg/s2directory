/*
 * Copyright 2005-2008 the Seasar Foundation and the Others.
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
package org.seasar.directory.tiger.examples.directorydao;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.dao.annotation.tiger.S2Directory;
import org.seasar.directory.tiger.examples.entity.Person;

/**
 * PersonのDaoクラスです。
 * 
 * @author Jun Futagawa
 * @version $Date:: 2007-12-08 03:35:41 +0900#$
 */
@S2Directory(bean = Person.class)
public interface PersonDirectoryDao {
	/**
	 * 指定したPersonを追加します。
	 * 
	 * @param person
	 *            Person
	 * @return 追加したエントリ数
	 */
	public int insertPerson(Person person);

	/**
	 * 指定したディレクトリ接続情報を使用して、指定したPersonを追加します。
	 * 
	 * @param property
	 *            ディレクトリ接続情報
	 * @param person
	 *            Person
	 * @return 追加したエントリ数
	 */
	public int insertPersonWithUserMode(DirectoryControlProperty property,
			Person person);

	/**
	 * 指定したPersonを更新します。
	 * 
	 * @param person
	 *            Person
	 * @return 更新したエントリ数
	 */
	public int updatePerson(Person person);

	/**
	 * 指定したPersonを削除します。
	 * 
	 * @param person
	 *            Person
	 * @return 削除したエントリ数
	 */
	public int deletePerson(Person person);

	/**
	 * 指定したPersonを検索します。
	 * 
	 * @param person
	 *            Person
	 * @return
	 */
	public Person getPerson(Person person);

	/**
	 * 指定したディレクトリ接続情報を使用して、指定したPersonを検索します。
	 * 
	 * @param property
	 *            ディレクトリ接続情報
	 * @param person
	 *            Person
	 * @return
	 */
	public Person getPersonWithUserMode(DirectoryControlProperty property,
			Person person);
}