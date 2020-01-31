package main;

import controler.Connexion;
import model.Rayon;
import model.Utilisateur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {

    public static Utilisateur utilisateurConnecte = null;
    public static Rayon rayonAffiche = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/ConnexionTest.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("StockManager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException, FileNotFoundException {

        Connexion connexion = new Connexion();
        connexion.setConnexion("127.0.0.1","3306","tp", "root", "",connexion);

        launch(args);

        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
    }
}