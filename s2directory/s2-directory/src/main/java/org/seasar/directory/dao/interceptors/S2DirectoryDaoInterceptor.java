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
package org.seasar.directory.dao.interceptors;

import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.directory.dao.DirectoryDaoMetaData;
import org.seasar.directory.dao.DirectoryCommand;
import org.seasar.directory.dao.DirectoryDaoMetaDataFactory;
import org.seasar.directory.dao.util.DaoUtils;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.util.MethodUtil;
import org.seasar.framework.util.NumberConversionUtil;

/**
 * DirectoryDao用のインタセプターです。 <br />
 * Daoクラスの関数が呼び出されるとその処理に割り込み結果を返します。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class S2DirectoryDaoInterceptor extends AbstractInterceptor {
	/** メタデータ生成器を表します。 */
	private DirectoryDaoMetaDataFactory directoryDaoMetaDataFactory;

	/**
	 * diconファイルに指定された生成器でインスタンスを生成します。 <br />
	 * Seasar本体により自動的に生成されたインスタンスがセットされます。
	 * 
	 * @param directoryDaoMetaDataFactory - メタデータ生成器
	 */
	public S2DirectoryDaoInterceptor(
			DirectoryDaoMetaDataFactory directoryDaoMetaDataFactory) {
		this.directoryDaoMetaDataFactory = directoryDaoMetaDataFactory;
	}

	/**
	 * 指定された関数に対応する処理を行い結果を返します。
	 * 
	 * @param invocation - 実行された関数
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		if (!MethodUtil.isAbstract(method)) {
			return invocation.proceed();
		}
		Class targetClass = getTargetClass(invocation);
		Class daoInterface = DaoUtils.getDaoInterface(targetClass);
		DirectoryDaoMetaData dmd = directoryDaoMetaDataFactory
				.getDirectoryDaoMetaData(daoInterface);
		DirectoryCommand cmd = dmd.getDirectoryCommand(method.getName());
		// コマンドを実行し返却します。
		Object ret = cmd.execute(invocation.getArguments());
		Class retType = method.getReturnType();
		if (retType.isPrimitive()) {
			return NumberConversionUtil.convertPrimitiveWrapper(retType, ret);
		} else if (Number.class.isAssignableFrom(retType)) {
			return NumberConversionUtil.convertNumber(retType, ret);
		}
		return ret;
	}
}
