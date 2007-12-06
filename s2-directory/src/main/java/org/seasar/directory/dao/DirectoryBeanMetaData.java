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
package org.seasar.directory.dao;

import org.seasar.directory.types.PropertyType;
import org.seasar.extension.jdbc.ColumnNotFoundRuntimeException;
import org.seasar.framework.beans.PropertyNotFoundRuntimeException;

/**
 * ディレクトリ用のビーンメタデータを表すクラスです。
 * 
 * @author Jun Futagawa
 * @version $Revision$ $Date$
 */
public interface DirectoryBeanMetaData extends DirectoryDtoMetaData {
	public String RELNO_SUFFIX = "_RELNO";
	public String RELKEYS_SUFFIX = "_RELKEYS";
	public String ID_SUFFIX = "_ID";
	public String NO_PERSISTENT_PROPS = "NO_PERSISTENT_PROPS";
	public String VERSION_NO_PROPERTY = "VERSION_NO_PROPERTY";
	public String TIMESTAMP_PROPERTY = "TIMESTAMP_PROPERTY";

	public String[] getObjectClasses();

	public PropertyType getVersionNoPropertyType()
			throws PropertyNotFoundRuntimeException;

	public String convertFullColumnName(String alias);

	public PropertyType getPropertyTypeByColumnName(String columnName)
			throws ColumnNotFoundRuntimeException;

	public boolean hasPropertyTypeByColumnName(String columnName);

	/**
	 * 割り当て可能なクラスがあるかどうか判定します。
	 * 
	 * @param clazz
	 *            クラス
	 * @return 割り当て可能なクラスがある場合 true
	 */
	public boolean isBeanClassAssignable(Class clazz);
}
