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
package org.seasar.directory.generater.parser;

/**
 * パーサ用定数クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class ParserConstant {
	/** output encoding */
	public static final String OUTPUT_ENCODING = "UTF-8";
	/** output directory */
	public static final String OUTPUT_DIR_PATH = "entity"
		+ System.getProperty("file.separator");
	/** indent string */
	public static final String INDENT_STRING = "\t";
}
