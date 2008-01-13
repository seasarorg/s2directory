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
package org.seasar.directory.examples.jndi;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.seasar.directory.examples.entity.PosixAccount;

/**
 * 通常の手段でPosixAccountクラスにオブジェクトを代入する例です。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class PosixAccountNormalSample {
	public static void run() {
		int COUNT1 = 10;
		int COUNT2 = 10;
		System.out.println("START");
		for (int i = 0; i < COUNT1; i++) {
			long start = System.currentTimeMillis();
			for (int j = 0; j < COUNT2; j++) {
				getPosixUser();
			}
			long end = System.currentTimeMillis();
			System.out.println(i + ": " + (end - start) + "ms");
		}
		System.out.println("START");
	}

	public static void getPosixUser() {
		List posixAccounts = new ArrayList();
		Hashtable env = new Hashtable(11);
		env.put(
			Context.INITIAL_CONTEXT_FACTORY,
			"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://192.168.3.202:389/");
		try {
			DirContext ctx = new InitialDirContext(env);
			NamingEnumeration ne = null;
			try {
				SearchControls controls = new SearchControls();
				controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
				ne =
					ctx.search(
						"dc=seasar,dc=org",
						"objectClass=posixAccount",
						controls);
				if (ne != null) {
					while (ne.hasMore()) {
						SearchResult result = (SearchResult)ne.next();
						Attributes attrs = result.getAttributes();
						NamingEnumeration ae = attrs.getAll();
						PosixAccount account = new PosixAccount();
						while (ae.hasMoreElements()) {
							Attribute attr = (Attribute)ae.next();
							// Get Attribute's Name
							String attrName = attr.getID();
							if (attrName.equals("cn")) {
								account.setCn(String.valueOf(attr.get()));
							} else if (attrName.equals("uid")) {
								account.setUid(String.valueOf(attr.get()));
							} else if (attrName.equals("uidNumber")) {
								account
									.setUidNumber(String.valueOf(attr.get()));
							} else if (attrName.equals("gidNumber")) {
								account
									.setGidNumber(String.valueOf(attr.get()));
							} else if (attrName.equals("homeDicretory")) {
								account.setHomeDirectory(String.valueOf(attr
									.get()));
							} else if (attrName.equals("userPassword")) {
								account.setUserPassword(String.valueOf(attr
									.get()));
							} else if (attrName.equals("loginShell")) {
								account.setLoginShell(String
									.valueOf(attr.get()));
							} else if (attrName.equals("gecos")) {
								account.setGecos(String.valueOf(attr.get()));
							} else if (attrName.equals("description")) {
								account.setDescription(String.valueOf(attr
									.get()));
							}
						}
						posixAccounts.add(account);
					}
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
			ne.close();
			ctx.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		/*
		 * for (Iterator iter = posixAccounts.iterator(); iter.hasNext();) {
		 * PosixAccount account = (PosixAccount)iter.next();
		 * System.out.println("[User: " + account.getCn() + "]");
		 * System.out.println("homeDicretory=" + account.getHomeDirectory());
		 * System.out.println("uidNumber=" + account.getUidNumber());
		 * System.out.println("gidNumber=" + account.getGidNumber());
		 * System.out.println("\tDEBUG: " + account); }
		 */
	}

	public static void main(String[] args) {
		run();
	}
}
