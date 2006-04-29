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
package org.seasar.directory.types;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

/**
 * 値の型インタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface ValueType {
	/**
	 * 指定した属性の読み出し用の属性値を属性の集合から取得します。
	 * <p>
	 * 複数の属性値を扱います。属性のツリー構造は無視します。
	 * </p>
	 * 
	 * @param attributes 属性の集合
	 * @param attributeName 属性名
	 * @param multipleValueDelimiter 複数の属性値のための区切り文字
	 * @return 属性値
	 * @throws NamingException
	 */
	public Object getReadValue(Attributes attributes, String attributeName,
			String multipleValueDelimiter) throws NamingException;

	/**
	 * 指定した属性の読み出し用の属性値を取得します。
	 * <p>
	 * 複数の属性値を扱います。属性のツリー構造は無視します。
	 * </p>
	 * 
	 * @param attribute 属性
	 * @param multipleValueDelimiter 複数の属性値のための区切り文字
	 * @return 属性値
	 * @throws NamingException
	 */
	public Object getReadValue(Attribute attribute,
			String multipleValueDelimiter) throws NamingException;

	/**
	 * フィルタを取得します。
	 * 
	 * @param key 検索キー
	 * @param value 値
	 * @return フィルタ
	 */
	public String getFilter(Object key, Object value);

	/**
	 * 書き込み用の値を取得します。
	 * 
	 * @param attributeName 属性名
	 * @param value 値
	 * @param multipleValueDelimiter 複数値の区切り文字
	 * @return 書き込み用属性地
	 */
	public Attribute getWriteValue(String attributeName, Object value,
			String multipleValueDelimiter);
}
