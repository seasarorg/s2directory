/*
 * Copyright 2005-2014 the Seasar Foundation and the Others.
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
import java.util.List;
import java.util.Map;

import org.seasar.directory.DirectoryAttributeHandlerFactory;
import org.seasar.directory.DirectoryDataSourceFactory;
import org.seasar.directory.NamingEnumerationHandler;
import org.seasar.directory.dao.AnnotationMethodArgs;
import org.seasar.directory.dao.AnnotationMethodArgsFactory;
import org.seasar.directory.dao.DirectoryAnnotationReaderFactory;
import org.seasar.directory.dao.DirectoryBeanMetaData;
import org.seasar.directory.dao.DirectoryCommand;
import org.seasar.directory.dao.DirectoryDaoAnnotationReader;
import org.seasar.directory.dao.DirectoryDaoMetaData;
import org.seasar.directory.dao.DirectoryDaoNamingConvention;
import org.seasar.directory.dao.handler.BeanListMetaDataNamingEnumerationHandler;
import org.seasar.directory.dao.handler.BeanMetaDataNamingEnumerationHandler;
import org.seasar.directory.dao.handler.ObjectNamingEnumerationHandler;
import org.seasar.directory.exception.DirectoryDaoNotFoundRuntimeException;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.MethodNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.MethodUtil;
import org.seasar.framework.util.StringUtil;

/**
 * DirectoryDao用のメタ情報を表わすクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class DirectoryDaoMetaDataImpl implements DirectoryDaoMetaData {

	/** Daoクラス */
	protected Class daoClass;

	/** Daoインターフェイス */
	protected Class daoInterface;

	/** Daoクラスの定義 */
	protected BeanDesc daoBeanDesc;

	/** Daoアノテーションリーダー */
	protected DirectoryDaoAnnotationReader daoAnnotationReader;

	/** データソースファクトリ */
	protected DirectoryDataSourceFactory dataSourceFactory;

	/** ディレクトリ属性ハンドラファクトリ */
	protected DirectoryAttributeHandlerFactory attributeHandlerFactory;

	/** ディレクトリアノテーションリーダーファクトリ */
	protected DirectoryAnnotationReaderFactory annotationReaderFactory;

	/** ビーンクラス */
	protected Class beanClass;

	/** ビーンメタデータ */
	protected DirectoryBeanMetaData beanMetaData;

	/** コマンドのキャッシュ */
	protected Map directoryCmmands = new HashMap();

	/** ディレクトリDao命名規約 */
	protected DirectoryDaoNamingConvention daoNamingConvention;

	/**
	 * インスタンスを作成します。
	 */
	public DirectoryDaoMetaDataImpl() {
	}

	/**
	 * 初期化します。
	 */
	public void initialize() {
		beanClass = daoAnnotationReader.getBeanClass();
		daoInterface = getDaoInterface(daoClass);
		daoBeanDesc = BeanDescFactory.getBeanDesc(daoClass);

		// TODO S2DaoのようにBeanMetaDataFactoryにまとめるかどうか
		DirectoryBeanMetaDataImpl beanMetaDataImpl =
			new DirectoryBeanMetaDataImpl();
		beanMetaDataImpl.setBeanClass(beanClass);
		beanMetaDataImpl.setDirectoryAnnotationReaderFactory(annotationReaderFactory);
		beanMetaDataImpl.setDirectoryAttributeHandlerFactory(attributeHandlerFactory);
		beanMetaDataImpl.initialize();
		beanMetaData = beanMetaDataImpl;

		// コマンドを作成します。
		setupDirectoryCommand();
	}

	protected void setupDirectoryCommand() {
		final String[] names = daoBeanDesc.getMethodNames();
		for (int i = 0; i < names.length; ++i) {
			final Method[] methods = daoBeanDesc.getMethods(names[i]);
			if (methods.length == 1 && MethodUtil.isAbstract(methods[0])) {
				setupMethod(methods[0]);
			}
		}
	}

	protected void setupMethod(final Method method) {
		final String filter = daoAnnotationReader.getFilter(method);
		if (filter != null) {
			// FILTERアノテーションが定義されていた場合
			setupSelectMethodByManual(method, filter);
		} else {
			setupMethodByAuto(method);
		}
	}

	/**
	 * FILTERアノテーションで定義されたフィルタで検索します。
	 * 
	 * @param method
	 * @param filter
	 */
	protected void setupSelectMethodByManual(final Method method,
			final String filter) {
		// コマンドを作成します。
		NamingEnumerationHandler handler =
			createNamingEnumerationHandler(method);
		SelectDynamicCommand cmd =
			new SelectDynamicCommand(
				dataSourceFactory,
				attributeHandlerFactory,
				null,
				null,
				handler);
		cmd.setFilter(filter);
		directoryCmmands.put(method.getName(), cmd);
	}

	/**
	 * 関数名より動作を自動決定し、実行します。
	 * 
	 * @param method
	 */
	protected void setupMethodByAuto(final Method method) {
		if (isAuthenticate(method.getName())) {
			setupAuthenticateMethodByAuto(method);
		} else if (isInsert(method.getName())) {
			setupInsertMethodByAuto(method);
		} else if (isUpdate(method.getName())) {
			setupUpdateMethodByAuto(method);
		} else if (isDelete(method.getName())) {
			setupDeleteMethodByAuto(method);
		} else {
			setupSelectMethodByAuto(method);
		}
	}

	protected boolean isAuthenticate(final String methodName) {
		final String[] authenticatePrefixes =
			getDirectoryDaoNamingConvention().getAuthenticatePrefixes();
		for (int i = 0; i < authenticatePrefixes.length; ++i) {
			if (methodName.startsWith(authenticatePrefixes[i])) {
				return true;
			}
		}
		return false;
	}

	protected boolean isInsert(final String methodName) {
		final String[] insertPrefixes =
			getDirectoryDaoNamingConvention().getInsertPrefixes();
		for (int i = 0; i < insertPrefixes.length; ++i) {
			if (methodName.startsWith(insertPrefixes[i])) {
				return true;
			}
		}
		return false;
	}

	protected boolean isUpdate(final String methodName) {
		final String[] updatePrefixes =
			getDirectoryDaoNamingConvention().getUpdatePrefixes();
		for (int i = 0; i < updatePrefixes.length; ++i) {
			if (methodName.startsWith(updatePrefixes[i])) {
				return true;
			}
		}
		return false;
	}

	protected boolean isDelete(final String methodName) {
		final String[] deletePrefixes =
			getDirectoryDaoNamingConvention().getDeletePrefixes();
		for (int i = 0; i < deletePrefixes.length; ++i) {
			if (methodName.startsWith(deletePrefixes[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 認証関数用の処理コマンドを準備します。
	 * 
	 * @param method
	 */
	protected void setupAuthenticateMethodByAuto(final Method method) {
		final AuthenticateDynamicCommand cmd =
			new AuthenticateDynamicCommand(
				dataSourceFactory,
				attributeHandlerFactory);
		directoryCmmands.put(method.getName(), cmd);
	}

	/**
	 * 挿入関数用の処理コマンドを準備します。
	 * 
	 * @param method
	 */
	protected void setupInsertMethodByAuto(final Method method) {
		// 引数の準備をします。
		final AnnotationMethodArgs methodArgs =
			AnnotationMethodArgsFactory.create(method, daoAnnotationReader);
		final InsertDynamicCommand cmd =
			new InsertDynamicCommand(
				dataSourceFactory,
				attributeHandlerFactory,
				beanMetaData,
				methodArgs);
		// オブジェクトクラスを設定します。
		cmd.setObjectClasses(daoAnnotationReader.getObjectClasses(beanMetaData.getObjectClasses()));
		directoryCmmands.put(method.getName(), cmd);
	}

	/**
	 * 更新関数用の処理コマンドを準備します。
	 * 
	 * @param method
	 */
	protected void setupUpdateMethodByAuto(final Method method) {
		// 引数の準備をします。
		final AnnotationMethodArgs methodArgs =
			AnnotationMethodArgsFactory.create(method, daoAnnotationReader);
		final DirectoryCommand cmd =
			new UpdateDynamicCommand(
				dataSourceFactory,
				attributeHandlerFactory,
				beanMetaData,
				methodArgs);
		directoryCmmands.put(method.getName(), cmd);
	}

	/**
	 * 削除関数用の処理コマンドを準備します。
	 * 
	 * @param method
	 */
	protected void setupDeleteMethodByAuto(final Method method) {
		// 引数の準備をします。
		final AnnotationMethodArgs methodArgs =
			AnnotationMethodArgsFactory.create(method, daoAnnotationReader);
		final DirectoryCommand cmd =
			new DeleteDynamicCommand(
				dataSourceFactory,
				attributeHandlerFactory,
				methodArgs);
		directoryCmmands.put(method.getName(), cmd);
	}

	/**
	 * 読み出し関数用の処理コマンドを準備します。
	 * 
	 * @param method
	 */
	protected void setupSelectMethodByAuto(final Method method) {
		// 引数の準備をします。
		final AnnotationMethodArgs methodArgs =
			AnnotationMethodArgsFactory.create(method, daoAnnotationReader);
		// コマンドを作成します。
		final NamingEnumerationHandler handler =
			createNamingEnumerationHandler(method);
		final SelectDynamicCommand cmd =
			new SelectDynamicCommand(
				dataSourceFactory,
				attributeHandlerFactory,
				beanMetaData,
				methodArgs,
				handler);
		// フィルタの準備をします。
		String filter =
			createSelectFilterFromObjectClasses(
				daoAnnotationReader,
				beanMetaData);
		String query = daoAnnotationReader.getQuery(method);
		if (query != null) {
			if (StringUtil.isEmpty(filter)) {
				filter = query;
			} else {
				if (!(query.startsWith("(") && query.endsWith(")"))) {
					query = "(" + query + ")";
				}
				filter = "(&(" + filter + ")" + query + ")";
			}
		}
		cmd.setFilter(filter);
		directoryCmmands.put(method.getName(), cmd);
	}

	/**
	 * オブジェクトクラスからフィルタを作成して返します。
	 * 
	 * @param annotationReader
	 *            {@link DirectoryDaoAnnotationReader}
	 * @param beanMetaData
	 *            {@link DirectoryBeanMetaData}
	 * @return フィルタ
	 */
	protected String createSelectFilterFromObjectClasses(
			final DirectoryDaoAnnotationReader annotationReader,
			final DirectoryBeanMetaData beanMetaData) {
		// オブジェクトクラスを設定します。
		final String[] objectClasses =
			annotationReader.getObjectClasses(beanMetaData.getObjectClasses());
		if (objectClasses.length == 0) {
			return "";
		}
		// aaa
		// objectClass=aaa
		if (objectClasses.length == 1) {
			return "objectClass=" + objectClasses[0];
		}
		// aaa bbb
		// (& (objectClass=aaa) (objectClass=bbb) )
		// aaa bbb ccc
		// (& (&(objectClass=aaa)(objectClass=bbb)) (objectClass=ccc) )
		final StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < objectClasses.length; i++) {
			if (i % 2 == 0) {
				buffer.insert(0, "(&");
			}
			buffer.append("(objectClass=").append(objectClasses[i]).append(")");
			if (i % 2 == 1) {
				buffer.append(")");
			}
		}
		return buffer.toString();
	}

	/**
	 * 処理結果を扱うハンドラを作成します。
	 * 
	 * @param method
	 *            メソッド
	 * @return 処理ハンドラ
	 */
	public NamingEnumerationHandler createNamingEnumerationHandler(
			final Method method) {
		if (List.class.isAssignableFrom(method.getReturnType())) {
			// List型ハンドラ
			return new BeanListMetaDataNamingEnumerationHandler(
				beanMetaData,
				dataSourceFactory.getDefaultDirectoryControlProperty());
		} else if (beanMetaData.isBeanClassAssignable(method.getReturnType())) {
			// Bean型ハンドラ
			return new BeanMetaDataNamingEnumerationHandler(
				beanMetaData,
				dataSourceFactory.getDefaultDirectoryControlProperty());
		} else {
			return new ObjectNamingEnumerationHandler(
				beanMetaData,
				dataSourceFactory.getDefaultDirectoryControlProperty());
		}
	}

	public Class getDaoInterface(final Class clazz) {
		if (clazz.isInterface()) {
			return clazz;
		}
		final String[] daoSuffixes =
			getDirectoryDaoNamingConvention().getDirectoryDaoSuffixes();
		for (Class target = clazz; target != AbstractDirectoryDao.class; target =
			target.getSuperclass()) {
			final Class[] interfaces = target.getInterfaces();
			for (int i = 0; i < interfaces.length; ++i) {
				final Class intf = interfaces[i];
				for (int j = 0; j < daoSuffixes.length; j++) {
					if (intf.getName().endsWith(daoSuffixes[j])) {
						return intf;
					}
				}
			}
		}
		throw new DirectoryDaoNotFoundRuntimeException(clazz);
	}

	/**
	 * 指定された関数名に対応するディレクトリコマンドが存在するか調べます。
	 * 
	 * @param methodName
	 *            関数名
	 * @return 存在する場合は true、存在しない場合は false を返します。
	 * @see org.seasar.directory.dao.DirectoryDaoMetaData#hasDirectoryCommand(java.lang.String)
	 */
	public boolean hasDirectoryCommand(final String methodName) {
		return directoryCmmands.containsKey(methodName);
	}

	//
	// getter / setter
	//
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
	public void setDaoClass(final Class daoClass) {
		this.daoClass = daoClass;
	}

	/**
	 * ディレクトリDaoアノテーションリーダを取得します。
	 * 
	 * @return ディレクトリDaoアノテーションリーダ
	 */
	public DirectoryDaoAnnotationReader getDirectoryDaoAnnotationReader() {
		return daoAnnotationReader;
	}

	/**
	 * ディレクトリDaoアノテーションリーダを設定します。
	 * 
	 * @param daoAnnotationReader
	 *            ディレクトリDaoアノテーションリーダを
	 */
	public void setDirectoryDaoAnnotationReader(
			final DirectoryDaoAnnotationReader daoAnnotationReader) {
		this.daoAnnotationReader = daoAnnotationReader;
	}

	/**
	 * データソースファクトリを取得します。
	 * 
	 * @return データソースファクトリ
	 */
	public DirectoryDataSourceFactory getDirectoryDataSourceFactory() {
		return dataSourceFactory;
	}

	/**
	 * データソースファクトリを設定します。
	 * 
	 * @param dataSourceFactory
	 *            データソースファクトリ
	 */
	public void setDirectoryDataSourceFactory(
			final DirectoryDataSourceFactory dataSourceFactory) {
		this.dataSourceFactory = dataSourceFactory;
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
			final DirectoryAttributeHandlerFactory attributeHandlerFactory) {
		this.attributeHandlerFactory = attributeHandlerFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public DirectoryCommand getDirectoryCommand(final String methodName)
			throws MethodNotFoundRuntimeException {
		DirectoryCommand cmd =
			(DirectoryCommand)directoryCmmands.get(methodName);
		if (cmd == null) {
			throw new MethodNotFoundRuntimeException(daoClass, methodName, null);
		}
		return cmd;
	}

	/**
	 * アノテーションリーダーファクトリを取得します。
	 * 
	 * @return アノテーションリーダーファクトリ
	 */
	public DirectoryAnnotationReaderFactory getDirectoryAnnotationReaderFactory() {
		return annotationReaderFactory;
	}

	/**
	 * アノテーションリーダーファクトリを設定します。
	 * 
	 * @param annotationReaderFactory
	 *            アノテーションリーダーファクトリ
	 */
	public void setDirectoryAnnotationReaderFactory(
			final DirectoryAnnotationReaderFactory annotationReaderFactory) {
		this.annotationReaderFactory = annotationReaderFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class getBeanClass() {
		return beanClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public DirectoryBeanMetaData getDirectoryBeanMetaData() {
		return beanMetaData;
	}

	/**
	 * ディレクトリDao命名規約を取得します。
	 * 
	 * @return ディレクトリDao命名規約
	 */
	public DirectoryDaoNamingConvention getDirectoryDaoNamingConvention() {
		return daoNamingConvention;
	}

	/**
	 * ディレクトリDao命名規約を設定します。
	 * 
	 * @param daoNamingConvention
	 *            ディレクトリDao命名規約
	 */
	public void setDirectoryDaoNamingConvention(
			final DirectoryDaoNamingConvention daoNamingConvention) {
		this.daoNamingConvention = daoNamingConvention;
	}

}
