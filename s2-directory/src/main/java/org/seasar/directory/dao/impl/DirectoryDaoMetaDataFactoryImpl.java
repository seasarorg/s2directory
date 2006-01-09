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
package org.seasar.directory.dao.impl;

import java.util.HashMap;
import java.util.Map;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.dao.DirecotryDaoMetaData;
import org.seasar.directory.dao.DirectoryDaoMetaDataFactory;
import org.seasar.directory.impl.DirectoryDataSourceImpl;

/**
 * DirectoryDaoMetaDataを生成します。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryDaoMetaDataFactoryImpl implements
		DirectoryDaoMetaDataFactory {
	/** メタデータのキャッシュを表わします。 */
	private Map DirectoryDaoMetaDataCache = new HashMap();
	/** Directory接続ファクトリを表わします。 */
	private DirectoryDataSource DirectoryDataSource;

	/**
	 * @param dataSource
	 * @param resultSetFactory
	 */
	public DirectoryDaoMetaDataFactoryImpl(
			DirectoryControlProperty DirectoryControlProperty) {
		this.DirectoryDataSource = new DirectoryDataSourceImpl(
				DirectoryControlProperty);
	}

	/**
	 * 指定されたDAOクラスから生成したDirectoryDaoMetaDataのインスタンスを返します。
	 * 
	 * @param daoClass 生成元となるDAOクラス
	 * @return 生成したDirectoryDaoMetaDataのインスタンス
	 * @see org.seasar.directory.dao.DirectoryDaoMetaDataFactory#getDirectoryDaoMetaData(Class)
	 */
	public DirecotryDaoMetaData getDirectoryDaoMetaData(Class daoClass) {
		String key = daoClass.getName();
		DirecotryDaoMetaData dmd = (DirecotryDaoMetaData)DirectoryDaoMetaDataCache
				.get(key);
		if (dmd != null) {
			return dmd;
		}
		synchronized (this) {
			dmd = (DirecotryDaoMetaData)DirectoryDaoMetaDataCache.get(key);
			if (dmd != null) {
				return dmd;
			}
			dmd = new DirectoryDaoMetaDataImpl(daoClass, DirectoryDataSource);
			DirectoryDaoMetaDataCache.put(key, dmd);
		}
		return dmd;
	}
}
