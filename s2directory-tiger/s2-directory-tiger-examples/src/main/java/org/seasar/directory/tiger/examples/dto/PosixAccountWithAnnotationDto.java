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
package org.seasar.directory.tiger.examples.dto;

import org.seasar.directory.tiger.examples.entity.PosixAccountWithAnnotation;

/**
 * PosixアカウントDtoクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 */
public class PosixAccountWithAnnotationDto extends PosixAccountWithAnnotation {

	private static final long serialVersionUID = 1L;

	/** snを表わします。 */
	private String sn;

	/**
	 * snを取得します。
	 * 
	 * @return sn
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * snを設定します。
	 * 
	 * @param sn
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

}
