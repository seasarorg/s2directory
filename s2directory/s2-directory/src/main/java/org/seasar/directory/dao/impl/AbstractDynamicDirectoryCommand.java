/*
 * Copyright 2005-2013 the Seasar Foundation and the Others.
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
import org.seasar.directory.DirectoryDataSourceFactory;
import org.seasar.directory.DirectoryValueTypeFactory;
import org.seasar.directory.context.CommandContextImpl;
import org.seasar.directory.dao.AnnotationMethodArgs;
import org.seasar.directory.dao.DirectoryBeanMetaData;
import org.seasar.directory.exception.IllegalArgsPositionRuntimeException;

/**
 * ディレクトリ用動的処理コマンドの抽象クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public abstract class AbstractDynamicDirectoryCommand extends
		AbstractDirectoryCommand {

	/** ビーンメタデータ */
	protected DirectoryBeanMetaData beanMetaData;

	/** 関数の引数 */
	private AnnotationMethodArgs methodArgs;

	/**
	 * * インスタンスを作成します。
	 * 
	 * @param dataSourceFactory
	 *            データソースファクトリ
	 * @param attributeHandlerFactory
	 *            属性ハンドラファクトリ
	 * @param methodArgs
	 *            引数
	 */
	public AbstractDynamicDirectoryCommand(
			DirectoryDataSourceFactory dataSourceFactory,
			DirectoryAttributeHandlerFactory attributeHandlerFactory,
			DirectoryBeanMetaData beanMetaData, AnnotationMethodArgs methodArgs) {
		super(dataSourceFactory, attributeHandlerFactory);
		this.beanMetaData = beanMetaData;
		this.methodArgs = methodArgs;
	}

	/**
	 * コマンドコンテキストを作成します。
	 * 
	 * @param args
	 *            引数値
	 */
	protected CommandContext apply(Object[] args) {
		final DirectoryValueTypeFactory valueTypeFactory =
			getDirectoryAttributeHandlerFactory().getDirectoryValueTypeFactory();
		CommandContext ctx =
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
						&& valueTypeFactory.getValueTypeByClass(args[i].getClass()) == valueTypeFactory.getObjectValueType()) {
						ctx.addDtoArg(args[i]);
					} else {
						ctx.addArg(argNames[i], args[i], argType);
					}
				} else {
					ctx.addArg("$" + (i + 1), args[i], argType);
				}
			}
		}
		// オブジェクトクラスを設定します。
		applyObjectClass(ctx);
		return ctx;
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
		if (args != null && args.length > 0) {
			// 第一引数が接続情報である場合は、
			// その接続情報を利用したデータソースを返します。
			if (args[0] != null && args[0] instanceof DirectoryControlProperty) {
				return dataSourceFactory.getDirectoryDataSource((DirectoryControlProperty)args[0]);
			}
			// 第一引数以外に接続情報がある場合は、
			// 例外を発生させます。
			for (int i = 1; i < args.length; ++i) {
				if (args[i] instanceof DirectoryControlProperty) {
					throw new IllegalArgsPositionRuntimeException();
				}
			}
		}
		return dataSourceFactory.getDirectoryDataSource();
	}

	/**
	 * オブジェクトクラスを設定します。
	 * 
	 * @param ctx
	 */
	protected void applyObjectClass(CommandContext ctx) {
		ctx.setObjectClasses(super.getObjectClasses());
	}

}
