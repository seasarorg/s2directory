/*
 * Copyright 2005-2014 the Seasar Foundation and the Others.
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
package org.seasar.directory.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.seasar.directory.DirectoryAttributeHandlerFactory;
import org.seasar.directory.DirectoryValueTypeFactory;
import org.seasar.directory.attribute.AttributeHandler;

/**
 * 属性ハンドラファクトリクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class DirectoryAttributeHandlerFactoryImpl implements
		DirectoryAttributeHandlerFactory {

	/** ディレクトリ用の値の型ファクトリ */
	private DirectoryValueTypeFactory valueTypeFactory;

	/** 属性名の大文字と小文字を区別するかどうか */
	private boolean ignoreCase = true;

	/** 標準で使用する属性ハンドラ */
	private AttributeHandler defaultAttributeHandler;

	/** 属性ハンドラの集合 */
	private static Map handlers = Collections.synchronizedMap(new HashMap());

	/**
	 * {@inheritDoc}
	 */
	public void setDirectoryValueTypeFactory(
			DirectoryValueTypeFactory valueTypeFactory) {
		this.valueTypeFactory = valueTypeFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public DirectoryValueTypeFactory getDirectoryValueTypeFactory() {
		return valueTypeFactory;
	}

	/**
	 * 属性名の大文字と小文字を区別するかを設定します。
	 * 
	 * @param ignoreCase
	 *            属性名の大文字と小文字を区別する場合は true そうでない場合は false
	 */
	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	/**
	 * 属性名の大文字と小文字を区別するか判別します。
	 * 
	 * @return 属性名の大文字と小文字を区別する場合は true そうでない場合は false を返す。
	 */
	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	/**
	 * 標準で使用する属性ハンドラを取得します。
	 * 
	 * @return defaultAttributeHandler 標準で使用する属性ハンドラ
	 */
	public AttributeHandler getDefaultAttributeHandler() {
		return defaultAttributeHandler;
	}

	/**
	 * 標準で使用する属性ハンドラを設定します。
	 * 
	 * @param defaultAttributeHandler
	 *            標準で使用する属性ハンドラ
	 */
	public void setDefaultAttributeHandler(
			AttributeHandler defaultAttributeHandler) {
		this.defaultAttributeHandler = defaultAttributeHandler;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setAttributeHandlers(Object[] attributeHandlers) {
		int lenght = attributeHandlers.length;
		for (int i = 0; i < lenght; i++) {
			AttributeHandler handler = (AttributeHandler)attributeHandlers[i];
			addAttributeHandler(handler);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void addAttributeHandler(String attributeName,
			AttributeHandler attributeHandler) {
		if (isIgnoreCase()) {
			attributeName = attributeName.toLowerCase();
		}
		handlers.put(attributeName, attributeHandler);
	}

	/**
	 * 指定された属性ハンドラを追加します。
	 * 
	 * @param attributeHandler
	 *            属性ハンドラ
	 */
	protected void addAttributeHandler(AttributeHandler attributeHandler) {
		List attributeNameList = attributeHandler.getAttributeNameList();
		if (attributeNameList != null) {
			for (Iterator iter = attributeNameList.iterator(); iter.hasNext();) {
				String attributeName = (String)iter.next();
				addAttributeHandler(attributeName, attributeHandler);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public AttributeHandler getAttributeHandler(String attributeName) {
		if (isIgnoreCase()) {
			attributeName = attributeName.toLowerCase();
		}
		AttributeHandler handler;
		if (handlers.containsKey(attributeName)) {
			handler = (AttributeHandler)handlers.get(attributeName);
		} else {
			handler = getDefaultAttributeHandler();
		}
		// DIで設定済みのものを上書きする場合
		// handler.setDirectoryValueTypeFactory(getDirectoryValueTypeFactory());
		return handler;
	}

}
