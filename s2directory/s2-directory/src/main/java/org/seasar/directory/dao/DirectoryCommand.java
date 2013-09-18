/*
 * Copyright 2005-2013 the Seasar Foundation and the Others.
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
package org.seasar.directory.dao;

/**
 * ディレクトリ用処理コマンドインタフェースです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public interface DirectoryCommand {

	/**
	 * 指定された引数を元に処理を実行します。
	 * 
	 * @param args
	 *            引数
	 * @return 結果
	 */
	public Object execute(Object[] args);

}
