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
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.dao.DirectoryAnnotationReaderFactory;
import org.seasar.directory.dao.DirectoryBeanMetaData;
import org.seasar.directory.dao.DirectoryCommand;
import org.seasar.directory.dao.DirectoryDaoAnnotationReader;
import org.seasar.directory.dao.DirectoryDaoMetaData;
import org.seasar.directory.exception.DirectoryDaoNotFoundRuntimeException;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.MethodNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * DirectoryDao用のメタ情報を表わすクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryDaoMetaDataImpl implements DirectoryDaoMetaData {
	/** データソース */
	protected DirectoryDataSource directoryDataSource;
	/** Daoクラス */
	protected Class daoClass;
	protected Class daoInterface;
	/** Daoクラスの定義 */
	protected BeanDesc daoBeanDesc;
	/** Daoアノテーションリーダー */
	protected DirectoryDaoAnnotationReader daoAnnotationReader;
	/** Daoアノテーションリーダーファクトリ */
	protected DirectoryAnnotationReaderFactory annotationReaderFactory;
	/** ディレクトリ属性ハンドラファクトリ */
	protected DirectoryAttributeHandlerFactory attributeHandlerFactory;
	/** ビーンクラス */
	protected Class beanClass;
	/** ビーンメタデータ */
	protected DirectoryBeanMetaData directoryBeanMetaData;
	/** コマンドのキャッシュ */
	protected Map directoryCmmands = new HashMap();
	/** Daoクラスの接尾辞 */
	protected String[] daoSuffixes = new String[] { "Dao" };

	/**
	 * インスタンスを作成します。
	 */
	public DirectoryDaoMetaDataImpl() {
	}

	public DirectoryDaoMetaDataImpl(Class daoClass,
			DirectoryAnnotationReaderFactory readerFactory,
			String[] daoSuffixes,
			DirectoryAttributeHandlerFactory attributeHandlerFactory) {
		setDaoClass(daoClass);
		setDirectoryAnnotationReaderFactory(readerFactory);
		setDirectoryAttributeHandlerFactory(attributeHandlerFactory);
		if (daoSuffixes != null) {
			setDaoSuffixes(daoSuffixes);
		}
		initialize();
	}

	/**
	 * 初期化します。
	 */
	public void initialize() {
		Class daoClass = getDaoClass();
		this.daoInterface = getDaoInterface(daoClass);
		this.daoBeanDesc = BeanDescFactory.getBeanDesc(daoClass);
		this.daoAnnotationReader =
			getDirectoryAnnotationReaderFactory()
				.createDirectoryDaoAnnotationReader(daoBeanDesc);
	}

	public Class getDaoInterface(Class clazz) {
		if (clazz.isInterface()) {
			return clazz;
		}
		for (Class target = clazz; target != AbstractDirectoryDao.class; target =
			target.getSuperclass()) {
			Class[] interfaces = target.getInterfaces();
			for (int i = 0; i < interfaces.length; ++i) {
				Class intf = interfaces[i];
				for (int j = 0; j < daoSuffixes.length; j++) {
					if (intf.getName().endsWith(daoSuffixes[j])) {
						return intf;
					}
				}
			}
		}
		throw new DirectoryDaoNotFoundRuntimeException(clazz);
	}

	public BeanDesc getDirectoryDaoBeanDesc() {
		return daoBeanDesc;
	}

	public DirectoryDaoAnnotationReader getDirectoryDaoAnnotationReader() {
		return daoAnnotationReader;
	}

	public void setDirectoryCommand(String methodName, DirectoryCommand cmd) {
		directoryCmmands.put(methodName, cmd);
	}

	/**
	 * {@inheritDoc}
	 */
	public Class getBeanClass() {
		return beanClass;
	}

	/**
	 * ビーンクラスを設定します。
	 * 
	 * @param beanClass
	 *            ビーンクラス
	 */
	protected void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
	}

	/**
	 * ビーンメタデータを取得します。
	 * 
	 * @return directoryBeanMetaData ビーンメタデータ
	 */
	public DirectoryBeanMetaData getDirectoryBeanMetaData() {
		return directoryBeanMetaData;
	}

	/**
	 * ビーンメタデータを設定します。
	 * 
	 * @param directoryBeanMetaData
	 *            ビーンメタデータ
	 */
	public void setDirectoryBeanMetaData(
			DirectoryBeanMetaData directoryBeanMetaData) {
		this.directoryBeanMetaData = directoryBeanMetaData;
	}

	/**
	 * {@inheritDoc}
	 */
	public DirectoryCommand getDirectoryCommand(String methodName)
			throws MethodNotFoundRuntimeException {
		DirectoryCommand cmd =
			(DirectoryCommand)directoryCmmands.get(methodName);
		if (cmd == null) {
			throw new MethodNotFoundRuntimeException(daoClass, methodName, null);
		}
		return cmd;
	}

	/**
	 * 指定された関数名に対応するディレクトリコマンドが存在するか調べます。
	 * 
	 * @param methodName
	 *            関数名
	 * @return 存在する場合は true、存在しない場合は false を返します。
	 * @see org.seasar.directory.dao.DirectoryDaoMetaData#hasDirectoryCommand(java.lang.String)
	 */
	public boolean hasDirectoryCommand(String methodName) {
		return directoryCmmands.containsKey(methodName);
	}

	/**
	 * アノテーションリーダーファクトリを取得します。
	 */
	public DirectoryAnnotationReaderFactory getDirectoryAnnotationReaderFactory() {
		return annotationReaderFactory;
	}

	/**
	 * アノテーションリーダーファクトリを設定します。
	 * 
	 * @param directoryAnnotationReaderFactory
	 *            アノテーションリーダーファクトリ
	 */
	public void setDirectoryAnnotationReaderFactory(
			DirectoryAnnotationReaderFactory directoryAnnotationReaderFactory) {
		this.annotationReaderFactory = directoryAnnotationReaderFactory;
	}

	/**
	 * ディレクトリ用の値の型ファクトリを取得します。
	 * 
	 * @return ディレクトリ用の値の型ファクトリ
	 */
	public DirectoryAttributeHandlerFactory getDirectoryAttributeHandlerFactory() {
		return attributeHandlerFactory;
	}

	/**
	 * ディレクトリ用の値の型ファクトリを設定します。
	 * 
	 * @param directoryAttributeHandlerFactory
	 *            ディレクトリ用の値の型ファクトリ
	 */
	public void setDirectoryAttributeHandlerFactory(
			DirectoryAttributeHandlerFactory attributeHandlerFactory) {
		this.attributeHandlerFactory = attributeHandlerFactory;
	}

	/**
	 * daoSuffixesを設定します。
	 * 
	 * @param daoSuffixes
	 */
	public void setDaoSuffixes(String[] daoSuffixes) {
		this.daoSuffixes = daoSuffixes;
	}

	/**
	 * Daoクラスを取得します。
	 * 
	 * @return daoClass Daoクラス
	 */
	public Class getDaoClass() {
		return daoClass;
	}

	/**
	 * Daoクラスを設定します。
	 * 
	 * @param daoClass
	 *            Daoクラス
	 */
	public void setDaoClass(Class daoClass) {
		this.daoClass = daoClass;
	}
}
