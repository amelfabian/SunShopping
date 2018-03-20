package beans;

public class Alcool extends Produit {
	String nomAlcool;
	String goutAlcool;
	String provenance;

	public Alcool() {
		System.out.println("Alcool.Alcool()");

	}

	public Alcool(Alcool alc) {

		numProduit = alc.numProduit;
		nomAlcool = alc.nomAlcool;
		goutAlcool = alc.goutAlcool;
		provenance = alc.provenance;
	}

	public Alcool(int NumProduit, String NomAlcool, String GoutAlcool, String Provenance, String ImageProduit,String NomProduit, int QuantiteStock, float Prix_unite) {
		super(NumProduit,ImageProduit, NomProduit, QuantiteStock,Prix_unite);
		
		nomAlcool = NomAlcool;
		goutAlcool = GoutAlcool;
		provenance = Provenance;
	}

	public String getNomAlcool() {
		return nomAlcool;
	}

	public void setNomAlcool(String nomAlcool) throws ExceptionMetier {
		if (nomAlcool.replace("", "").compareTo("") == 0)
			throw new ExceptionMetier("la chaine du nom est vide");
		this.nomAlcool = nomAlcool;

	}

	public String getGoutAlcool() {
		return goutAlcool;
	}

	public void setGoutAlcool(String goutAlcool) throws ExceptionMetier {
		if (goutAlcool.replace("", "").compareTo("") == 0)
			throw new ExceptionMetier("la chaine du gout est vide");
		this.goutAlcool = goutAlcool;
	}

	public String getProvenance() {
		return provenance;
	}

	public void setProvenance(String provenance) throws ExceptionMetier {
		if (provenance.replace("", "").compareTo("") == 0)
			throw new ExceptionMetier("la chaine provenance est vide");
		this.provenance = provenance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((goutAlcool == null) ? 0 : goutAlcool.hashCode());
		result = prime * result + ((nomAlcool == null) ? 0 : nomAlcool.hashCode());
		result = prime * result + ((provenance == null) ? 0 : provenance.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alcool other = (Alcool) obj;
		if (goutAlcool == null) {
			if (other.goutAlcool != null)
				return false;
		} else if (!goutAlcool.equals(other.goutAlcool))
			return false;
		if (nomAlcool == null) {
			if (other.nomAlcool != null)
				return false;
		} else if (!nomAlcool.equals(other.nomAlcool))
			return false;
		if (provenance == null) {
			if (other.provenance != null)
				return false;
		} else if (!provenance.equals(other.provenance))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Alcool [nomAlcool=" + nomAlcool + ", goutAlcool=" + goutAlcool + ", provenance=" + provenance + "]";
	}



}
