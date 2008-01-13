/*
 * Copyright 2005-2008 the Seasar Foundation and the Others.
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
package org.seasar.directory.attribute;

import org.seasar.directory.DirectoryControlProperty;

/**
 * 属性dnのためのハンドラクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DistinguishedNameAttributeHandler extends AbstractAttributeHandler {
	/**
	 * {@inheritDoc}
	 * <p>
	 * 文字列で比較します。
	 * </p>
	 */
	public boolean equalAttributeValue(DirectoryControlProperty property,
			Object currentValue, Object newValue) {
		String currentStringValue = String.valueOf(currentValue);
		String newStringValue = String.valueOf(newValue);
		return currentStringValue.equals(newStringValue);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 常にnullを返します。
	 * </p>
	 */
	public Object toAttributeValue(DirectoryControlProperty property,
			Object value) {
		return null;
	}
}
