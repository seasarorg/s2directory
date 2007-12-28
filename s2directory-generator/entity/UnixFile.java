import java.io.Serializable;

public class UnixFile implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String unixFilePath;

	public UnixFile() {
		super();
	}

	public UnixFile(String unixFilePath) {
		super();
		this.unixFilePath = unixFilePath;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setUnixFilePath(String unixFilePath) {
		this.unixFilePath = unixFilePath;
	}

	public String getUnixFilePath() {
		return unixFilePath;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("unixFilePath=").append(unixFilePath);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}