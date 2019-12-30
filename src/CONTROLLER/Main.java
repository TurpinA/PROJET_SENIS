package CONTROLLER;

import CONTROLLER.Connexion;
import MODEL.Rayon;
import MODEL.Utilisateur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    public static Utilisateur utilisateurConnecte = null;
    public static Rayon rayonAffiche = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(new File("IHM/ConnexionTest.fxml").toURI().toURL());
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("StockManager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args){

        Connexion connexion = new Connexion();
        connexion.setConnexion("127.0.0.1","3306","tp", "root", "");

        launch(args);
    }
}
