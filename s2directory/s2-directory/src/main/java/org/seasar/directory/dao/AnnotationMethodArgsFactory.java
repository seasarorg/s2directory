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
package org.seasar.directory.dao;

import java.lang.reflect.Method;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.dao.impl.ArgsAnnotationMethodArgsImpl;

/**
 * アノテーションを保持したメソッドのファクトリクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class AnnotationMethodArgsFactory {
	/**
	 * アノテーションを保持したメソッドのインスタンスを作成します。
	 * 
	 * @return DirectoryDaoContainerのインスタンス
	 */
	public static AnnotationMethodArgs create(Method method,
			DirectoryDaoAnnotationReader annotationReader) {
		// ARGアノテーションを取得
		String[] argNames = annotationReader.getArgNames(method);
		// 引数の型を取得
		Object types[] = method.getParameterTypes();
		if (types.length > 0) {
			// 第一引数に接続情報がある場合
			if (types[0] == DirectoryControlProperty.class) {
				// Dtoを伴う場合
				if (argNames.length == 0 && types.length == 2) {
					argNames = new String[] { "#property", "#dto" };
				} else if (argNames.length == 0) {
					argNames = new String[] { "#property" };
				} else {
					argNames[0] = "#property";
				}
			} else if (argNames.length == 0 && types.length == 1) {
				// Dtoの場合
				argNames = new String[] { "#dto" };
			}
		}
		if (argNames.length > 0) {
			return new ArgsAnnotationMethodArgsImpl(
				argNames,
				method.getParameterTypes());
		} else {
			return null;
		}
	}
}
