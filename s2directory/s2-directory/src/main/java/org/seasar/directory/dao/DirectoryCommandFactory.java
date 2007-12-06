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
package org.seasar.directory.dao;

import java.lang.reflect.Method;

import org.seasar.directory.NamingEnumerationHandler;

/**
 * ディレクトリコマンドファクトリのインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface DirectoryCommandFactory {
	/**
	 * 処理結果を扱うハンドラを作成します。
	 * 
	 * @param beanMetaData
	 * @param method
	 * @param returnType
	 * @return
	 */
	public NamingEnumerationHandler createNamingEnumerationHandler(
			DirectoryBeanMetaData beanMetaData, Method method, Class returnType);

	/**
	 * TODO:
	 * 
	 * @param annotationReader
	 * @param beanMetaData
	 * @param method
	 * @return
	 */
	public abstract DirectoryCommand createDirectoryCommand(
			DirectoryDaoAnnotationReader annotationReader,
			DirectoryBeanMetaData beanMetaData, Method method);

}