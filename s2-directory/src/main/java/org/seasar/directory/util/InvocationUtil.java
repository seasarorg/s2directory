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
package org.seasar.directory.util;

import java.lang.reflect.Method;
import org.aopalliance.intercept.Invocation;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Invocationに関するユーティリティクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public final class InvocationUtil {
	public static String toDetailString(Invocation invocation) throws Throwable {
		Method method = ((MethodInvocation)invocation).getMethod();
		StringBuffer buff = new StringBuffer();
		buff.append((method.getDeclaringClass().getName() + "#"));
		buff.append(method.getName() + "(");
		Class[] parameters = method.getParameterTypes();
		for (int i = 0; i < parameters.length; i++) {
			String clazz = parameters[i].getName();
			clazz = clazz.replaceAll("(\\[*)L(.*?);", "$2$1");
			clazz = clazz.replaceAll("\\[", "[]");
			buff.append(clazz);
			if (i != parameters.length - 1)
				buff.append(",");
		}
		buff.append(")");
		return buff.toString();
	}
}
