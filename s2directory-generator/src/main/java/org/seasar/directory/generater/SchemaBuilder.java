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

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * スキーマを分類するクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class SchemaBuilder {
	/** the search filter of object definition. NUMERICOID: identifier */
	private static final String schemaFilter = "(|(NUMERICOID=*)(objectclass=*))";
	private SchemaMap classDefinition;
	private SchemaMap attributeDefinition;
	private SchemaMap matchingRuleDefinition;
	private SchemaMap syntaxDefinition;
	private SchemaMap unkownDefinition;

	private void init() {
		classDefinition = new SchemaMap();
		attributeDefinition = new SchemaMap();
		matchingRuleDefinition = new SchemaMap();
		syntaxDefinition = new SchemaMap();
		unkownDefinition = new SchemaMap();
	}

	public void build(DirContext ctx) {
		init();
		try {
			// DirContext ctx = DirContextFactory.get();
			DirContext schemaCtx = ctx.getSchema("");
			SearchControls controls = new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration ne = schemaCtx.search("", schemaFilter, controls);
			while (ne.hasMore()) {
				SearchResult result = (SearchResult)ne.next();
				AttributeSet attrSet = AttributeSetFactory.create(result);
				// System.out.println(attrSet.toString());
				if (attrSet.getType() == DirectoryConstant.OBJECTCLASS) {
					classDefinition.put(attrSet.getName(), attrSet
							.getAttributes());
				} else if (attrSet.getType() == DirectoryConstant.ATTRIBUTE) {
					attributeDefinition.put(attrSet.getName(), attrSet
							.getAttributes());
				} else if (attrSet.getType() == DirectoryConstant.MATCHINGRULE) {
					matchingRuleDefinition.put(attrSet.getName(), attrSet
							.getAttributes());
				} else if (attrSet.getType() == DirectoryConstant.SYNTAX) {
					syntaxDefinition.put(attrSet.getName(), attrSet
							.getAttributes());
				} else {
					unkownDefinition.put(attrSet.getName(), attrSet
							.getAttributes());
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return Returns the classDefinition.
	 */
	public SchemaMap getClassDefinition() {
		return classDefinition;
	}

	/**
	 * @return Returns the attributeDefinition.
	 */
	public SchemaMap getAttributeDefinition() {
		return attributeDefinition;
	}

	/**
	 * @return Returns the matchingRuleDefinition.
	 */
	public SchemaMap getMatchingRuleDefinition() {
		return matchingRuleDefinition;
	}

	/**
	 * @return Returns the syntaxDefinition.
	 */
	public SchemaMap getSyntaxDefinition() {
		return syntaxDefinition;
	}

	/**
	 * @return Returns the unkownDefinition.
	 */
	public SchemaMap getUnkownDefinition() {
		return unkownDefinition;
	}
}