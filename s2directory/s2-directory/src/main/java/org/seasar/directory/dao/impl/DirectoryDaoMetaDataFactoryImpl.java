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
import org.seasar.directory.DirectoryAttributeHandlerFactory;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.dao.DirectoryDaoMetaData;
import org.seasar.directory.dao.DirectoryAnnotationReaderFactory;
import org.seasar.directory.dao.DirectoryDaoMetaDataFactory;
import org.seasar.directory.impl.DirectoryDataSourceImpl;
import org.seasar.framework.util.Disposable;
import org.seasar.framework.util.DisposableUtil;

/**
 * DirectoryDaoMetaDataを生成します。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryDaoMetaDataFactoryImpl implements
		DirectoryDaoMetaDataFactory, Disposable {
	/** メタデータのキャッシュを表わします。 */
	protected Map directoryDaoMetaDataCache = new HashMap();
	/** ディレクトリ接続ファクトリを表わします。 */
	protected DirectoryDataSource directoryDataSource;
	/** ディレクトリアノテーションリーダファクトリを表します。 */
	protected DirectoryAnnotationReaderFactory directoryAnnotationReaderFactory;
	/** ディレクトリ用の値の属性ハンドラファクトリを表します。 */
	protected DirectoryAttributeHandlerFactory directoryAttributeHandlerFactory;
	/** Daoクラスの接尾辞を表します。 */
	protected String[] daoSuffixes;
	/** 認証関数用の接頭辞を表わします。 */
	protected String[] authenticatePrefixes;
	/** 新規挿入操作関数用の接頭辞を表わします。 */
	protected String[] insertPrefixes;
	/** 更新操作関数用の接頭辞を表わします。 */
	protected String[] updatePrefixes;
	/** 削除操作関数用の接頭辞を表わします。 */
	protected String[] deletePrefixes;
	/** 初期化フラグを表します。 */
	protected boolean initialized;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param directoryControlProperty ディレクトリサーバ接続情報
	 * @param directoryAnnotationReaderFactory ディレクトリアノテーションリーダファクトリ
	 */
	public DirectoryDaoMetaDataFactoryImpl(
			DirectoryControlProperty directoryControlProperty,
			DirectoryAnnotationReaderFactory directoryAnnotationReaderFactory) {
		this.directoryDataSource =
			new DirectoryDataSourceImpl(directoryControlProperty);
		this.directoryAnnotationReaderFactory =
			directoryAnnotationReaderFactory;
	}

	/**
	 * Daoクラスの接尾辞を設定します。
	 * 
	 * @param daoSuffixes Daoクラスの接尾辞
	 */
	public void setDaoSuffixes(String[] daoSuffixes) {
		this.daoSuffixes = daoSuffixes;
	}

	/**
	 * 認証関数用の接頭辞を設定します。
	 * 
	 * @param authenticatePrefixes 認証関数用の接頭辞
	 */
	public void setAuthenticatePrefixes(String[] authenticatePrefixes) {
		this.authenticatePrefixes = authenticatePrefixes;
	}

	/**
	 * 新規挿入操作関数用の接頭辞を設定します。
	 * 
	 * @param insertPrefixes 新規挿入操作関数用の接頭辞
	 */
	public void setInsertPrefixes(String[] insertPrefixes) {
		this.insertPrefixes = insertPrefixes;
	}

	/**
	 * 削除操作関数用の接頭辞を設定します。
	 * 
	 * @param updatePrefixes 削除操作関数用の接頭辞
	 */
	public void setUpdatePrefixes(String[] updatePrefixes) {
		this.updatePrefixes = updatePrefixes;
	}

	/**
	 * 更新操作関数用の接頭辞を設定します。
	 * 
	 * @param deletePrefixes 更新操作関数用の接頭辞
	 */
	public void setDeletePrefixes(String[] deletePrefixes) {
		this.deletePrefixes = deletePrefixes;
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized DirectoryDaoMetaData getDirectoryDaoMetaData(
			Class daoClass) {
		if (!initialized) {
			DisposableUtil.add(this);
			initialized = true;
		}
		String key = daoClass.getName();
		DirectoryDaoMetaData dmd =
			(DirectoryDaoMetaData)directoryDaoMetaDataCache.get(key);
		if (dmd != null) {
			return dmd;
		}
		DirectoryDaoMetaData dmdi = createDirectoryDaoMetaData(daoClass);
		directoryDaoMetaDataCache.put(key, dmdi);
		return dmdi;
	}

	/**
	 * 指定されたDAOクラスから生成したDirecotryDaoMetaDataのインスタンスを作成します。
	 * 
	 * @param daoClass 生成元となるDAOクラス
	 * @return 生成したDirecotryDaoMetaDataのインスタンス
	 */
	protected DirectoryDaoMetaData createDirectoryDaoMetaData(Class daoClass) {
		DirectoryDaoMetaDataImpl directoryDaoMetaData =
			new DirectoryDaoMetaDataImpl();
		directoryDaoMetaData.setDaoClass(daoClass);
		directoryDaoMetaData.setDirectoryDataSource(directoryDataSource);
		directoryDaoMetaData
				.setDirectoryAnnotationReaderFactory(directoryAnnotationReaderFactory);
		directoryDaoMetaData
				.setDirectoryAttributeHandlerFactory(directoryAttributeHandlerFactory);
		if (daoSuffixes != null) {
			directoryDaoMetaData.setDaoSuffixes(daoSuffixes);
		}
		if (authenticatePrefixes != null) {
			directoryDaoMetaData.setAuthenticatePrefixes(authenticatePrefixes);
		}
		if (insertPrefixes != null) {
			directoryDaoMetaData.setInsertPrefixes(insertPrefixes);
		}
		if (updatePrefixes != null) {
			directoryDaoMetaData.setUpdatePrefixes(updatePrefixes);
		}
		if (deletePrefixes != null) {
			directoryDaoMetaData.setDeletePrefixes(deletePrefixes);
		}
		directoryDaoMetaData.initialize();
		return directoryDaoMetaData;
	}

	/**
	 * ディレクトリ用の値の型ファクトリを設定します。
	 * 
	 * @param directoryValueTypeFactory ディレクトリ用の値の型ファクトリ
	 */
	public void setDirectoryAttributeHandlerFactory(
			DirectoryAttributeHandlerFactory directoryAttributeHandlerFactory) {
		this.directoryAttributeHandlerFactory =
			directoryAttributeHandlerFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized void dispose() {
		directoryDaoMetaDataCache.clear();
		initialized = false;
	}
}
