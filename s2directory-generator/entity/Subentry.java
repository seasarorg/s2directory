import java.io.Serializable;

public class Subentry implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String subtreeSpecification;

	public Subentry() {
		super();
	}

	public Subentry(String cn, String subtreeSpecification) {
		super();
		this.cn = cn;
		this.subtreeSpecification = subtreeSpecification;
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

	public void setSubtreeSpecification(String subtreeSpecification) {
		this.subtreeSpecification = subtreeSpecification;
	}

	public String getSubtreeSpecification() {
		return subtreeSpecification;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("cn=").append(cn).append(", ");
		buffer.append("subtreeSpecification=").append(subtreeSpecification);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}