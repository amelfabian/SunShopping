package coucheAccesBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Alcool;

public class AlcoolDAO extends gestionnaireDAO<Alcool> {

	public AlcoolDAO(Connection sqlConn) {
		super(sqlConn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Alcool Charger(int num) throws ExceptionAccesBD {
		Alcool alcool = null;

		try {
			PreparedStatement sqlCmd = SqlConn.prepareCall("select * from Alcool  where a.numProd = ? ");
			sqlCmd.setInt(1, num);
			ResultSet sqlRes = sqlCmd.executeQuery();
			if (sqlRes.next() == true) {
				alcool = new Alcool(num,
							sqlRes.getString(1),
							sqlRes.getString(2),
							sqlRes.getString(3),
							sqlRes.getString(4),
							sqlRes.getString(5),
							sqlRes.getInt(6),
							sqlRes.getFloat(7));
			}
			sqlRes.close();
			return alcool;
		} catch (Exception e) {
			throw new ExceptionAccesBD(e.getMessage());
		}

	}

	@Override
	public boolean Ajouter(Alcool obj) throws ExceptionAccesBD {

		try {
			PreparedStatement sqlCmd = SqlConn.prepareCall("select max(numProd) + 1 from Alcool");
			ResultSet sqlRes = sqlCmd.executeQuery();
			sqlRes.next();

			int numAlcool = sqlRes.getInt(1);
			if (sqlRes.wasNull())
				numAlcool = 1;

			sqlCmd.close();

			sqlCmd = SqlConn.prepareCall("insert into Produit " + "values(?,?,?,?)");

			sqlCmd.setString(2, obj.getImageProduit());
			sqlCmd.setInt(3, obj.getQuantiteStock());
			sqlCmd.setFloat(4, obj.getPrix_unite());

			sqlCmd = SqlConn.prepareCall("insert into Alcool" + "values(?,?,?,?)");

			sqlCmd.setInt(1, numAlcool);
			sqlCmd.setString(2, obj.getNomAlcool());
			sqlCmd.setString(3, obj.getGoutAlcool());
			sqlCmd.setString(4, obj.getProvenance());

			sqlCmd.close();

			sqlCmd.setInt(1, obj.getNumProduit());

			return (sqlCmd.executeUpdate() == 0) ? false : true;

		} catch (SQLException e) {
			throw new ExceptionAccesBD(e.getMessage());
		}
	}

	@Override
	public boolean Modifier(Alcool obj) throws ExceptionAccesBD {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Supprimer(int num) throws ExceptionAccesBD {
		try {
			SqlConn.setAutoCommit(false);
			
			PreparedStatement sqlCmd = SqlConn.prepareCall("delete from Produit where numero_produit = ?");
			sqlCmd.setInt(1, num);
			sqlCmd.executeUpdate();
			sqlCmd = SqlConn.prepareCall("delete from Alcool where numProd = ?");
			sqlCmd.setInt(1,num);
			int iRes = sqlCmd.executeUpdate();
			SqlConn.commit();
			SqlConn.setAutoCommit(true);
			return (iRes == 0)? false : true;
			
		}catch (Exception e) {
			try {
				SqlConn.rollback();
				SqlConn.setAutoCommit(true);
			} catch (Exception e2) {
			
			}
			throw new ExceptionAccesBD(e.getMessage());
		}
	
	}

	@Override
	public List<Alcool> ListerTous() throws ExceptionAccesBD {
		
		ArrayList<Alcool> liste = new ArrayList<Alcool>();
		
		try {
			
			PreparedStatement sqlCmd = SqlConn.prepareCall(" select p.numero_produit,a.nomAlcool,a.goutAlcool,a.provenance,p.nom_produit,p.image_produit,p.quantiteEnStock,p.prix_unite from Alcool as a, Produit as p order by a.nomAlcool asc" );
			
			ResultSet sqlRes = sqlCmd.executeQuery();
			
			while (sqlRes.next() == true)
				liste.add(new Alcool(sqlRes.getInt(1),
									sqlRes.getString(2),
									sqlRes.getString(3),
									sqlRes.getString(4),
									sqlRes.getString(5),
									sqlRes.getString(6),
									sqlRes.getInt(7),
									sqlRes.getFloat(8)));
			sqlRes.close();
			
			
		} catch (Exception e) {
			throw new ExceptionAccesBD(e.getMessage());
		}
		
		return liste;
		
	}

}
