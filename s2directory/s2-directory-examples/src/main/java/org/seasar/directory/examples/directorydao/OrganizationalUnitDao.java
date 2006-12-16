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
package org.seasar.directory.examples.directorydao;

import org.seasar.directory.examples.entity.OrganizationalUnit;

/**
 * OrganizationalUnitのDaoクラスです。
 * 
 * @author Jun Futagawa
 * @version $Date::                           $
 */
public interface OrganizationalUnitDao {
	/** BEANアノテーション */
	public static final Class BEAN = OrganizationalUnit.class;

	/**
	 * 指定したOrganizationalUnitを追加します。
	 * 
	 * @param organizationalUnit OrganizationalUnit
	 * @return 追加したエントリ数
	 */
	public int insertOrganizationalUnit(OrganizationalUnit organizationalUnit);

	/**
	 * 指定したOrganizationalUnitを更新します。
	 * 
	 * @param organizationalUnit OrganizationalUnit
	 * @return 更新したエントリ数
	 */
	public int updateOrganizationalUnit(OrganizationalUnit organizationalUnit);

	/**
	 * 指定したOrganizationalUnitを削除します。
	 * 
	 * @param organizationalUnit OrganizationalUnit
	 * @return 削除したエントリ数
	 */
	public int deleteOrganizationalUnit(OrganizationalUnit organizationalUnit);

	/**
	 * 指定したOrganizationalUnitを検索します。
	 * 
	 * @param organizationalUnit OrganizationalUnit
	 * @return
	 */
	public OrganizationalUnit getOrganizationalUnit(
			OrganizationalUnit organizationalUnit);
}