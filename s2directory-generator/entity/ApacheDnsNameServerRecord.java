import java.io.Serializable;

public class ApacheDnsNameServerRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String apacheDnsDomainName;

	public ApacheDnsNameServerRecord() {
		super();
	}

	public ApacheDnsNameServerRecord(String apacheDnsDomainName) {
		super();
		this.apacheDnsDomainName = apacheDnsDomainName;
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

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("apacheDnsDomainName=").append(apacheDnsDomainName);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}