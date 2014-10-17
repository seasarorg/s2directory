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
package org.seasar.directory.exception;

import org.seasar.framework.exception.SRuntimeException;

/**
 * 指定されたクラスが存在しないことを通知するシグナルを発生させます。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class DirectoryDaoNotFoundRuntimeException extends SRuntimeException {

	private static final long serialVersionUID = 1L;

	/** ターゲットクラス */
	private Class targetClass;

	/**
	 * 指定されたクラスについての実行時例外を生成します。
	 * 
	 * @param targetClass
	 */
	public DirectoryDaoNotFoundRuntimeException(Class targetClass) {
		super("EDIR0008", new Object[] { targetClass.getName() });
		this.targetClass = targetClass;
	}

	/**
	 * 実行時例外が発生したクラスのインスタンスを返します。
	 * 
	 * @return targetClass
	 */
	public Class getTargetClass() {
		return targetClass;
	}

}
