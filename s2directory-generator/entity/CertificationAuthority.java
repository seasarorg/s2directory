import java.io.Serializable;

public class CertificationAuthority implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String authorityRevocationList;
	private String certificateRevocationList;
	private String cACertificate;
	private String crossCertificatePair;

	public CertificationAuthority() {
		super();
	}

	public CertificationAuthority(String authorityRevocationList, String certificateRevocationList, String cACertificate) {
		super();
		this.authorityRevocationList = authorityRevocationList;
		this.certificateRevocationList = certificateRevocationList;
		this.cACertificate = cACertificate;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setAuthorityRevocationList(String authorityRevocationList) {
		this.authorityRevocationList = authorityRevocationList;
	}

	public String getAuthorityRevocationList() {
		return authorityRevocationList;
	}

	public void setCertificateRevocationList(String certificateRevocationList) {
		this.certificateRevocationList = certificateRevocationList;
	}

	public String getCertificateRevocationList() {
		return certificateRevocationList;
	}

	public void setCACertificate(String cACertificate) {
		this.cACertificate = cACertificate;
	}

	public String getCACertificate() {
		return cACertificate;
	}

	public void setCrossCertificatePair(String crossCertificatePair) {
		this.crossCertificatePair = crossCertificatePair;
	}

	public String getCrossCertificatePair() {
		return crossCertificatePair;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("authorityRevocationList=").append(authorityRevocationList).append(", ");
		buffer.append("certificateRevocationList=").append(certificateRevocationList).append(", ");
		buffer.append("cACertificate=").append(cACertificate).append(", ");
		buffer.append("MAY: ");
		buffer.append("crossCertificatePair=").append(crossCertificatePair);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}