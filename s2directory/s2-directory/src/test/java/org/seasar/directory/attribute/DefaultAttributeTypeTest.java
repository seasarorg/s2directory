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
package org.seasar.directory.attribute;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import junit.framework.TestCase;

import org.seasar.directory.DirectoryAttributeHandlerFactory;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

/**
 * 標準属性ハンドラのテストクラスです。
 * 
 * @author Jun Futagawa
 * @version $Revision$ $Date$
 */
public class DefaultAttributeTypeTest extends TestCase {
	private static final String PATH = "directorydao.dicon";
	private DirectoryAttributeHandlerFactory directoryAttributeTypeFactory;
	private DirectoryControlProperty property;

	/**
	 * テストの初期設定を行います。
	 */
	public void setUp() {
		S2Container container = S2ContainerFactory.create(PATH);
		directoryAttributeTypeFactory =
			(DirectoryAttributeHandlerFactory)container.getComponent(DirectoryAttributeHandlerFactory.class);
		property =
			(DirectoryControlProperty)container.getComponent(DirectoryControlProperty.class);
	}

	public void testGetAddAttribute() {
		String inputValue = new String("user1");
		AttributeHandler attributeType =
			directoryAttributeTypeFactory.getAttributeHandler("uid");
		// Single String value
		Attribute attribute =
			attributeType.getAddAttribute(
				property,
				"uid",
				inputValue,
				String.class);
		assertEquals(
			javax.naming.directory.BasicAttribute.class,
			attribute.getClass());
		assertEquals("uid", attribute.getID());
		try {
			assertEquals("user1", attribute.get());
		} catch (NamingException e) {
			assertFalse(true);
		}
		// Multiple String values
		inputValue =
			new String("user1" + property.getMultipleValueDelimiter() + "user2");
		attribute =
			attributeType.getAddAttribute(
				property,
				"uid",
				inputValue,
				String.class);
		assertEquals(
			javax.naming.directory.BasicAttribute.class,
			attribute.getClass());
		assertEquals("uid", attribute.getID());
		try {
			assertEquals("user1", attribute.get(0));
			assertEquals("user2", attribute.get(1));
		} catch (NamingException e) {
			assertFalse(true);
		}
		// Multiple List values
		List inputList = new ArrayList();
		inputList.add("user1");
		inputList.add("user2");
		attribute =
			attributeType.getAddAttribute(
				property,
				"uid",
				inputList,
				List.class);
		assertEquals(
			javax.naming.directory.BasicAttribute.class,
			attribute.getClass());
		assertEquals("uid", attribute.getID());
		try {
			assertEquals("user1", attribute.get(0));
			assertEquals("user2", attribute.get(1));
		} catch (NamingException e) {
			assertFalse(true);
		}
		// null value
		attribute =
			attributeType.getAddAttribute(property, "uid", null, String.class);
		assertNull(attribute);
	}

	public void testGetModificationItem() throws NamingException {
		BasicAttribute currentAttribute = new BasicAttribute("uid");
		currentAttribute.add("user1");
		String inputValue = new String("user1");
		AttributeHandler attributeType =
			directoryAttributeTypeFactory.getAttributeHandler("uid");
		// same
		ModificationItem item =
			attributeType.getModificationItem(
				property,
				currentAttribute,
				"uid",
				inputValue,
				String.class);
		assertNull(item);
		// update
		item =
			attributeType.getModificationItem(
				property,
				currentAttribute,
				"uid",
				"user2",
				String.class);
		assertEquals(DirContext.REPLACE_ATTRIBUTE, item.getModificationOp());
		assertEquals("user2", item.getAttribute().get());
		// delete 1
		item =
			attributeType.getModificationItem(
				property,
				currentAttribute,
				"uid",
				null,
				String.class);
		assertEquals(DirContext.REMOVE_ATTRIBUTE, item.getModificationOp());
		// delete 2
		item =
			attributeType.getModificationItem(
				property,
				currentAttribute,
				"uid",
				"",
				String.class);
		assertEquals(DirContext.REMOVE_ATTRIBUTE, item.getModificationOp());
		// add
		item =
			attributeType.getModificationItem(
				property,
				null,
				"uid",
				"user1",
				String.class);
		assertEquals(DirContext.ADD_ATTRIBUTE, item.getModificationOp());
		assertEquals("user1", item.getAttribute().get());
	}
}
