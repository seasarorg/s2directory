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
package org.seasar.directory.generater;

import javax.naming.directory.Attributes;

/**
 * 属性の集合クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class AttributeSet {
	private DirectoryConstant type;
	private String name;
	private Attributes attributes;

	public AttributeSet(DirectoryConstant type, String name,
			Attributes attributes) {
		this.type = type;
		this.name = name;
		this.attributes = attributes;
	}

	/**
	 * @return Returns the attributes.
	 */
	public Attributes getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            The attributes to set.
	 */
	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the type.
	 */
	public DirectoryConstant getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(DirectoryConstant type) {
		this.type = type;
	}

	public String toString() {
		return "type: "
			+ getType()
			+ ", name: "
			+ getName()
			+ ", attributes: "
			+ attributes.toString();
	}
}
