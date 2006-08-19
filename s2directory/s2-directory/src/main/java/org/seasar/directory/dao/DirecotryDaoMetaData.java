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

import org.seasar.framework.beans.MethodNotFoundRuntimeException;

/**
 * DirectoryDao用のメタ情報を表わすインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public interface DirecotryDaoMetaData {
	/**
	 * ビーンクラスを取得します。
	 * 
	 * @return
	 */
	public Class getBeanClass();

	/**
	 * ビーンメタデータを取得します。
	 * 
	 * @return
	 */
	public DirectoryBeanMetaData getDirectoryBeanMetaData();

	/**
	 * * キャッシュされているディレクトリコマンドを取得します。
	 * 
	 * @param methodName 関数名
	 * @return 存在する場合はディレクトリコマンド 、存在しない場合は例外を発生させます。
	 * @throws MethodNotFoundRuntimeException
	 */
	public DirectoryCommand getDirectoryCommand(String methodName)
			throws MethodNotFoundRuntimeException;

	/**
	 * ディレクトリコマンドを持っているか判別します。<br />
	 * ディレクトリコマンドを持っている場合 <code>true</code> 持っていない場合 <code>false</code>
	 * を返します。
	 * 
	 * @param methodName 関数名
	 * @return ディレクトリコマンドを持っている場合 <code>true</code> 持っていない場合
	 *         <code>false</code>
	 */
	public boolean hasDirectoryCommand(String methodName);
}
