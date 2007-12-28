import java.io.Serializable;

public class CRLDistributionPoint implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String certificateRevocationList;
	private String authorityRevocationList;
	private String deltaRevocationList;

	public CRLDistributionPoint() {
		super();
	}

	public CRLDistributionPoint(String cn) {
		super();
		this.cn = cn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getCn() {
		return cn;
	}

	public void setCertificateRevocationList(String certificateRevocationList) {
		this.certificateRevocationList = certificateRevocationList;
	}

	public String getCertificateRevocationList() {
		return certificateRevocationList;
	}

	public void setAuthorityRevocationList(String authorityRevocationList) {
		this.authorityRevocationList = authorityRevocationList;
	}

	public String getAuthorityRevocationList() {
		return authorityRevocationList;
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
		buffer.append("MUST: ");
		buffer.append("cn=").append(cn).append(", ");
		buffer.append("MAY: ");
		buffer.append("certificateRevocationList=").append(certificateRevocationList).append(", ");
		buffer.append("authorityRevocationList=").append(authorityRevocationList).append(", ");
		buffer.append("deltaRevocationList=").append(deltaRevocationList);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}