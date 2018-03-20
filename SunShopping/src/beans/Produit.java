package beans;

public class Produit {
	protected int numProduit;
	protected String imageProduit;
	protected String nomProduit;
	protected int quantiteStock;
	protected float prix_unite;
	
	public Produit() {
		System.out.println("Produit.Produit()");
	}

	public Produit(Produit prod) {
		numProduit = prod.numProduit;
		imageProduit = prod.imageProduit;
		nomProduit = prod.nomProduit;
		quantiteStock = prod.quantiteStock;
		prix_unite = prod.prix_unite;
	}

	public Produit(int NumProduit, String ImageProduit,String NomProduit, int QuantiteStock, float Prix_unite) {
		numProduit = NumProduit;
		imageProduit = ImageProduit;
		nomProduit = NomProduit;
		quantiteStock = QuantiteStock;
		prix_unite = Prix_unite;
	}

	public int getNumProduit() {
		return numProduit;
	}
	
	public String getNumProduitTxt() {
		return Integer.toString(numProduit);
	}

	public void setNumProduit(int numProduit) {
		this.numProduit = numProduit;
	}
	
	public String getImageProduit() {
		return imageProduit;
	}
	

	public void setImageProduit(String imageProduit) {
		this.imageProduit = imageProduit;
	}

	public int getQuantiteStock() {
		return quantiteStock;
	}
	
	public String getQuantiteStockTxt() {
		return Integer.toString(quantiteStock);
	}

	public void setQuantiteStock(int quantiteStock) {
		this.quantiteStock = quantiteStock;
	}

	public float getPrix_unite() {
		return prix_unite;
	}
	
	public String getPrix_uniteTxt() {
		return Float.toString(prix_unite);
	}

	public void setPrix_unite(float prix_unite) {
		this.prix_unite = prix_unite;
	}

	
	public String getNomProduit() {
		return nomProduit;
	}

	public void setNomProduit(String nomProduit) {
		this.nomProduit = nomProduit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imageProduit == null) ? 0 : imageProduit.hashCode());
		result = prime * result + ((nomProduit == null) ? 0 : nomProduit.hashCode());
		result = prime * result + numProduit;
		result = prime * result + Float.floatToIntBits(prix_unite);
		result = prime * result + quantiteStock;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produit other = (Produit) obj;
		if (imageProduit == null) {
			if (other.imageProduit != null)
				return false;
		} else if (!imageProduit.equals(other.imageProduit))
			return false;
		if (nomProduit == null) {
			if (other.nomProduit != null)
				return false;
		} else if (!nomProduit.equals(other.nomProduit))
			return false;
		if (numProduit != other.numProduit)
			return false;
		if (Float.floatToIntBits(prix_unite) != Float.floatToIntBits(other.prix_unite))
			return false;
		if (quantiteStock != other.quantiteStock)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produit [numProduit=" + numProduit + ", imageProduit=" + imageProduit + ", nomProduit=" + nomProduit
				+ ", quantiteStock=" + quantiteStock + ", prix_unite=" + prix_unite + "]";
	}
	
}
