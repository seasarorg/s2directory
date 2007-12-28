import java.io.Serializable;

public class ApacheServiceConfiguration implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String apacheServicePid;
	private String apacheServiceFactoryPid;

	public ApacheServiceConfiguration() {
		super();
	}

	public ApacheServiceConfiguration(String cn, String apacheServicePid) {
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

	public void setApacheServiceFactoryPid(String apacheServiceFactoryPid) {
		this.apacheServiceFactoryPid = apacheServiceFactoryPid;
	}

	public String getApacheServiceFactoryPid() {
		return apacheServiceFactoryPid;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("cn=").append(cn).append(", ");
		buffer.append("apacheServicePid=").append(apacheServicePid).append(", ");
		buffer.append("MAY: ");
		buffer.append("apacheServiceFactoryPid=").append(apacheServiceFactoryPid);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}