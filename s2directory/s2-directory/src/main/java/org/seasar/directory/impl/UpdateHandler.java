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
package org.seasar.directory.impl;

import java.util.ArrayList;
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
import org.seasar.directory.types.ValueTypes;
import org.seasar.directory.util.DirectoryDataSourceUtils;
import org.seasar.directory.util.DirectoryUtils;
import org.seasar.framework.exception.NamingRuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.CaseInsensitiveSet;

/**
 * 更新を処理するクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class UpdateHandler extends BasicHandler implements ExecuteHandler {
	/** ロガーを表わします。 */
	private static Logger logger = Logger.getLogger(BasicSelectHandler.class);
	/** 引数をコマンドとみなしたコンテキストを表します。 */
	private CommandContext cmd;
	/** 暗号化アルゴリズムを表します。 */
	private String algorithm;

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
	 * エントリを作成します。
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
			String newValue = String.valueOf(cmd.getArg(attributeName));
			if (attributeName.equals("dn"))
				continue;
			// 通常属性
			if (attributeNameSet.contains(attributeName)) {
				Attribute attribute = result.getAttributes().get(attributeName);
				String alreadyValue = String.valueOf(ValueTypes.STRING
						.getValue(result.getAttributes(), attributeName));
				if (newValue.equals("")) {
					// 削除
					itemList.add(new ModificationItem(
							DirContext.REMOVE_ATTRIBUTE, attribute));
				} else if (!newValue.equals(alreadyValue)) {
					// 更新
					int size = attribute.size();
					if (size == 1) {
						// ユーザパスワード更新
						if (attributeName.equals("userpassword")) {
							if (!DirectoryUtils.verifyPassword(alreadyValue,
									newValue)) {
								attribute.set(0, DirectoryUtils.createPassword(
										newValue, algorithm));
							}
						} else {
							// 単一値
							attribute.set(0, newValue);
						}
					} else {
						// TODO: ツリー構造になっている場合への対応
					}
					itemList.add(new ModificationItem(
							DirContext.REPLACE_ATTRIBUTE, attribute));
				}
			} else {
				if (attributeName.equals("userpassword"))
					newValue = DirectoryUtils
							.createPassword(newValue, algorithm);
				if (!newValue.equals("")) {
					// 追加
					Attribute attribute = new BasicAttribute(attributeName,
							newValue);
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
		DirContext context = null;
		try {
			// 更新対象を検索
			String firstDn = DirectoryUtils.getFirstDn(dn);
			String baseDn = DirectoryUtils.getBaseDn(dn);
			NamingEnumeration results = super.search(firstDn, baseDn);
			context = getConnection();
			String fullDn = firstDn + "," + baseDn;
			return super.update(fullDn, createModificationItems(results));
		} catch (NamingException e) {
			throw new DirectoryRuntimeException(e);
		} finally {
			DirectoryDataSourceUtils.close(context);
		}
	}

	/**
	 * 処理を実行します。
	 * 
	 * @return
	 * @throws NamingRuntimeException
	 * @see org.seasar.directory.impl.ExecuteHandler#execute()
	 */
	public Object execute() throws NamingRuntimeException {
		if (logger.isDebugEnabled()) {
			logger.debug("Update: " + cmd.getDn());
		}
		algorithm = super.getDirectoryDataSource()
				.getDirectoryControlProperty().getPasswordType();
		return update(cmd.getDn());
	}
}
