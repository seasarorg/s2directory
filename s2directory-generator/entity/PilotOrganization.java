import java.io.Serializable;

public class PilotOrganization implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String buildingName;

	public PilotOrganization() {
		super();
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MAY: ");
		buffer.append("buildingName=").append(buildingName);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}