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
import org.seasar.directory.dao.DirectoryBeanMetaData;
import org.seasar.directory.exception.DirectoryRuntimeException;
import org.seasar.directory.types.PropertyType;
import org.seasar.directory.util.DirectoryDataSourceUtil;
import org.seasar.framework.beans.PropertyDesc;
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
	private DirectoryBeanMetaData beanMetaData;
	/** 引数をコマンドとみなしたコンテキスト */
	private CommandContext ctx;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param dataSource
	 * @param ctx
	 */
	public UpdateHandler(DirectoryDataSource dataSource,
			DirectoryBeanMetaData beanMetaData, CommandContext ctx) {
		super(dataSource);
		this.beanMetaData = beanMetaData;
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
	 * @param dn
	 *            更新対象のDN
	 * @return 更新した数を返します。
	 */
	private Integer update(String dn) {
		NamingEnumeration currentEntrys = null;
		try {
			// 更新対象を検索
			currentEntrys = super.searchOneLevel(dn);
			// 更新アイテムを作成
			ModificationItem[] items = createModificationItems(currentEntrys);
			if (logger.isDebugEnabled()) {
				logger.debug("Update: " + dn);
				if (items != null) {
					for (int i = 0; i < items.length; i++) {
						logger.debug("\t" + items[i]);
					}
				}
			}
			return super.update(dn, items);
		} catch (NamingException e) {
			throw new DirectoryRuntimeException(e);
		} finally {
			DirectoryDataSourceUtil.close(currentEntrys);
		}
	}

	/**
	 * 検索結果から更新アイテムを作成します。
	 * 
	 * @param results
	 *            現在のエントリの検索結果
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
	 * エントリから更新アイテムを作成します。
	 * 
	 * @param result
	 *            現在のエントリの検索結果
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
			if (argName.equals("#dto")) {
				// 引数がDTOの場合、DTOに定義されたフィールドを
				// 対象エントリの変更属性とみなします。
				int propSize = beanMetaData.getPropertyTypeSize();
				for (int j = 0; j < propSize; ++j) {
					PropertyType pt = beanMetaData.getPropertyType(j);
					PropertyDesc pd = pt.getPropertyDesc();
					String attributeName = pt.getColumnName();
					Object attributeValue = pd.getValue(argValue);
					Attribute currentAttribute = null;
					if (currentAttributeNameSet.contains(attributeName)) {
						// 既に属性がある場合、値を変更するために現在の属性を取得します。
						currentAttribute =
							result.getAttributes().get(attributeName);
					}
					ModificationItem modificationItem =
						createModificationItem(
							currentAttribute,
							attributeName,
							attributeValue,
							pd.getPropertyType());
					if (modificationItem != null) {
						modificationItemList.add(modificationItem);
					}
				}
			} else {
				// 引数がDTOではない場合、
				// 引数を作成エントリの属性とみなします。
				Attribute currentAttribute = null;
				if (currentAttributeNameSet.contains(argName)) {
					// 既に属性がある場合、値を変更するために現在の属性を取得します。
					currentAttribute = result.getAttributes().get(argName);
				}
				ModificationItem modificationItem =
					createModificationItem(
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

	/**
	 * 更新アイテムを作成します。
	 * 
	 * @param currentAttribute
	 *            現在の属性
	 * @param attributeName
	 *            属性名
	 * @param value
	 *            新しい値
	 * @param valueClass
	 *            新しい値の型
	 * @return 更新アイテム
	 * @throws NamingException
	 */
	private ModificationItem createModificationItem(Attribute currentAttribute,
			String attributeName, Object value, Class valueClass)
			throws NamingException {
		DirectoryAttributeHandlerFactory directoryAttributeHandlerFactory =
			ctx.getDirectoryAttributeHandlerFactory();
		AttributeHandler attributeHandler =
			directoryAttributeHandlerFactory.getAttributeHandler(attributeName);
		ModificationItem modificationItem;
		modificationItem =
			attributeHandler.getModificationItem(
				property,
				currentAttribute,
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
		while (ae.hasMore()) {
			Attribute attribute = (Attribute)ae.next();
			String attributeName = attribute.getID();
			attributeNameSet.add(attributeName);
		}
		DirectoryDataSourceUtil.close(ae);
		return attributeNameSet;
	}
}
