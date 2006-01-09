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

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.NamingEnumerationHandler;
import org.seasar.framework.exception.NamingRuntimeException;
import org.seasar.framework.log.Logger;

/**
 * 読み出し処理クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class BasicSelectHandler extends BasicHandler implements ExecuteHandler {
	/** ロガーを表わします。 */
	private static Logger logger = Logger.getLogger(BasicSelectHandler.class);
	/** 検索結果のハンドラクラスを表わします。 */
	private NamingEnumerationHandler handler;
	/** 検索フィルタを表します。 */
	private String filter;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param directoryDataSource
	 * @param filter
	 * @param handler
	 */
	public BasicSelectHandler(DirectoryDataSource directoryDataSource,
			String filter, NamingEnumerationHandler handler) {
		super(directoryDataSource);
		this.filter = filter;
		this.handler = handler;
	}

	/**
	 * 処理を実行します。
	 * 
	 * @return
	 * @throws NamingRuntimeException
	 * @see org.seasar.directory.impl.ExecuteHandler#execute()
	 */
	public Object execute() throws NamingRuntimeException {
		if (logger.isDebugEnabled()) {
			logger.debug("Filter: " + filter);
		}
		try {
			NamingEnumeration results = search(filter);
			return handler.handle(results, getBaseDn());
		} catch (NamingException e) {
			throw new NamingRuntimeException(e);
		}
	}
}
