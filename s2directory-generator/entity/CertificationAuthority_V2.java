import java.io.Serializable;

public class CertificationAuthority_V2 implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String deltaRevocationList;

	public CertificationAuthority_V2() {
		super();
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setDeltaRevocationList(String deltaRevocationList) {
		this.deltaRevocationList = deltaRevocationList;
	}

	public String getDeltaRevocationList() {
		return deltaRevocationList;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MAY: ");
		buffer.append("deltaRevocationList=").append(deltaRevocationList);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}