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
package org.seasar.directory.dao.impl;

import java.util.HashMap;
import java.util.Map;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.dao.DirecotryDaoMetaData;
import org.seasar.directory.dao.DirectoryAnnotationReaderFactory;
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
	private Map directoryDaoMetaDataCache = new HashMap();
	/** ディレクトリ接続ファクトリを表わします。 */
	private DirectoryDataSource directoryDataSource;
	/** ディレクトリアノテーションリーダファクトリを表します。 */
	private DirectoryAnnotationReaderFactory directoryAnnotationReaderFactory;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param directoryControlProperty ディレクトリサーバ接続情報
	 * @param directoryAnnotationReaderFactory ディレクトリアノテーションリーダファクトリ
	 */
	public DirectoryDaoMetaDataFactoryImpl(
			DirectoryControlProperty directoryControlProperty,
			DirectoryAnnotationReaderFactory directoryAnnotationReaderFactory) {
		this.directoryDataSource = new DirectoryDataSourceImpl(
				directoryControlProperty);
		this.directoryAnnotationReaderFactory = directoryAnnotationReaderFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public DirecotryDaoMetaData getDirectoryDaoMetaData(Class daoClass) {
		String key = daoClass.getName();
		DirecotryDaoMetaData dmd = (DirecotryDaoMetaData)directoryDaoMetaDataCache
				.get(key);
		if (dmd != null) {
			return dmd;
		}
		synchronized (this) {
			dmd = (DirecotryDaoMetaData)directoryDaoMetaDataCache.get(key);
			if (dmd != null) {
				return dmd;
			}
			dmd = new DirectoryDaoMetaDataImpl(daoClass, directoryDataSource,
					directoryAnnotationReaderFactory);
			directoryDaoMetaDataCache.put(key, dmd);
		}
		return dmd;
	}
}
