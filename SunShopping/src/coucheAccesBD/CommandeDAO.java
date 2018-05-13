package coucheAccesBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.Commande;

public class CommandeDAO extends gestionnaireDAO<Commande> {

	public CommandeDAO(Connection sqlConn) {
		super(sqlConn);
		System.out.println("CommandeDAO.CommandeDAO()");

		// TODO Auto-generated constructor stub
	}

	@Override
	public Commande Charger(int num) throws ExceptionAccesBD {
		Commande cmd = null;
		
		try {
			 
			PreparedStatement sqlCmd = SqlConn.prepareCall("select * from Commande where numero_commande = ?" );
			sqlCmd.setInt(1, num);
			
			ResultSet sqlRes = sqlCmd.executeQuery();
			
			if(sqlRes.next() == true) {
				cmd = new Commande(sqlRes.getInt(1),
									sqlRes.getDate(2),
									sqlRes.getTime(3),
									sqlRes.getInt(4),
									sqlRes.getInt(5),
									sqlRes.getString(6)	
									);
			}	
				sqlRes.close();
				
				return cmd;					
			
		} catch (Exception e) {
			throw new ExceptionAccesBD(e.getMessage());
		}
	
	}

	@Override
	public boolean Ajouter(Commande obj) throws ExceptionAccesBD {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean Modifier(Commande obj) throws ExceptionAccesBD {
		try {
			PreparedStatement sqlCmd = SqlConn
					.prepareCall("update Commande" + " set indication_livraison = ?" + " where numero_commande = ?");
			
			sqlCmd.setString(1, obj.getIndicLivraison());
			sqlCmd.setInt(2, obj.getNumCommande());
			
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
	public List<Commande> ListerTous() throws ExceptionAccesBD {
    System.out.println("CommandeDAO.ListerTous()");
		ArrayList<Commande> liste = new ArrayList<Commande>();
		
		try {
			PreparedStatement sqlCmd = 
					SqlConn.prepareCall("select * from Commande order by numero_commande");
			ResultSet sqlRes = sqlCmd.executeQuery();
			
			while (sqlRes.next() == true)
				liste.add(new Commande(sqlRes.getInt(1),
										sqlRes.getDate(2),
										sqlRes.getTime(3),
										sqlRes.getInt(4),
										sqlRes.getInt(5),
										sqlRes.getString(6)));
			sqlRes.close();
			
		}
		catch(Exception e) {
			throw new ExceptionAccesBD(e.getMessage());
		}
		return liste;
	}
	
	public List<Commande> ListerTous(int numCom) throws ExceptionAccesBD {
	    System.out.println("CommandeDAO.ListerTous(int)");
			ArrayList<Commande> liste = new ArrayList<Commande>();
			
			try {
				PreparedStatement sqlCmd = 
						SqlConn.prepareCall("select c.numero_commande, c.date_commande, c.heure_client,"
								+ "c.client_fk, c.montant_total, c.indication_livraison from Commande as c, Client as cl where c.client_fk = cl.numero_client AND c.client_fk = ? order by numero_commande");
				sqlCmd.setInt(1, numCom);
				ResultSet sqlRes = sqlCmd.executeQuery();
				
				while (sqlRes.next() == true)
					liste.add(new Commande(numCom,
											sqlRes.getDate(2),
											sqlRes.getTime(3),
											sqlRes.getInt(4),
											sqlRes.getInt(5),
											sqlRes.getString(6)));
				sqlRes.close();
				
			}
			catch(Exception e) {
				throw new ExceptionAccesBD(e.getMessage());
			}
			return liste;
		}

}
