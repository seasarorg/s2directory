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
package org.seasar.directory.generater;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;

/**
 * ディレクトリコネクション管理クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class ConnectionManager {
	private static DirContext context;
	/** Directory directory environment */
	private static Properties env = null;

	private ConnectionManager() {}

	public static DirContext connect(String host, int port)
			throws DirectoryException {
		return connect(host, port, "", null, false);
	}

	public static DirContext connect(String host, int port, String dn,
			String passwd) throws DirectoryException {
		return connect(host, port, dn, passwd, true);
	}

	public static DirContext connect(String host, int port, String dn,
			String passwd, boolean doAuthenticate) throws DirectoryException {
		if ((host == null) || (host.equals(""))) {
			throw new DirectoryException("no host for connection");
		}
		if (isConnected()) {
			close();
		}
		env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://" + host + ":" + port);
		env.put(Context.SECURITY_PRINCIPAL, dn);
		if (doAuthenticate) {
			env.put(Context.SECURITY_CREDENTIALS, passwd);
		}
		try {
			context = new InitialDirContext(env);
		} catch (NamingException e) {
			throw new DirectoryException(e.getMessage());
		}
		return context;
	}

	public synchronized static boolean isConnected() {
		if (env == null)
			return false;
		return true;
	}

	public synchronized static void destroy(String name)
			throws DirectoryException {
		if (!isConnected())
			return;
		try {
			context.destroySubcontext(name);
			env = null;
		} catch (NamingException e) {
			throw new DirectoryException(e.getMessage());
		}
	}

	public synchronized static void close() throws RuntimeException {
		if (!isConnected())
			return;
		try {
			context.close();
			env = null;
		} catch (NamingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Get the object value of attribute.
	 * 
	 * @param attribute name
	 * @return value
	 */
	public Object read(String dn) {
		return read(dn, "*", SearchControls.SUBTREE_SCOPE);
	}

	public Object read(String dn, String filter) {
		return read(dn, filter, SearchControls.SUBTREE_SCOPE);
	}

	public Object read(String dn, String filter, int scope) {
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(scope);
			env.put(Context.SECURITY_PRINCIPAL, dn);
			DirContext ctx = new InitialDirContext(env);
			NamingEnumeration result = ctx.search(dn, filter, constraints);
			result.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
