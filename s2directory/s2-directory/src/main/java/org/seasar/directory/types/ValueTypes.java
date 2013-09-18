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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 値タイプの集合クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class ValueTypes {

	/**
	 * String用の値タイプです。
	 */
	public static final ValueType STRING = new StringType();

	/**
	 * List用の値タイプです。
	 */
	public static final ValueType LIST = new ListType();

	/**
	 * Binary用の値タイプです。
	 */
	public static final ValueType BINARY = new BinaryType();

	/**
	 * 汎用的な値タイプです。
	 */
	public static final ValueType OBJECT = new ObjectType();

	/**
	 * バイト配列用の型です。
	 */
	private static final Class BYTE_ARRAY_CLASS = new byte[0].getClass();

	/** 値タイプの集合を現します。 */
	private static Map types = Collections.synchronizedMap(new HashMap());

	/**
	 * 初期化します。
	 */
	static {
		registerValueType(String.class, STRING);
		registerValueType(List.class, LIST);
		registerValueType(BYTE_ARRAY_CLASS, BINARY);
	}

	/**
	 * インスタンス化を禁止します。
	 */
	private ValueTypes() {
		super();
	}

	/**
	 * 値タイプを登録します。
	 * 
	 * @param clazz
	 *            型
	 * @param valueType
	 *            値タイプ
	 */
	public static void registerValueType(Class clazz, ValueType valueType) {
		types.put(clazz, valueType);
	}

	/**
	 * 指定された型に対応する値タイプを取得します。
	 * 
	 * @param clazz
	 *            型
	 * @return 値タイプ
	 */
	public static ValueType getValueType(Class clazz) {
		// スーパークラスで取得
		for (Class c = clazz; c != null; c = c.getSuperclass()) {
			ValueType valueType = getValueType0(c);
			if (valueType != null) {
				return valueType;
			}
		}
		// インタフェースで取得
		Class[] interfaces = clazz.getInterfaces();
		int length = interfaces.length;
		for (int i = 0; i < length; i++) {
			ValueType valueType = getValueType0(interfaces[i]);
			if (valueType != null) {
				return valueType;
			}
		}
		return OBJECT;
	}

	/**
	 * 指定された型に対応する値タイプを取得します。
	 * 
	 * @param clazz
	 *            型
	 * @return 値タイプ
	 */
	private static ValueType getValueType0(Class clazz) {
		return (ValueType)types.get(clazz);
	}

}
