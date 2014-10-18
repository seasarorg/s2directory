/*
 * Copyright 2005-2014 the Seasar Foundation and the Others.
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
import org.seasar.directory.dao.annotation.tiger.Arguments;
import org.seasar.directory.dao.annotation.tiger.Query;
import org.seasar.directory.dao.annotation.tiger.S2Directory;
import org.seasar.directory.tiger.examples.dto.PosixAccountWithAnnotationDto;

/**
 * PosixAccountのDaoインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
@S2Directory(bean = PosixAccountWithAnnotationDto.class)
public interface PosixAccountWithAnnocationDtoDirectoryDao {

	public boolean authenticate();

	@Arguments("user")
	public boolean authenticateByUserMode(DirectoryControlProperty user);

	@Arguments("uid")
	public PosixAccountWithAnnotationDto getUserByUid(String uid);

	@Arguments({ "user", "uid" })
	public PosixAccountWithAnnotationDto getUserByUidWithUserMode(
			DirectoryControlProperty user, String uid);

	@Arguments({ "user", "uidNumber" })
	public PosixAccountWithAnnotationDto getUserByUidAndUidNumber(String uid,
			int uidNumber);

	@Query("uid=user2,ou=Users,dc=seasar,dc=org")
	public List<PosixAccountWithAnnotationDto> getUserByFilter();

	public PosixAccountWithAnnotationDto getUser(
			PosixAccountWithAnnotationDto account);

	public PosixAccountWithAnnotationDto getUserWithUserMode(
			DirectoryControlProperty user, PosixAccountWithAnnotationDto account);

	public int insert(PosixAccountWithAnnotationDto account);

	public int insertWithUserMode(DirectoryControlProperty user,
			PosixAccountWithAnnotationDto account);

	public int update(PosixAccountWithAnnotationDto account);

	public int updateWithUserMode(DirectoryControlProperty user,
			PosixAccountWithAnnotationDto account);

	public List<PosixAccountWithAnnotationDto> getAllUser();

	public int delete(PosixAccountWithAnnotationDto account);

	public int deleteWithUserMode(DirectoryControlProperty user,
			PosixAccountWithAnnotationDto account);

}
