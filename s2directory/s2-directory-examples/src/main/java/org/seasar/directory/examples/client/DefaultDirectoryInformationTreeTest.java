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
package org.seasar.directory.examples.client;

import junit.framework.TestCase;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.examples.directorydao.OrganizationalUnitDirectoryDao;
import org.seasar.directory.examples.directorydao.PersonDirectoryDao;
import org.seasar.directory.examples.entity.OrganizationalUnit;
import org.seasar.directory.examples.entity.Person;
import org.seasar.directory.exception.AuthenticationRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * 基本となるディレクトリ情報ツリーを作成する抽象テストクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public abstract class DefaultDirectoryInformationTreeTest extends TestCase {
	private static final String PATH = "app.dicon";
	private static boolean isApacheDS = false;
	private static S2Container container;
	private static PersonDirectoryDao personDao;
	private static OrganizationalUnitDirectoryDao organizationalUnitDao;

	public static void main(String[] args) {
		junit.textui.TestRunner.run(DefaultDirectoryInformationTreeTest.class);
	}

	protected void setUp() throws Exception {
		if (container == null) {
			container = S2ContainerFactory.create(PATH);
			container.init();
			personDao =
				(PersonDirectoryDao)container
					.getComponent(PersonDirectoryDao.class);
			organizationalUnitDao =
				(OrganizationalUnitDirectoryDao)container
					.getComponent(OrganizationalUnitDirectoryDao.class);
		}
		setupAdministratorEntry();
		setupDefaultDirectoryInformationTree();
	}

	protected void tearDown() throws Exception {
		tearDownDefaultDirectoryInformationTree();
		tearDownAdministratorEntry();
	}

	private void setupAdministratorEntry() {
		// check exist an administrator user entry
		Person person = new Person();
		person.setCn("Manager");
		try {
			person = personDao.getPerson(person);
		} catch (AuthenticationRuntimeException e) {
			// set super user for ApacheDS
			isApacheDS = true;
			DirectoryControlProperty property = getSuperUserPropertyOfApachDS();
			person = personDao.getPersonWithUserMode(property, person);
		}
		if (person != null) {
			System.err
				.println("### Please clean directory information tree!! ###");
		}
		assertNull(person);
	}

	private void tearDownAdministratorEntry() {
		if (isApacheDS) {
			Person person = new Person();
			person.setCn("Manager");
			person = personDao.getPerson(person);
			assertEquals(1, personDao.deletePerson(person));
		}
	}

	private void setupDefaultDirectoryInformationTree() {
		if (isApacheDS) {
			// add an administrator user entry for dc=seasar,dc=org
			// cn=Manager,dc=seasar,dc=org
			Person person = new Person();
			person.setDn("cn=Manager,dc=seasar,dc=org");
			person.setUserPassword("secret");
			person.setSn("administrator");
			person.setCn("system administrator");
			try {
				personDao.insertPerson(person);
			} catch (AuthenticationRuntimeException e) {
				// set super user for ApacheDS
				DirectoryControlProperty property =
					getSuperUserPropertyOfApachDS();
				personDao.insertPersonWithUserMode(property, person);
			}
		}
		DirectoryControlProperty property =
			(DirectoryControlProperty)container
				.getComponent(DirectoryControlProperty.class);
		// check ou=Users
		OrganizationalUnit usersUnit = new OrganizationalUnit();
		usersUnit.setDn("ou=Users," + property.getBaseDn());
		usersUnit.setOu("Users");
		assertNull(organizationalUnitDao.getOrganizationalUnit(usersUnit));
		// add ou=Users
		organizationalUnitDao.insertOrganizationalUnit(usersUnit);
		usersUnit = organizationalUnitDao.getOrganizationalUnit(usersUnit);
		// check ou=Groups
		OrganizationalUnit groupsUnit = new OrganizationalUnit();
		groupsUnit.setDn("ou=Groups," + property.getBaseDn());
		groupsUnit.setOu("Groups");
		assertNull(organizationalUnitDao.getOrganizationalUnit(groupsUnit));
		// add ou=Groups
		organizationalUnitDao.insertOrganizationalUnit(groupsUnit);
	}

	private void tearDownDefaultDirectoryInformationTree() {
		// search ou=Users
		OrganizationalUnit usersUnit = new OrganizationalUnit();
		usersUnit.setOu("Users");
		usersUnit = organizationalUnitDao.getOrganizationalUnit(usersUnit);
		// delete ou=Users
		organizationalUnitDao.deleteOrganizationalUnit(usersUnit);
		// search ou=Groups
		OrganizationalUnit groupsUnit = new OrganizationalUnit();
		groupsUnit.setOu("Groups");
		groupsUnit = organizationalUnitDao.getOrganizationalUnit(groupsUnit);
		// delete ou=Groups
		organizationalUnitDao.deleteOrganizationalUnit(groupsUnit);
	}

	private DirectoryControlProperty getSuperUserPropertyOfApachDS() {
		DirectoryControlProperty property =
			(DirectoryControlProperty)container
				.getComponent(DirectoryControlProperty.class);
		property.setUser("uid=admin");
		property.setPassword("secret");
		property.setUserSuffix("");
		property.setBaseDn("ou=system");
		return property;
	}
}
