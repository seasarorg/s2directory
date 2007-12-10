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
package org.seasar.directory.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.directory.DirectoryAttributeHandlerFactory;
import org.seasar.directory.DirectoryDataSourceFactory;
import org.seasar.directory.dao.DirectoryAnnotationReaderFactory;
import org.seasar.directory.dao.DirectoryDaoAnnotationReader;
import org.seasar.directory.dao.DirectoryDaoMetaData;
import org.seasar.directory.dao.DirectoryDaoMetaDataFactory;
import org.seasar.directory.dao.DirectoryDaoNamingConvention;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.Disposable;
import org.seasar.framework.util.DisposableUtil;

/**
 * ディレクトリメタデータを生成する実装クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryDaoMetaDataFactoryImpl implements
		DirectoryDaoMetaDataFactory, Disposable {
	/** ディレクトリコマンドファクトリのBindingアノテーション */
	public static final String dataSourceFactory_BINDING = "bindingType=must";
	/** ディレクトリ属性ハンドラファクトリのBindingアノテーション */
	public static final String attributeHandlerFactory_BINDING =
		"bindingType=must";
	/** ディレクトリアノテーションリーダファクトリのBindingアノテーション */
	public static final String annotationReaderFactory_BINDING =
		"bindingType=must";
	/** ディレクトリ命名規則のBindingアノテーション */
	public static final String daoNamingConvention_BINDING = "bindingType=must";

	/** ディレクトリコマンドファクトリ */
	protected DirectoryDataSourceFactory dataSourceFactory;
	/** ディレクトリ属性ハンドラファクトリ */
	protected DirectoryAttributeHandlerFactory attributeHandlerFactory;
	/** ディレクトリアノテーションリーダファクトリ */
	protected DirectoryAnnotationReaderFactory annotationReaderFactory;
	/** ディレクトリ命名規則 */
	protected DirectoryDaoNamingConvention daoNamingConvention;
	/** ディレクトリDaoメタデータのキャッシュ */
	protected Map daoMetaDataCache = new HashMap();
	/** 初期化フラグ */
	protected boolean initialized;

	/**
	 * インスタンスを作成します。
	 */
	public DirectoryDaoMetaDataFactoryImpl() {
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
		final String key = daoClass.getName();
		DirectoryDaoMetaData dmd;
		synchronized (daoMetaDataCache) {
			dmd = (DirectoryDaoMetaData)daoMetaDataCache.get(key);
		}
		if (dmd != null) {
			return dmd;
		}
		final DirectoryDaoMetaData dmdi = createDirectoryDaoMetaData(daoClass);
		synchronized (daoMetaDataCache) {
			dmd = (DirectoryDaoMetaData)daoMetaDataCache.get(daoClass);
			if (dmd != null) {
				return dmd;
			} else {
				daoMetaDataCache.put(key, dmdi);
			}
		}
		return dmdi;
	}

	/**
	 * 指定されたDAOクラスから作成したDirecotryDaoMetaDataのインスタンスを作成します。
	 * 
	 * @param daoClass
	 *            作成元となるDAOクラス
	 * @return 作成したDirecotryDaoMetaDataのインスタンス
	 */
	protected DirectoryDaoMetaData createDirectoryDaoMetaData(
			final Class daoClass) {
		final BeanDesc daoBeanDesc = BeanDescFactory.getBeanDesc(daoClass);
		final DirectoryDaoAnnotationReader daoAnnotationReader =
			annotationReaderFactory
				.createDirectoryDaoAnnotationReader(daoBeanDesc);

		final DirectoryDaoMetaDataImpl daoMetaData =
			createDirectoryDaoMetaDataImpl();
		daoMetaData.setDaoClass(daoClass);
		daoMetaData.setDirectoryDataSourceFactory(dataSourceFactory);
		daoMetaData
			.setDirectoryAttributeHandlerFactory(attributeHandlerFactory);

		daoMetaData.setDirectoryDaoAnnotationReader(daoAnnotationReader);
		daoMetaData
			.setDirectoryAnnotationReaderFactory(annotationReaderFactory);
		daoMetaData.setDirectoryDaoNamingConvention(daoNamingConvention);
		daoMetaData.initialize();
		return daoMetaData;
	}

	/**
	 * DirecotryDaoMetaDataImplのインスタンスを作成します。
	 * 
	 * @return DirecotryDaoMetaDataImplのインスタンス
	 */
	protected DirectoryDaoMetaDataImpl createDirectoryDaoMetaDataImpl() {
		return new DirectoryDaoMetaDataImpl();
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized void dispose() {
		daoMetaDataCache.clear();
		initialized = false;
	}

	//
	// getter / setter
	//

	public DirectoryDataSourceFactory getDirectoryDataSourceFactory() {
		return dataSourceFactory;
	}

	public void setDirectoryDataSourceFactory(
			DirectoryDataSourceFactory dataSourceFactory) {
		this.dataSourceFactory = dataSourceFactory;
	}

	public DirectoryAttributeHandlerFactory getDirectoryAttributeHandlerFactory() {
		return attributeHandlerFactory;
	}

	public void setDirectoryAttributeHandlerFactory(
			DirectoryAttributeHandlerFactory attributeHandlerFactory) {
		this.attributeHandlerFactory = attributeHandlerFactory;
	}

	public DirectoryAnnotationReaderFactory getDirectoryAnnotationReaderFactory() {
		return annotationReaderFactory;
	}

	public void setDirectoryAnnotationReaderFactory(
			DirectoryAnnotationReaderFactory annotationReaderFactory) {
		this.annotationReaderFactory = annotationReaderFactory;
	}

	public DirectoryDaoNamingConvention getDirectoryDaoNamingConvention() {
		return daoNamingConvention;
	}

	public void setDirectoryDaoNamingConvention(
			DirectoryDaoNamingConvention daoNamingConvention) {
		this.daoNamingConvention = daoNamingConvention;
	}
}
