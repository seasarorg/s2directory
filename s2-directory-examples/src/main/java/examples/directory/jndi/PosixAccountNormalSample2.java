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
package examples.directory.jndi;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import examples.directory.entity.PosixAccount;

/**
 * 通常の手段でPosixAccountクラスにオブジェクトを代入する例です。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class PosixAccountNormalSample2 {
	public static void main(String[] args) {
		List posixAccounts = new ArrayList();
		Hashtable env = new Hashtable(11);
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://localhost:30389/");
		env.put(Context.SECURITY_PRINCIPAL, "cn=Manager,dc=seasar,dc=org");
		env.put(Context.SECURITY_CREDENTIALS, "secret");
		try {
			DirContext ctx = new InitialDirContext(env);
			try {
				SearchControls controls = new SearchControls();
				controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
				NamingEnumeration ne = ctx.search("dc=seasar,dc=org",
						"uid=user4", controls);
				if (ne != null) {
					while (ne.hasMore()) {
						SearchResult result = (SearchResult)ne.next();
						Attributes attrs = result.getAttributes();
						NamingEnumeration ae = attrs.getAll();
						PosixAccount account = new PosixAccount();
						account.setDn(result.getName() + ",dc=seasar,dc=org");
						while (ae.hasMoreElements()) {
							Attribute attr = (Attribute)ae.next();
							// Get Attribute's Name
							String attrName = attr.getID();
							if (attrName.equals("cn")) {
								System.out.println(attr);
								account.setCn(String.valueOf(attr.get()));
							} else if (attrName.equals("uid")) {
								account.setUid(String.valueOf(attr.get()));
							} else if (attrName.equals("uidNumber")) {
								account
										.setUidNumber(String
												.valueOf(attr.get()));
							} else if (attrName.equals("gidNumber")) {
								account
										.setGidNumber(String
												.valueOf(attr.get()));
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
			ctx.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		for (Iterator iter = posixAccounts.iterator(); iter.hasNext();) {
			PosixAccount account = (PosixAccount)iter.next();
			System.out.println("DEBUG: " + account);
		}
		// update
		for (Iterator iter = posixAccounts.iterator(); iter.hasNext();) {
			PosixAccount account = (PosixAccount)iter.next();
			account.setLoginShell("/bin/sh");
			account.setGecos("Rename User");
			try {
				DirContext ctx = new InitialDirContext(env);
				Attributes attrs = new BasicAttributes();
				attrs.put("loginShell", "/bin/sh");
				attrs.put("gecos", "Rename User");
				ctx.modifyAttributes(account.getDn(),
						DirContext.REPLACE_ATTRIBUTE, attrs);
				ctx.close();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		// delete
		// for (Iterator iter = posixAccounts.iterator(); iter.hasNext();) {
		// PosixAccount account = (PosixAccount)iter.next();
		// try {
		// DirContext ctx = new InitialDirContext(env);
		// ctx.destroySubcontext(account.getDn());
		// ctx.close();
		// } catch (NamingException e) {
		// e.printStackTrace();
		// }
		// }
	}
}
