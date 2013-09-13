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
package org.seasar.directory.types;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;

import org.seasar.directory.util.DirectoryDataSourceUtil;

/**
 * String型での値取得クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class StringType extends AbstractValueType {
	/**
	 * {@inheritDoc}
	 */
	public Object getReadValue(Attributes attributes, String attributeName,
			String multipleValueDelimiter) throws NamingException {
		Attribute attribute = attributes.get(attributeName);
		return getReadValue(attribute, multipleValueDelimiter);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getReadValue(Attribute attribute,
			String multipleValueDelimiter) throws NamingException {
		if (attribute != null && attribute.size() > 0) {
			StringBuffer buffer = new StringBuffer();
			NamingEnumeration array = attribute.getAll();
			while (array.hasMore()) {
				Object value = array.next();
				if (value instanceof String) {
					// テキスト型
					buffer.append(value).append(multipleValueDelimiter);
				} else {
					// バイナリ型
					buffer.append(new String((byte[])value)).append(
						multipleValueDelimiter);
				}
			}
			DirectoryDataSourceUtil.close(array);
			if (0 < multipleValueDelimiter.length()) {
				int index = buffer.lastIndexOf(multipleValueDelimiter);
				if (index != -1) {
					buffer.delete(index, buffer.length());
				}
			}
			return buffer.toString();
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Attribute getWriteValue(String attributeName, Object value,
			String multipleValueDelimiter) {
		Attribute attribute = new BasicAttribute(attributeName);
		attribute.add(value);
		return attribute;
	}
}
