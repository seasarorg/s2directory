package org.seasar.directory.attribute;

import org.seasar.directory.DirectoryControlProperty;

/**
 * 通常属性のためのハンドラクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DefaultAttributeHandler extends AbstractAttributeHandler {
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
	 */
	public Object toAttributeValue(DirectoryControlProperty property, Object value) {
		return value;
	}
}
