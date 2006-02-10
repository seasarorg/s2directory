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
package org.seasar.directory.context;

import java.util.Set;
import org.seasar.directory.CommandContext;
import org.seasar.directory.types.ValueType;
import org.seasar.directory.types.ValueTypes;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.CaseInsensitiveMap;

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
				if (propertyDesc.getValue(dtoArg) != null) {
					addArg(propertyDesc.getPropertyName(), propertyDesc
							.getValue(dtoArg), propertyDesc.getPropertyType());
				}
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
		StringBuffer buffer = new StringBuffer();
		int size = args.size();
		if (size > 0) {
			Object key = args.getKey(0);
			Object value = args.get(key);
			ValueType type = ValueTypes.getValueType(value);
			buffer.append(type.getFilter(key, value));
		}
		if (size > 1) {
			String firstFilter = buffer.toString();
			buffer = new StringBuffer("(&");
			buffer.append("(").append(firstFilter).append(")");
			for (int i = 1; i < size; i++) {
				Object key = args.getKey(i);
				buffer.append("(");
				buffer.append(key).append("=").append(args.get(key));
				buffer.append(")");
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
		return String.valueOf(this.getArg("dn"));
	}

	/**
	 * 文字列表現を表わします。
	 * 
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SEARCH: ");
		buffer.append(args);
		buffer.append(argTypes);
		return buffer.toString();
	}
}