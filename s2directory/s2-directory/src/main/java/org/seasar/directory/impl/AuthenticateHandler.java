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
package org.seasar.directory.impl;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.framework.exception.NamingRuntimeException;
import org.seasar.framework.log.Logger;

/**
 * 認証を処理するクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class AuthenticateHandler extends BasicDirectoryHandler implements
		ExecuteHandler {
	/** ロガー */
	private static Logger logger = Logger.getLogger(SelectHandler.class);

	/**
	 * インスタンスを生成します。
	 * 
	 * @param dataSource
	 */
	public AuthenticateHandler(DirectoryDataSource dataSource) {
		super(dataSource);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 認証処理を実行します。
	 * </p>
	 */
	public Object execute() throws NamingRuntimeException {
		if (logger.isDebugEnabled()) {
			DirectoryControlProperty property =
				super.getDirectoryDataSource().getDirectoryControlProperty();
			logger.debug("Authenticate: [user] " + property.getUser()
				+ " [password] " + property.getPassword());
		}
		return new Boolean(super.authenticate());
	}
}
