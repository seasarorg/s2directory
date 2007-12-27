/*
 * Copyright 2005-2007 the Seasar Foundation and the Others.
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
package org.seasar.directory.dao.impl;

import java.util.Iterator;
import java.util.Set;

import org.seasar.directory.CommandContext;
import org.seasar.directory.DirectoryAttributeHandlerFactory;
import org.seasar.directory.DirectoryDataSourceFactory;
import org.seasar.directory.NamingEnumerationHandler;
import org.seasar.directory.dao.AnnotationMethodArgs;
import org.seasar.directory.dao.DirectoryBeanMetaData;
import org.seasar.directory.impl.ExecuteHandler;
import org.seasar.directory.impl.SelectHandler;
import org.seasar.directory.types.PropertyType;
import org.seasar.directory.types.ValueType;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.util.ArrayMap;
import org.seasar.framework.util.StringUtil;

/**
 * 動的に読み出し処理を実行するクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class SelectDynamicCommand extends AbstractDynamicDirectoryCommand {
	/** 検索結果ハンドラ */
	private NamingEnumerationHandler namingEnumerationHandler;

	/**
	 * @param dataSource
	 *            データソース
	 * @param attributeHandlerFactory
	 *            属性ハンドラファクトリ
	 * @param beanMetaData
	 *            ビーンメタデータ
	 * @param methodArgs
	 *            関数の引数
	 * @param namingEnumerationHandler
	 *            検索結果ハンドラ
	 */
	public SelectDynamicCommand(DirectoryDataSourceFactory dataSourceFactory,
			DirectoryAttributeHandlerFactory attributeHandlerFactory,
			DirectoryBeanMetaData beanMetaData,
			AnnotationMethodArgs methodArgs,
			NamingEnumerationHandler namingEnumerationHandler) {
		super(
			dataSourceFactory,
			attributeHandlerFactory,
			beanMetaData,
			methodArgs);
		this.namingEnumerationHandler = namingEnumerationHandler;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 読み出し処理を実行します。
	 * </p>
	 */
	public Object execute(Object[] args) {
		CommandContext ctx = apply(args);
		String filter = this.getFilter(ctx);
		if (filter == null) {
			return null;
		} else {
			ExecuteHandler selectHandler =
				new SelectHandler(
					getDirectoryDataSource(args),
					ctx.getDn(),
					filter,
					namingEnumerationHandler);
			return selectHandler.execute();
		}
	}

	/**
	 * 検索フィルタを取得します。
	 * 
	 * @return 検索フィルタ
	 */
	protected String getFilter(CommandContext ctx) {
		// DNがある場合、DNをフィルタとして利用します。
		if (!StringUtil.isEmpty(ctx.getDn())) {
			return ctx.getDn();
		}

		// エンティティもしくはDaoクラスに定義されたアノテーションにより取得した
		// オブジェクトクラスを指し示すフィルタを取得します。
		String daoFilter = super.getFilter();

		// 引数から展開したフィルタを取得します。
		String argumentFilter = this.getArgumentFilter(ctx);

		// 引数があるにも関わらずオブジェクトにすべての値がnullの場合は、
		// オブジェクトクラスによるフィルタも利用せずにnullを返す。
		if (ctx.getArgKeySet().size() != 0
			&& StringUtil.isEmpty(argumentFilter)) {
			return null;
		}

		if (StringUtil.isEmpty(daoFilter)) {
			return argumentFilter;
		} else {
			if (!StringUtil.isEmpty(argumentFilter)) {
				return "(&(" + daoFilter + ")(" + argumentFilter + "))";
			} else {
				return daoFilter;
			}
		}
	}

	/**
	 * 引数から展開したフィルタを取得します。
	 * 
	 * @return 引数から展開したフィルタ
	 */
	protected String getArgumentFilter(CommandContext ctx) {
		// 値が null ではない、属性のMapを取得します。
		ArrayMap fitlerArgs = getArgumentMap(ctx);

		// フィルタを作成します。
		StringBuffer buffer = new StringBuffer();
		int size = fitlerArgs.size();
		if (size > 0) {
			// 値が null ではない属性が一つ以上ある場合、最初の条件を作成します。
			Object key = fitlerArgs.getKey(0);
			Object value = fitlerArgs.get(key);
			ValueType type =
				getDirectoryAttributeHandlerFactory()
					.getDirectoryValueTypeFactory()
					.getValueTypeByClass(value.getClass());
			buffer.append(type.getFilter(key, value));
		}
		if (size > 1) {
			// 値が null ではない属性が複数ある場合、先ほどの最初の条件に足してフィルタを作成します。
			String firstFilter = buffer.toString();
			buffer = new StringBuffer("(&");
			buffer.append("(").append(firstFilter).append(")");
			for (int i = 1; i < size; i++) {
				Object key = fitlerArgs.getKey(i);
				Object value = fitlerArgs.get(key);
				if (value != null) {
					buffer.append("(");
					buffer.append(key).append("=").append(value);
					buffer.append(")");
				}
			}
			buffer.append(")");
		}
		return buffer.toString();
	}

	/**
	 * 値が null ではない、属性のArrayMapを取得します。
	 * 
	 * @param ctx
	 *            引数をコマンドとみなしたコンテキスト
	 * @return 値が null ではない、属性のArrayMap
	 */
	protected ArrayMap getArgumentMap(CommandContext ctx) {
		ArrayMap fitlerArgs = new ArrayMap();
		Set keySet = ctx.getArgKeySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String argName = String.valueOf(iter.next());
			Object argValue = ctx.getArg(argName);
			if (argName.equals("#dto")) {
				// 引数がDTOの場合、DTOに定義されているフィールドの値を利用します。
				int propSize = beanMetaData.getPropertyTypeSize();
				for (int j = 0; j < propSize; ++j) {
					PropertyType pt = beanMetaData.getPropertyType(j);
					PropertyDesc pd = pt.getPropertyDesc();
					String attributeName = pt.getColumnName();
					Object attributeValue = pd.getValue(argValue);
					if (attributeValue != null
						&& !StringUtil.isEmpty(String.valueOf(attributeValue))) {
						fitlerArgs.put(attributeName, attributeValue);
					}
				}
			} else {
				if (argValue != null
					&& !StringUtil.isEmpty(String.valueOf(argValue))) {
					fitlerArgs.put(argName, argValue);
				}
			}
		}
		return fitlerArgs;
	}
}
