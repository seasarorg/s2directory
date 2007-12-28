import java.io.Serializable;

public class CorbaObjectReference implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String corbaIor;

	public CorbaObjectReference() {
		super();
	}

	public CorbaObjectReference(String corbaIor) {
		super();
		this.corbaIor = corbaIor;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setCorbaIor(String corbaIor) {
		this.corbaIor = corbaIor;
	}

	public String getCorbaIor() {
		return corbaIor;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("corbaIor=").append(corbaIor);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}