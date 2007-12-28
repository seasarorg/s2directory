import java.io.Serializable;

public class Document implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dn;
	private String documentIdentifier;
	private String cn;
	private String description;
	private String seeAlso;
	private String l;
	private String o;
	private String ou;
	private String documentTitle;
	private String documentVersion;
	private String documentAuthor;
	private String documentLocation;
	private String documentPublisher;

	public Document() {
		super();
	}

	public Document(String documentIdentifier) {
		super();
		this.documentIdentifier = documentIdentifier;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDn() {
		return dn;
	}

	public void setDocumentIdentifier(String documentIdentifier) {
		this.documentIdentifier = documentIdentifier;
	}

	public String getDocumentIdentifier() {
		return documentIdentifier;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getCn() {
		return cn;
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

	public void setL(String l) {
		this.l = l;
	}

	public String getL() {
		return l;
	}

	public void setO(String o) {
		this.o = o;
	}

	public String getO() {
		return o;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String getOu() {
		return ou;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentVersion(String documentVersion) {
		this.documentVersion = documentVersion;
	}

	public String getDocumentVersion() {
		return documentVersion;
	}

	public void setDocumentAuthor(String documentAuthor) {
		this.documentAuthor = documentAuthor;
	}

	public String getDocumentAuthor() {
		return documentAuthor;
	}

	public void setDocumentLocation(String documentLocation) {
		this.documentLocation = documentLocation;
	}

	public String getDocumentLocation() {
		return documentLocation;
	}

	public void setDocumentPublisher(String documentPublisher) {
		this.documentPublisher = documentPublisher;
	}

	public String getDocumentPublisher() {
		return documentPublisher;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("DN: ");
		buffer.append("dn=").append(dn).append(", ");
		buffer.append("MUST: ");
		buffer.append("documentIdentifier=").append(documentIdentifier).append(", ");
		buffer.append("MAY: ");
		buffer.append("cn=").append(cn).append(", ");
		buffer.append("description=").append(description).append(", ");
		buffer.append("seeAlso=").append(seeAlso).append(", ");
		buffer.append("l=").append(l).append(", ");
		buffer.append("o=").append(o).append(", ");
		buffer.append("ou=").append(ou).append(", ");
		buffer.append("documentTitle=").append(documentTitle).append(", ");
		buffer.append("documentVersion=").append(documentVersion).append(", ");
		buffer.append("documentAuthor=").append(documentAuthor).append(", ");
		buffer.append("documentLocation=").append(documentLocation).append(", ");
		buffer.append("documentPublisher=").append(documentPublisher);
		return buffer.toString();
	}

	public int hashCode() {
		return dn.hashCode();
	}
}