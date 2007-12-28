import java.io.Serializable;

public class OncRpc implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String oncRpcNumber;
	private String description;

	public OncRpc() {
		super();
	}

	public OncRpc(String cn, String oncRpcNumber, String description) {
		super();
		this.cn = cn;
		this.oncRpcNumber = oncRpcNumber;
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

	public void setOncRpcNumber(String oncRpcNumber) {
		this.oncRpcNumber = oncRpcNumber;
	}

	public String getOncRpcNumber() {
		return oncRpcNumber;
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
		buffer.append("oncRpcNumber=").append(oncRpcNumber).append(", ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}