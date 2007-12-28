import java.io.Serializable;

public class JavaObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String javaClassName;
	private String javaClassNames;
	private String javaCodebase;
	private String javaDoc;
	private String description;

	public JavaObject() {
		super();
	}

	public JavaObject(String javaClassName) {
		super();
		this.javaClassName = javaClassName;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setJavaClassName(String javaClassName) {
		this.javaClassName = javaClassName;
	}

	public String getJavaClassName() {
		return javaClassName;
	}

	public void setJavaClassNames(String javaClassNames) {
		this.javaClassNames = javaClassNames;
	}

	public String getJavaClassNames() {
		return javaClassNames;
	}

	public void setJavaCodebase(String javaCodebase) {
		this.javaCodebase = javaCodebase;
	}

	public String getJavaCodebase() {
		return javaCodebase;
	}

	public void setJavaDoc(String javaDoc) {
		this.javaDoc = javaDoc;
	}

	public String getJavaDoc() {
		return javaDoc;
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
		buffer.append("javaClassName=").append(javaClassName).append(", ");
		buffer.append("MAY: ");
		buffer.append("javaClassNames=").append(javaClassNames).append(", ");
		buffer.append("javaCodebase=").append(javaCodebase).append(", ");
		buffer.append("javaDoc=").append(javaDoc).append(", ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}