/*
 * Copyright 2005-2013 the Seasar Foundation and the Others.
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
package org.seasar.directory.examples.dto;

import org.seasar.directory.examples.entity.PosixGroup;

/**
 * PosixグループDtoクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Revision$ $Date$
 */
public class PosixGroupDto extends PosixGroup {

	private static final long serialVersionUID = 1L;

	/** 非永続化属性 */
	public static final String NO_PERSISTENT_PROPS =
		"field1, field2, publicField1";

	/** field1 */
	private String field1;

	/** field2 */
	private String field2;

	public Object publicField1;

	/**
	 * field1を取得します。
	 * 
	 * @return field1
	 */
	public String getField1() {
		return field1;
	}

	/**
	 * field1を設定します。
	 * 
	 * @param field1
	 */
	public void setField1(String field1) {
		this.field1 = field1;
	}

	/**
	 * field2を取得します。
	 * 
	 * @return field2
	 */
	public String getField2() {
		return field2;
	}

	/**
	 * field2を設定します。
	 * 
	 * @param field2
	 */
	public void setField2(String field2) {
		this.field2 = field2;
	}
}
