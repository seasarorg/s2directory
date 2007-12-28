import java.io.Serializable;

public class NisObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String nisMapEntry;
	private String nisMapName;
	private String description;

	public NisObject() {
		super();
	}

	public NisObject(String cn, String nisMapEntry, String nisMapName) {
		super();
		this.cn = cn;
		this.nisMapEntry = nisMapEntry;
		this.nisMapName = nisMapName;
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

	public void setNisMapEntry(String nisMapEntry) {
		this.nisMapEntry = nisMapEntry;
	}

	public String getNisMapEntry() {
		return nisMapEntry;
	}

	public void setNisMapName(String nisMapName) {
		this.nisMapName = nisMapName;
	}

	public String getNisMapName() {
		return nisMapName;
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
		buffer.append("nisMapEntry=").append(nisMapEntry).append(", ");
		buffer.append("nisMapName=").append(nisMapName).append(", ");
		buffer.append("MAY: ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}