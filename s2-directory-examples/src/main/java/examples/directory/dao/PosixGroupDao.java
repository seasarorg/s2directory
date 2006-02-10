/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
package examples.directory.dao;

import java.util.List;
import examples.directory.entity.PosixGroup;

/**
 * PosixAccountのDaoインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface PosixGroupDao {
	public Class BEAN = PosixGroup.class;
	public static final String getPosixGroupList_QUERY = "objectclass=posixGroup";

	public List getPosixGroupList();

	public PosixGroup getPosixGroupByGroup(PosixGroup group);

	public List getPosixGroupListByGroup(PosixGroup group);
	public static final String getPosixGroupByCn_ARGS = "cn";

	public PosixGroup getPosixGroupByCn(String cn);
	public static final String getPosixGroupListByMemberUid_ARGS = "memberUid";

	public List getPosixGroupListByMemberUid(String memberUid);

	public static final String getPosixGroupListByMemberUid2_ARGS = "memberUid";
	public List getPosixGroupListByMemberUid2(List memberUid);

	public void update(PosixGroup account);

	public List getAllGroup();
}