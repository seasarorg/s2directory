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
package org.seasar.directory;

import java.util.Set;

/**
 * 引数をコマンドとみなしたコンテキストのインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface CommandContext {
	/**
	 * 指定した引数名の値を取得します。
	 * 
	 * @param name 引数名
	 * @return 引数の値
	 */
	public Object getArg(String name);

	/**
	 * すべての引数名を取得します。
	 * 
	 * @return すべての引数名
	 */
	public Set getArgKeySet();

	/**
	 * 指定した引数名の型を取得します。
	 * 
	 * @param name 引数名
	 * @return 引数の型
	 */
	public Class getArgType(String name);

	/**
	 * Dtoの引数コンテキストを追加します。
	 * 
	 * @param dtoArg Dtoの引数
	 */
	public void addDtoArg(Object dtoArg);

	/**
	 * 引数コンテキストを追加します。
	 * 
	 * @param name 引数名
	 * @param arg 引数の値
	 * @param argType 引数の型
	 */
	public void addArg(String name, Object arg, Class argType);

	/**
	 * フィルターを取得します。
	 * 
	 * @return フィルター
	 */
	public String getFilter();

	/**
	 * 識別名を取得します。
	 * 
	 * @return 識別名
	 */
	public String getDn();

	/**
	 * オブジェクトクラスを取得します。
	 * 
	 * @return objectClass
	 */
	public String[] getObjectClasses();

	/**
	 * オブジェクトクラスを設定します。
	 * 
	 * @param objectClasses
	 */
	public void setObjectClasses(String[] objectClasses);
}
