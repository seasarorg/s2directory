import java.io.Serializable;

public class ApacheFactoryConfiguration implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String apacheServicePid;

	public ApacheFactoryConfiguration() {
		super();
	}

	public ApacheFactoryConfiguration(String cn, String apacheServicePid) {
		super();
		this.cn = cn;
		this.apacheServicePid = apacheServicePid;
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

	public void setApacheServicePid(String apacheServicePid) {
		this.apacheServicePid = apacheServicePid;
	}

	public String getApacheServicePid() {
		return apacheServicePid;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("cn=").append(cn).append(", ");
		buffer.append("apacheServicePid=").append(apacheServicePid);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}