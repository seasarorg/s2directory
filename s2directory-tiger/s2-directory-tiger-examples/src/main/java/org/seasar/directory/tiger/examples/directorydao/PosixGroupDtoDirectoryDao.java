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
package org.seasar.directory.tiger.examples.directorydao;

import java.util.List;

import org.seasar.directory.dao.annotation.tiger.S2Directory;
import org.seasar.directory.tiger.examples.dto.PosixGroupDto;

/**
 * PosixグループDtoDaoクラスです。
 * 
 * @author Jun Futagawa
 * @version $Date:: 2007-12-08 03:35:41 +0900#$
 */
@S2Directory(bean = PosixGroupDto.class)
public interface PosixGroupDtoDirectoryDao {
	/**
	 * 指定したPosixグループを追加します。
	 * 
	 * @param posixGroupDto
	 *            Posixグループ
	 * @return 追加したエントリ数
	 */
	public int insertPosixGroup(PosixGroupDto posixGroupDto);

	/**
	 * 指定したPosixグループを更新します。
	 * 
	 * @param posixGroupDto
	 *            Posixグループ
	 * @return 更新したエントリ数
	 */
	public int updatePosixGroup(PosixGroupDto posixGroupDto);

	/**
	 * 指定したPosixグループを削除します。
	 * 
	 * @param posixGroupDto
	 *            Posixグループ
	 * @return 削除したエントリ数
	 */
	public int deletePosixGroup(PosixGroupDto posixGroupDto);

	/**
	 * 指定したPosixグループを検索します。
	 * 
	 * @param posixGroupDto
	 *            Posixグループ
	 * @return グループ
	 */
	public PosixGroupDto getPosixGroup(PosixGroupDto posixGroupDto);

	/** QUERYアノテーション getPosixGroupList */
	public static final String getPosixGroupList_QUERY =
		"objectclass=posixGroup";

	/**
	 * すべてのPosixグループのリストを取得します。
	 * 
	 * @return グループリスト
	 */
	public List<PosixGroupDto> getPosixGroupList();

	/** ARGSアノテーション getPosixGroupListByMemberUid */
	public static final String getPosixGroupListByMemberUid_ARGS = "memberUid";

	/**
	 * 指定したグループ名のPosixグループのリストを取得します。
	 * 
	 * @param memberUid
	 *            メンバー名
	 * @return グループリスト
	 */
	public List<PosixGroupDto> getPosixGroupListByMemberUid(
			List<PosixGroupDto> memberUid);
}