/*
 * Copyright 2005-2007 the Seasar Foundation and the Others.
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
import org.seasar.directory.util.DirectoryDataSourceUtil;
import org.seasar.directory.util.DirectoryUtil;
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
	/** ロガー */
	private static Logger logger = Logger.getLogger(SelectHandler.class);
	/** 識別子 */
	private String dn;
	/** 検索フィルタ */
	private String filter;
	/** 検索結果のハンドラクラス */
	private NamingEnumerationHandler handler;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param dataSource
	 * @param dn
	 * @param filter
	 * @param handler
	 */
	public SelectHandler(DirectoryDataSource dataSource, String dn,
			String filter, NamingEnumerationHandler handler) {
		super(dataSource);
		this.dn = dn;
		this.filter = filter;
		this.handler = handler;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 読み出し処理を実行します。
	 * </p>
	 */
	public Object execute() throws NamingRuntimeException {
		NamingEnumeration results = null;
		try {
			if (StringUtil.isEmpty(dn)) {
				// dn が無い場合、構築したフィルタで検索します。
				if (logger.isDebugEnabled()) {
					logger.debug("Filter: " + filter);
				}
				results = super.search(filter);
				return handler.handle(results, property.getBaseDn());
			} else {
				// dn がある場合、dn で検索します。
				if (logger.isDebugEnabled()) {
					logger.debug("Filter: " + dn + " [DN]");
				}
				results = super.searchOneLevel(dn);
				String baseDn = DirectoryUtil.getBaseDn(dn);
				return handler.handle(results, baseDn);
			}
		} catch (NamingException e) {
			throw new NamingRuntimeException(e);
		} finally {
			DirectoryDataSourceUtil.close(results);
		}
	}
}
