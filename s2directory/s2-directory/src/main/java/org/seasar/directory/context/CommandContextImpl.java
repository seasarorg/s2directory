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
package org.seasar.directory.context;

import java.util.Set;
import org.seasar.directory.CommandContext;
import org.seasar.directory.dao.DirectoryValueTypeFactory;
import org.seasar.directory.types.ValueType;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.CaseInsensitiveMap;
import org.seasar.framework.util.StringUtil;

/**
 * 引数をコマンドとみなしたコンテキストを表わします。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class CommandContextImpl implements CommandContext {
	/** ロガーを表わします。 */
	private static Logger logger = Logger.getLogger(CommandContextImpl.class);
	/** 引数の値マップを表わします。 */
	private CaseInsensitiveMap args = new CaseInsensitiveMap();
	/** 引数の型マップを表わします。 */
	private CaseInsensitiveMap argTypes = new CaseInsensitiveMap();
	/** オブジェクトクラスを表します。 */
	private String[] objectClasses;
	/** ディレクトリ用の値の型ファクトリを表します。 */
	protected DirectoryValueTypeFactory directoryValueTypeFactory;

	/**
	 * インスタンスを作成します。
	 * 
	 * @param directoryValueTypeFactory
	 */
	public CommandContextImpl(
			DirectoryValueTypeFactory directoryValueTypeFactory) {
		setDirectoryValueTypeFactory(directoryValueTypeFactory);
	}

	/**
	 * 指定した引数名の値を取得します。
	 * 
	 * @param name 引数名
	 * @return 引数の値
	 * @see org.seasar.directory.CommandContext#getArg(java.lang.String)
	 */
	public Object getArg(String name) {
		if (args.containsKey(name)) {
			return args.get(name);
		} else {
			if (args.size() == 1) {
				return args.get(0);
			}
			// TODO: ログ形式
			logger.log("WDAO0001", new Object[] { name });
			return null;
		}
	}

	/**
	 * すべての引数名を取得します。
	 * 
	 * @return すべての引数名
	 * @see org.seasar.directory.CommandContext#getArgKeySet()
	 */
	public Set getArgKeySet() {
		return args.keySet();
	}

	/**
	 * 指定した引数名の型を取得します。
	 * 
	 * @param name 引数名
	 * @return 引数の型
	 * @see org.seasar.directory.CommandContext#getArgType(java.lang.String)
	 */
	public Class getArgType(String name) {
		if (argTypes.containsKey(name)) {
			return (Class)argTypes.get(name);
		} else {
			if (argTypes.size() == 1) {
				return (Class)argTypes.get(0);
			}
			// TODO: ログ形式
			logger.log("WDAO0001", new Object[] { name });
			return null;
		}
	}

	/**
	 * Dtoの引数コンテキストを追加します。
	 * 
	 * @param dtoArg Dtoの引数
	 * @see org.seasar.directory.CommandContext#addDtoArg(java.lang.Object)
	 */
	public void addDtoArg(Object dtoArg) {
		if (dtoArg != null) {
			BeanDesc beanDesc = BeanDescFactory.getBeanDesc(dtoArg.getClass());
			int size = beanDesc.getPropertyDescSize();
			for (int i = 0; i < size; i++) {
				PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
				addArg(propertyDesc.getPropertyName(), propertyDesc
						.getValue(dtoArg), propertyDesc.getPropertyType());
			}
		}
	}

	/**
	 * 引数コンテキストを追加します。
	 * 
	 * @param name 引数名
	 * @param arg 引数の値
	 * @param argType 引数の型
	 * @see org.seasar.directory.CommandContext#addArg(java.lang.String,
	 *      java.lang.Object, java.lang.Class)
	 */
	public void addArg(String name, Object arg, Class argType) {
		args.put(name, arg);
		argTypes.put(name, argType);
	}

	/**
	 * フィルターを取得します。
	 * 
	 * @return フィルター
	 * @see org.seasar.directory.CommandContext#getFilter()
	 */
	public String getFilter() {
		// 値が null ではない、属性を抽出します。
		CaseInsensitiveMap fitlerArgs = new CaseInsensitiveMap();
		int size = args.size();
		for (int i = 0; i < size; i++) {
			Object key = args.getKey(i);
			Object value = args.get(key);
			if (value != null && !StringUtil.isEmpty(String.valueOf(value))) {
				fitlerArgs.put(key, value);
			}
		}
		// フィルターを作成します。
		StringBuffer buffer = new StringBuffer();
		size = fitlerArgs.size();
		if (size > 0) {
			// 値が null ではない属性が一つ以上ある場合、最初の条件を作成します。
			Object key = fitlerArgs.getKey(0);
			Object value = fitlerArgs.get(key);
			ValueType type = getDirectoryValueTypeFactory()
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
	 * 識別名を取得します。
	 * 
	 * @return 識別名
	 */
	public String getDn() {
		if (args.containsKey("dn")) {
			Object dn = args.get("dn");
			if (dn != null) {
				// dn が null ではない場合、値を返します。
				return String.valueOf(dn);
			}
		}
		return null;
	}

	/**
	 * オブジェクトクラスを取得します。
	 * 
	 * @return objectClasses
	 */
	public String[] getObjectClasses() {
		return objectClasses;
	}

	/**
	 * オブジェクトクラスを設定します。
	 * 
	 * @param objectClasses
	 */
	public void setObjectClasses(String[] objectClasses) {
		this.objectClasses = objectClasses;
	}

	/**
	 * {@inheritDoc}
	 */
	public DirectoryValueTypeFactory getDirectoryValueTypeFactory() {
		return directoryValueTypeFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setDirectoryValueTypeFactory(
			DirectoryValueTypeFactory directoryValueTypeFactory) {
		this.directoryValueTypeFactory = directoryValueTypeFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("CommandContext: ");
		buffer.append(args);
		buffer.append(argTypes);
		return buffer.toString();
	}
}
