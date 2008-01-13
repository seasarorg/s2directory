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
package org.seasar.directory.examples.directorydao;

import java.util.List;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.examples.entity.PosixAccountWithAnnotation;

/**
 * PosixAccountのDaoインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date:: 2007-12-08 03:35:41 +0900#$
 */
public interface PosixAccountWithAnnocationDirectoryDao {
	public Class BEAN = PosixAccountWithAnnotation.class;

	public boolean authenticate();

	public String authenticateByUser_ARGS = "user";

	public boolean authenticateByUserMode(DirectoryControlProperty user);

	public static final String getUserByUid_ARGS = "uid";

	public PosixAccountWithAnnotation getUserByUid(String uid);

	public static final String getUserByUidWithUser_ARGS = "user, uid";

	public PosixAccountWithAnnotation getUserByUidWithUserMode(
			DirectoryControlProperty user, String uid);

	public static final String getUserByUidAndUidNumber_ARGS = "uid, uidNumber";

	public PosixAccountWithAnnotation getUserByUidAndUidNumber(String uid,
			int uidNumber);

	public static final String getUserByFilter_QUERY =
		"uid=user2,ou=Users,dc=seasar,dc=org";

	public List getUserByFilter();

	public PosixAccountWithAnnotation getUser(PosixAccountWithAnnotation account);

	public PosixAccountWithAnnotation getUserWithUserMode(
			DirectoryControlProperty user, PosixAccountWithAnnotation account);

	public int insert(PosixAccountWithAnnotation account);

	public int insertWithUserMode(DirectoryControlProperty user,
			PosixAccountWithAnnotation account);

	public int update(PosixAccountWithAnnotation account);

	public int updateWithUserMode(DirectoryControlProperty user,
			PosixAccountWithAnnotation account);

	public List getAllUser();

	public int delete(PosixAccountWithAnnotation account);

	public int deleteWithUserMode(DirectoryControlProperty user,
			PosixAccountWithAnnotation account);
}
