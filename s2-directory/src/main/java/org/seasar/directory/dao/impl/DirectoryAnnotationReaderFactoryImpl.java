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

import org.seasar.directory.dao.DirectoryAnnotationReaderFactory;
import org.seasar.directory.dao.DirectoryBeanAnnotationReader;
import org.seasar.directory.dao.DirectoryDaoAnnotationReader;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.exception.ClassNotFoundRuntimeException;
import org.seasar.framework.util.ClassUtil;

/**
 * ディレクトリアノテーションリーダファクトリです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryAnnotationReaderFactoryImpl implements
		DirectoryAnnotationReaderFactory {
	// TODO: 未実装 TIGER_ANNOTATION_READER_FACTORY
	private static final String TIGER_ANNOTATION_READER_FACTORY =
		"org.seasar.directory.dao.annotation.tiger.impl.AnnotationReaderFactoryImpl";
	// TODO: 未実装 BACKPORT175_ANNOTATION_READER_FACTORY
	private static final String BACKPORT175_ANNOTATION_READER_FACTORY =
		"org.seasar.directory.dao.annotation.backport175.impl.AnnotationReaderFactoryImpl";
	private DirectoryAnnotationReaderFactory directoryAnnotationReaderFactory;

	public DirectoryAnnotationReaderFactoryImpl() {
		Class clazz = DirectoryFieldAnnotationReaderFactory.class;
		try {
			clazz = ClassUtil.forName(TIGER_ANNOTATION_READER_FACTORY);
		} catch (ClassNotFoundRuntimeException ignore1) {
			try {
				clazz =
					ClassUtil.forName(BACKPORT175_ANNOTATION_READER_FACTORY);
			} catch (ClassNotFoundRuntimeException ignore2) {
			}
		}
		directoryAnnotationReaderFactory =
			(DirectoryAnnotationReaderFactory)ClassUtil.newInstance(clazz);
	}

	public DirectoryBeanAnnotationReader createDirectoryBeanAnnotationReader(
			Class beanClass) {
		return directoryAnnotationReaderFactory
			.createDirectoryBeanAnnotationReader(beanClass);
	}

	public DirectoryDaoAnnotationReader createDirectoryDaoAnnotationReader(
			BeanDesc daoBeanDesc) {
		return directoryAnnotationReaderFactory
			.createDirectoryDaoAnnotationReader(daoBeanDesc);
	}
}
