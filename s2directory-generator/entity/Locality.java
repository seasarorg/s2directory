import java.io.Serializable;

public class Locality implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String street;
	private String seeAlso;
	private String searchGuide;
	private String st;
	private String l;
	private String description;

	public Locality() {
		super();
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet() {
		return street;
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

	public void setSt(String st) {
		this.st = st;
	}

	public String getSt() {
		return st;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getL() {
		return l;
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
		buffer.append("MAY: ");
		buffer.append("street=").append(street).append(", ");
		buffer.append("seeAlso=").append(seeAlso).append(", ");
		buffer.append("searchGuide=").append(searchGuide).append(", ");
		buffer.append("st=").append(st).append(", ");
		buffer.append("l=").append(l).append(", ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}