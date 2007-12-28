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
package org.seasar.directory.examples.client.common;

import java.util.Iterator;
import java.util.List;
import org.seasar.directory.examples.entity.PosixGroup;
import org.seasar.directory.examples.entity.PosixGroupWithList;
import org.seasar.framework.container.S2Container;

/**
 * PosixGroup生成クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class PosixGroupFactory extends DirectoryDtoFactory {
	/**
	 * 指定されたコンテナを持ったインスタンスを作成します。
	 * 
	 * @param container
	 */
	public PosixGroupFactory(S2Container container) {
		super(container);
	}

	/**
	 * オブジェクトを作成します。
	 * 
	 * @param name
	 *            グループ名
	 * @param users
	 *            ユーザリスト
	 * @return グループインスタンス
	 */
	public PosixGroup getGroup(String name, List users) {
		PosixGroup group = new PosixGroup();
		group.setDn("cn=" + name + ",ou=Groups," + property.getBaseDn());
		group.setCn(name);
		group.setGidNumber("1001");
		StringBuffer buffer = new StringBuffer();
		for (Iterator iter = users.iterator(); iter.hasNext();) {
			buffer.append(String.valueOf(iter.next()));
			if (iter.hasNext()) {
				buffer.append(property.getMultipleValueDelimiter());
			}
		}
		group.setMemberUid(buffer.toString());
		group.setDescription("Group " + name);
		return group;
	}

	/**
	 * オブジェクトを作成します。
	 * 
	 * @param name
	 *            グループ名
	 * @param users
	 *            ユーザリスト
	 * @return グループインスタンス
	 */
	public PosixGroupWithList getGroupWithList(String name, List users) {
		PosixGroupWithList group = new PosixGroupWithList();
		group.setDn("cn=" + name + ",ou=Groups," + property.getBaseDn());
		group.setCn(name);
		group.setGidNumber("1001");
		group.setMemberUid(users);
		group.setDescription("Group " + name);
		return group;
	}
}
