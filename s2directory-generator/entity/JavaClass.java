import java.io.Serializable;

public class JavaClass implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String fqcn;
	private String byteCode;

	public JavaClass() {
		super();
	}

	public JavaClass(String fqcn, String byteCode) {
		super();
		this.fqcn = fqcn;
		this.byteCode = byteCode;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setFqcn(String fqcn) {
		this.fqcn = fqcn;
	}

	public String getFqcn() {
		return fqcn;
	}

	public void setByteCode(String byteCode) {
		this.byteCode = byteCode;
	}

	public String getByteCode() {
		return byteCode;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("fqcn=").append(fqcn).append(", ");
		buffer.append("byteCode=").append(byteCode);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}