import java.io.Serializable;

public class Country implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String c;
	private String searchGuide;
	private String description;

	public Country() {
		super();
	}

	public Country(String c) {
		super();
		this.c = c;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getC() {
		return c;
	}

	public void setSearchGuide(String searchGuide) {
		this.searchGuide = searchGuide;
	}

	public String getSearchGuide() {
		return searchGuide;
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
		buffer.append("MUST: ");
		buffer.append("c=").append(c).append(", ");
		buffer.append("MAY: ");
		buffer.append("searchGuide=").append(searchGuide).append(", ");
		buffer.append("description=").append(description);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}