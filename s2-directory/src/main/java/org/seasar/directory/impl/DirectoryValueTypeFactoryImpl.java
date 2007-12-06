/*
 * Copyright 2005-2006 the Seasar Foundation and the Others.
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

import org.seasar.directory.DirectoryValueTypeFactory;
import org.seasar.directory.types.ValueType;
import org.seasar.directory.types.ValueTypes;
import org.seasar.framework.container.S2Container;

/**
 * ディレクトリ用の値の型ファクトリクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryValueTypeFactoryImpl implements DirectoryValueTypeFactory {
	private S2Container container;

	/**
	 * Seasar2コンテナを設定します。
	 * 
	 * @param container
	 *            Seasar2コンテナ
	 */
	public void setContainer(S2Container container) {
		this.container = container;
	}

	/**
	 * {@inheritDoc}
	 */
	public ValueType getValueTypeByName(String name) {
		return (ValueType)container.getComponent(name);
	}

	/**
	 * {@inheritDoc}
	 */
	public ValueType getValueTypeByClass(Class clazz) {
		return ValueTypes.getValueType(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	public ValueType getObjectValueType() {
		return ValueTypes.OBJECT;
	}

	/**
	 * {@inheritDoc}
	 */
	public ValueType getListValueType() {
		return ValueTypes.LIST;
	}

	/**
	 * {@inheritDoc}
	 */
	public ValueType getStringValueType() {
		return ValueTypes.STRING;
	}
}
