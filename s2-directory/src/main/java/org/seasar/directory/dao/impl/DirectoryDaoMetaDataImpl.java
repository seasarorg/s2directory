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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.seasar.directory.DirectoryAttributeHandlerFactory;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.NamingEnumerationHandler;
import org.seasar.directory.dao.AnnotationMethodArgs;
import org.seasar.directory.dao.AnnotationMethodArgsFactory;
import org.seasar.directory.dao.DirectoryDaoMetaData;
import org.seasar.directory.dao.DirectoryAnnotationReaderFactory;
import org.seasar.directory.dao.DirectoryBeanMetaData;
import org.seasar.directory.dao.DirectoryCommand;
import org.seasar.directory.dao.DirectoryDaoAnnotationReader;
import org.seasar.directory.dao.handler.BeanListMetaDataNamingEnumerationHandler;
import org.seasar.directory.dao.handler.BeanMetaDataNamingEnumerationHandler;
import org.seasar.directory.dao.handler.ObjectNamingEnumerationHandler;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.MethodNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.MethodUtil;
import org.seasar.framework.util.StringUtil;

/**
 * DirectoryDao用のメタ情報を表わすクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryDaoMetaDataImpl implements DirectoryDaoMetaData {
	/** データソースを表わします。 */
	protected DirectoryDataSource directoryDataSource;
	/** Daoクラスを表わします。 */
	protected Class daoClass;
	/** Daoクラスの定義を表わします。 */
	protected BeanDesc daoBeanDesc;
	/** Daoアノテーションリーダーを表します。 */
	protected DirectoryDaoAnnotationReader directoryDaoAnnotationReader;
	/** Daoアノテーションリーダーファクトリを表します。 */
	protected DirectoryAnnotationReaderFactory directoryAnnotationReaderFactory;
	/** TODO: コメント */
	protected DirectoryAttributeHandlerFactory directoryAttributeHandlerFactory;
	/** ビーンクラスを表わします。 */
	protected Class beanClass;
	/** ビーンメタデータを表します。 */
	protected DirectoryBeanMetaData directoryBeanMetaData;
	/** コマンドのキャッシュを表わします。 */
	protected Map commands = new HashMap();
	/** Daoクラスの接尾辞を表します。 */
	protected String[] daoSuffixes = new String[] { "Dao" };
	/** 認証関数用の接頭辞を表わします。 */
	protected String[] authenticatePrefixes = new String[] { "auth" };
	/** 新規挿入操作関数用の接頭辞を表わします。 */
	protected String[] insertPrefixes = new String[] { "insert", "create",
			"add" };
	/** 更新操作関数用の接頭辞を表わします。 */
	protected String[] updatePrefixes = new String[] { "update", "modify",
			"store" };
	/** 削除操作関数用の接頭辞を表わします。 */
	protected String[] deletePrefixes = new String[] { "delete", "remove" };

	/**
	 * インスタンスを作成します。
	 */
	public DirectoryDaoMetaDataImpl() {}

	/**
	 * 初期化します。
	 */
	public void initialize() {
		Class daoClass = getDaoClass();
		// beanClassを作成します。
		daoBeanDesc = BeanDescFactory.getBeanDesc(daoClass);
		directoryDaoAnnotationReader = getDirectoryAnnotationReaderFactory()
				.createDirectoryDaoAnnotationReader(daoBeanDesc);
		setBeanClass(directoryDaoAnnotationReader.getBeanClass());
		DirectoryBeanMetaDataImpl directoryBeanMetaDataImpl = new DirectoryBeanMetaDataImpl();
		directoryBeanMetaDataImpl.setBeanClass(getBeanClass());
		directoryBeanMetaDataImpl
				.setDirectoryAnnotationReaderFactory(getDirectoryAnnotationReaderFactory());
		directoryBeanMetaDataImpl
				.setDirectoryAttributeHandlerFactory(directoryAttributeHandlerFactory);
		directoryBeanMetaDataImpl.initialize();
		directoryBeanMetaData = directoryBeanMetaDataImpl;
		// コマンドを作成します。
		setupDirectoryCommand();
	}

	private void setupDirectoryCommand() {
		String[] names = daoBeanDesc.getMethodNames();
		for (int i = 0; i < names.length; ++i) {
			Method[] methods = daoBeanDesc.getMethods(names[i]);
			if (methods.length == 1 && MethodUtil.isAbstract(methods[0])) {
				setupMethod(methods[0]);
			}
		}
	}

	private void setupMethod(Method method) {
		String filter = directoryDaoAnnotationReader
				.getFilter(method.getName());
		if (filter != null) {
			// FILTERアノテーションが定義されていた場合
			setupMethodByManual(method, filter);
		} else {
			setupMethodByAuto(method);
		}
	}

	private void setupMethodByManual(Method method, String filter) {
		if (isSelect(method)) {
			setupSelectMethodByManual(method, filter);
		} else {
			// setupUpdateMethodByManual(method, sql);
		}
	}

	/**
	 * FILTERアノテーションで定義されたフィルタで検索します。
	 * 
	 * @param method
	 * @param filter
	 */
	protected void setupSelectMethodByManual(Method method, String filter) {
		// コマンドを作成します。
		SelectAutoCommand cmd = new SelectAutoCommand(directoryDataSource,
				createNamingEnumerationHandler(method),
				getDirectoryAttributeHandlerFactory(), null);
		cmd.setFilter(filter);
		commands.put(method.getName(), cmd);
	}

	/**
	 * 関数名より動作を自動決定し、実行します。
	 * 
	 * @param method
	 */
	private void setupMethodByAuto(Method method) {
		if (isStartsWithMethodName(method.getName(), authenticatePrefixes)) {
			setupAuthenticateMethodByAuto(method);
		} else if (isStartsWithMethodName(method.getName(), insertPrefixes)) {
			setupInsertMethodByAuto(method);
		} else if (isStartsWithMethodName(method.getName(), updatePrefixes)) {
			setupUpdateMethodByAuto(method);
		} else if (isStartsWithMethodName(method.getName(), deletePrefixes)) {
			setupDeleteMethodByAuto(method);
		} else {
			setupSelectMethodByAuto(method);
		}
	}

	/**
	 * 関数名に指定した接頭辞が含まれているか調べます。
	 * 
	 * @param methodName 関数名
	 * @param prefix 接頭辞
	 * @return 含まれている場合は true 含まれていない場合は falase を返します。
	 */
	private boolean isStartsWithMethodName(String methodName, String[] prefix) {
		for (int i = 0; i < prefix.length; ++i) {
			if (methodName.startsWith(prefix[i])) {
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
	private void setupAuthenticateMethodByAuto(Method method) {
		// 引数の準備をします。
		AnnotationMethodArgs methodArgs = AnnotationMethodArgsFactory.create(
				method, directoryDaoAnnotationReader);
		AuthenticateAutoCommand cmd = new AuthenticateAutoCommand(
				directoryDataSource, getDirectoryAttributeHandlerFactory(),
				methodArgs);
		commands.put(method.getName(), cmd);
	}

	/**
	 * 挿入関数用の処理コマンドを準備します。
	 * 
	 * @param method
	 */
	protected void setupInsertMethodByAuto(Method method) {
		// 引数の準備をします。
		AnnotationMethodArgs methodArgs = AnnotationMethodArgsFactory.create(
				method, directoryDaoAnnotationReader);
		InsertAutoCommand cmd = new InsertAutoCommand(directoryDataSource,
				getDirectoryAttributeHandlerFactory(), methodArgs);
		// オブジェクトクラスを設定します。
		cmd.setObjectClasses(directoryDaoAnnotationReader
				.getObjectClasses(directoryBeanMetaData.getObjectClasses()));
		commands.put(method.getName(), cmd);
	}

	/**
	 * 更新関数用の処理コマンドを準備します。
	 * 
	 * @param method
	 */
	protected void setupUpdateMethodByAuto(Method method) {
		// 引数の準備をします。
		AnnotationMethodArgs methodArgs = AnnotationMethodArgsFactory.create(
				method, directoryDaoAnnotationReader);
		DirectoryCommand cmd = new UpdateAutoCommand(directoryDataSource,
				getDirectoryAttributeHandlerFactory(), methodArgs);
		commands.put(method.getName(), cmd);
	}

	/**
	 * 削除関数用の処理コマンドを準備します。
	 * 
	 * @param method
	 */
	protected void setupDeleteMethodByAuto(Method method) {
		// 引数の準備をします。
		AnnotationMethodArgs methodArgs = AnnotationMethodArgsFactory.create(
				method, directoryDaoAnnotationReader);
		DirectoryCommand cmd = new DeleteAutoCommand(directoryDataSource,
				getDirectoryAttributeHandlerFactory(), methodArgs);
		commands.put(method.getName(), cmd);
	}

	/**
	 * 読み出し関数用の処理コマンドを準備します。
	 * 
	 * @param method
	 */
	private void setupSelectMethodByAuto(Method method) {
		// 引数の準備をします。
		AnnotationMethodArgs methodArgs = AnnotationMethodArgsFactory.create(
				method, directoryDaoAnnotationReader);
		// コマンドを作成します。
		NamingEnumerationHandler handler = createNamingEnumerationHandler(method);
		SelectAutoCommand cmd = new SelectAutoCommand(directoryDataSource,
				handler, getDirectoryAttributeHandlerFactory(), methodArgs);
		// フィルタの準備をします。
		String filter = createAutoSelectFilter();
		String query = directoryDaoAnnotationReader.getQuery(method.getName());
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
		commands.put(method.getName(), cmd);
	}

	protected String createAutoSelectFilter() {
		// オブジェクトクラスを設定します。
		String[] objectClasses = directoryDaoAnnotationReader
				.getObjectClasses(directoryBeanMetaData.getObjectClasses());
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
	 * @param method メソッド
	 * @return 処理ハンドラ
	 */
	private NamingEnumerationHandler createNamingEnumerationHandler(
			Method method) {
		if (List.class.isAssignableFrom(method.getReturnType())) {
			// List型ハンドラ
			return new BeanListMetaDataNamingEnumerationHandler(
					directoryBeanMetaData, directoryDataSource
							.getDirectoryControlProperty());
		} else if (isBeanClassAssignable(method.getReturnType())) {
			// Bean型ハンドラ
			return new BeanMetaDataNamingEnumerationHandler(
					directoryBeanMetaData, directoryDataSource
							.getDirectoryControlProperty());
		} else {
			return new ObjectNamingEnumerationHandler(directoryBeanMetaData,
					directoryDataSource.getDirectoryControlProperty());
		}
	}

	/**
	 * 割り当て可能なクラスがあるかどうか判定します。
	 * 
	 * @param clazz クラス
	 * @return 割り当て可能なクラスがある場合 true
	 */
	protected boolean isBeanClassAssignable(Class clazz) {
		return beanClass.isAssignableFrom(clazz)
				|| clazz.isAssignableFrom(beanClass);
	}

	protected boolean isSelect(Method method) {
		if (isInsert(method.getName())) {
			return false;
		}
		if (isUpdate(method.getName())) {
			return false;
		}
		if (isDelete(method.getName())) {
			return false;
		}
		return true;
	}

	protected boolean isInsert(String methodName) {
		for (int i = 0; i < insertPrefixes.length; ++i) {
			if (methodName.startsWith(insertPrefixes[i])) {
				return true;
			}
		}
		return false;
	}

	protected boolean isUpdate(String methodName) {
		for (int i = 0; i < updatePrefixes.length; ++i) {
			if (methodName.startsWith(updatePrefixes[i])) {
				return true;
			}
		}
		return false;
	}

	protected boolean isDelete(String methodName) {
		for (int i = 0; i < deletePrefixes.length; ++i) {
			if (methodName.startsWith(deletePrefixes[i])) {
				return true;
			}
		}
		return false;
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
	 * @param beanClass ビーンクラス
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
	 * @param directoryBeanMetaData ビーンメタデータ
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
		DirectoryCommand cmd = (DirectoryCommand)commands.get(methodName);
		if (cmd == null) {
			throw new MethodNotFoundRuntimeException(daoClass, methodName, null);
		}
		return cmd;
	}

	/**
	 * 指定された関数名に対応するディレクトリコマンドが存在するか調べます。
	 * 
	 * @param methodName 関数名
	 * @return 存在する場合は true、存在しない場合は false を返します。
	 * @see org.seasar.directory.dao.DirectoryDaoMetaData#hasDirectoryCommand(java.lang.String)
	 */
	public boolean hasDirectoryCommand(String methodName) {
		return commands.containsKey(methodName);
	}

	/**
	 * アノテーションリーダーファクトリを取得します。
	 */
	public DirectoryAnnotationReaderFactory getDirectoryAnnotationReaderFactory() {
		return directoryAnnotationReaderFactory;
	}

	/**
	 * アノテーションリーダーファクトリを設定します。
	 * 
	 * @param directoryAnnotationReaderFactory アノテーションリーダーファクトリ
	 */
	public void setDirectoryAnnotationReaderFactory(
			DirectoryAnnotationReaderFactory directoryAnnotationReaderFactory) {
		this.directoryAnnotationReaderFactory = directoryAnnotationReaderFactory;
	}

	/**
	 * ディレクトリ用の値の型ファクトリを取得します。
	 * 
	 * @return ディレクトリ用の値の型ファクトリ
	 */
	public DirectoryAttributeHandlerFactory getDirectoryAttributeHandlerFactory() {
		return directoryAttributeHandlerFactory;
	}

	/**
	 * ディレクトリ用の値の型ファクトリを設定します。
	 * 
	 * @param directoryAttributeHandlerFactory ディレクトリ用の値の型ファクトリ
	 */
	public void setDirectoryAttributeHandlerFactory(
			DirectoryAttributeHandlerFactory directoryAttributeHandlerFactory) {
		this.directoryAttributeHandlerFactory = directoryAttributeHandlerFactory;
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
	 * 更新操作関数用の接頭辞を設定します。
	 * 
	 * @param updatePrefixes 更新操作関数用の接頭辞
	 */
	public void setUpdatePrefixes(String[] updatePrefixes) {
		this.updatePrefixes = updatePrefixes;
	}

	/**
	 * 削除操作関数用の接頭辞を設定します。
	 * 
	 * @param deletePrefixes 削除操作関数用の接頭辞
	 */
	public void setDeletePrefixes(String[] deletePrefixes) {
		this.deletePrefixes = deletePrefixes;
	}

	/**
	 * データソースを設定します。
	 * 
	 * @param directoryDataSource データソース
	 */
	public void setDirectoryDataSource(DirectoryDataSource directoryDataSource) {
		this.directoryDataSource = directoryDataSource;
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
	 * @param daoClass Daoクラス
	 */
	public void setDaoClass(Class daoClass) {
		this.daoClass = daoClass;
	}
}
