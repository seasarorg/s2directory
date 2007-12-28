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
import org.seasar.directory.tiger.examples.entity.PosixGroup;

/**
 * PosixAccountのDaoインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date:: 2007-12-08 03:35:41 +0900#$
 */
@S2Directory(bean = PosixGroup.class)
public interface PosixGroupDirectoryDao {
	public static final String getPosixGroupList_QUERY =
		"objectclass=posixGroup";

	public List<PosixGroup> getPosixGroupList();

	public PosixGroup getPosixGroup(PosixGroup group);

	public List<PosixGroup> getPosixGroupList(PosixGroup group);

	public static final String getPosixGroupByCn_ARGS = "cn";

	public PosixGroup getPosixGroupByCn(String cn);

	public static final String getPosixGroupListByMemberUid_ARGS = "memberUid";

	public List<PosixGroup> getPosixGroupListByMemberUid(String memberUid);

	public static final String getPosixGroupListByMemberUid2_ARGS = "memberUid";

	public List<PosixGroup> getPosixGroupListByMemberUid2(List<String> memberUid);

	public int insert(PosixGroup group);

	public int update(PosixGroup group);

	public int delete(PosixGroup group);

	public List<PosixGroup> getAllGroup();
}
