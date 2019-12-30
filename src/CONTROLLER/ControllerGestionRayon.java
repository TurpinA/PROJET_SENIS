package CONTROLLER;

import MODEL.Article;
import MODEL.Rayon;
import MODEL.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class ControllerGestionRayon {

    @FXML
    private Button disconnectButton;
    @FXML
    private Label utilisateurLabel;

    @FXML   private TableView<Article> tableView;
    @FXML   private TableColumn<Article,String> nomProduit;
    @FXML   private TableColumn<Article,String> prix;
    @FXML   private TableColumn<Article,String> stock;
    @FXML   private TableColumn<Article,String> action;

    @FXML
    public void initialize() throws SQLException {
        utilisateurLabel.setText(Main.utilisateurConnecte.getPrenom() + " " + Main.utilisateurConnecte.getNom());
        nomProduit.setCellValueFactory(new PropertyValueFactory<Article, String>("nom"));
        prix.setCellValueFactory(new PropertyValueFactory<Article, String>("prix"));
        stock.setCellValueFactory(new PropertyValueFactory<Article, String>("quantite"));

        tableView.getItems().setAll(MODEL.ExtractionData.rechercheAllArticleParRayon(Main.rayonAffiche));
    }

    public void deconnexion(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(new File("IHM/ConnexionTest.fxml").toURI().toURL());
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

}
