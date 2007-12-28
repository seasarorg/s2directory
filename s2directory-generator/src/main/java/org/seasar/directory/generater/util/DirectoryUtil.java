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
package org.seasar.directory.generater.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.seasar.directory.generater.AttributeField;
import org.seasar.directory.generater.NullAttribute;
import org.seasar.directory.generater.SchemaMap;
import org.seasar.directory.generater.parser.ParseException;

/**
 * Directory Utility.
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryUtil {
	/** Javaの予約語 */
	public static final String[] javaKeyword =
		new String[] { "abstract", "assert", "boolean", "break", "byte",
			"case", "catch", "char", "class", "const", "continue", "default",
			"do", "double", "else", "enum", "extends", "false", "final",
			"finally", "float", "for", "goto", "if", "implements", "import",
			"instanceof", "int", "interface", "long", "native", "new", "null",
			"package", "private", "protected", "public", "return", "short",
			"static", "strictfp", "super", "switch", "synchrnized", "this",
			"throw", "throws", "transient", "true", "try", "void", "volatile",
			"while" };
	/** フィールドもしくは関数名に利用できないキャラクタ */
	public static final String[] javaNoIncludeChar = new String[] { "-" };

	/**
	 * Returns the character sequence which first character was changed into the
	 * capital letter.
	 * 
	 * @param str
	 *            the character sequence
	 * @return the character sequence
	 */
	public static String getFirstUpperString(String str) {
		StringBuffer sb = new StringBuffer();
		if (str.length() > 0)
			sb.append(Character.toUpperCase(str.charAt(0)));
		if (str.length() > 1)
			sb.append(str.substring(1));
		return sb.toString();
	}

	/**
	 * Returns the simple class name without package name.
	 * 
	 * @param className
	 *            the full class name
	 * @return the simple class name without package name
	 */
	public static String getSimpleClassName(String className) {
		int index = className.lastIndexOf('.');
		if (index > 0)
			return className.substring(index + 1);
		return className;
	}

	/**
	 * Returns the character sequence of set method.
	 * 
	 * @param methodName
	 *            method name
	 * @return the character sequence
	 */
	public static String getSetMethodName(String methodName) {
		return "set" + DirectoryUtil.getFirstUpperString(methodName);
	}

	/**
	 * Returns the character sequence of get method.
	 * 
	 * @param methodName
	 *            method name
	 * @return the character sequence
	 */
	public static String getGetMethodName(String methodName) {
		return "get" + DirectoryUtil.getFirstUpperString(methodName);
	}

	/**
	 * Returns the map of no multiple key value.
	 * 
	 * @param attrs
	 *            the objectClass
	 * @param key
	 *            the definition name
	 * @return SchemaMap
	 * @throws NamingException
	 */
	public synchronized static SchemaMap getNoMultipleDirectoryDefinitionMap(
			Attributes attrs, String key) throws NamingException {
		SchemaMap definition = new SchemaMap();
		Attribute objectClasses = getAttribute(attrs, key);
		if (objectClasses == NullAttribute.getInstance())
			return definition;
		NamingEnumeration enumeration = objectClasses.getAll();
		while (enumeration.hasMoreElements()) {
			String objectClassName = String.valueOf(enumeration.nextElement());
			Attributes objectClass = (Attributes)attrs.clone();
			objectClass.put(key, objectClassName);
			definition.put(objectClassName, objectClass);
		}
		return definition;
	}

	/**
	 * Returns the attribute of the specific key.
	 * 
	 * @param attrs
	 *            the objectClass
	 * @param key
	 *            the definition name
	 * @return the attribute
	 */
	public synchronized static Attribute getAttribute(Attributes attrs,
			String key) {
		Enumeration enumeration = attrs.getIDs();
		while (enumeration.hasMoreElements()) {
			String value = String.valueOf(enumeration.nextElement());
			if (value.equals(key))
				return attrs.get(key);
		}
		return NullAttribute.getInstance();
	}

	/**
	 * Returns the field names.
	 * 
	 * @param attr
	 *            a entry
	 * @return the field names
	 * @throws ParseException
	 */
	public synchronized static AttributeField[] createFieldNames(Attribute attr)
			throws ParseException {
		AttributeField[] instances = new AttributeField[attr.size()];
		try {
			String fieldName;
			String attributeName;
			for (int i = 0; i < instances.length; i++) {
				attributeName = String.valueOf(attr.get(i));
				fieldName = getFiledName(attributeName);
				instances[i] = new AttributeField(attributeName, fieldName);
			}
		} catch (NamingException e) {
			throw new ParseException("Attribute \"" + attr.getID()
				+ "\" cannot parse.", e);
		}
		return instances;
	}

	public static String getFiledName(String attributeName) {
		String fieldName = attributeName;
		for (int i = 0; i < javaKeyword.length; i++) {
			if (javaKeyword[i].equals(attributeName)) {
				fieldName = fieldName + "_";
				break;
			}
		}
		for (int i = 0; i < javaNoIncludeChar.length; i++) {
			if (fieldName.indexOf(javaNoIncludeChar[i]) != -1) {
				fieldName = fieldName.replaceAll(javaNoIncludeChar[i], "");
			}
		}
		return fieldName;
	}

	/**
	 * Returns the first value of the specific attribute.
	 * 
	 * @param attr
	 *            the attribute
	 * @return the first value
	 * @throws ParseException
	 */
	public synchronized static String getSingleValue(Attribute attr)
			throws ParseException {
		if (attr.size() != 1) {
			throw new ParseException("Attribute \"" + attr.getID()
				+ "\" is not single value. values: " + attr.toString());
		}
		try {
			return String.valueOf(attr.get(0));
		} catch (NamingException e) {
			throw new ParseException(e.getMessage(), e);
		}
	}

	/**
	 * Returns the valied object class name.
	 * 
	 * @param the
	 *            className
	 * @return the valied object class name
	 */
	public static String getObjectClassName(String className) {
		return className.replaceAll("-", "_");
	}

	/**
	 * Returns the unique string array.
	 * 
	 * @param target
	 *            the target string array
	 * @param master
	 *            the master string array
	 * @return the unique string array
	 */
	public static AttributeField[] getUniqueStringArray(
			AttributeField[] target, AttributeField[] master) {
		List targetList = new ArrayList();
		for (int i = 0; i < target.length; i++) {
			boolean hasAttribute = false;
			for (int j = 0; j < master.length; j++) {
				if (master[j].getAttributeName().equals(
					target[i].getAttributeName())) {
					hasAttribute = true;
					break;
				}
			}
			if (!hasAttribute) {
				targetList.add(target[i]);
			}
		}
		return (AttributeField[])targetList.toArray(new AttributeField[0]);
	}
}