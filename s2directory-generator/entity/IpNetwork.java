import java.io.Serializable;

public class IpNetwork implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String ipNetworkNumber;
	private String ipNetmaskNumber;
	private String l;
	private String description;
	private String manager;

	public IpNetwork() {
		super();
	}

	public IpNetwork(String cn, String ipNetworkNumber) {
		super();
		this.cn = cn;
		this.ipNetworkNumber = ipNetworkNumber;
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

	public void setIpNetworkNumber(String ipNetworkNumber) {
		this.ipNetworkNumber = ipNetworkNumber;
	}

	public String getIpNetworkNumber() {
		return ipNetworkNumber;
	}

	public void setIpNetmaskNumber(String ipNetmaskNumber) {
		this.ipNetmaskNumber = ipNetmaskNumber;
	}

	public String getIpNetmaskNumber() {
		return ipNetmaskNumber;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getL() {
		return l;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getManager() {
		return manager;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("cn=").append(cn).append(", ");
		buffer.append("ipNetworkNumber=").append(ipNetworkNumber).append(", ");
		buffer.append("MAY: ");
		buffer.append("ipNetmaskNumber=").append(ipNetmaskNumber).append(", ");
		buffer.append("l=").append(l).append(", ");
		buffer.append("description=").append(description).append(", ");
		buffer.append("manager=").append(manager);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}