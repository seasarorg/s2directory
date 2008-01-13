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
package org.seasar.directory.generater;

import javax.naming.directory.SearchResult;

/**
 * 属性の集合の生成器です。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class AttributeSetFactory {
	private AttributeSetFactory() {}

	/**
	 * DirectoryAttributeSetに積みます。
	 * 
	 * @param result
	 * @return
	 */
	public static AttributeSet create(SearchResult result) {
		// System.out.println(result);
		String type, name;
		String fullName = result.getName();
		// System.out.println(fullName);
		if (fullName.lastIndexOf("/") == -1) {
			type = fullName;
			name = fullName;
		} else {
			type = fullName.split("/")[0];
			name = fullName.split("/")[1];
		}
		return new AttributeSet(DirectoryConstant.getType(type), name, result
				.getAttributes());
	}
}
