/*
 * Copyright 2005-2007 the Seasar Foundation and the Others.
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
package org.seasar.directory.dao.creator;

import org.seasar.framework.container.ComponentCustomizer;
import org.seasar.framework.container.creator.ComponentCreatorImpl;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.convention.NamingConvention;

/**
 * SMART deployにおいてDirectoryDaoのコンポーネント定義を作成する{@link ComponentCreator}の実装クラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Date::                           $
 */
public class DirectoryDaoCreator extends ComponentCreatorImpl {
	/**
	 * インスタンスを作成します。
	 * 
	 * @param namingConvention
	 *            ネーミングコンベンション
	 */
	public DirectoryDaoCreator(NamingConvention namingConvention) {
		super(namingConvention);
		setNameSuffix("Directory" + namingConvention.getDaoSuffix());
		setInstanceDef(InstanceDefFactory.PROTOTYPE);
		setEnableInterface(true);
		setEnableAbstract(true);
	}

	/**
	 * DirectoryDao用コンポーネントカスタマイザを取得します。
	 * 
	 * @return コンポーネントカスタマイザ
	 */
	public ComponentCustomizer getDirectoryDaoCustomizer() {
		return getCustomizer();
	}

	/**
	 * DirectoryDao用コンポーネントカスタマイザを設定します。
	 * 
	 * @param customizer
	 *            コンポーネントカスタマイザ
	 */
	public void setDirectoryDaoCustomizer(ComponentCustomizer customizer) {
		setCustomizer(customizer);
	}
}