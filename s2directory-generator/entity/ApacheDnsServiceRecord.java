import java.io.Serializable;

public class ApacheDnsServiceRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String apacheDnsServicePriority;
	private String apacheDnsServiceWeight;
	private String apacheDnsServicePort;
	private String apacheDnsDomainName;

	public ApacheDnsServiceRecord() {
		super();
	}

	public ApacheDnsServiceRecord(String apacheDnsServicePriority, String apacheDnsServiceWeight, String apacheDnsServicePort, String apacheDnsDomainName) {
		super();
		this.apacheDnsServicePriority = apacheDnsServicePriority;
		this.apacheDnsServiceWeight = apacheDnsServiceWeight;
		this.apacheDnsServicePort = apacheDnsServicePort;
		this.apacheDnsDomainName = apacheDnsDomainName;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setApacheDnsServicePriority(String apacheDnsServicePriority) {
		this.apacheDnsServicePriority = apacheDnsServicePriority;
	}

	public String getApacheDnsServicePriority() {
		return apacheDnsServicePriority;
	}

	public void setApacheDnsServiceWeight(String apacheDnsServiceWeight) {
		this.apacheDnsServiceWeight = apacheDnsServiceWeight;
	}

	public String getApacheDnsServiceWeight() {
		return apacheDnsServiceWeight;
	}

	public void setApacheDnsServicePort(String apacheDnsServicePort) {
		this.apacheDnsServicePort = apacheDnsServicePort;
	}

	public String getApacheDnsServicePort() {
		return apacheDnsServicePort;
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
		buffer.append("apacheDnsServicePriority=").append(apacheDnsServicePriority).append(", ");
		buffer.append("apacheDnsServiceWeight=").append(apacheDnsServiceWeight).append(", ");
		buffer.append("apacheDnsServicePort=").append(apacheDnsServicePort).append(", ");
		buffer.append("apacheDnsDomainName=").append(apacheDnsDomainName);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}