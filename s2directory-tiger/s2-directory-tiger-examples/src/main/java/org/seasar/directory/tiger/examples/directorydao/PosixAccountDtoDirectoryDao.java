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

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.dao.annotation.tiger.S2Directory;
import org.seasar.directory.tiger.examples.dto.PosixAccountDto;

/**
 * PosixAccountのDaoインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date:: 2007-12-08 03:35:41 +0900#$
 */
@S2Directory(bean = PosixAccountDto.class)
public interface PosixAccountDtoDirectoryDao {
	public String OBJECTCLASSES = "posixAccount, inetOrgPerson";

	public boolean authenticate();

	public String authenticateByUser_ARGS = "user";

	public boolean authenticateByUserMode(DirectoryControlProperty user);

	public static final String getUserByUid_ARGS = "uid";

	public PosixAccountDto getUserByUid(String uid);

	public static final String getUserByUidWithUser_ARGS = "user, uid";

	public PosixAccountDto getUserByUidWithUserMode(
			DirectoryControlProperty user, String uid);

	public static final String getUserByUidAndUidNumber_ARGS = "uid, uidNumber";

	public PosixAccountDto getUserByUidAndUidNumber(String uid, int uidNumber);

	public static final String getUserByFilter_QUERY =
		"uid=user2,ou=Users,dc=seasar,dc=org";

	public List<PosixAccountDto> getUserByFilter();

	public PosixAccountDto getUser(PosixAccountDto account);

	public PosixAccountDto getUserWithUserMode(DirectoryControlProperty user,
			PosixAccountDto account);

	public int insert(PosixAccountDto account);

	public int insertWithUserMode(DirectoryControlProperty user,
			PosixAccountDto account);

	public int update(PosixAccountDto account);

	public int updateWithUserMode(DirectoryControlProperty user,
			PosixAccountDto account);

	public List<PosixAccountDto> getAllUser();

	public int delete(PosixAccountDto account);

	public int deleteWithUserMode(DirectoryControlProperty user,
			PosixAccountDto account);
}
