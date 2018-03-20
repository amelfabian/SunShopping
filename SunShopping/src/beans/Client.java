package beans;

public class Client {
	int numClient;
	String nomClient;
	String prenomClient;
	String adresseClient;
	String emailClient;
	String passwdClient;
	
	public Client() {
		System.out.println("Client.Client()");
	}
	
	public Client(Client cli) {
		numClient = cli.numClient;
		nomClient = cli.nomClient;
		prenomClient = cli.prenomClient;
		adresseClient = cli.adresseClient;
		emailClient = cli.adresseClient;
		passwdClient = cli.passwdClient;
	}
	
	public Client(int NumClient, String NomClient, String PrenomClient, String AdresseClient, String EmailClient,
			String PasswdClient) {
		numClient = NumClient;
		nomClient = NomClient;
		prenomClient = PrenomClient;
		adresseClient = AdresseClient;
		emailClient = EmailClient;
		passwdClient = PasswdClient;
		
	}
	public int getNumClient() {
		return numClient;
	}
	public void setNumClient(int numClient) {
		this.numClient = numClient;
	}
	public String getNomClient() {
		return nomClient;
	}
	public void setNomClient(String nomClient) throws ExceptionMetier {
		if (nomClient.replace("", "").compareTo("") == 0)
			throw new ExceptionMetier("la chaine du nom est vide");
		this.nomClient = nomClient;
	}
	public String getPrenomClient() {
		return prenomClient;
	}
	public void setPrenomClient(String prenomClient) throws ExceptionMetier{
		if (prenomClient.replace("", "").compareTo("") == 0)
			throw new ExceptionMetier("la chaine du prenom est vide");
		this.prenomClient = prenomClient;
	}
	public String getAdresseClient() {
		return adresseClient;
	}
	public void setAdresseClient(String adresseClient) {
		this.adresseClient = adresseClient;
	}
	public String getEmailClient() {
		return emailClient;
	}
	public void setEmailClient(String emailClient) throws ExceptionMetier {
		if (emailClient.replace("", "").compareTo("") == 0)
			throw new ExceptionMetier("la chaine email est vide");
		this.emailClient = emailClient;
	}
	public String getPasswdClient() {
		return passwdClient;
	}
	public void setPasswdClient(String passwdClient) throws ExceptionMetier{
		if (passwdClient.replace("", "").compareTo("") == 0)
			throw new ExceptionMetier("la chaine du nom est vide");
		this.passwdClient = passwdClient;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresseClient == null) ? 0 : adresseClient.hashCode());
		result = prime * result + ((emailClient == null) ? 0 : emailClient.hashCode());
		result = prime * result + ((nomClient == null) ? 0 : nomClient.hashCode());
		result = prime * result + numClient;
		result = prime * result + ((passwdClient == null) ? 0 : passwdClient.hashCode());
		result = prime * result + ((prenomClient == null) ? 0 : prenomClient.hashCode());
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
		Client other = (Client) obj;
		if (adresseClient == null) {
			if (other.adresseClient != null)
				return false;
		} else if (!adresseClient.equals(other.adresseClient))
			return false;
		if (emailClient == null) {
			if (other.emailClient != null)
				return false;
		} else if (!emailClient.equals(other.emailClient))
			return false;
		if (nomClient == null) {
			if (other.nomClient != null)
				return false;
		} else if (!nomClient.equals(other.nomClient))
			return false;
		if (numClient != other.numClient)
			return false;
		if (passwdClient == null) {
			if (other.passwdClient != null)
				return false;
		} else if (!passwdClient.equals(other.passwdClient))
			return false;
		if (prenomClient == null) {
			if (other.prenomClient != null)
				return false;
		} else if (!prenomClient.equals(other.prenomClient))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Client [numClient=" + numClient + ", nomClient=" + nomClient + ", prenomClient=" + prenomClient
				+ ", adressePrenom=" + adresseClient + ", emailClient=" + emailClient + ", passwdClient=" + passwdClient
				+ "]";
	}
	
	
}
