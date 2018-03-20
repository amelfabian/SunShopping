package coucheAccesBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import beans.Client;

public class ClientDAO extends gestionnaireDAO<Client> {

	public ClientDAO(Connection sqlConn) {
		super(sqlConn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Client Charger(int num) throws ExceptionAccesBD {
		// TODO Auto-generated method stub
	
		Client cli = null;
		try {
			PreparedStatement sqlCmd = 
					SqlConn.prepareCall("select * from client"
							+ " where numero_client = ?");
			
			sqlCmd.setInt(1, num);
			ResultSet sqlRes = sqlCmd.executeQuery();
			
			if(sqlRes.next() == true) {
				cli = new Client(num,
						sqlRes.getString(1),sqlRes.getString(2),sqlRes.getString(3),sqlRes.getString(4),sqlRes.getString(5));
			
			}
			sqlRes.close();
			return cli;
		} catch (Exception e) {
			throw new ExceptionAccesBD(e.getMessage());
		}
	
	}

	@Override
	public boolean Ajouter(Client obj) throws ExceptionAccesBD {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Modifier(Client obj) throws ExceptionAccesBD {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Supprimer(int num) throws ExceptionAccesBD {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Client> ListerTous() throws ExceptionAccesBD {
		ArrayList<Client> liste = new ArrayList<Client>();
		System.out.println("ClientDAO.ListerTous()");
		try {
			PreparedStatement sqlCmd = 
					SqlConn.prepareCall("select numero_client,nom_client,prenom_client,prenom_client,adresse_client,email_client,passwd_client from Client order by nom_client asc");
			
			ResultSet sqlRes = sqlCmd.executeQuery();
			
			while(sqlRes.next() == true) 
				liste.add(new Client(sqlRes.getInt(1),
										sqlRes.getString(2),
										sqlRes.getString(3),
										sqlRes.getString(4),
										sqlRes.getString(5),
										sqlRes.getString(6)
										));
				
				sqlRes.close();
			
		} catch (Exception e) {
			throw new ExceptionAccesBD(e.getMessage());
		}
		return liste;
	}

}
