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

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;

import org.seasar.directory.util.DirectoryDataSourceUtil;

/**
 * List型での値取得クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class ListType extends AbstractValueType {

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
		List value = new ArrayList();
		if (attribute != null && attribute.size() > 0) {
			NamingEnumeration array = attribute.getAll();
			while (array.hasMore()) {
				value.add(array.next());
			}
			DirectoryDataSourceUtil.close(array);
			return value;
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String getFilter(Object key, Object value) {
		List list = (List)value;
		int size = list.size();
		StringBuffer buffer = new StringBuffer("(&");
		for (int i = 0; i < size; i++) {
			buffer.append("(");
			buffer.append(key).append("=").append(list.get(i));
			buffer.append(")");
		}
		buffer.append(")");
		return buffer.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public Attribute getWriteValue(String attributeName, Object value,
			String multipleValueDelimiter) {
		List list = (List)value;
		int size = list.size();
		Attribute attributes = new BasicAttribute(attributeName);
		for (int i = 0; i < size; i++) {
			attributes.add(list.get(i));
		}
		return attributes;
	}

}
