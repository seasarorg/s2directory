import java.io.Serializable;

public class ApacheDnsMailExchangeRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String apacheDnsMxPreference;
	private String apacheDnsDomainName;

	public ApacheDnsMailExchangeRecord() {
		super();
	}

	public ApacheDnsMailExchangeRecord(String apacheDnsMxPreference, String apacheDnsDomainName) {
		super();
		this.apacheDnsMxPreference = apacheDnsMxPreference;
		this.apacheDnsDomainName = apacheDnsDomainName;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setApacheDnsMxPreference(String apacheDnsMxPreference) {
		this.apacheDnsMxPreference = apacheDnsMxPreference;
	}

	public String getApacheDnsMxPreference() {
		return apacheDnsMxPreference;
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
		buffer.append("apacheDnsMxPreference=").append(apacheDnsMxPreference).append(", ");
		buffer.append("apacheDnsDomainName=").append(apacheDnsDomainName);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}