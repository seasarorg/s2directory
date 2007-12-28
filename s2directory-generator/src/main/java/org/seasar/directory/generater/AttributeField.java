/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
package org.seasar.directory.generater;

/**
 * 属性とフィールドの対応クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date:: 2006-01-09 09:04:00 +0900#$
 */
/**
 * @author jun
 */
public class AttributeField {
	private String attributeName;
	private String fieldName;
	private boolean needAnnotation = false;

	public AttributeField(String attributeName, String fieldName) {
		this.fieldName = fieldName;
		this.attributeName = attributeName;
		if (!fieldName.equals(attributeName)) {
			needAnnotation = true;
		}
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public boolean isNeedAnnotation() {
		return needAnnotation;
	}

	public String toString() {
		return "attributeName: " + getAttributeName() + ", filedName: "
			+ getFieldName() + ", needAnnotation: " + needAnnotation;
	}
}