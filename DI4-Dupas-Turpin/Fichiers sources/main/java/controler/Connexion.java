package controler;

import model.ExtractionData;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connexion {

	private Connection conn = null;
	private String adress;
	private String port;
	private String base;
	private String user;
	private String password;

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void enableConnexion() {

		try {
			conn = DriverManager.getConnection("jdbc:mysql://"+ adress + ":" + port + "/" + base + "?serverTimezone=UTC&useSSL=false", user, password);
		} catch (SQLException ex2) {
			Logger.getLogger("logger").log(Level.WARNING,"",ex2);
			System.exit(1);
		}

	}

	public static void setConnexion(String adress,String port,String base, String user, String password,Connexion connexion) {
		connexion.adress = adress;
		connexion.port = port;
		connexion.base = base;
		connexion.user = user;
		connexion.password = password;

		ControlerConnexion.connexion = connexion;
		ExtractionData.connexion = connexion;
		ActionBD.connexion = connexion;
	}
	
	public void stopConnexion() throws SQLException {
		conn.close();
		conn = null;
	}

	public Connection getConn() {
		return conn;
	}

}
