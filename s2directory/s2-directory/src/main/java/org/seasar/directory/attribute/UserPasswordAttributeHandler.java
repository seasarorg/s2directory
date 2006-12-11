package org.seasar.directory.attribute;

import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.util.DirectoryUtils;

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
		boolean isHashEquals = DirectoryUtils.verifyPassword(
				currentStringValue, newStringValue);
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
		return DirectoryUtils.createPassword(stringValue, property
				.getPasswordAlgorithm());
	}
}
