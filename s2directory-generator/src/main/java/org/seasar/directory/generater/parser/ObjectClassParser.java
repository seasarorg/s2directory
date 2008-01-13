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
package org.seasar.directory.generater.parser;

import java.io.BufferedWriter;
import java.io.IOException;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.seasar.directory.generater.AttributeField;
import org.seasar.directory.generater.util.DirectoryUtil;

/**
 * オブジェクトクラスのパーサクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class ObjectClassParser implements Parser {
	/**
	 * パースを実行します。
	 */
	public void parse(Attributes attrs, BufferedWriter writer)
			throws ParseException, IOException {
		String objectClassName =
			DirectoryUtil.getObjectClassName(DirectoryUtil
				.getSingleValue(DirectoryUtil.getAttribute(attrs, "NAME")));
		StringBuffer buffer = new StringBuffer();
		// インポートを生成します。
		buffer.append(createImportDefinition());
		// クラス宣言部を生成します。
		buffer.append(createClassDefinition(objectClassName));
		// フィールド名の配列を生成します。
		Attribute mustAttribute = DirectoryUtil.getAttribute(attrs, "MUST");
		Attribute mayAttribute = DirectoryUtil.getAttribute(attrs, "MAY");
		AttributeField[] mustNames =
			DirectoryUtil.createFieldNames(mustAttribute);
		AttributeField[] mayNames =
			DirectoryUtil.createFieldNames(mayAttribute);
		mayNames = DirectoryUtil.getUniqueStringArray(mayNames, mustNames);
		// フィールド宣言部を生成します。
		buffer.append(createSerialVersionDefinition());
		// dnのフィールド宣言を生成します。
		AttributeField dn = new AttributeField("dn", "dn");
		buffer.append(createOneFieldDefinition(dn));
		// dn以外のフィールド宣言を生成します。
		buffer.append(createFieldDefinition(mustNames));
		buffer.append(createFieldDefinition(mayNames));
		buffer.append("\n");
		// コンストラクタ部を生成します。
		buffer.append(createConstructerMethod(objectClassName, mustNames));
		// セッター、ゲッター部を生成します。
		// dnのセッター、ゲッターを生成します。
		buffer.append(createSetter(dn));
		buffer.append(createGetter(dn));
		// dn以外のセッター、ゲッターを生成します。
		buffer.append(createMethod(mustNames));
		buffer.append(createMethod(mayNames));
		// toString部を生成します。
		buffer.append(createToStringMethod(dn, mustNames, mayNames));
		// hashCode部を生成します。
		buffer.append(createHashCode(new String[] { "dn" }));
		// if (mustNames.length > 0) {
		// buffer.append(createHashCode(mustNames));
		// } else {
		// buffer.append(createHashCode(mayNames));
		// }
		// クラス宣言を閉じます。
		buffer.append("}");
		writer.write(buffer.toString());
	}

	/**
	 * import宣言部を生成します。
	 * 
	 * @return string
	 */
	protected String createImportDefinition() {
		return "import java.io.Serializable;\n\n";
	}

	/**
	 * クラス宣言部を生成します。
	 * 
	 * @param className
	 * @return
	 */
	protected String createClassDefinition(String className) {
		return "public class " + DirectoryUtil.getFirstUpperString(className)
			+ " implements Serializable {\n";
	}

	/**
	 * シリアルバージョンの宣言部を生成します。
	 * 
	 * @return
	 */
	protected String createSerialVersionDefinition() {
		return ParserUtil.getIndent(1)
			+ "private static final long serialVersionUID = 1L;\n";
	}

	/**
	 * フィールド宣言部を生成します。
	 * 
	 * @param attrNames
	 * @return
	 */
	protected String createFieldDefinition(AttributeField[] attrNames) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < attrNames.length; i++) {
			buffer.append(createOneFieldDefinition(attrNames[i]));
		}
		return buffer.toString();
	}

	/**
	 * 指定されたフィールドの宣言を生成します。
	 * 
	 * @param attributeField
	 * @return
	 */
	protected String createOneFieldDefinition(AttributeField attributeField) {
		String filedDefinition =
			ParserUtil.getIndent(1) + "private String "
				+ attributeField.getFieldName() + ";\n";
		if (attributeField.isNeedAnnotation()) {
			filedDefinition =
				"public static final String " + attributeField.getFieldName()
					+ "_COLUMN = \"" + attributeField.getAttributeName()
					+ "\";\n" + filedDefinition;
			return filedDefinition;
		} else {
			return filedDefinition;
		}
	}

	/**
	 * コンストラクタ部を生成します。
	 * 
	 * @param methodName
	 * @param attrNames
	 * @return
	 */
	protected String createConstructerMethod(String methodName,
			AttributeField[] attrNames) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(createOneConstructerMethod(
			methodName,
			new AttributeField[] {}));
		if (attrNames.length > 0) {
			buffer.append(createOneConstructerMethod(methodName, attrNames));
		}
		return buffer.toString();
	}

	protected String createOneConstructerMethod(String methodName,
			AttributeField[] attrNames) {
		StringBuffer buffer = new StringBuffer(ParserUtil.getIndent(1));
		buffer.append("public ");
		buffer.append(DirectoryUtil.getFirstUpperString(methodName));
		buffer.append("(");
		StringBuffer ibuffer = new StringBuffer();
		for (int i = 0; i < attrNames.length; i++) {
			ibuffer.append("String " + attrNames[i].getFieldName() + ", ");
		}
		buffer.append(ParserUtil.deleteLast(ibuffer, 2));
		buffer.append(") {\n");
		buffer.append(ParserUtil.getIndent(2));
		buffer.append("super();\n");
		for (int i = 0; i < attrNames.length; i++) {
			buffer.append(setInstance(attrNames[i]));
		}
		buffer.append(ParserUtil.getIndent(1) + "}\n\n");
		return buffer.toString();
	}

	/**
	 * セッター、ゲッター部を生成します。
	 * 
	 * @param attrNames
	 * @return
	 * @throws ParseException
	 */
	protected String createMethod(AttributeField[] attrNames)
			throws ParseException {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < attrNames.length; i++) {
			buffer.append(createSetter(attrNames[i]));
			buffer.append(createGetter(attrNames[i]));
		}
		return buffer.toString();
	}

	/**
	 * セッターを生成します。
	 * 
	 * @param instanceName
	 * @return
	 */
	protected String createSetter(AttributeField instanceName) {
		StringBuffer buffer = new StringBuffer(ParserUtil.getIndent(1));
		buffer.append("public void ");
		buffer.append(DirectoryUtil.getSetMethodName(instanceName
			.getFieldName()));
		buffer.append("(String " + instanceName.getFieldName() + ") {\n");
		buffer.append(setInstance(instanceName));
		buffer.append(ParserUtil.getIndent(1) + "}\n\n");
		return buffer.toString();
	}

	protected String setInstance(AttributeField instanceName) {
		StringBuffer buffer = new StringBuffer(ParserUtil.getIndent(2));
		buffer.append("this.");
		buffer.append(instanceName.getFieldName());
		buffer.append(" = ");
		buffer.append(instanceName.getFieldName());
		buffer.append(";\n");
		return buffer.toString();
	}

	/**
	 * ゲッターを生成します。
	 * 
	 * @param instanceName
	 * @return
	 */
	protected String createGetter(AttributeField instanceName) {
		StringBuffer buffer = new StringBuffer(ParserUtil.getIndent(1));
		buffer.append("public String ");
		buffer.append(DirectoryUtil.getGetMethodName(instanceName
			.getFieldName()));
		buffer.append("() {\n");
		buffer.append(ParserUtil.getIndent(2) + "return "
			+ instanceName.getFieldName() + ";\n");
		buffer.append(ParserUtil.getIndent(1) + "}\n\n");
		return buffer.toString();
	}

	/**
	 * toString部を生成します。
	 * 
	 * @param mustNames
	 * @param mayNames
	 * @return
	 */
	protected String createToStringMethod(AttributeField dnName,
			AttributeField[] mustNames, AttributeField[] mayNames) {
		StringBuffer buffer = new StringBuffer(ParserUtil.getIndent(1));
		buffer.append("public String toString() {\n");
		String INDENT = ParserUtil.getIndent(2);
		buffer.append(INDENT + "StringBuffer buffer = new StringBuffer();\n");
		// DN
		buffer.append(INDENT + "buffer.append(\"DN: \");\n");
		buffer.append(INDENT + "buffer");
		buffer.append(".append(\"" + dnName.getFieldName() + "=\")");
		buffer.append(".append(" + dnName.getFieldName() + ")");
		buffer.append(".append(\", \");\n");
		// MUST
		if (mustNames.length > 0) {
			buffer.append(INDENT + "buffer.append(\"MUST: \");\n");
			for (int i = 0; i < mustNames.length; i++) {
				buffer.append(INDENT + "buffer");
				buffer.append(".append(\"" + mustNames[i].getFieldName()
					+ "=\")");
				buffer.append(".append(" + mustNames[i].getFieldName() + ")");
				buffer.append(".append(\", \");\n");
			}
		}
		// MAY
		if (mayNames.length > 0) {
			buffer.append(INDENT + "buffer.append(\"MAY: \");\n");
			for (int i = 0; i < mayNames.length; i++) {
				buffer.append(INDENT + "buffer");
				buffer.append(".append(\"" + mayNames[i].getFieldName()
					+ "=\")");
				buffer.append(".append(" + mayNames[i].getFieldName() + ")");
				buffer.append(".append(\", \");\n");
			}
		}
		int index = buffer.lastIndexOf(".");
		if (index != -1) {
			buffer.delete(index, buffer.length());
			buffer.append(";\n");
		}
		buffer.append(INDENT + "return buffer.toString();\n");
		buffer.append(ParserUtil.getIndent(1) + "}\n\n");
		return buffer.toString();
	}

	/**
	 * hashCode部を生成します。
	 * 
	 * @param instanceNames
	 * @return
	 */
	protected String createHashCode(String[] instanceNames) {
		StringBuffer buffer = new StringBuffer(ParserUtil.getIndent(1));
		buffer.append("public int hashCode() {\n");
		String INDENT = ParserUtil.getIndent(2);
		buffer.append(INDENT + "return ");
		for (int i = 0; i < instanceNames.length; i++) {
			buffer.append(instanceNames[i] + ".hashCode()");
			buffer.append(" + ");
		}
		int index = buffer.lastIndexOf(" + ");
		if (index != -1) {
			buffer.delete(index, buffer.length());
			buffer.append(";\n");
		}
		buffer.append(ParserUtil.getIndent(1) + "}\n");
		return buffer.toString();
	}
}