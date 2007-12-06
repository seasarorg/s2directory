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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchResult;

import org.seasar.directory.CommandContext;
import org.seasar.directory.DirectoryAttributeHandlerFactory;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.attribute.AttributeHandler;
import org.seasar.directory.exception.DirectoryRuntimeException;
import org.seasar.directory.util.DirectoryUtil;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.exception.NamingRuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.CaseInsensitiveSet;

/**
 * 更新を処理するクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class UpdateHandler extends BasicDirectoryHandler implements
		ExecuteHandler {
	/** ロガー */
	private static Logger logger = Logger.getLogger(SelectHandler.class);
	/** 引数をコマンドとみなしたコンテキスト */
	private CommandContext ctx;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param directoryDataSource
	 * @param cmd
	 */
	public UpdateHandler(DirectoryDataSource directoryDataSource,
			CommandContext ctx) {
		super(directoryDataSource);
		this.ctx = ctx;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 更新処理を実行します。
	 * </p>
	 */
	public Object execute() throws NamingRuntimeException {
		return update(ctx.getDn());
	}

	/**
	 * 指定されたエントリを更新します。
	 * 
	 * @return 更新した数を返します。
	 */
	private Integer update(String dn) {
		try {
			// 更新対象を検索
			String firstDn = DirectoryUtil.getFirstDn(dn);
			String baseDn = DirectoryUtil.getBaseDn(dn);
			NamingEnumeration currentEntrys = super.search(firstDn, baseDn);
			String fullDn = firstDn + "," + baseDn;
			// 更新アイテムを作成
			ModificationItem[] items = createModificationItems(currentEntrys);
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
	 * エントリから更新アイテムを作成します。
	 * 
	 * @param results
	 *            エントリの検索結果
	 * @return 更新アイテムの配列
	 * @throws NamingException
	 */
	public ModificationItem[] createModificationItems(NamingEnumeration results)
			throws NamingException {
		if (results != null && results.hasMore()) {
			SearchResult entry = (SearchResult)results.next();
			return createModificationItems(entry);
		}
		return null;
	}

	/**
	 * 更新アイテムを作成します。
	 * 
	 * @param result
	 *            エントリの検索結果
	 * @param attributeNameSet
	 * @return 更新アイテムの配列
	 * @throws NamingException
	 */
	protected ModificationItem[] createModificationItems(SearchResult result)
			throws NamingException {
		// 更新アイテムのリスト
		List modificationItemList = new ArrayList();
		// エントリにある属性名を取得します。
		Set currentAttributeNameSet = createAttributesNames(result);
		// 属性を設定します。
		Set keySet = ctx.getArgKeySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String argName = String.valueOf(iter.next());
			Object argValue = ctx.getArg(argName);
			Class argClass = ctx.getArgType(argName);
			if (argName.equals("dto")) {
				BeanDesc beanDesc = BeanDescFactory.getBeanDesc(argClass);
				int size = beanDesc.getPropertyDescSize();
				for (int i = 0; i < size; i++) {
					PropertyDesc pd = beanDesc.getPropertyDesc(i);
					String propName = pd.getPropertyName();
					Attribute currentAttribute = null;
					if (currentAttributeNameSet.contains(propName)) {
						// 既に属性がある場合
						currentAttribute = result.getAttributes().get(propName);
					}
					ModificationItem modificationItem =
						createModificationItem(
							currentAttributeNameSet,
							currentAttribute,
							propName,
							pd.getValue(argValue),
							pd.getPropertyType());
					if (modificationItem != null) {
						modificationItemList.add(modificationItem);
					}
				}
			} else {
				Attribute currentAttribute = null;
				if (currentAttributeNameSet.contains(argName)) {
					// 既に属性がある場合
					currentAttribute = result.getAttributes().get(argName);
				}
				ModificationItem modificationItem =
					createModificationItem(
						currentAttributeNameSet,
						currentAttribute,
						argName,
						argValue,
						argClass);
				if (modificationItem != null) {
					modificationItemList.add(modificationItem);
				}
			}
		}
		return (ModificationItem[])modificationItemList
			.toArray(new ModificationItem[0]);
	}

	private ModificationItem createModificationItem(
			Set currentAttributeNameSet, Attribute attribute,
			String attributeName, Object value, Class valueClass)
			throws NamingException {
		DirectoryAttributeHandlerFactory directoryAttributeHandlerFactory =
			ctx.getDirectoryAttributeHandlerFactory();
		AttributeHandler attributeHandler =
			directoryAttributeHandlerFactory.getAttributeHandler(attributeName);
		ModificationItem modificationItem;
		modificationItem =
			attributeHandler.getModificationItem(
				directoryControlProperty,
				attribute,
				attributeName,
				value,
				valueClass);
		return modificationItem;
	}

	/**
	 * 指定されたエントリに含まれる属性名の集合を作成します。
	 * 
	 * @param entry
	 *            エントリ
	 * @return 属性名の集合
	 * @throws NamingException
	 */
	private Set createAttributesNames(SearchResult entry)
			throws NamingException {
		Set attributeNameSet = new CaseInsensitiveSet();
		Attributes attributes = entry.getAttributes();
		NamingEnumeration ae = attributes.getAll();
		while (ae.hasMoreElements()) {
			Attribute attribute = (Attribute)ae.next();
			String attributeName = attribute.getID();
			attributeNameSet.add(attributeName);
		}
		return attributeNameSet;
	}
}
