import java.io.Serializable;

public class JavaNamingReference implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String javaReferenceAddress;
	private String javaFactory;

	public JavaNamingReference() {
		super();
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setJavaReferenceAddress(String javaReferenceAddress) {
		this.javaReferenceAddress = javaReferenceAddress;
	}

	public String getJavaReferenceAddress() {
		return javaReferenceAddress;
	}

	public void setJavaFactory(String javaFactory) {
		this.javaFactory = javaFactory;
	}

	public String getJavaFactory() {
		return javaFactory;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MAY: ");
		buffer.append("javaReferenceAddress=").append(javaReferenceAddress).append(", ");
		buffer.append("javaFactory=").append(javaFactory);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}