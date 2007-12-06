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

import java.lang.reflect.Method;
import java.util.List;

import org.seasar.directory.DirectoryAttributeHandlerFactory;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.DirectoryDaoNamingConvention;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.NamingEnumerationHandler;
import org.seasar.directory.dao.AnnotationMethodArgs;
import org.seasar.directory.dao.AnnotationMethodArgsFactory;
import org.seasar.directory.dao.DirectoryBeanMetaData;
import org.seasar.directory.dao.DirectoryCommand;
import org.seasar.directory.dao.DirectoryCommandFactory;
import org.seasar.directory.dao.DirectoryDaoAnnotationReader;
import org.seasar.directory.dao.handler.BeanListMetaDataNamingEnumerationHandler;
import org.seasar.directory.dao.handler.BeanMetaDataNamingEnumerationHandler;
import org.seasar.directory.dao.handler.ObjectNamingEnumerationHandler;
import org.seasar.directory.impl.DirectoryDataSourceImpl;
import org.seasar.framework.util.StringUtil;

/**
 * ディレクトリコマンドファクトリの実装クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryCommandFactoryImpl implements DirectoryCommandFactory {

	/** データソース */
	protected DirectoryDataSource dataSource;

	/** ディレクトリ用の値の属性ハンドラファクトリ */
	protected DirectoryAttributeHandlerFactory attributeHandlerFactory;

	/** ディレクトリ用の命名規約 */
	protected DirectoryDaoNamingConvention configuration;

	public DirectoryCommandFactoryImpl(DirectoryControlProperty property,
			DirectoryAttributeHandlerFactory attributeHandlerFactory,
			DirectoryDaoNamingConvention configuration) {
		this.dataSource = new DirectoryDataSourceImpl(property);
		this.attributeHandlerFactory = attributeHandlerFactory;
		this.configuration = configuration;
	}

	public DirectoryCommand createDirectoryCommand(
			DirectoryDaoAnnotationReader annotationReader,
			DirectoryBeanMetaData beanMetaData, Method method) {
		String filter = annotationReader.getFilter(method.getName());
		if (filter != null) {
			// FILTERアノテーションが定義されていた場合
			return setupMethodByManual(beanMetaData, method, filter);
		} else {
			return setupMethodByAuto(annotationReader, beanMetaData, method);
		}
	}

	private DirectoryCommand setupMethodByManual(
			DirectoryBeanMetaData beanMetaData, Method method, String filter) {
		if (configuration.isSelectMethod(method)) {
			return setupSelectMethodByManual(beanMetaData, method, filter);
		} else {
			// TODO: 不要？
			// setupUpdateMethodByManual(method, sql);
		}
		return null;
	}

	/**
	 * FILTERアノテーションで定義されたフィルタで検索します。
	 * 
	 * @param method
	 * @param filter
	 */
	protected DirectoryCommand setupSelectMethodByManual(
			DirectoryBeanMetaData beanMetaData, Method method, String filter) {
		// コマンドを作成します。
		SelectAutoCommand cmd =
			new SelectAutoCommand(dataSource, createNamingEnumerationHandler(
				beanMetaData,
				method,
				method.getReturnType()), attributeHandlerFactory, null);
		cmd.setFilter(filter);
		return cmd;
	}

	/**
	 * 関数名より動作を自動決定し、実行します。
	 * 
	 * @param method
	 */
	private DirectoryCommand setupMethodByAuto(
			DirectoryDaoAnnotationReader annotationReader,
			DirectoryBeanMetaData beanMetaData, Method method) {
		if (configuration.isAuthenticateMethod(method)) {
			return setupAuthenticateMethodByAuto(
				annotationReader,
				beanMetaData,
				method);
		} else if (configuration.isInsertMethod(method)) {
			return setupInsertMethodByAuto(
				annotationReader,
				beanMetaData,
				method);
		} else if (configuration.isUpdateMethod(method)) {
			return setupUpdateMethodByAuto(
				annotationReader,
				beanMetaData,
				method);
		} else if (configuration.isDeleteMethod(method)) {
			return setupDeleteMethodByAuto(
				annotationReader,
				beanMetaData,
				method);
		} else {
			return setupSelectMethodByAuto(
				annotationReader,
				beanMetaData,
				method);
		}
	}

	/**
	 * 認証関数用の処理コマンドを準備します。
	 * 
	 * @param method
	 */
	private DirectoryCommand setupAuthenticateMethodByAuto(
			DirectoryDaoAnnotationReader annotationReader,
			DirectoryBeanMetaData beanMetaData, Method method) {
		// 引数の準備をします。
		AnnotationMethodArgs methodArgs =
			AnnotationMethodArgsFactory.create(method, annotationReader);
		AuthenticateAutoCommand cmd =
			new AuthenticateAutoCommand(
				dataSource,
				attributeHandlerFactory,
				methodArgs);
		return cmd;
	}

	/**
	 * 挿入関数用の処理コマンドを準備します。
	 * 
	 * @param method
	 */
	protected DirectoryCommand setupInsertMethodByAuto(
			DirectoryDaoAnnotationReader annotationReader,
			DirectoryBeanMetaData beanMetaData, Method method) {
		// 引数の準備をします。
		AnnotationMethodArgs methodArgs =
			AnnotationMethodArgsFactory.create(method, annotationReader);
		InsertAutoCommand cmd =
			new InsertAutoCommand(
				dataSource,
				attributeHandlerFactory,
				methodArgs);
		// オブジェクトクラスを設定します。
		cmd.setObjectClasses(annotationReader.getObjectClasses(beanMetaData
			.getObjectClasses()));
		return cmd;
	}

	/**
	 * 更新関数用の処理コマンドを準備します。
	 * 
	 * @param method
	 */
	protected DirectoryCommand setupUpdateMethodByAuto(
			DirectoryDaoAnnotationReader annotationReader,
			DirectoryBeanMetaData beanMetaData, Method method) {
		// 引数の準備をします。
		AnnotationMethodArgs methodArgs =
			AnnotationMethodArgsFactory.create(method, annotationReader);
		DirectoryCommand cmd =
			new UpdateAutoCommand(
				dataSource,
				attributeHandlerFactory,
				methodArgs);
		return cmd;
	}

	/**
	 * 削除関数用の処理コマンドを準備します。
	 * 
	 * @param method
	 */
	protected DirectoryCommand setupDeleteMethodByAuto(
			DirectoryDaoAnnotationReader annotationReader,
			DirectoryBeanMetaData beanMetaData, Method method) {
		// 引数の準備をします。
		AnnotationMethodArgs methodArgs =
			AnnotationMethodArgsFactory.create(method, annotationReader);
		DirectoryCommand cmd =
			new DeleteAutoCommand(
				dataSource,
				attributeHandlerFactory,
				methodArgs);
		return cmd;
	}

	/**
	 * 読み出し関数用の処理コマンドを準備します。
	 * 
	 * @param method
	 */
	private DirectoryCommand setupSelectMethodByAuto(
			DirectoryDaoAnnotationReader annotationReader,
			DirectoryBeanMetaData beanMetaData, Method method) {
		// 引数の準備をします。
		AnnotationMethodArgs methodArgs =
			AnnotationMethodArgsFactory.create(method, annotationReader);
		// コマンドを作成します。
		NamingEnumerationHandler handler =
			createNamingEnumerationHandler(beanMetaData, method, method
				.getReturnType());
		SelectAutoCommand cmd =
			new SelectAutoCommand(
				dataSource,
				handler,
				attributeHandlerFactory,
				methodArgs);
		// フィルタの準備をします。
		String filter = createAutoSelectFilter(annotationReader, beanMetaData);
		String query = annotationReader.getQuery(method.getName());
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
		return cmd;
	}

	protected String createAutoSelectFilter(
			DirectoryDaoAnnotationReader annotationReader,
			DirectoryBeanMetaData beanMetaData) {
		// オブジェクトクラスを設定します。
		String[] objectClasses =
			annotationReader.getObjectClasses(beanMetaData.getObjectClasses());
		StringBuffer buffer = new StringBuffer();
		if (false) {
			buffer.append("(&");
			for (int i = 0; i < objectClasses.length; i++) {
				buffer.append("(objectclass=").append(objectClasses[i]).append(
					")");
			}
			buffer.append(")");
		} else {
			buffer.append("objectclass=").append(objectClasses[0]);
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
			DirectoryBeanMetaData beanMetaData, Method method, Class returnType) {
		if (List.class.isAssignableFrom(returnType)) {
			// List型ハンドラ
			return new BeanListMetaDataNamingEnumerationHandler(
				beanMetaData,
				dataSource.getDirectoryControlProperty());
		} else if (beanMetaData.isBeanClassAssignable(returnType)) {
			// Bean型ハンドラ
			return new BeanMetaDataNamingEnumerationHandler(
				beanMetaData,
				dataSource.getDirectoryControlProperty());
		} else {
			return new ObjectNamingEnumerationHandler(beanMetaData, dataSource
				.getDirectoryControlProperty());
		}
	}
}
