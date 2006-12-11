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
	public Object toAttributeValue(DirectoryControlProperty property, Object value) {
		return null;
	}
}
