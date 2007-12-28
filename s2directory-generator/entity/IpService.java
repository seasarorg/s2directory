import java.io.Serializable;

public class IpService implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String ipServicePort;
	private String ipServiceProtocol;
	private String description;

	public IpService() {
		super();
	}

	public IpService(String cn, String ipServicePort, String ipServiceProtocol) {
		super();
		this.cn = cn;
		this.ipServicePort = ipServicePort;
		this.ipServiceProtocol = ipServiceProtocol;
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

	public void setIpServicePort(String ipServicePort) {
		this.ipServicePort = ipServicePort;
	}

	public String getIpServicePort() {
		return ipServicePort;
	}

	public void setIpServiceProtocol(String ipServiceProtocol) {
		this.ipServiceProtocol = ipServiceProtocol;
	}

	public String getIpServiceProtocol() {
		return ipServiceProtocol;
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
		buffer.append("ipServicePort=").append(ipServicePort).append(", ");
		buffer.append("ipServiceProtocol=").append(ipServiceProtocol).append(", ");
		buffer.append("MAY: ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}