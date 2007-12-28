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
package org.seasar.directory.generater;

/**
 * ディレクトリ用定数クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryConstant {
	/** 値を表します。 */
	private final String value;

	/**
	 * インスタンスを生成します。
	 */
	private DirectoryConstant(String value) {
		super();
		this.value = value;
	}

	/**
	 * 出力用文字列を取得します。
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return value;
	}
	/**
	 * 型定義名を表します。
	 */
	public static final DirectoryConstant OBJECTCLASS = new DirectoryConstant(
			"ClassDefinition");
	public static final DirectoryConstant ATTRIBUTE = new DirectoryConstant(
			"AttributeDefinition");
	public static final DirectoryConstant SYNTAX = new DirectoryConstant(
			"SyntaxDefinition");
	public static final DirectoryConstant MATCHINGRULE = new DirectoryConstant(
			"MatchingRule");
	public static final DirectoryConstant UnkownDefinition = new DirectoryConstant(
			"UnkownDefinition");

	/**
	 * 型のインスタンスを取得します。
	 * 
	 * @param type 型名
	 * @return 型のインスタンス
	 */
	public static DirectoryConstant getType(String type) {
		if (type.equals("ClassDefinition"))
			return DirectoryConstant.OBJECTCLASS;
		else if (type.equals("AttributeDefinition"))
			return DirectoryConstant.ATTRIBUTE;
		else if (type.equals("MatchingRule"))
			return DirectoryConstant.MATCHINGRULE;
		else if (type.equals("SyntaxDefinition"))
			return DirectoryConstant.SYNTAX;
		return UnkownDefinition;
	}
}
