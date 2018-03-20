package coucheAccesBD;


import java.sql.Connection;
import java.util.List;

public abstract class gestionnaireDAO<T> {

	protected Connection SqlConn;
	
	public gestionnaireDAO(Connection sqlConn) {
		System.out.println("gestionnaireDAO.gestionnaireDAO()");
		SqlConn = sqlConn;
	}
	
/* Methode abstraite*/
	
	public abstract T Charger (int num) throws ExceptionAccesBD;
	public abstract boolean Ajouter(T obj) throws ExceptionAccesBD;
	public abstract boolean Modifier(T obj) throws ExceptionAccesBD;
	public abstract boolean Supprimer( int num) throws ExceptionAccesBD;
	public abstract List<T> ListerTous() throws ExceptionAccesBD;
}
