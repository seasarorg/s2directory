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
package org.seasar.directory.attribute;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.util.DirectoryUtil;

/**
 * 属性userPasswordのためのハンドラクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class UserPasswordAttributeHandler extends AbstractAttributeHandler {
	/**
	 * {@inheritDoc}
	 * <p>
	 * 暗号化した文字列で比較します。
	 * </p>
	 */
	public boolean equalAttributeValue(DirectoryControlProperty property,
			Object currentValue, Object newValue) {
		String currentStringValue = String.valueOf(currentValue);
		String newStringValue = String.valueOf(newValue);
		boolean isStringEquals = currentStringValue.equals(newStringValue);
		boolean isHashEquals =
			DirectoryUtil.verifyPassword(currentStringValue, newStringValue);
		return isStringEquals | isHashEquals;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 暗号化した文字列を返します。
	 * </p>
	 */
	public Object toAttributeValue(DirectoryControlProperty property,
			Object value) {
		if (value == null)
			return value;
		String stringValue = String.valueOf(value);
		return DirectoryUtil.createPassword(stringValue, property
			.getPasswordAlgorithm());
	}
}
