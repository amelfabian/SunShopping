package coucheAccesBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Alcool;
import beans.ChemiseHawaienne;

public class ChemiseHawaienneDAO extends gestionnaireDAO<ChemiseHawaienne> {

	public ChemiseHawaienneDAO(Connection sqlConn) {
		super(sqlConn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ChemiseHawaienne Charger(int num) throws ExceptionAccesBD {
		ChemiseHawaienne chemise = null;
		try {
			PreparedStatement sqlCmd = SqlConn.prepareCall("select * from ChemiseHawaienne where numProd = ?");
			sqlCmd.setInt(1, num);

			ResultSet sqlRes = sqlCmd.executeQuery();

			if (sqlRes.next() == true) {
				chemise = new ChemiseHawaienne(num,
						sqlRes.getString(1),
						sqlRes.getString(2),
						sqlRes.getString(3),
						sqlRes.getString(4).charAt(0),
						sqlRes.getString(5),
						sqlRes.getString(6),
						sqlRes.getInt(7),
						sqlRes.getFloat(8));
			}
			sqlRes.close();
			return chemise;
		} catch (SQLException e) {
			throw new ExceptionAccesBD(e.getMessage());
		}

	}

	@Override
	public boolean Ajouter(ChemiseHawaienne obj) throws ExceptionAccesBD {
		try {
			SqlConn.setAutoCommit(false);
			PreparedStatement sqlCmd = SqlConn.prepareCall("select max(numero_produit) + 1 from Produit");
			ResultSet sqlRes = sqlCmd.executeQuery();
			sqlRes.next();

			int numChemise = sqlRes.getInt(1);
			if (sqlRes.wasNull())
				numChemise = 1;

			sqlCmd.close();

			sqlCmd = SqlConn.prepareCall("insert into Produit values(?,?,?,?,?)");
			sqlCmd.setInt(1, numChemise);
			sqlCmd.setInt(2, obj.getQuantiteStock());
			sqlCmd.setString(3, obj.getNomProduit());
			sqlCmd.setString(4, obj.getImageProduit());
			sqlCmd.setFloat(5, obj.getPrix_unite());
			sqlCmd.executeUpdate();
			
			sqlCmd = SqlConn.prepareCall("insert into ChemiseHawaienne values(?,?,?,?,?)");

			sqlCmd.setInt(1, numChemise);
			sqlCmd.setString(2, obj.getNomChemise());
			sqlCmd.setString(3, obj.getMatierChemise());
			sqlCmd.setString(4, obj.getCouleurChemise());
			Character c = obj.getTailleChemise();
			sqlCmd.setString(5, c.toString());
			int iRes = sqlCmd.executeUpdate();
			SqlConn.setAutoCommit(true);

			
			return (iRes == 0) ? false : true;

		} catch (SQLException e) {
			throw new ExceptionAccesBD(e.getMessage());
		}

	}

	@Override
	public boolean Modifier(ChemiseHawaienne obj) throws ExceptionAccesBD {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Supprimer(int num) throws ExceptionAccesBD {
		try {
			SqlConn.setAutoCommit(false);
			PreparedStatement sqlCmd = SqlConn.prepareStatement("delete from Produit where numero_produit = ? ");
			sqlCmd.setInt(1, num);
			sqlCmd.executeUpdate();
			sqlCmd = SqlConn.prepareCall("delete from ChemiseHawaienne where numero_produit = ? ");
			sqlCmd.setInt(1,num);
			int iRes = sqlCmd.executeUpdate();
			SqlConn.commit();
			SqlConn.setAutoCommit(true);
			return (iRes == 0)? false : true;
			
		}catch(Exception e) {
			try {
					SqlConn.rollback();
					SqlConn.setAutoCommit(true);
				} catch (Exception e2) {
				
				}
				throw new ExceptionAccesBD(e.getMessage());
			}
			
			}	
	

	@Override
	public List<ChemiseHawaienne> ListerTous() throws ExceptionAccesBD {
	ArrayList<ChemiseHawaienne> liste = new ArrayList<ChemiseHawaienne>();
		
		try {
			
			PreparedStatement sqlCmd = SqlConn.prepareCall("select p.numero_produit, c.nomChemise,c.matiereChemise,c.couleurChemise,c.tailleChemise,p.image_produit,p.nom_produit,p.quantiteEnStock,p.prix_unite from ChemiseHawaienne as c , Produit as p where c.numProd = p.numero_produit order by nomChemise asc" );
			
			ResultSet sqlRes = sqlCmd.executeQuery();
			
			while (sqlRes.next() == true)
				liste.add(new ChemiseHawaienne(sqlRes.getInt(1),
									sqlRes.getString(2),
									sqlRes.getString(3),
									sqlRes.getString(4),
									sqlRes.getString(5).charAt(0),
									sqlRes.getString(6),
									sqlRes.getString(7),
									sqlRes.getInt(8),
									sqlRes.getFloat(9)));
			sqlRes.close();
			
			
		} catch (Exception e) {
			throw new ExceptionAccesBD(e.getMessage());
		}
		
		return liste;
		
	}

}
