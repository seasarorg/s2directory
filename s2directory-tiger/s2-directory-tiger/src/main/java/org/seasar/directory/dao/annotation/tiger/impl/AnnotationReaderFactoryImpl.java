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
package org.seasar.directory.dao.annotation.tiger.impl;

import org.seasar.directory.dao.DirectoryAnnotationReaderFactory;
import org.seasar.directory.dao.DirectoryBeanAnnotationReader;
import org.seasar.directory.dao.DirectoryDaoAnnotationReader;
import org.seasar.framework.beans.BeanDesc;

/**
 * Tigerアノテーションを利用したディレクトリアノテーションリーダファクトリです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class AnnotationReaderFactoryImpl implements
		DirectoryAnnotationReaderFactory {

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public DirectoryBeanAnnotationReader createDirectoryBeanAnnotationReader(
			Class beanClass) {
		if (beanClass == null) {
			throw new IllegalArgumentException("beanClass");
		}
		return new TigerDirectoryBeanAnnotationReader(beanClass);
	}

	/**
	 * {@inheritDoc}
	 */
	public DirectoryDaoAnnotationReader createDirectoryDaoAnnotationReader(
			BeanDesc daoBeanDesc) {
		if (daoBeanDesc == null) {
			throw new IllegalArgumentException("daoBeanDesc");
		}
		return new TigerDirectoryDaoAnnotationReader(daoBeanDesc);
	}

}
