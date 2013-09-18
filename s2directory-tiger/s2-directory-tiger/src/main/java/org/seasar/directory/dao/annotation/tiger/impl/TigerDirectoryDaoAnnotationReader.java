/*
 * Copyright 2005-2013 the Seasar Foundation and the Others.
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
package org.seasar.directory.dao.annotation.tiger.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.seasar.directory.dao.annotation.tiger.Arguments;
import org.seasar.directory.dao.annotation.tiger.Filter;
import org.seasar.directory.dao.annotation.tiger.ObjectClasses;
import org.seasar.directory.dao.annotation.tiger.Query;
import org.seasar.directory.dao.annotation.tiger.S2Directory;
import org.seasar.directory.dao.impl.DirectoryFieldDaoAnnotationReader;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.exception.ClassNotFoundRuntimeException;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ConstructorUtil;

/**
 * Daoクラスに定義されたTigerアノテーションを読み込みます。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class TigerDirectoryDaoAnnotationReader extends
		DirectoryFieldDaoAnnotationReader {

	/** S2DaoのDaoAnnotationReaderクラス名 */
	private static final String S2DAO_TIGER_DAO_ANNOTATION_READER =
		"org.seasar.dao.annotation.tiger.impl.DaoAnnotationReader";

	/** S2DaoのDaoAnnotationReaderインスタンス */
	private Object S2DaoDaoAnnotationReader;

	/** S2DaoのDaoAnnotationReaderのビーン定義 */
	private BeanDesc S2DaoDaoAnnotationReaderBeanDesc;

	/** S2Dao Tigerが存在するかどうか */
	private boolean hasS2DaoTiger = false;

	/** Daoクラス */
	protected Class<?> daoClass;

	/**
	 * 指定されたクラスを持つインスタンスを作成します。
	 * 
	 * @param daoClass
	 *            Daoクラス
	 */
	public TigerDirectoryDaoAnnotationReader(final Class<?> daoClass) {
		super(BeanDescFactory.getBeanDesc(notNull(daoClass)));
		this.daoClass = daoClass;
		try {
			// S2DaoTigerがある場合、そのDaoアノテーションリーダのインスタンスを作成します。
			Class<?> clazz =
				ClassUtil.forName(S2DAO_TIGER_DAO_ANNOTATION_READER);
			Constructor<?> constructor =
				ClassUtil.getConstructor(clazz, new Class[] { Class.class });
			S2DaoDaoAnnotationReader =
				ConstructorUtil.newInstance(
					constructor,
					new Class[] { daoClass });
			S2DaoDaoAnnotationReaderBeanDesc =
				BeanDescFactory.getBeanDesc(clazz);
			hasS2DaoTiger = true;
		} catch (ClassNotFoundRuntimeException ignore) {
			// do nothing
		}
	}

	/**
	 * 指定されたDaoクラスのメタ情報からインスタンスを作成します。
	 * 
	 * @param daoBeanDesc
	 *            Daoクラスのメタ情報
	 */
	public TigerDirectoryDaoAnnotationReader(final BeanDesc daoBeanDesc) {
		super(daoBeanDesc);
		this.daoClass = daoBeanDesc.getBeanClass();
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getArgNames(Method method) {
		Arguments args = method.getAnnotation(Arguments.class);
		if (args != null) {
			return args.value();
		}
		// S2Dao互換用
		if (hasS2DaoTiger) {
			Object value =
				getS2DaoDaoAnnotationValue(
					"getArgNames",
					new Object[] { method });
			if (value != null) {
				return (String[])value;
			}
		}
		return super.getArgNames(method);
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getBeanClass() {
		S2Directory s2directory = daoClass.getAnnotation(S2Directory.class);
		if (s2directory != null) {
			return s2directory.bean();
		}
		// S2Dao互換用
		if (hasS2DaoTiger) {
			Object value = getS2DaoDaoAnnotationValue("getBeanClass", null);
			if (value != null) {
				return (Class<?>)value;
			}
		}
		return super.getBeanClass();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getQuery(Method method) {
		Query query = method.getAnnotation(Query.class);
		if (query == null) {
			return super.getQuery(method);
		}
		return query.value();
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getObjectClasses(String[] beanObjectClasses) {
		ObjectClasses objectClasses =
			daoClass.getAnnotation(ObjectClasses.class);
		if (objectClasses == null) {
			return super.getObjectClasses(beanObjectClasses);
		}
		return objectClasses.value();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getFilter(Method method) {
		Filter filter = method.getAnnotation(Filter.class);
		if (filter == null) {
			return super.getFilter(method);
		}
		return filter.value();
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getNoPersistentProps(Method method) {
		return super.getNoPersistentProps(method);
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getPersistentProps(Method method) {
		return super.getPersistentProps(method);
	}

	/**
	 * Nullチェック制約
	 * 
	 * @param <T>
	 * @param t
	 * @return
	 */
	private static <T> T notNull(T t) {
		if (t == null) {
			throw new NullPointerException("target is null");
		}
		return t;
	}

	/**
	 * S2DaoTigerのDaoアノテーションリーダにある指定された関数を実行します。
	 * 
	 * @param methodName
	 *            関数名
	 * @param args
	 *            引数
	 * @return 実行結果
	 */
	protected Object getS2DaoDaoAnnotationValue(String methodName, Object[] args) {
		return S2DaoDaoAnnotationReaderBeanDesc.invoke(
			S2DaoDaoAnnotationReader,
			methodName,
			args);
	}

}
