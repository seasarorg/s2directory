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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

import org.seasar.directory.dao.annotation.tiger.Attribute;
import org.seasar.directory.dao.annotation.tiger.Column;
import org.seasar.directory.dao.annotation.tiger.ObjectClasses;
import org.seasar.directory.dao.annotation.tiger.ValueType;
import org.seasar.directory.dao.impl.DirectoryFieldBeanAnnotationReader;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.exception.ClassNotFoundRuntimeException;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ConstructorUtil;

/**
 * ビーンクラスに定義されたTigerアノテーションを読み込みます。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class TigerDirectoryBeanAnnotationReader extends
		DirectoryFieldBeanAnnotationReader {

	/** S2DaoのDaoAnnotationReaderクラス名 */
	private static final String S2DAO_TIGER_BEAN_ANNOTATION_READER =
		"org.seasar.dao.annotation.tiger.impl.BeanAnnotationReaderImpl";

	/** S2DaoのDaoAnnotationReaderインスタンス */
	private Object S2DaoBeanAnnotationReader;

	/** S2DaoのDaoAnnotationReaderのビーン定義 */
	private BeanDesc S2DaoBeanAnnotationReaderBeanDesc;

	/** S2Dao Tigerが存在するかどうか */
	private boolean hasS2DaoTiger = false;

	/** ビーンクラス */
	protected Class<?> beanClass;

	/** ビーンクラスのメタ情報 */
	protected BeanDesc beanDesc;

	/**
	 * 指定されたビーンクラスからインスタンスを作成します。
	 * 
	 * @param beanClass
	 *            ビーンクラス
	 */
	public TigerDirectoryBeanAnnotationReader(Class<?> beanClass) {
		super(beanClass);
		this.beanClass = beanClass;
		this.beanDesc = BeanDescFactory.getBeanDesc(beanClass);
		try {
			// S2DaoTigerがある場合、そのビーンアノテーションリーダのインスタンスを作成します。
			Class<?> clazz =
				ClassUtil.forName(S2DAO_TIGER_BEAN_ANNOTATION_READER);
			Constructor<?> constructor =
				ClassUtil.getConstructor(clazz, new Class[] { Class.class });
			S2DaoBeanAnnotationReader =
				ConstructorUtil.newInstance(
					constructor,
					new Class[] { beanClass });
			S2DaoBeanAnnotationReaderBeanDesc =
				BeanDescFactory.getBeanDesc(clazz);
			hasS2DaoTiger = true;
		} catch (ClassNotFoundRuntimeException ignore) {
			// do nothing
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getBeanClass() {
		return super.getBeanClass();
	}

	/**
	 * 指定されたフィールドにある指定されたアノテーション情報を取得します。
	 * 
	 * @param <T>
	 * @param clazz
	 *            クラス
	 * @param pd
	 *            フィールド情報
	 * @return アノテーション情報
	 */
	private <T extends Annotation> T getPropertyAnnotation(Class<T> clazz,
			PropertyDesc pd) {
		BeanDesc bd = BeanDescFactory.getBeanDesc(beanClass);
		if (bd.hasField(pd.getPropertyName())) {
			T fieldAnnotation =
				bd.getField(pd.getPropertyName()).getAnnotation(clazz);
			if (fieldAnnotation != null) {
				return fieldAnnotation;
			}
		}

		if (pd.getWriteMethod() != null) {
			T annotation = pd.getWriteMethod().getAnnotation(clazz);
			if (annotation != null) {
				return annotation;
			}
		}
		if (pd.getReadMethod() != null) {
			return pd.getReadMethod().getAnnotation(clazz);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getColumnAnnotation(PropertyDesc pd) {
		Attribute attribute = getPropertyAnnotation(Attribute.class, pd);
		Column column = getPropertyAnnotation(Column.class, pd);
		// ATTRIBUTEアノテーションがある場合、COLUMNアノテーションに優先して利用します。
		if (attribute != null) {
			return attribute.value();
		}
		// ATTRIBUTEアノテーションがなく、COLUMNアノテーションがある場合は利用します。
		if (column != null) {
			return column.value();
		}
		// S2Dao互換用
		if (hasS2DaoTiger) {
			Object value =
				getS2DaoBeanAnnotationValue(
					"getColumnAnnotation",
					new Object[] { pd });
			if (value != null) {
				return (String)value;
			}
		}
		// フィールドアノテーションがあればそれを利用します。
		return super.getColumnAnnotation(pd);
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getNoPersistentProps() {
		return super.getNoPersistentProps();
	}

	/**
	 * {@inheritDoc}
	 */
	public String[] getObjectClasses() {
		ObjectClasses objectClasses =
			beanClass.getAnnotation(ObjectClasses.class);
		if (objectClasses == null) {
			return super.getObjectClasses();
		}
		return objectClasses.value();
	}

	/**
	 * {@inheritDoc}
	 */
	public String getValueType(PropertyDesc pd) {
		ValueType valueType = getPropertyAnnotation(ValueType.class, pd);
		if (valueType == null) {
			return super.getValueType(pd);
		}
		return valueType.value();
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
	protected Object getS2DaoBeanAnnotationValue(String methodName,
			Object[] args) {
		return S2DaoBeanAnnotationReaderBeanDesc.invoke(
			S2DaoBeanAnnotationReader,
			methodName,
			args);
	}

}
