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

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.exception.AuthenticationRuntimeException;
import org.seasar.directory.exception.CommunicationRuntimeException;
import org.seasar.directory.exception.NameAlreadyBoundRuntimeException;
import org.seasar.directory.exception.DirectoryRuntimeException;
import org.seasar.directory.exception.NoSuchEntryRuntimeException;
import org.seasar.directory.util.DirectoryDataSourceUtil;
import org.seasar.directory.util.DirectoryUtil;
import org.seasar.framework.exception.EmptyRuntimeException;

/**
 * ディレクトリサーバと接続し、処理するクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class BasicDirectoryHandler {
	/** データソース */
	private DirectoryDataSource dataSource;
	/** ディレクトリサーバ接続情報 */
	protected DirectoryControlProperty property;

	/**
	 * 指定したデータソースを持ったインスタンスを作成します。
	 * 
	 * @param dataSource
	 *            データース
	 */
	public BasicDirectoryHandler(DirectoryDataSource dataSource) {
		this.dataSource = dataSource;
		this.property = dataSource.getDirectoryControlProperty();
	}

	/**
	 * データースを取得します。
	 * 
	 * @return dataSource データース
	 */
	public DirectoryDataSource getDirectoryDataSource() {
		return dataSource;
	}

	/**
	 * データースを設定します。
	 * 
	 * @param dataSource
	 *            データース
	 */
	public void setDirectoryDataSource(DirectoryDataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * ディレクトリコネクションを取得します。
	 * 
	 * @return ディレクトリコネクション
	 */
	protected DirContext getConnection() {
		if (dataSource == null) {
			throw new EmptyRuntimeException("directoryDataSource");
		}
		try {
			return dataSource.getConnection();
		} catch (AuthenticationException ae) {
			throw new AuthenticationRuntimeException(dataSource
				.getDirectoryControlProperty());
		} catch (CommunicationException ce) {
			throw new CommunicationRuntimeException(dataSource
				.getDirectoryControlProperty());
		} catch (NamingException ex) {
			throw new DirectoryRuntimeException(ex);
		}
	}

	/**
	 * 認証を実行します。
	 * 
	 * @return 認証結果を返します。
	 */
	public boolean authenticate() {
		if (dataSource == null) {
			throw new EmptyRuntimeException("directoryDataSource");
		}
		DirContext context = null;
		try {
			context = dataSource.getConnection();
			if (context != null) {
				return true;
			} else {
				return false;
			}
		} catch (NamingException e) {
			return false;
		} finally {
			DirectoryDataSourceUtil.close(context);
		}
	}

	/**
	 * 検索を実行します。
	 * 
	 * @param filter
	 * @return 検索結果
	 */
	public NamingEnumeration search(String filter) {
		return search(filter, property.getBaseDn());
	}

	/**
	 * 検索を実行します。
	 * 
	 * @param baseDn
	 * @param filter
	 * @return 検索結果
	 */
	public NamingEnumeration search(String baseDn, String filter) {
		SearchControls controls = new SearchControls();
		controls.setSearchScope(property.getSearchControls());
		return search(filter, baseDn, controls);
	}

	/**
	 * 検索を実行します。
	 * 
	 * @param baseDn
	 * @param filter
	 * @param controls
	 * @return 検索結果
	 */
	public NamingEnumeration search(String baseDn, String filter,
			SearchControls controls) {
		DirContext context = null;
		try {
			context = getConnection();
			return context.search(baseDn, filter, controls);
		} catch (NamingException e) {
			throw new DirectoryRuntimeException(e);
		} finally {
			DirectoryDataSourceUtil.close(context);
		}
	}

	/**
	 * 指定したDNのエントリを検索します。
	 * 
	 * @param dn
	 *            検索するDN
	 * @return 検索結果
	 */
	public NamingEnumeration searchOneLevel(String dn) {
		DirContext context = null;
		try {
			String firstDn = DirectoryUtil.getFirstDn(dn);
			String baseDn = DirectoryUtil.getBaseDn(dn);
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
			return search(baseDn, firstDn, controls);
		} finally {
			DirectoryDataSourceUtil.close(context);
		}
	}

	/**
	 * 作成を実行します。
	 * 
	 * @param dn
	 *            作成するエントリのDN
	 * @param attrs
	 *            作成するエントリの属性
	 * @return 作成した数を返します。
	 */
	public Integer insert(String dn, Attributes attrs) {
		DirContext context = null;
		DirContext createdContext = null;
		try {
			context = getConnection();
			createdContext = context.createSubcontext(dn, attrs);
			return new Integer(1);
		} catch (NameAlreadyBoundException e) {
			throw new NameAlreadyBoundRuntimeException(e);
		} catch (NamingException e) {
			throw new DirectoryRuntimeException(e);
		} finally {
			DirectoryDataSourceUtil.close(createdContext);
			DirectoryDataSourceUtil.close(context);
		}
	}

	/**
	 * 更新を実行します。
	 * 
	 * @param dn
	 *            更新対象のDN
	 * @param items
	 *            更新対象の属性
	 * @return 更新した数
	 */
	public Integer update(String dn, ModificationItem[] items) {
		if (!isExistEntry(dn)) {
			throw new NoSuchEntryRuntimeException(dn);
		} else {
			if (items.length == 0) {
				return new Integer(0);
			}
			DirContext context = null;
			try {
				context = getConnection();
				context.modifyAttributes(dn, items);
				return new Integer(1);
			} catch (NamingException e) {
				throw new DirectoryRuntimeException(e);
			} finally {
				DirectoryDataSourceUtil.close(context);
			}
		}
	}

	/**
	 * 削除を実行します。
	 * 
	 * @param dn
	 *            削除対象のDN
	 * @return 削除した数を返します。
	 */
	public Integer delete(String dn) {
		DirContext context = null;
		try {
			if (!isExistEntry(dn)) {
				throw new NoSuchEntryRuntimeException(dn);
			} else {
				context = getConnection();
				context.destroySubcontext(dn);
				return new Integer(1);
			}
		} catch (NamingException e) {
			throw new DirectoryRuntimeException(e);
		} finally {
			DirectoryDataSourceUtil.close(context);
		}
	}

	/**
	 * 指定したDNにエントリが存在しているかどうか調べます。
	 * 
	 * @param dn
	 *            調査対象のDN
	 * @return エントリが存在しているかどうか
	 */
	public boolean isExistEntry(String dn) {
		DirContext context = null;
		NamingEnumeration results = null;
		try {
			results = searchOneLevel(dn);
			int count = count(results);
			if (count == 1) {
				return true;
			} else {
				return false;
			}
		} catch (NamingException e) {
			throw new DirectoryRuntimeException(e);
		} finally {
			DirectoryDataSourceUtil.close(context);
			DirectoryDataSourceUtil.close(results);
		}
	}

	/**
	 * 検索結果の数を返します。
	 * 
	 * @param results
	 *            検索結果
	 * @return 検索結果の数
	 * @throws NamingException
	 */
	public int count(NamingEnumeration results) throws NamingException {
		int count = 0;
		while (results.hasMore()) {
			results.next();
			count++;
		}
		return count;
	}
}
