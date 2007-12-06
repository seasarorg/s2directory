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
package org.seasar.directory.dao.impl;

import org.seasar.directory.CommandContext;
import org.seasar.directory.DirectoryAttributeHandlerFactory;
import org.seasar.directory.DirectoryControlProperty;
import org.seasar.directory.DirectoryDataSource;
import org.seasar.directory.DirectoryValueTypeFactory;
import org.seasar.directory.context.CommandContextImpl;
import org.seasar.directory.dao.AnnotationMethodArgs;
import org.seasar.directory.exception.IllegalArgsPositionRuntimeException;
import org.seasar.directory.impl.DirectoryDataSourceImpl;
import org.seasar.framework.util.StringUtil;

/**
 * ディレクトリ用動的処理コマンドの抽象クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public abstract class AbstractAutoDirectoryCommand extends
		AbstractDirectoryCommand {
	private AnnotationMethodArgs methodArgs;
	/** 実行フィルタを表します。 */
	private String runFilter;

	/**
	 * インスタンスを作成します。
	 * 
	 * @param dataSource -
	 *            データソース
	 */
	public AbstractAutoDirectoryCommand(DirectoryDataSource dataSource,
			DirectoryAttributeHandlerFactory directoryAttributeHandlerFactory,
			AnnotationMethodArgs methodArgs) {
		super(dataSource, directoryAttributeHandlerFactory);
		this.methodArgs = methodArgs;
	}

	/**
	 * 実行フィルタを取得します。
	 * 
	 * @return runFilter - フィルタ
	 */
	public String getRunFilter() {
		return runFilter;
	}

	/**
	 * データソースを取得します。 <br />
	 * 引数の第一引数が接続情報である場合は、その接続情報を利用したデータソースを返します。
	 * 
	 * @param args
	 *            引数値
	 * @return データソース
	 */
	public DirectoryDataSource getDirectoryDataSource(Object[] args) {
		if (args != null && methodArgs != null && args.length > 0) {
			// 第一引数が接続情報である場合は、その接続情報を利用したデータソースを返します。
			if (args[0] != null
				&& methodArgs.getArgTypes()[0] == DirectoryControlProperty.class) {
				return new DirectoryDataSourceImpl(
					(DirectoryControlProperty)args[0]);
			}
			// 第一引数以外に接続情報がある場合は、例外を発生させます。
			for (int i = 1; i < args.length; ++i) {
				if (methodArgs.getArgTypes()[i] == DirectoryControlProperty.class) {
					throw new IllegalArgsPositionRuntimeException();
				}
			}
		}
		return getDirectoryDataSource();
	}

	/**
	 * コマンドコンテキストを作成します。
	 * 
	 * @param args
	 *            引数値
	 */
	protected CommandContext apply(Object[] args) {
		final DirectoryValueTypeFactory valueTypeFactory =
			getDirectoryAttributeHandlerFactory()
				.getDirectoryValueTypeFactory();
		CommandContext cmd =
			new CommandContextImpl(getDirectoryAttributeHandlerFactory());
		if (args != null && methodArgs != null) {
			String[] argNames = methodArgs.getArgNames();
			Class[] argTypes = methodArgs.getArgTypes();
			int i = 0;
			// 第一引数に接続情報がある場合は、インデックスを2つ目にします。
			if (argNames[i].equals("#property")
				&& argTypes[0] == DirectoryControlProperty.class) {
				i = 1;
			}
			int length = args.length;
			for (; i < length; ++i) {
				Class argType = null;
				// 引数の型を取得します。
				if (args[i] != null) {
					if (i < argTypes.length) {
						argType = argTypes[i];
					} else if (args[i] != null) {
						argType = args[i].getClass();
					}
				}
				if (i < argNames.length) {
					// Dtoの場合
					if (argNames[i].equals("#dto")
						&& valueTypeFactory.getValueTypeByClass(args[i]
							.getClass()) == valueTypeFactory
							.getObjectValueType()) {
						cmd.addDtoArg(args[i]);
					} else {
						cmd.addArg(argNames[i], args[i], argType);
					}
				} else {
					cmd.addArg("$" + (i + 1), args[i], argType);
				}
			}
		}
		// フィルタを作成します。
		applyFilter(cmd);
		// オブジェクトクラスを設定します。
		applyObjectClass(cmd);
		return cmd;
	}

	/**
	 * フィルタを設定します。
	 * 
	 * @param cmd
	 */
	protected void applyFilter(CommandContext cmd) {
		// フィルタを作成します。
		String filter = super.getFilter();
		String ctxFilter = cmd.getFilter();
		if (StringUtil.isEmpty(filter)) {
			filter = ctxFilter;
		} else {
			if (!StringUtil.isEmpty(ctxFilter)) {
				filter = "(&(" + filter + ")(" + ctxFilter + "))";
			}
		}
		// フィルタが空の場合はダミーフィルタを設定します。
		if (filter.length() == 0)
			filter = "null=null";
		runFilter = filter;
	}

	/**
	 * オブジェクトクラスを設定します。
	 * 
	 * @param cmd
	 */
	protected void applyObjectClass(CommandContext cmd) {
		cmd.setObjectClasses(super.getObjectClasses());
	}
}
