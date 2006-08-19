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
package org.seasar.directory.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import org.seasar.directory.CommandContext;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.dao.DirectoryValueTypeFactory;
import org.seasar.directory.exception.DirectoryRuntimeException;
import org.seasar.directory.types.ValueType;
import org.seasar.directory.util.DirectoryUtils;
import org.seasar.framework.exception.NamingRuntimeException;
import org.seasar.framework.log.Logger;

/**
 * 新規追加処理するクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class InsertHandler extends BasicDirectoryHandler implements
		ExecuteHandler {
	/** ロガーを表わします。 */
	private static Logger logger = Logger.getLogger(SelectHandler.class);
	/** 引数をコマンドとみなしたコンテキストを表します。 */
	private CommandContext cmd;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param directoryDataSource
	 * @param cmd
	 */
	public InsertHandler(DirectoryDataSource directoryDataSource,
			CommandContext cmd) {
		super(directoryDataSource);
		this.cmd = cmd;
	}

	/**
	 * エントリを作成します。
	 * 
	 * @param result
	 * @param attributeNameSet
	 * @return
	 * @throws NamingException
	 */
	protected Attributes createAttributes(String dn) throws NamingException {
		String firstDn = DirectoryUtils.getFirstDn(dn);
		String dnName = DirectoryUtils.getAttributeName(firstDn);
		String dnValue = DirectoryUtils.getAttributeValue(firstDn);
		Attributes entry = new BasicAttributes(dnName, dnValue);
		// オブジェクトクラスを設定します。
		Attribute objectClass = new BasicAttribute("objectClass");
		entry.put(objectClass);
		String[] objectClasses = cmd.getObjectClasses();
		for (int i = 0; i < objectClasses.length; i++) {
			objectClass.add(objectClasses[i]);
		}
		// 属性を設定します。
		DirectoryValueTypeFactory directoryValueTypeFactory = cmd
				.getDirectoryValueTypeFactory();
		ValueType stringValueType = directoryValueTypeFactory
				.getStringValueType();
		ValueType listValueType = directoryValueTypeFactory.getListValueType();
		Set keySet = cmd.getArgKeySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String attributeName = String.valueOf(iter.next());
			Object value = cmd.getArg(attributeName);
			if (value == null) {
				// 値が null の場合、無視します。
				continue;
			}
			if (attributeName.equals("dn")) {
				// dn は BasicAttribute で設定済みなので除外する必要します。
				continue;
			}
			String stringValue = String.valueOf(value);
			if (attributeName.equals("userpassword")) {
				// ユーザパスワード属性の場合、暗号化します。
				value = DirectoryUtils.createPassword(stringValue,
						directoryControlProperty.getPasswordAlgorithm());
			}
			ValueType type = directoryValueTypeFactory.getValueTypeByClass(cmd
					.getArgType(attributeName));
			if (type == stringValueType
					&& stringValue.contains(directoryControlProperty
							.getMultipleValueDelimiter())) {
				// String型で定義されていて、多重属性を持つ場合List型に変換します。
				value = Arrays.asList(stringValue
						.split(directoryControlProperty
								.getMultipleValueDelimiter()));
				type = listValueType;
			}
			entry.put(type.getWriteValue(attributeName, value,
					directoryControlProperty.getMultipleValueDelimiter()));
		}
		return entry;
	}

	/**
	 * 指定されたエントリを更新します。
	 * 
	 * @return 更新した数を返します。
	 */
	private Integer insert(String dn) {
		try {
			// 更新対象を検索
			String firstDn = DirectoryUtils.getFirstDn(dn);
			String baseDn = DirectoryUtils.getBaseDn(dn);
			NamingEnumeration results = super.search(firstDn, baseDn);
			if (results != null && results.hasMore()) {
				// TODO: Exception処理追加
				return new Integer(0);
			} else {
				String fullDn = firstDn + "," + baseDn;
				return super.insert(fullDn, createAttributes(dn));
			}
		} catch (NamingException e) {
			throw new DirectoryRuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 新規追加処理を実行します。
	 * </p>
	 */
	public Object execute() throws NamingRuntimeException {
		if (logger.isDebugEnabled()) {
			logger.debug("Insert: " + cmd.getDn());
			String[] objectClasses = cmd.getObjectClasses();
			for (int i = 0; i < objectClasses.length; i++) {
				logger.debug("\tobjectClass: " + objectClasses[i]);
			}
		}
		return insert(cmd.getDn());
	}
}