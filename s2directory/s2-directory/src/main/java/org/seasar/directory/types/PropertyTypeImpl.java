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
package org.seasar.directory.types;

import org.seasar.framework.beans.PropertyDesc;

/**
 * ディレクトリ用のプロパティタイプの実装クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class PropertyTypeImpl implements PropertyType {

	private PropertyDesc propertyDesc;

	private String propertyName;

	private String columnName;

	private ValueType valueType;

	private boolean persistent = true;

	public PropertyTypeImpl(PropertyDesc propertyDesc, ValueType valueType) {
		this(propertyDesc, valueType, propertyDesc.getPropertyName());
	}

	public PropertyTypeImpl(PropertyDesc propertyDesc, ValueType valueType,
			String columnName) {
		this.propertyDesc = propertyDesc;
		this.propertyName = propertyDesc.getPropertyName();
		this.valueType = valueType;
		this.columnName = columnName;
	}

	public PropertyTypeImpl(String propertyName, ValueType valueType) {
		this.propertyName = propertyName;
		this.valueType = valueType;
		this.columnName = propertyName;
	}

	public PropertyDesc getPropertyDesc() {
		return propertyDesc;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public ValueType getValueType() {
		return valueType;
	}

	public boolean isPersistent() {
		return persistent;
	}

	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}

}
