import java.io.Serializable;

public class RFC822localPart implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String cn;
	private String sn;
	private String description;
	private String seeAlso;
	private String telephoneNumber;
	private String physicalDeliveryOfficeName;
	private String postalAddress;
	private String postalCode;
	private String postOfficeBox;
	private String street;
	private String facsimileTelephoneNumber;
	private String internationaliSDNNumber;
	private String teletexTerminalIdentifier;
	private String telexNumber;
	private String preferredDeliveryMethod;
	private String destinationIndicator;
	private String registeredAddress;
	private String x121Address;

	public RFC822localPart() {
		super();
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

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getSn() {
		return sn;
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

	public void setPhysicalDeliveryOfficeName(String physicalDeliveryOfficeName) {
		this.physicalDeliveryOfficeName = physicalDeliveryOfficeName;
	}

	public String getPhysicalDeliveryOfficeName() {
		return physicalDeliveryOfficeName;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostOfficeBox(String postOfficeBox) {
		this.postOfficeBox = postOfficeBox;
	}

	public String getPostOfficeBox() {
		return postOfficeBox;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet() {
		return street;
	}

	public void setFacsimileTelephoneNumber(String facsimileTelephoneNumber) {
		this.facsimileTelephoneNumber = facsimileTelephoneNumber;
	}

	public String getFacsimileTelephoneNumber() {
		return facsimileTelephoneNumber;
	}

	public void setInternationaliSDNNumber(String internationaliSDNNumber) {
		this.internationaliSDNNumber = internationaliSDNNumber;
	}

	public String getInternationaliSDNNumber() {
		return internationaliSDNNumber;
	}

	public void setTeletexTerminalIdentifier(String teletexTerminalIdentifier) {
		this.teletexTerminalIdentifier = teletexTerminalIdentifier;
	}

	public String getTeletexTerminalIdentifier() {
		return teletexTerminalIdentifier;
	}

	public void setTelexNumber(String telexNumber) {
		this.telexNumber = telexNumber;
	}

	public String getTelexNumber() {
		return telexNumber;
	}

	public void setPreferredDeliveryMethod(String preferredDeliveryMethod) {
		this.preferredDeliveryMethod = preferredDeliveryMethod;
	}

	public String getPreferredDeliveryMethod() {
		return preferredDeliveryMethod;
	}

	public void setDestinationIndicator(String destinationIndicator) {
		this.destinationIndicator = destinationIndicator;
	}

	public String getDestinationIndicator() {
		return destinationIndicator;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setX121Address(String x121Address) {
		this.x121Address = x121Address;
	}

	public String getX121Address() {
		return x121Address;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MAY: ");
		buffer.append("cn=").append(cn).append(", ");
		buffer.append("sn=").append(sn).append(", ");
		buffer.append("description=").append(description).append(", ");
		buffer.append("seeAlso=").append(seeAlso).append(", ");
		buffer.append("telephoneNumber=").append(telephoneNumber).append(", ");
		buffer.append("physicalDeliveryOfficeName=").append(physicalDeliveryOfficeName).append(", ");
		buffer.append("postalAddress=").append(postalAddress).append(", ");
		buffer.append("postalCode=").append(postalCode).append(", ");
		buffer.append("postOfficeBox=").append(postOfficeBox).append(", ");
		buffer.append("street=").append(street).append(", ");
		buffer.append("facsimileTelephoneNumber=").append(facsimileTelephoneNumber).append(", ");
		buffer.append("internationaliSDNNumber=").append(internationaliSDNNumber).append(", ");
		buffer.append("teletexTerminalIdentifier=").append(teletexTerminalIdentifier).append(", ");
		buffer.append("telexNumber=").append(telexNumber).append(", ");
		buffer.append("preferredDeliveryMethod=").append(preferredDeliveryMethod).append(", ");
		buffer.append("destinationIndicator=").append(destinationIndicator).append(", ");
		buffer.append("registeredAddress=").append(registeredAddress).append(", ");
		buffer.append("x121Address=").append(x121Address);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}