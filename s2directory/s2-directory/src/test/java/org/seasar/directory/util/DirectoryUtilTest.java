/*
 * Copyright 2005-2014 the Seasar Foundation and the Others.
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

import javax.naming.directory.SearchControls;

import junit.framework.TestCase;

/**
 * データソース用ユーティリティクラスのテストクラスです。
 * 
 * @author Jun Futagawa
 */
public class DirectoryUtilTest extends TestCase {

	public void testGetFirstDn() {
		assertEquals(
			"uid=user1",
			DirectoryUtil.getFirstDn("uid=user1, ou=Users, dc=seasar,dc=org"));
		assertEquals("uid=user1", DirectoryUtil.getFirstDn("uid=user1"));
	}

	public void testGetBaseDn() {
		assertEquals(
			"ou=Users, dc=seasar,dc=org",
			DirectoryUtil.getBaseDn("uid=user1, ou=Users, dc=seasar,dc=org"));
	}

	public void testGetAttributeName() {
		assertEquals("uid", DirectoryUtil.getAttributeName("uid=user1"));
		assertEquals(
			"uid",
			DirectoryUtil.getAttributeName("uid=user1, ou=Users, dc=seasar,dc=org"));
	}

	public void testGetAttributeValue() {
		assertEquals("user1", DirectoryUtil.getAttributeValue("uid=user1"));
		assertEquals(
			"user1",
			DirectoryUtil.getAttributeValue("uid=user1, ou=Users, dc=seasar,dc=org"));
	}

	public void testVerify() {
		assertEquals(true, DirectoryUtil.verifyPassword(
			"{MD5}Xr4ilOzQ4PCOq3aQ0qbuaQ==",
			"secret"));
		assertEquals(true, DirectoryUtil.verifyPassword(
			"{SHA}5en6G6MezRroT3XKqkdPOmY/BfQ=",
			"secret"));
		assertEquals(false, DirectoryUtil.verifyPassword("{SHA}x", "secret"));
	}

	public void testGetPassword() {
		assertEquals(
			"{MD5}Xr4ilOzQ4PCOq3aQ0qbuaQ==",
			DirectoryUtil.createPassword("secret", "MD5", -1));
		assertEquals(
			"{SHA}5en6G6MezRroT3XKqkdPOmY/BfQ=",
			DirectoryUtil.createPassword("secret", "SHA", -1));
	}

	public void testCreateFilter() {
		assertEquals("", DirectoryUtil.createFilter(null, null, null));
		assertEquals(
			"",
			DirectoryUtil.createFilter("&", "objectClass", new String[] {}));
		assertEquals(
			"",
			DirectoryUtil.createFilter("&", "objectClass", new String[] { "" }));
		assertEquals("objectClass=aaa", DirectoryUtil.createFilter(
			"+",
			"objectClass",
			new String[] { "aaa" }));
		assertEquals(
			"(&(objectClass=aaa)(objectClass=bbb))",
			DirectoryUtil.createFilter("&", "objectClass", new String[] {
				"aaa",
				"bbb" }));
		assertEquals(
			"(&(&(objectClass=aaa)(objectClass=bbb))(objectClass=ccc))",
			DirectoryUtil.createFilter("&", "objectClass", new String[] {
				"aaa",
				"bbb",
				"ccc" }));
		assertEquals(
			"(&(&(&(objectClass=aaa)(objectClass=bbb))(objectClass=ccc))(objectClass=ddd))",
			DirectoryUtil.createFilter("&", "objectClass", new String[] {
				"aaa",
				"bbb",
				"ccc",
				"ddd" }));
		assertEquals(
			"(&(&(&(&(objectClass=aaa)(objectClass=bbb))(objectClass=ccc))(objectClass=ddd))(objectClass=eee))",
			DirectoryUtil.createFilter("&", "objectClass", new String[] {
				"aaa",
				"bbb",
				"ccc",
				"ddd",
				"eee" }));
	}

	public void testAddFilter() {
		assertEquals("", DirectoryUtil.addFilter(null, null, null));
		assertEquals("", DirectoryUtil.addFilter(null, "", null));
		assertEquals("", DirectoryUtil.addFilter(null, null, ""));
		assertEquals("aaa", DirectoryUtil.addFilter(null, "aaa", null));
		assertEquals("bbb", DirectoryUtil.addFilter(null, null, "bbb"));
		assertEquals(
			"(&(aaa=bbb)(ccc=ddd))",
			DirectoryUtil.addFilter("&", "aaa=bbb", "ccc=ddd"));
		assertEquals(
			"(&(aaa=bbb)(ccc=ddd))",
			DirectoryUtil.addFilter("&", "(aaa=bbb)", "ccc=ddd"));
		assertEquals(
			"(&(aaa=bbb)(ccc=ddd))",
			DirectoryUtil.addFilter("&", "aaa=bbb", "(ccc=ddd)"));
		assertEquals(
			"(&(aaa=bbb)(ccc=ddd))",
			DirectoryUtil.addFilter("&", "(aaa=bbb)", "(ccc=ddd)"));
		assertEquals(
			"(&(&(aaa=bbb)(ccc=ddd))(eee=fff))",
			DirectoryUtil.addFilter("&", "(&(aaa=bbb)(ccc=ddd))", "(eee=fff)"));
		assertEquals(
			"(&(&(&(aaa=bbb)(ccc=ddd))(eee=fff))(ggg=hhh))",
			DirectoryUtil.addFilter(
				"&",
				"(&(&(aaa=bbb)(ccc=ddd))(eee=fff))",
				"ggg=hhh"));
	}

	public void testToStringFromSearchControls() {
		assertEquals("", DirectoryUtil.toStringFromSearchControls(null));
		assertEquals(
			"{countLimit: 0, derefLinkFlag: false, returningAttributes: null, searchScope: ONELEVEL_SCOPE, timeLimit: 0}",
			DirectoryUtil.toStringFromSearchControls(new SearchControls()));

		SearchControls controls = new SearchControls();
		controls.setCountLimit(1);
		controls.setDerefLinkFlag(true);
		controls.setReturningAttributes(new String[] { "a", "b", "c" });
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setTimeLimit(1);
		assertEquals(
			"{countLimit: 1, derefLinkFlag: true, returningAttributes: [a, b, c], searchScope: SUBTREE_SCOPE, timeLimit: 1}",
			DirectoryUtil.toStringFromSearchControls(controls));
	}

}
