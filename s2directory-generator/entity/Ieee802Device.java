import java.io.Serializable;

public class Ieee802Device implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String macAddress;

	public Ieee802Device() {
		super();
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MAY: ");
		buffer.append("macAddress=").append(macAddress);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}