import java.io.Serializable;

public class QualityLabelledData implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String dSAQuality;
	private String subtreeMinimumQuality;
	private String subtreeMaximumQuality;

	public QualityLabelledData() {
		super();
	}

	public QualityLabelledData(String dSAQuality) {
		super();
		this.dSAQuality = dSAQuality;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setDSAQuality(String dSAQuality) {
		this.dSAQuality = dSAQuality;
	}

	public String getDSAQuality() {
		return dSAQuality;
	}

	public void setSubtreeMinimumQuality(String subtreeMinimumQuality) {
		this.subtreeMinimumQuality = subtreeMinimumQuality;
	}

	public String getSubtreeMinimumQuality() {
		return subtreeMinimumQuality;
	}

	public void setSubtreeMaximumQuality(String subtreeMaximumQuality) {
		this.subtreeMaximumQuality = subtreeMaximumQuality;
	}

	public String getSubtreeMaximumQuality() {
		return subtreeMaximumQuality;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("dSAQuality=").append(dSAQuality).append(", ");
		buffer.append("MAY: ");
		buffer.append("subtreeMinimumQuality=").append(subtreeMinimumQuality).append(", ");
		buffer.append("subtreeMaximumQuality=").append(subtreeMaximumQuality);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}