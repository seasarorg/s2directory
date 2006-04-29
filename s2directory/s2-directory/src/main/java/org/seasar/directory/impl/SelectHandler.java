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
package org.seasar.directory.impl;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import org.seasar.directory.CommandContext;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.NamingEnumerationHandler;
import org.seasar.directory.util.DirectoryUtils;
import org.seasar.framework.exception.NamingRuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;

/**
 * 読み出し処理クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class SelectHandler extends BasicDirectoryHandler implements
		ExecuteHandler {
	/** ロガーを表わします。 */
	private static Logger logger = Logger.getLogger(SelectHandler.class);
	/** 検索結果のハンドラクラスを表わします。 */
	private NamingEnumerationHandler handler;
	/** 検索フィルタを表します。 */
	private String filter;
	/** 引数をコマンドとみなしたコンテキストを表します。 */
	private CommandContext cmd;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param directoryDataSource
	 * @param filter
	 * @param handler
	 */
	public SelectHandler(DirectoryDataSource directoryDataSource,
			String filter, NamingEnumerationHandler handler, CommandContext cmd) {
		super(directoryDataSource);
		this.filter = filter;
		this.handler = handler;
		this.cmd = cmd;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 読み出し処理を実行します。
	 * </p>
	 */
	public Object execute() throws NamingRuntimeException {
		try {
			if (StringUtil.isEmpty(cmd.getDn())) {
				// dn が無い場合、構築したフィルタで検索します。
				if (logger.isDebugEnabled()) {
					logger.debug("Filter: " + filter);
				}
				NamingEnumeration results = search(filter);
				return handler.handle(results, directoryControlProperty
						.getBaseDn());
			} else {
				// dn がある場合、dn で検索します。
				String dn = cmd.getDn();
				if (logger.isDebugEnabled()) {
					logger.debug("Filter: " + dn);
				}
				String firstDn = DirectoryUtils.getFirstDn(dn);
				String baseDn = DirectoryUtils.getBaseDn(dn);
				NamingEnumeration results = super.search(firstDn, baseDn);
				return handler.handle(results, baseDn);
			}
		} catch (NamingException e) {
			throw new NamingRuntimeException(e);
		}
	}
}
