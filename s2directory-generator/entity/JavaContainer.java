import java.io.Serializable;

public class JavaContainer implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;

	public JavaContainer() {
		super();
	}

	public JavaContainer(String cn) {
		super();
		this.cn = cn;
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

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("cn=").append(cn);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}