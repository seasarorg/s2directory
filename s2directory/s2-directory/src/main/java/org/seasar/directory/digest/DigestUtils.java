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
package org.seasar.directory.digest;

import java.util.Random;

/**
 * ダイジェスト用のユーティリティクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public final class DigestUtils {
	/** 乱数器 */
	private static final Random RANDOM = new Random();
	/** 乱数用の種のデフォルトの長さ */
	private static final int SALT_LENGTH = 4;

	/**
	 * 乱数用の種を生成します。
	 * 
	 * @return 乱数用の種
	 */
	public static byte[] getRandomSalt() {
		return getRandomSalt(SALT_LENGTH);
	}

	/**
	 * 指定された長さの乱数用の種を生成します。
	 * 
	 * @param length
	 * @return 乱数用の種
	 */
	public static byte[] getRandomSalt(int length) {
		return randomString(length).getBytes();
	}

	/**
	 * 指定された文字数のランダムな文字列を生成します。
	 * 
	 * @param count
	 *            文字数
	 * @return ランダムな文字列
	 */
	public static String randomString(int count) {
		return randomString(count, RANDOM);
	}

	/**
	 * 指定された乱数器を用いて指定された文字数のランダムな文字列を生成します。
	 * 
	 * @param count
	 *            文字数
	 * @param random
	 *            乱数器
	 * @return ランダムな文字列
	 */
	public static String randomString(int count, Random random) {
		if (count <= 0) {
			return "";
		}
		int start = ' ';
		int end = 'z' + 1;
		int size = end - start;
		StringBuffer buffer = new StringBuffer();
		while (count-- != 0) {
			char ch = (char)(random.nextInt(size) + start);
			buffer.append(ch);
		}
		return buffer.toString();
	}

	/**
	 * 指定された配列を合成します。
	 * 
	 * @param left
	 *            配列1
	 * @param right
	 *            配列2
	 * @return byte[] 合成した配列
	 */
	public static byte[] concat(byte[] left, byte[] right) {
		byte[] array = new byte[left.length + right.length];
		System.arraycopy(left, 0, array, 0, left.length);
		System.arraycopy(right, 0, array, left.length, right.length);
		return array;
	}

	/**
	 * 指定された配列を指定された位置で2つの配列に分離します。
	 * 
	 * @param src
	 *            分離する配列
	 * @param index
	 *            分離する位置
	 * @return byte[][] 分離した配列
	 */
	public static byte[][] split(byte[] src, int index) {
		byte[] left, right;
		if (src == null || src.length <= index) {
			left = src;
			right = new byte[0];
		} else {
			left = new byte[index];
			right = new byte[src.length - index];
			System.arraycopy(src, 0, left, 0, index);
			System.arraycopy(src, index, right, 0, right.length);
		}
		byte[][] array = { left, right };
		return array;
	}
}
