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

import java.util.Iterator;
import java.util.List;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;
import examples.directory.entity.PosixGroup;

/**
 * PosixGroupDaoのサンプルクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class PosixGroupDaoSample {
	private static final String PATH = "examples/directory/dao/PosixGroupDao.dicon";
	private static S2Container container;

	public static void main(String[] args) {
		// 初期化します。
		container = S2ContainerFactory.create(PATH);
		container.init();
		getPosixGroupNull();
		getPosixGroup1();
		getPosixGroup2();
		getPosixGroupList();
	}

	public static void getPosixGroupNull() {
		// Daoクラスを取得します。
		PosixGroupDao dao = (PosixGroupDao)container
				.getComponent(PosixGroupDao.class);
		List group = dao.getPosixGroupListByGroup(null);
		// 表示します。
		System.out.println("DEBUG: " + group);
	}

	public static void getPosixGroup1() {
		// Daoクラスを取得します。
		PosixGroupDao dao = (PosixGroupDao)container
				.getComponent(PosixGroupDao.class);
		// グループs2directoryを取得します。
		PosixGroup search = new PosixGroup();
		search.setCn("s2directory");
		search.setGidNumber("1008");
		List group = dao.getPosixGroupListByGroup(search);
		// 表示します。
		System.out.println("DEBUG: " + group);
	}

	public static void getPosixGroup2() {
		// Daoクラスを取得します。
		PosixGroupDao dao = (PosixGroupDao)container
				.getComponent(PosixGroupDao.class);
		// グループs2directoryを取得します。
		PosixGroup search = new PosixGroup();
		search.setCn("s2directory");
		PosixGroup group = dao.getPosixGroupByGroup(search);
		// 表示します。
		System.out.println("DEBUG: " + group);
	}

	public static void getPosixGroupList() {
		// Daoクラスを取得します。
		PosixGroupDao dao = (PosixGroupDao)container
				.getComponent(PosixGroupDao.class);
		// グループ一覧を取得します。
		List group = dao.getPosixGroupList();
		// 表示します。
		for (Iterator iter = group.iterator(); iter.hasNext();) {
			System.out.println("DEBUG: " + iter.next());
		}
	}

	public static void getPosixGroupByCn() {
		// Daoクラスを取得します。
		PosixGroupDao dao = (PosixGroupDao)container
				.getComponent(PosixGroupDao.class);
		// グループs2directoryを取得します。
		PosixGroup group = dao.getPosixGroupByCn("s2directory");
		// 表示します。
		System.out.println("DEBUG: " + group);
		List array = group.getMemberUid();
		for (Iterator iter = array.iterator(); iter.hasNext();) {
			iter.next();
			// System.out.println("memberUid: " + iter.next());
		}
		List groups = dao.getPosixGroupListByMemberUid("gwatsman");
		System.out.println("size: " + groups.size());
		for (Iterator iter = groups.iterator(); iter.hasNext();) {
			System.out.println("val: " + iter.next());
		}
		// dao.update(account);
		// dao.deleteUser(account);
	}
}