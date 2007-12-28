import java.io.Serializable;

public class CollectiveAttributeSubentry implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;

	public CollectiveAttributeSubentry() {
		super();
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}