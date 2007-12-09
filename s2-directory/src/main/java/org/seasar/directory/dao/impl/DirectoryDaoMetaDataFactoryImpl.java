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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.seasar.directory.DirectoryAttributeHandlerFactory;
import org.seasar.directory.DirectoryDaoNamingConvention;
import org.seasar.directory.dao.DirectoryAnnotationReaderFactory;
import org.seasar.directory.dao.DirectoryBeanMetaData;
import org.seasar.directory.dao.DirectoryCommand;
import org.seasar.directory.dao.DirectoryCommandFactory;
import org.seasar.directory.dao.DirectoryDaoAnnotationReader;
import org.seasar.directory.dao.DirectoryDaoMetaData;
import org.seasar.directory.dao.DirectoryDaoMetaDataFactory;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.util.MethodUtil;

/**
 * ディレクトリメタデータを生成する実装クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryDaoMetaDataFactoryImpl implements
		DirectoryDaoMetaDataFactory {
	/** ディレクトリDaoメタデータのキャッシュ */
	protected Map directoryDaoMetaDataCache = new HashMap();
	/** ディレクトリアノテーションリーダファクトリ */
	protected DirectoryAnnotationReaderFactory readerFactory;
	/** ディレクトリコマンドファクトリ */
	protected DirectoryCommandFactory directoryCommandFactory;
	/** ディレクトリ属性ハンドラファクトリ */
	protected DirectoryAttributeHandlerFactory attributeHandlerFactory;
	/** ディレクトリ命名規則 */
	protected DirectoryDaoNamingConvention configuration;
	/** ビーンクラスのキャッシュ */
	protected Map beanMetaDataCache = new HashMap();

	/**
	 * インスタンスを生成します。
	 * 
	 * @param property
	 *            ディレクトリサーバ接続情報
	 * @param readerFactory
	 *            ディレクトリアノテーションリーダファクトリ
	 * @param attributeHandlerFactory
	 *            ディレクトリ用の値の型ファクトリ
	 */
	public DirectoryDaoMetaDataFactoryImpl(
			DirectoryCommandFactory directoryCommandFactory,
			DirectoryAnnotationReaderFactory readerFactory,
			DirectoryAttributeHandlerFactory attributeHandlerFactory,
			DirectoryDaoNamingConvention configuration) {
		this.directoryCommandFactory = directoryCommandFactory;
		this.readerFactory = readerFactory;
		this.attributeHandlerFactory = attributeHandlerFactory;
		this.configuration = configuration;
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized DirectoryDaoMetaData getDirectoryDaoMetaData(
			Class daoClass) {
		String key = daoClass.getName();
		DirectoryDaoMetaData dmd =
			(DirectoryDaoMetaData)directoryDaoMetaDataCache.get(key);
		if (dmd != null) {
			return dmd;
		}
		DirectoryDaoMetaDataImpl dmdi =
			new DirectoryDaoMetaDataImpl(daoClass, readerFactory, configuration
				.getDirectoryDaoSuffixes(), attributeHandlerFactory);
		setupDirectoryCommand(dmdi);
		directoryDaoMetaDataCache.put(key, dmdi);
		return dmdi;
	}

	protected void setupDirectoryCommand(
			DirectoryDaoMetaDataImpl daoMetaDataImpl) {
		BeanDesc idbd = daoMetaDataImpl.getDirectoryDaoBeanDesc();
		String[] names = idbd.getMethodNames();
		for (int i = 0; i < names.length; ++i) {
			Method[] methods = idbd.getMethods(names[i]);
			if (methods.length == 1 && MethodUtil.isAbstract(methods[0])) {
				setupMethod(daoMetaDataImpl, methods[0]);
			}
		}
	}

	protected void setupMethod(DirectoryDaoMetaDataImpl daoMetaDataImpl,
			Method method) {
		DirectoryDaoAnnotationReader daoAnnotationReader =
			daoMetaDataImpl.getDirectoryDaoAnnotationReader();
		DirectoryBeanMetaData beanMetaData =
			getDirectoryBeanMetaData(daoAnnotationReader.getBeanClass());
		DirectoryCommand command =
			directoryCommandFactory.createDirectoryCommand(
				daoAnnotationReader,
				beanMetaData,
				method);
		daoMetaDataImpl.setDirectoryCommand(method.getName(), command);
	}

	public DirectoryBeanMetaData getDirectoryBeanMetaData(Class beanClass) {
		DirectoryBeanMetaData beanMetaData =
			(DirectoryBeanMetaData)beanMetaDataCache.get(beanClass);
		if (beanMetaData == null) {
			DirectoryBeanMetaDataImpl beanMetaDataImpl =
				new DirectoryBeanMetaDataImpl();
			beanMetaDataImpl.setBeanClass(beanClass);
			beanMetaDataImpl.setDirectoryAnnotationReaderFactory(readerFactory);
			beanMetaDataImpl
				.setDirectoryAttributeHandlerFactory(attributeHandlerFactory);
			beanMetaDataImpl.initialize();
			beanMetaData = beanMetaDataImpl;
			beanMetaDataCache.put(beanClass, beanMetaData);
		}
		return beanMetaData;
	}
}
