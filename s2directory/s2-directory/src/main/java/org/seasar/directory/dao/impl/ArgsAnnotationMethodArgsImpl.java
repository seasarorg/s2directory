/*
 * Copyright 2005-2008 the Seasar Foundation and the Others.
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
package org.seasar.directory.dao.impl;

import org.seasar.directory.dao.AnnotationMethodArgs;

/**
 * ARGSによるアノテーション情報を保持したメソッドを表すクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class ArgsAnnotationMethodArgsImpl implements AnnotationMethodArgs {
	/** 引数 */
	private String[] argNames = new String[0];
	/** 引数の型 */
	private Class[] argTypes = new Class[0];

	/**
	 * インスタンスを作成します。
	 */
	public ArgsAnnotationMethodArgsImpl() {
		super();
	}

	/**
	 * 指定した引数の値を持ったインスタンスを作成します。
	 * 
	 * @param argNames
	 * @param argTypes
	 */
	public ArgsAnnotationMethodArgsImpl(String[] argNames, Class[] argTypes) {
		this.argNames = argNames;
		this.argTypes = argTypes;
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getArgNames() {
		return argNames;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class[] getArgTypes() {
		return argTypes;
	}
}
