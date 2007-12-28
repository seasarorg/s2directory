import java.io.Serializable;

public class BootableDevice implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String bootFile;
	private String bootParameter;

	public BootableDevice() {
		super();
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setBootFile(String bootFile) {
		this.bootFile = bootFile;
	}

	public String getBootFile() {
		return bootFile;
	}

	public void setBootParameter(String bootParameter) {
		this.bootParameter = bootParameter;
	}

	public String getBootParameter() {
		return bootParameter;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MAY: ");
		buffer.append("bootFile=").append(bootFile).append(", ");
		buffer.append("bootParameter=").append(bootParameter);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}