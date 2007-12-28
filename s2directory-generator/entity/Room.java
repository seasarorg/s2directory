import java.io.Serializable;

public class Room implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String roomNumber;
	private String description;
	private String seeAlso;
	private String telephoneNumber;

	public Room() {
		super();
	}

	public Room(String cn) {
		super();
		this.cn = cn;
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

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setSeeAlso(String seeAlso) {
		this.seeAlso = seeAlso;
	}

	public String getSeeAlso() {
		return seeAlso;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("cn=").append(cn).append(", ");
		buffer.append("MAY: ");
		buffer.append("roomNumber=").append(roomNumber).append(", ");
		buffer.append("description=").append(description).append(", ");
		buffer.append("seeAlso=").append(seeAlso).append(", ");
		buffer.append("telephoneNumber=").append(telephoneNumber);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}