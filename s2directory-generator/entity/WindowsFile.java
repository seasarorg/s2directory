import java.io.Serializable;

public class WindowsFile implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String windowsFilePath;

	public WindowsFile() {
		super();
	}

	public WindowsFile(String windowsFilePath) {
		super();
		this.windowsFilePath = windowsFilePath;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setWindowsFilePath(String windowsFilePath) {
		this.windowsFilePath = windowsFilePath;
	}

	public String getWindowsFilePath() {
		return windowsFilePath;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("windowsFilePath=").append(windowsFilePath);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}