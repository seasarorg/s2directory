import java.io.Serializable;

public class NisMap implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String nisMapName;
	private String description;

	public NisMap() {
		super();
	}

	public NisMap(String nisMapName) {
		super();
		this.nisMapName = nisMapName;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
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
		buffer.append("nisMapName=").append(nisMapName).append(", ");
		buffer.append("MAY: ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}