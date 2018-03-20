package beans;

import java.util.Date;
import java.util.List;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class Commande {

	int numCommande;
	Date date;
	Time heure;
	int client;
	int montantTotal;
	String indicLivraison;

	public Commande() {
		System.out.println("Commande.Commande()");
	}

	public Commande(Commande com) {
		numCommande = com.numCommande;
		date = com.date;
		heure = com.heure;
		montantTotal = com.montantTotal;
		indicLivraison = com.indicLivraison;

	}

	public Commande(int NumCommande, Date daTe, Time Heure, int Client, int MontantTotal, String IndicLivraison) {
		numCommande = NumCommande;
		date = daTe;
		heure = Heure;
		client = Client;
		montantTotal = MontantTotal;
		indicLivraison = IndicLivraison;

	}

	public int getNumCommande() {
		return numCommande;
	}

	public String getNumCommandeTxt() {
		return Integer.toString(numCommande);
	}

	public void setNumCommande(int numCommande) {
		this.numCommande = numCommande;
	}

	public Date getDate() {
		return date;
	}

	public String getDateTxt() {
		return convertStringToDate(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getHeure() {
		return heure;
	}

	public String getHeureTxt() {
		return convertTimeToString(heure);
	}

	public void setHeure(Time heure) {
		this.heure = heure;
	}

	public int getClient() {
		return client;
	}

	public String getClientTxt() {
		return Integer.toString(client);
	}

	public void setClient(int client) {
		this.client = client;
	}

	public int getMontantTotal() {
		return montantTotal;
	}

	public String getMontantTotalTxt() {
		return Integer.toString(montantTotal);
	}

	public void setMontantTotal(int montantTotal) {
		this.montantTotal = montantTotal;
	}

	public String getIndicLivraison() {
		return indicLivraison;
	}

	public void setIndicLivraison(String indicLivraison) {
		this.indicLivraison = indicLivraison;
	}


	

	

	@Override
	public String toString() {
		return "Commande [numCommande=" + numCommande + ", date=" + date + ", heure=" + heure + ", client=" + client
				+ ", montantTotal=" + montantTotal + ", indicLivraison=" + indicLivraison + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + client;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((heure == null) ? 0 : heure.hashCode());
		result = prime * result + ((indicLivraison == null) ? 0 : indicLivraison.hashCode());
		result = prime * result + montantTotal;
		result = prime * result + numCommande;
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
		Commande other = (Commande) obj;
		if (client != other.client)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (heure == null) {
			if (other.heure != null)
				return false;
		} else if (!heure.equals(other.heure))
			return false;
		if (indicLivraison == null) {
			if (other.indicLivraison != null)
				return false;
		} else if (!indicLivraison.equals(other.indicLivraison))
			return false;
		if (montantTotal != other.montantTotal)
			return false;
		if (numCommande != other.numCommande)
			return false;
		return true;
	}

	public String convertStringToDate(Date indate) {
		String dateString = null;
		SimpleDateFormat sdfr = new SimpleDateFormat("dd/MMM/yyyy");
		/*
		 * you can also use DateFormat reference instead of SimpleDateFormat like this:
		 * DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
		 */
		try {
			dateString = sdfr.format(indate);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return dateString;
	}

	public String convertTimeToString(Time intime) {
		String TimeString = null;
		SimpleDateFormat sdfr = new SimpleDateFormat("hh:mm:ss");

		try {
			TimeString = sdfr.format(intime);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return TimeString;

	}

}
