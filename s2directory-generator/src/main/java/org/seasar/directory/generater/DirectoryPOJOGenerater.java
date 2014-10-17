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
package org.seasar.directory.generater;

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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

import org.seasar.directory.generater.parser.ObjectClassParser;
import org.seasar.directory.generater.parser.ParseException;
import org.seasar.directory.generater.parser.Parser;
import org.seasar.directory.generater.parser.ParserConstant;
import org.seasar.directory.generater.util.DirectoryUtil;

/**
 * オブジェクトクラスに対応したPOJOクラスを作成します。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryPOJOGenerater {
	/**
	 * デフォルトコンストラクタです。
	 */
	public DirectoryPOJOGenerater() {
		super();
	}

	public void build(String host, int port) {
		try {
			DirContext context = ConnectionManager.connect(host, port);
			SchemaBuilder schemaBuilder = new SchemaBuilder();
			schemaBuilder.build(context);
			ConnectionManager.close();
			parse(schemaBuilder.getClassDefinition());
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (DirectoryException e) {
			System.err.println(e);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close();
		}
	}

	public void parse(SchemaMap definition) throws ParseException, IOException {
		Set set = definition.keySet();
		Iterator iter = set.iterator();
		while (iter.hasNext()) {
			String name = String.valueOf(iter.next());
			try {
				SchemaMap map =
					DirectoryUtil.getNoMultipleDirectoryDefinitionMap(
						(Attributes)definition.get(name),
						"NAME");
				if (map.size() > 1)
					parse(map);
				else if (map.size() == 1)
					parse(name, (Attributes)definition.get(name));
				else { // map.size() == 0: no name objectClass
						// do nothing.
				}
			} catch (NamingException e) {
				throw new ParseException("", e);
			}
		}
	}

	public void parse(String name, Attributes attrs) throws ParseException,
			IOException {
		String fileName =
			DirectoryUtil.getObjectClassName(DirectoryUtil.getFirstUpperString(name))
				+ ".java";
		// 出力エンコーディング
		String encoding = ParserConstant.OUTPUT_ENCODING;
		// 出力先ディレクトリ
		String outputDir = ParserConstant.OUTPUT_DIR_PATH;
		File outputDirFile = new File(outputDir);
		if (!outputDirFile.exists()) {
			outputDirFile.mkdir();
		}
		// パーサを生成します。
		Parser parser = new ObjectClassParser();
		// パースし、結果をファイルに出力します。
		File target = new File(outputDir + fileName);
		FileOutputStream out = new FileOutputStream(target);
		BufferedWriter writer =
			new BufferedWriter(new OutputStreamWriter(out, encoding));
		parser.parse(attrs, writer);
		writer.close();
	}

	public static void main(String[] args) {
		DirectoryPOJOGenerater main = new DirectoryPOJOGenerater();
		// プロパティファイルの読み込み
		String host = "localhost";
		int port = 389;
		if (args.length == 2) {
			// 引数にある設定を使用
			host = args[0];
			port = Integer.parseInt(args[1]);
		}
		// ビルドの開始
		main.build(host, port);
		System.out.println(ParserConstant.OUTPUT_DIR_PATH + "ディレクトリ以下に作成しました。");
	}
}
