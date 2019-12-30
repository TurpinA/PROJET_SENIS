package CONTROLLER;

import MODEL.ExtractionData;

import java.sql.*;

public class Connexion {

	public Connection conn = null;
	public String Adress;
	public String Port;
	public String Base;
	public String User;
	public String Password;
	
	public Connexion() {
	};

	public void enableConnexion() {

		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+ Adress + ":" + Port + "/" + Base + "?serverTimezone=UTC", User, Password);
		} catch (SQLException ex2) {
			System.out.println("Erreur JDBC: " + ex2);
			System.exit(1);
		}

	}

	public void setConnexion(String Adress,String Port,String Base, String User, String Password) {
		this.Adress = Adress;
		this.Port = Port;
		this.Base = Base;
		this.User = User;
		this.Password = Password;

		ControllerConnexion.connexion = this;
		ExtractionData.connexion = this;
	}
	
	public void stopConnexion() throws SQLException {
		conn.close();
		conn = null;
	}

	public Connection getConn() {
		return conn;
	}

}
