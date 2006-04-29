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
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.NamingEnumerationHandler;
import org.seasar.directory.dao.AnnotationMethodArgs;
import org.seasar.directory.dao.AnnotationMethodArgsFactory;
import org.seasar.directory.dao.DirecotryDaoMetaData;
import org.seasar.directory.dao.DirectoryBeanMetaData;
import org.seasar.directory.dao.DirectoryCommand;
import org.seasar.directory.dao.DirectoryDaoAnnotationReader;
import org.seasar.directory.impl.AuthenticateAutoCommand;
import org.seasar.directory.impl.DeleteAutoCommand;
import org.seasar.directory.impl.InsertAutoCommand;
import org.seasar.directory.impl.SelectAutoCommand;
import org.seasar.directory.impl.UpdateAutoCommand;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.MethodNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.MethodUtil;
import org.seasar.framework.util.StringUtil;

/**
 * DirectoryDao用のメタ情報を表わすクラスです。 TODO: 関数、コメント整理
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryDaoMetaDataImpl implements DirecotryDaoMetaData {
	/** 認証関数用の接頭辞を表わします。 */
	private static final String[] AUTHENTICATE_NAMES = new String[] { "auth" };
	/** 新規挿入操作関数用の接頭辞を表わします。 */
	private static final String[] INSERT_NAMES = new String[] { "insert",
			"create", "add" };
	/** 更新操作関数用の接頭辞を表わします。 */
	private static final String[] UPDATE_NAMES = new String[] { "update",
			"modify", "store" };
	/** 削除操作関数用の接頭辞を表わします。 */
	private static final String[] DELETE_NAMES = new String[] { "delete",
			"remove" };
	/** Daoクラスを表わします。 */
	private Class daoClass;
	/** データソースを表わします。 */
	private DirectoryDataSource dataSource;
	/** アノテーションリーダーを表します。 */
	protected DirectoryDaoAnnotationReader annotationReader;
	/** メタ情報を表わします。 */
	private BeanDesc daoBeanDesc;
	/** コンポーネントを表わします。 */
	private Class beanClass;
	private DirectoryBeanMetaData directoryBeanMetaData;
	/** コマンドのキャッシュを表わします。 */
	private Map commands = new HashMap();

	/**
	 * 指定されたDirectoryDaoクラスとDirectoryデータソースを保持したインスタンスを作成します。
	 * 
	 * @param DirectoryDaoClass DirectoryDaoクラス
	 * @param DirectoryDataSource Directoryデータソース
	 */
	public DirectoryDaoMetaDataImpl(Class daoClass,
			DirectoryDataSource dataSource) {
		this.daoClass = daoClass;
		this.dataSource = dataSource;
		// beanClassを作成します。
		daoBeanDesc = BeanDescFactory.getBeanDesc(daoClass);
		annotationReader = new DirectoryFieldAnnotationReader(daoBeanDesc);
		beanClass = annotationReader.getBeanClass();
		directoryBeanMetaData = new DirectoryBeanMetaDataImpl(beanClass);
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
		String filter = annotationReader.getFilter(method.getName());
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
		SelectAutoCommand cmd = new SelectAutoCommand(dataSource,
				createNamingEnumerationHandler(method), null);
		cmd.setFilter(filter);
		commands.put(method.getName(), cmd);
	}

	/**
	 * 関数名より動作を自動決定し、実行します。
	 * 
	 * @param method
	 */
	private void setupMethodByAuto(Method method) {
		if (isStartsWithMethodName(method.getName(), AUTHENTICATE_NAMES)) {
			setupAuthenticateMethodByAuto(method);
		} else if (isStartsWithMethodName(method.getName(), INSERT_NAMES)) {
			setupInsertMethodByAuto(method);
		} else if (isStartsWithMethodName(method.getName(), UPDATE_NAMES)) {
			setupUpdateMethodByAuto(method);
		} else if (isStartsWithMethodName(method.getName(), DELETE_NAMES)) {
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
				method, annotationReader);
		AuthenticateAutoCommand cmd = new AuthenticateAutoCommand(dataSource,
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
				method, annotationReader);
		InsertAutoCommand cmd = new InsertAutoCommand(dataSource, methodArgs);
		// objectClassを設定します。
		cmd.setObjectClass(annotationReader.getObjectClasses());
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
				method, annotationReader);
		DirectoryCommand cmd = new UpdateAutoCommand(dataSource, methodArgs);
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
				method, annotationReader);
		DirectoryCommand cmd = new DeleteAutoCommand(dataSource, methodArgs);
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
				method, annotationReader);
		// コマンドを作成します。
		NamingEnumerationHandler handler = createNamingEnumerationHandler(method);
		SelectAutoCommand cmd = new SelectAutoCommand(dataSource, handler,
				methodArgs);
		// フィルタの準備をします。
		String filter = createAutoSelectFilter();
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
		commands.put(method.getName(), cmd);
	}

	protected String createAutoSelectFilter() {
		String[] objectClass = annotationReader.getObjectClasses();
		StringBuffer buffer = new StringBuffer();
		// TODO: 複数オブジェクトクラスの取り扱いについて決める
		if (false) {
			buffer.append("(&");
			for (int i = 0; i < objectClass.length; i++) {
				buffer.append("(objectclass=").append(objectClass[i]).append(
						")");
			}
			buffer.append(")");
		} else {
			buffer.append("objectclass=").append(objectClass[0]);
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
					directoryBeanMetaData);
		} else if (isBeanClassAssignable(method.getReturnType())) {
			// Bean型ハンドラ
			return new BeanMetaDataNamingEnumerationHandler(
					directoryBeanMetaData, dataSource
							.getDirectoryControlProperty());
		} else {
			return new ObjectNamingEnumerationHandler();
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
		for (int i = 0; i < INSERT_NAMES.length; ++i) {
			if (methodName.startsWith(INSERT_NAMES[i])) {
				return true;
			}
		}
		return false;
	}

	protected boolean isUpdate(String methodName) {
		for (int i = 0; i < UPDATE_NAMES.length; ++i) {
			if (methodName.startsWith(UPDATE_NAMES[i])) {
				return true;
			}
		}
		return false;
	}

	protected boolean isDelete(String methodName) {
		for (int i = 0; i < DELETE_NAMES.length; ++i) {
			if (methodName.startsWith(DELETE_NAMES[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 指定された関数名に対応するディレクトリコマンドが存在するか調べます。
	 * 
	 * @param methodName - 関数名
	 * @return 存在する場合は true、存在しない場合は false を返します。
	 * @see org.seasar.directory.dao.DirecotryDaoMetaData#hasDirectoryCommand(java.lang.String)
	 */
	public boolean hasDirectoryCommand(String methodName) {
		return commands.containsKey(methodName);
	}

	/**
	 * キャッシュされているディレクトリコマンドを取得します。
	 * 
	 * @param methodName - 関数名
	 * @return 存在する場合はディレクトリコマンド 、存在しない場合は例外を発生させます。
	 * @throws MethodNotFoundRuntimeException
	 * @see org.seasar.directory.dao.DirecotryDaoMetaData#getSqlCommand(java.lang.String)
	 */
	public DirectoryCommand getDirectoryCommand(String methodName)
			throws MethodNotFoundRuntimeException {
		DirectoryCommand cmd = (DirectoryCommand)commands.get(methodName);
		if (cmd == null) {
			throw new MethodNotFoundRuntimeException(daoClass, methodName, null);
		}
		return cmd;
	}
}
