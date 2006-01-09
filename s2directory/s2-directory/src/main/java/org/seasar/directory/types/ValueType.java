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
package org.seasar.directory.types;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

/**
 * 値取得インタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface ValueType {
	/**
	 * 指定した属性名の値を属性の集合から取得します。
	 * 
	 * @param attributes 属性の集合
	 * @param attributeName 属性名
	 * @return 属性値
	 * @throws NamingException
	 */
	public Object getValue(Attributes attributes, String attributeName)
			throws NamingException;

	/**
	 * 指定した属性の値を取得します。
	 * 
	 * @param attribute 属性
	 * @return 属性値
	 * @throws NamingException
	 */
	public Object getValue(Attribute attribute) throws NamingException;
}
