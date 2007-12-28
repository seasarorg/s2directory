import java.io.Serializable;

public class ApacheDnsReferralAddress implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String apacheDnsDomainName;
	private String apacheDnsIpAddress;

	public ApacheDnsReferralAddress() {
		super();
	}

	public ApacheDnsReferralAddress(String apacheDnsDomainName, String apacheDnsIpAddress) {
		super();
		this.apacheDnsDomainName = apacheDnsDomainName;
		this.apacheDnsIpAddress = apacheDnsIpAddress;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setApacheDnsDomainName(String apacheDnsDomainName) {
		this.apacheDnsDomainName = apacheDnsDomainName;
	}

	public String getApacheDnsDomainName() {
		return apacheDnsDomainName;
	}

	public void setApacheDnsIpAddress(String apacheDnsIpAddress) {
		this.apacheDnsIpAddress = apacheDnsIpAddress;
	}

	public String getApacheDnsIpAddress() {
		return apacheDnsIpAddress;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("apacheDnsDomainName=").append(apacheDnsDomainName).append(", ");
		buffer.append("apacheDnsIpAddress=").append(apacheDnsIpAddress);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}