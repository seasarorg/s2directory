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
import org.seasar.directory.DirectoryControlProperty;
import examples.directory.entity.PosixAccount;

/**
 * PosixAccountのDaoインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface PosixAccountDao {
	public Class BEAN = PosixAccount.class;

	public boolean authenticate();

	public String authenticateByUser_ARGS = "user";
	public boolean authenticateByUser(DirectoryControlProperty user);

	public static final String getUserByUid_ARGS = "uid";
	public PosixAccount getUserByUid(String uid);

	public static final String getUserByUidWithUser_ARGS = "user, uid";
	public PosixAccount getUserByUidWithUser(DirectoryControlProperty user, String uid);

	public static final String getUserByUidAndUidNumber_ARGS = "uid, uidNumber";
	public PosixAccount getUserByUidAndUidNumber(String uid, int uidNumber);

	public static final String getUserByFilter_QUERY = "uid=user2,ou=Users,dc=seasar,dc=org";
	public List getUserByFilter();

	public PosixAccount getUser(PosixAccount account);

	public int update(PosixAccount account);

	public int updateWithUser(DirectoryControlProperty user, PosixAccount account);

	public List getAllUser();

	public int delete(PosixAccount account);

	public int deleteWithUser(DirectoryControlProperty user, PosixAccount account);
}