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

import org.seasar.directory.attribute.AttributeHandler;

/**
 * 属性ハンドラファクトリインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface DirectoryAttributeHandlerFactory {
	/**
	 * ディレクトリ用の値の型ファクトリを取得します。
	 * 
	 * @return ディレクトリ用の値の型ファクトリ
	 */
	public DirectoryValueTypeFactory getDirectoryValueTypeFactory();

	/**
	 * ディレクトリ用の値の型ファクトリを設定します。
	 * 
	 * @param directoryValueTypeFactory
	 *            ディレクトリ用の値の型ファクトリ
	 */
	public void setDirectoryValueTypeFactory(
			DirectoryValueTypeFactory directoryValueTypeFactory);

	/**
	 * 属性ハンドラの集合を取得します。
	 * 
	 * @param attributeHandlers
	 *            属性ハンドラの集合
	 */
	public void setAttributeHandlers(Object[] attributeHandlers);

	/**
	 * 属性ハンドラを追加します。
	 * 
	 * @param attributeName
	 *            属性名
	 * @param attributeHandler
	 *            属性ハンドラ
	 */
	public void addAttributeHandler(String attributeName,
			AttributeHandler attributeHandler);

	/**
	 * 指定した属性名に対応する属性ハンドラを取得します。
	 * 
	 * @param attributeName
	 *            属性名
	 * @return 属性ハンドラ
	 */
	public AttributeHandler getAttributeHandler(String attributeName);
}
