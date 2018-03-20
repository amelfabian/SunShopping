package coucheAccesBD;

import java.sql.Connection;
import java.sql.DriverManager;

public class FabriqueDAO {

	private static FabriqueDAO instance = new FabriqueDAO();

	private Connection sqlConn = null;

	private FabriqueDAO() {

	}

	public static FabriqueDAO getInstance() {
		return instance;
	}

	public void creerConnexion() throws ExceptionAccesBD {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			sqlConn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;" + "database=ProjetAnalyse;"
					+ "user=fabian;" + "Password=Fa.90120535118;");

		} catch (Exception e) {
			throw new ExceptionAccesBD(e.getMessage());
		}
	}

	public AlcoolDAO getInstAlcool() {
		return new AlcoolDAO(sqlConn);

	}
	
	public ChemiseHawaienneDAO getInstChemiseHawaienneDAO() {
		return new ChemiseHawaienneDAO(sqlConn);
	}
	
	
	public ClientDAO getInstClientDAO() {
		return new ClientDAO(sqlConn);
	}
	
	public CommandeDAO getInstCommandeDAO() {
		return new CommandeDAO(sqlConn);
	}
	

	
	public ProduitDAO getInstProduitDAO() {
		return new ProduitDAO(sqlConn);
	}
	
}
