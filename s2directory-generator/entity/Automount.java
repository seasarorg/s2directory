import java.io.Serializable;

public class Automount implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String automountInformation;
	private String description;

	public Automount() {
		super();
	}

	public Automount(String cn, String automountInformation) {
		super();
		this.cn = cn;
		this.automountInformation = automountInformation;
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

	public void setAutomountInformation(String automountInformation) {
		this.automountInformation = automountInformation;
	}

	public String getAutomountInformation() {
		return automountInformation;
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
		buffer.append("automountInformation=").append(automountInformation).append(", ");
		buffer.append("MAY: ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}