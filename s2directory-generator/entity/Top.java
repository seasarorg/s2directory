import java.io.Serializable;

public class Top implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String objectClass;

	public Top() {
		super();
	}

	public Top(String objectClass) {
		super();
		this.objectClass = objectClass;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setObjectClass(String objectClass) {
		this.objectClass = objectClass;
	}

	public String getObjectClass() {
		return objectClass;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("objectClass=").append(objectClass);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}