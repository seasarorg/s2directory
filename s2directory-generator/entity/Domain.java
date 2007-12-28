import java.io.Serializable;

public class Domain implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String dc;
	private String associatedName;
	private String o;
	private String description;
	private String businessCategory;
	private String seeAlso;
	private String searchGuide;
	private String userPassword;
	private String l;
	private String st;
	private String street;
	private String physicalDeliveryOfficeName;
	private String postalAddress;
	private String postalCode;
	private String postOfficeBox;
	private String facsimileTelephoneNumber;
	private String internationaliSDNNumber;
	private String telephoneNumber;
	private String teletexTerminalIdentifier;
	private String telexNumber;
	private String preferredDeliveryMethod;
	private String destinationIndicator;
	private String registeredAddress;
	private String x121Address;

	public Domain() {
		super();
	}

	public Domain(String dc) {
		super();
		this.dc = dc;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public String getDc() {
		return dc;
	}

	public void setAssociatedName(String associatedName) {
		this.associatedName = associatedName;
	}

	public String getAssociatedName() {
		return associatedName;
	}

	public void setO(String o) {
		this.o = o;
	}

	public String getO() {
		return o;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setSeeAlso(String seeAlso) {
		this.seeAlso = seeAlso;
	}

	public String getSeeAlso() {
		return seeAlso;
	}

	public void setSearchGuide(String searchGuide) {
		this.searchGuide = searchGuide;
	}

	public String getSearchGuide() {
		return searchGuide;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getL() {
		return l;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getSt() {
		return st;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet() {
		return street;
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

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
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
		buffer.append("MUST: ");
		buffer.append("dc=").append(dc).append(", ");
		buffer.append("MAY: ");
		buffer.append("associatedName=").append(associatedName).append(", ");
		buffer.append("o=").append(o).append(", ");
		buffer.append("description=").append(description).append(", ");
		buffer.append("businessCategory=").append(businessCategory).append(", ");
		buffer.append("seeAlso=").append(seeAlso).append(", ");
		buffer.append("searchGuide=").append(searchGuide).append(", ");
		buffer.append("userPassword=").append(userPassword).append(", ");
		buffer.append("l=").append(l).append(", ");
		buffer.append("st=").append(st).append(", ");
		buffer.append("street=").append(street).append(", ");
		buffer.append("physicalDeliveryOfficeName=").append(physicalDeliveryOfficeName).append(", ");
		buffer.append("postalAddress=").append(postalAddress).append(", ");
		buffer.append("postalCode=").append(postalCode).append(", ");
		buffer.append("postOfficeBox=").append(postOfficeBox).append(", ");
		buffer.append("facsimileTelephoneNumber=").append(facsimileTelephoneNumber).append(", ");
		buffer.append("internationaliSDNNumber=").append(internationaliSDNNumber).append(", ");
		buffer.append("telephoneNumber=").append(telephoneNumber).append(", ");
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