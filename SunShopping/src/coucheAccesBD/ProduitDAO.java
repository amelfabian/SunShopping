package coucheAccesBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import beans.Produit;

public class ProduitDAO extends gestionnaireDAO<Produit> {

	public ProduitDAO(Connection sqlConn) {
		super(sqlConn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Produit Charger(int num) throws ExceptionAccesBD {

		Produit prod = null;

		try {
			PreparedStatement sqlCmd = SqlConn.prepareCall("select * from Produit" + "where numero_produit = ?");

			sqlCmd.setInt(1, num);

			ResultSet sqlRes = sqlCmd.executeQuery();

			if (sqlRes.next() == true) {
				prod = new Produit(sqlRes.getInt(1), sqlRes.getString(2),sqlRes.getString(3), sqlRes.getInt(4),sqlRes.getFloat(5));

			}
			sqlRes.close();
			return prod;
		} catch (Exception e) {
			throw new ExceptionAccesBD(e.getMessage());
		}

	}

	@Override
	public boolean Ajouter(Produit obj) throws ExceptionAccesBD {
		try {

			PreparedStatement sqlCmd = SqlConn.prepareCall("select max(numero_produit) + 1 from Produit");

			ResultSet sqlRes = sqlCmd.executeQuery();

			int numProd = sqlRes.getInt(1);
			if (sqlRes.wasNull())
				numProd = 1;

			sqlCmd.close();

			sqlCmd = SqlConn.prepareCall("insert into Produit" + "values(?,?,?,?,?)");

			sqlCmd.setInt(1, numProd);
			sqlCmd.setString(2, obj.getImageProduit());
			sqlCmd.setString(3, obj.getNomProduit() );
			sqlCmd.setInt(4, obj.getQuantiteStock());
			sqlCmd.setFloat(5, obj.getPrix_unite());

			return (sqlCmd.executeUpdate() == 0) ? false : true;

		} catch (Exception e) {
			throw new ExceptionAccesBD(e.getMessage());
		}

	}

	@Override
	public boolean Modifier(Produit obj) throws ExceptionAccesBD {
		try {
			PreparedStatement sqlCmd = SqlConn
					.prepareCall("update Produit" 
								+ " set quantiteEnStock = ?" 
								+ " where numero_produit = ?");

			sqlCmd.setInt(1, obj.getQuantiteStock());
			sqlCmd.setInt(2, obj.getNumProduit());

			return (sqlCmd.executeUpdate() == 0) ? false : true;

		} catch (Exception e) {
			throw new ExceptionAccesBD(e.getMessage());
		}

	}

	@Override
	public boolean Supprimer(int num) throws ExceptionAccesBD {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Produit> ListerTous() throws ExceptionAccesBD {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Produit> ListerTous(int numCommande) throws ExceptionAccesBD {
		ArrayList<Produit> liste = new ArrayList<>();
		
		try {
			PreparedStatement sqlCmd = SqlConn.prepareCall(
					"select p.numero_produit,p.nom_produit, p.quantiteEnStock, p.image_produit, p.prix_unite from Produit as p, ListeProduit as lp, Commande as c where p.numero_produit = lp.numero_produits AND"
					+ " lp.numero_commande = c.numero_commande AND c.numero_commande = ? "
					);
			sqlCmd.setInt(1, numCommande);
			ResultSet sqlRes = sqlCmd.executeQuery();
			
			while(sqlRes.next() == true ) 
				liste.add(new Produit( numCommande,
									sqlRes.getString(1),
									sqlRes.getString(2),
									sqlRes.getInt(3),
									sqlRes.getFloat(4)));
			
			sqlRes.close();
		} catch (Exception e) {
			throw new ExceptionAccesBD(e.getMessage());
		}
		return liste;
		
	}

}
