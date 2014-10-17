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
package org.seasar.directory;

import org.seasar.directory.types.ValueType;

/**
 * ディレクトリ用の値の型ファクトリインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public interface DirectoryValueTypeFactory {

	/**
	 * コンポーネント名から値の型を取得します。
	 * 
	 * @param name
	 *            コンポーネント名
	 * @return 値の型
	 */
	public ValueType getValueTypeByName(String name);

	/**
	 * クラスインスタンスから値の型を取得します。
	 * 
	 * @param clazz
	 *            クラスインスタンス
	 * @return 値の型
	 */
	public ValueType getValueTypeByClass(Class clazz);

	/**
	 * Object型を取得します。
	 * 
	 * @return Object型
	 */
	public ValueType getObjectValueType();

	/**
	 * List型を取得します。
	 * 
	 * @return List型
	 */
	public ValueType getListValueType();

	/**
	 * String型を取得します。
	 * 
	 * @return String型
	 */
	public ValueType getStringValueType();

}
