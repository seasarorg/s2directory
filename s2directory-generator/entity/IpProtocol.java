import java.io.Serializable;

public class IpProtocol implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String ipProtocolNumber;
	private String description;

	public IpProtocol() {
		super();
	}

	public IpProtocol(String cn, String ipProtocolNumber, String description) {
		super();
		this.cn = cn;
		this.ipProtocolNumber = ipProtocolNumber;
		this.description = description;
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

	public void setIpProtocolNumber(String ipProtocolNumber) {
		this.ipProtocolNumber = ipProtocolNumber;
	}

	public String getIpProtocolNumber() {
		return ipProtocolNumber;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("cn=").append(cn).append(", ");
		buffer.append("ipProtocolNumber=").append(ipProtocolNumber).append(", ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}