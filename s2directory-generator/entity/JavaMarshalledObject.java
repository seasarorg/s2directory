import java.io.Serializable;

public class JavaMarshalledObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String javaSerializedData;

	public JavaMarshalledObject() {
		super();
	}

	public JavaMarshalledObject(String javaSerializedData) {
		super();
		this.javaSerializedData = javaSerializedData;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setJavaSerializedData(String javaSerializedData) {
		this.javaSerializedData = javaSerializedData;
	}

	public String getJavaSerializedData() {
		return javaSerializedData;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("javaSerializedData=").append(javaSerializedData);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}