package couchePresentation;

import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import coucheAccesBD.ExceptionAccesBD;
import coucheAccesBD.FabriqueDAO;

public class GererErreur {
	public static void GererErreurSQL(String classe, String methode, String msgDetail) {
		GererErreurGen(classe,methode,msgDetail);
		try {
			FabriqueDAO.getInstance().creerConnexion();
		} catch (ExceptionAccesBD ex) {
			System.out.println("Problème pour se connecter à la base de données!");
			System.exit(0);
		}
		
	}

	public static void GererErreurGen(String classe, String methode, String msgDetail) {
		try {
			FileWriter fichier = new FileWriter("trace.log",true);
			
			fichier.append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))+ " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + " -- " +
			"classe " + classe + " -- " + "méthode " + " -- " + methode + " -- " + msgDetail + System.lineSeparator());
			fichier.close();
		} catch (Exception e) {
			System.out.println("Problème lors de l'écriture dans le fichier de trace ! ");
		}
		
	}
}
