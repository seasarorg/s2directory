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
package test.org.seasar.directory.util;

import junit.framework.TestCase;
import org.seasar.directory.util.DirectoryUtils;

/**
 * データソース用ユーティリティクラスのテストクラスです。
 * 
 * @author Jun Futagawa
 * @version $Revision$ $Date$
 */
public class DirectoryUtilTest extends TestCase {
	public void testVerify() {
		assertEquals(true, DirectoryUtils.verifyPassword(
				"{MD5}Xr4ilOzQ4PCOq3aQ0qbuaQ==", "secret"));
		assertEquals(true, DirectoryUtils.verifyPassword(
				"{SHA}5en6G6MezRroT3XKqkdPOmY/BfQ=", "secret"));
		assertEquals(false, DirectoryUtils.verifyPassword("{SHA}x", "secret"));
	}

	public void testGetPassword() {
		assertEquals("{MD5}Xr4ilOzQ4PCOq3aQ0qbuaQ==", DirectoryUtils
				.createPassword("secret", "MD5"));
		assertEquals("{SHA}5en6G6MezRroT3XKqkdPOmY/BfQ=", DirectoryUtils
				.createPassword("secret", "SHA"));
	}
}