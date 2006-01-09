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
package org.seasar.directory.impl;

import javax.naming.NamingException;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.dao.AnnotationMethodArgs;

/**
 * 動的に認証処理を実行するクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class AuthenticateDynamicCommand extends AbstractDynamicDirectoryCommand {
	/**
	 * インスタンスを作成します。
	 * 
	 * @param dataSource - データソース
	 */
	public AuthenticateDynamicCommand(DirectoryDataSource dataSource,
			AnnotationMethodArgs methodArgs) {
		super(dataSource, methodArgs);
	}

	/**
	 * 認証処理を実行します。
	 * 
	 * @param args - 引数
	 * @return
	 * @see org.seasar.directory.dao.DirectoryCommand#execute(java.lang.Object[])
	 */
	public Object execute(Object[] args) {
		// apply(args);
		DirectoryDataSource dataSource = getDirectoryDataSource(args);
		try {
			dataSource.getConnection();
			return new Boolean(true);
		} catch (NamingException e) {
			// throw new NamingRuntimeException(e);
			return new Boolean(false);
		}
	}
}
