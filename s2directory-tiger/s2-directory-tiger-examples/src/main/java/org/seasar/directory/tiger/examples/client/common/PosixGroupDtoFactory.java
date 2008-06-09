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
package org.seasar.directory.tiger.examples.client.common;

import java.util.Iterator;
import java.util.List;

import org.seasar.directory.tiger.examples.client.common.DirectoryDtoFactory;
import org.seasar.directory.tiger.examples.dto.PosixGroupDto;
import org.seasar.framework.container.S2Container;

/**
 * PosixGroup生成クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date:: 2007-12-28 16:29:46 +0900#$
 */
public class PosixGroupDtoFactory extends DirectoryDtoFactory {
	/**
	 * 指定されたコンテナを持ったインスタンスを作成します。
	 * 
	 * @param container
	 */
	public PosixGroupDtoFactory(S2Container container) {
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
	public PosixGroupDto getGroup(String name, List<PosixGroupDto> users) {
		PosixGroupDto group = new PosixGroupDto();
		group.setDn("cn=" + name + ",ou=Groups," + property.getBaseDn());
		group.setCn(name);
		group.setGidNumber("1001");
		StringBuffer buffer = new StringBuffer();
		for (Iterator<PosixGroupDto> iter = users.iterator(); iter.hasNext();) {
			buffer.append(String.valueOf(iter.next()));
			if (iter.hasNext()) {
				buffer.append(property.getMultipleValueDelimiter());
			}
		}
		group.setMemberUid(buffer.toString());
		group.setDescription("Group " + name);
		return group;
	}
}