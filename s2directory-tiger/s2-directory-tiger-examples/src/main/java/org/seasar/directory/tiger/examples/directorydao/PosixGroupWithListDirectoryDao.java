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

import java.util.List;

import org.seasar.directory.dao.annotation.tiger.S2Directory;
import org.seasar.directory.tiger.examples.entity.PosixGroupWithList;

/**
 * PosixAccountのDaoインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date:: 2007-12-08 03:35:41 +0900#$
 */
@S2Directory(bean = PosixGroupWithList.class)
public interface PosixGroupWithListDirectoryDao {
	public String OBJECTCLASSES = "posixGroup";

	public static final String getPosixGroupList_QUERY =
		"objectclass=posixGroup";

	public List<PosixGroupWithList> getPosixGroupList();

	public PosixGroupWithList getPosixGroup(PosixGroupWithList group);

	public List<PosixGroupWithList> getPosixGroupList(PosixGroupWithList group);

	public static final String getPosixGroupByCn_ARGS = "cn";

	public PosixGroupWithList getPosixGroupByCn(String cn);

	public static final String getPosixGroupListByMemberUid_ARGS = "memberUid";

	public List<PosixGroupWithList> getPosixGroupListByMemberUid(
			String memberUid);

	public static final String getPosixGroupListByMemberUidList_ARGS =
		"memberUid";

	public List<PosixGroupWithList> getPosixGroupListByMemberUidList(
			List<String> memberUid);

	public int insert(PosixGroupWithList group);

	public int update(PosixGroupWithList group);

	public int delete(PosixGroupWithList group);

	public List<PosixGroupWithList> getAllGroup();
}
