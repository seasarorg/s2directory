package examples.directorydao.dto;

import examples.directorydao.entity.PosixAccount;

/**
 * PosixアカウントDtoクラスです。
 * 
 * @author Jun Futagawa (Integsystem Corporation)
 * @version $Revision$ $Date$
 */
public class PosixAccountDto extends PosixAccount {
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
