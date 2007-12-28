import java.io.Serializable;

public class ApacheDnsAddressRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String apacheDnsIpAddress;

	public ApacheDnsAddressRecord() {
		super();
	}

	public ApacheDnsAddressRecord(String apacheDnsIpAddress) {
		super();
		this.apacheDnsIpAddress = apacheDnsIpAddress;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
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
		buffer.append("apacheDnsIpAddress=").append(apacheDnsIpAddress);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}