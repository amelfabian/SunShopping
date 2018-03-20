package beans;

public class ChemiseHawaienne extends Produit {

	String nomChemise;
	String matierChemise;
	String couleurChemise;
	char tailleChemise;

	public ChemiseHawaienne() {
		System.out.println("ChemiseHawaienne.ChemiseHawaienne()");
	}

	public ChemiseHawaienne(ChemiseHawaienne chem) {
		numProduit = chem.numProduit;
		nomChemise = chem.nomChemise;
		matierChemise = chem.matierChemise;
		couleurChemise = chem.matierChemise;
		tailleChemise = chem.tailleChemise;

	}

	public ChemiseHawaienne(int NumProduit, String NomChemise, String MatierChemise, String CouleurChemise,
			char TailleChemise, String ImageProduit,String NomProduit, int QuantiteStock, float Prix_unite) {
		
		super(NumProduit,ImageProduit,NomProduit, QuantiteStock,Prix_unite);

		nomChemise = NomChemise;
		matierChemise = MatierChemise;
		couleurChemise = CouleurChemise;
		tailleChemise = TailleChemise;

	}

	public String getNomChemise() {
		return nomChemise;
	}

	public void setNomChemise(String nomChemise) throws ExceptionMetier {
		if (nomChemise.replace("", "").compareTo("") == 0)
			throw new ExceptionMetier("la chaine du nom est vide");
		this.nomChemise = nomChemise;
	}

	public String getMatierChemise() {
		return matierChemise;
	}

	public void setMatierChemise(String matierChemise) throws ExceptionMetier {
		if (matierChemise.replace("", "").compareTo("") == 0)
			throw new ExceptionMetier("la chaine matiere est vide");
		this.matierChemise = matierChemise;
	}

	public String getCouleurChemise() {
		return couleurChemise;
	}

	public void setCouleurChemise(String couleurChemise) throws ExceptionMetier {
		if (couleurChemise.replace("", "").compareTo("") == 0)
			throw new ExceptionMetier("la chaine du nom est vide");
		this.couleurChemise = couleurChemise;
	}

	public char getTailleChemise() {
		return tailleChemise;
	}
	
	public String getTailleChemiseTxt() {
		return Character.toString(tailleChemise);
	}

	public void setTailleChemise(char tailleChemise) {
		this.tailleChemise = tailleChemise;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((couleurChemise == null) ? 0 : couleurChemise.hashCode());
		result = prime * result + ((matierChemise == null) ? 0 : matierChemise.hashCode());
		result = prime * result + ((nomChemise == null) ? 0 : nomChemise.hashCode());
		result = prime * result + tailleChemise;
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
		ChemiseHawaienne other = (ChemiseHawaienne) obj;
		if (couleurChemise == null) {
			if (other.couleurChemise != null)
				return false;
		} else if (!couleurChemise.equals(other.couleurChemise))
			return false;
		if (matierChemise == null) {
			if (other.matierChemise != null)
				return false;
		} else if (!matierChemise.equals(other.matierChemise))
			return false;
		if (nomChemise == null) {
			if (other.nomChemise != null)
				return false;
		} else if (!nomChemise.equals(other.nomChemise))
			return false;
		if (tailleChemise != other.tailleChemise)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChemiseHawaienne [nomChemise=" + nomChemise + ", matierChemise=" + matierChemise + ", couleurChemise="
				+ couleurChemise + ", tailleChemise=" + tailleChemise + "]";
	}

	
}
