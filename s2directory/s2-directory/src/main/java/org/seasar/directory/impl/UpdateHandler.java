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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchResult;
import org.seasar.directory.CommandContext;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.exception.DirectoryRuntimeException;
import org.seasar.directory.types.ValueType;
import org.seasar.directory.types.ValueTypes;
import org.seasar.directory.util.DirectoryUtils;
import org.seasar.framework.exception.NamingRuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.CaseInsensitiveSet;
import org.seasar.framework.util.StringUtil;

/**
 * 更新を処理するクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class UpdateHandler extends BasicDirectoryHandler implements
		ExecuteHandler {
	/** ロガーを表わします。 */
	private static Logger logger = Logger.getLogger(SelectHandler.class);
	/** 引数をコマンドとみなしたコンテキストを表します。 */
	private CommandContext cmd;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param directoryDataSource
	 * @param dn
	 */
	public UpdateHandler(DirectoryDataSource directoryDataSource,
			CommandContext cmd) {
		super(directoryDataSource);
		this.cmd = cmd;
	}

	/**
	 * 更新アイテムを作成します。
	 * 
	 * @param result
	 * @param attributeNameSet
	 * @return
	 * @throws NamingException
	 */
	protected ModificationItem[] createModificationItems(SearchResult result,
			Set attributeNameSet) throws NamingException {
		List itemList = new ArrayList();
		Set keySet = cmd.getArgKeySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String attributeName = String.valueOf(iter.next());
			Object value = cmd.getArg(attributeName);
			String stringValue = String.valueOf(cmd.getArg(attributeName));
			if (attributeName.equals("dn"))
				continue;
			if (attributeNameSet.contains(attributeName)) {
				// 既に属性がある場合、現在の属性を取得し、値を更新します。
				Attribute attribute = result.getAttributes().get(attributeName);
				String alreadyValue = String.valueOf(ValueTypes.STRING
						.getReadValue(result.getAttributes(), attributeName,
								directoryControlProperty
										.getMultipleValueDelimiter()));
				if (value == null || StringUtil.isEmpty(stringValue)) {
					// 値が空の場合、削除します。
					itemList.add(new ModificationItem(
							DirContext.REMOVE_ATTRIBUTE, attribute));
				} else if (!stringValue.equals(alreadyValue)) {
					// 値があり、現在の値と異なる場合、更新します。
					if (attributeName.equals("userpassword")) {
						if (!DirectoryUtils.verifyPassword(alreadyValue,
								stringValue)) {
							// ユーザパスワード属性の場合、暗号化します。
							value = DirectoryUtils.createPassword(stringValue,
									directoryControlProperty
											.getPasswordAlgorithm());
						} else {
							continue;
						}
					}
					ValueType type = ValueTypes.getValueType(cmd
							.getArgType(attributeName));
					if (type == ValueTypes.STRING
							&& stringValue.contains(directoryControlProperty
									.getMultipleValueDelimiter())) {
						// String型で定義されていて、多重属性を持つ場合List型に変換します。
						value = Arrays.asList(stringValue
								.split(directoryControlProperty
										.getMultipleValueDelimiter()));
						type = ValueTypes.LIST;
					}
					attribute = type.getWriteValue(attributeName, value,
							directoryControlProperty
									.getMultipleValueDelimiter());
					itemList.add(new ModificationItem(
							DirContext.REPLACE_ATTRIBUTE, attribute));
				}
			} else {
				// 新規に属性を追加する場合、新しい属性を追加します。
				if (attributeName.equals("userpassword"))
					stringValue = DirectoryUtils.createPassword(stringValue,
							directoryControlProperty.getPasswordAlgorithm());
				if (value != null && !StringUtil.isEmpty(stringValue)) {
					// 値がある場合、追加します。
					Attribute attribute = new BasicAttribute(attributeName,
							stringValue);
					itemList.add(new ModificationItem(DirContext.ADD_ATTRIBUTE,
							attribute));
				}
			}
		}
		return (ModificationItem[])itemList.toArray(new ModificationItem[0]);
	}

	/**
	 * 属性名の集合を作成します。
	 * 
	 * @param result
	 * @return
	 * @throws NamingException
	 */
	private Set createAttributesNames(SearchResult result)
			throws NamingException {
		Set columnNames = new CaseInsensitiveSet();
		Attributes attributes = result.getAttributes();
		NamingEnumeration ae = attributes.getAll();
		while (ae.hasMoreElements()) {
			Attribute attribute = (Attribute)ae.next();
			String attributeName = attribute.getID();
			columnNames.add(attributeName);
		}
		return columnNames;
	}

	/**
	 * 更新アイテムを作成します。
	 * 
	 * @param results
	 * @return
	 * @throws NamingException
	 */
	public ModificationItem[] createModificationItems(NamingEnumeration results)
			throws NamingException {
		if (results != null && results.hasMore()) {
			SearchResult result = (SearchResult)results.next();
			Set attributeNameSet = createAttributesNames(result);
			return createModificationItems(result, attributeNameSet);
		}
		return null;
	}

	/**
	 * 指定されたエントリを更新します。
	 * 
	 * @return 更新した数を返します。
	 */
	private Integer update(String dn) {
		try {
			// 更新対象を検索
			String firstDn = DirectoryUtils.getFirstDn(dn);
			String baseDn = DirectoryUtils.getBaseDn(dn);
			NamingEnumeration results = super.search(firstDn, baseDn);
			String fullDn = firstDn + "," + baseDn;
			ModificationItem[] items = createModificationItems(results);
			if (logger.isDebugEnabled()) {
				logger.debug("Update: " + fullDn);
				for (int i = 0; i < items.length; i++) {
					logger.debug("\t" + items[i]);
				}
			}
			return super.update(fullDn, items);
		} catch (NamingException e) {
			throw new DirectoryRuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 更新処理を実行します。
	 * </p>
	 */
	public Object execute() throws NamingRuntimeException {
		return update(cmd.getDn());
	}
}
