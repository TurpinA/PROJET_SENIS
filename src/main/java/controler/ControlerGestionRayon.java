package controler;

import model.Article;
import model.Rayon;
import model.Role;
import main.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class ControlerGestionRayon {
    
    @FXML   private Button ajouterButton;
    @FXML   private Button modifierButton;
    @FXML   private Button supprimerButton;
    @FXML   private Button gestionUtilisateurButton;

    @FXML   private Label utilisateurLabel;

    @FXML   private TableView<Article> tableView;
    @FXML   private TableColumn<Article,String> nomProduit;
    @FXML   private TableColumn<Article,String> prix;
    @FXML   private TableColumn<Article,String> stock;
    @FXML   private TableColumn<Article,String> description;

    @FXML   private MenuButton menuButton;

    @FXML   private ArrayList<Rayon> rayonList;

    @FXML
    public void initialize() throws SQLException {

        if(Main.utilisateurConnecte.getRole().equals(Role.Vendeur))
            gestionUtilisateurButton.setVisible(false);

        utilisateurLabel.setText(Main.utilisateurConnecte.getPrenom() + " " + Main.utilisateurConnecte.getNom());

        nomProduit.setCellValueFactory(new PropertyValueFactory<Article, String>("nom"));
        prix.setCellValueFactory(new PropertyValueFactory<Article, String>("prix"));
        stock.setCellValueFactory(new PropertyValueFactory<Article, String>("quantite"));
        description.setCellValueFactory(new PropertyValueFactory<Article, String>("description"));

        tableView.getItems().setAll(model.ExtractionData.rechercheAllArticleParRayon(Main.rayonAffiche));

        rayonList =  model.ExtractionData.rechercheAllRayon();

        for(int i=0;i<rayonList.size();i++) {
            MenuItem menuItem = new MenuItem("Rayon " + rayonList.get(i).getNom());
            menuButton.getItems().add(menuItem);
            menuItem.setOnAction(event1);
            menuButton.setText("Rayon " + Main.rayonAffiche.getNom());
        }

        if(Main.utilisateurConnecte.getRole() == Role.Manager || Main.rayonAffiche.getResponsable().getID() == Main.utilisateurConnecte.getID()){
            ajouterButton.setVisible(true);
            modifierButton.setVisible(true);
            supprimerButton.setVisible(true);
        }
        else
        {
            ajouterButton.setVisible(false);
            modifierButton.setVisible(false);
            supprimerButton.setVisible(false);
        }
    }

    public void deconnexion(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/ConnexionTest.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void ajouterProduit(ActionEvent actionEvent) {
        Parent root;
        Stage stage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/AjouterArticle.fxml"));
            root = fxmlLoader.load();
            stage.setTitle("Ajouter un article");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        stage.setOnHiding(event -> {
            try {
                tableView.getItems().setAll(model.ExtractionData.rechercheAllArticleParRayon(Main.rayonAffiche));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void supprimerProduit(ActionEvent actionEvent) throws SQLException {

        Article articleASupprimer = tableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation suppression");
        alert.setHeaderText("Etes-vous sur de vouloir supprimer l'article : " + articleASupprimer.getNom() + " ?");
        alert.setContentText("");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ActionBD.supprimerArticle(articleASupprimer);
            tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
        }
    }

    public void modifierProduit(ActionEvent actionEvent) {

       if(tableView.getSelectionModel().getSelectedItem() != null) {
           ControlerModifierArticle.articleSelectione = tableView.getSelectionModel().getSelectedItem();

           Parent root;
           Stage stage = new Stage();
           try {
               FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/ModifierArticle.fxml"));
               root = fxmlLoader.load();
               stage.setTitle("Modifier un article");
               stage.setScene(new Scene(root));
               stage.show();
           } catch (IOException e) {
               e.printStackTrace();
           }

           stage.setOnHiding(event -> {
               try {
                   tableView.getItems().setAll(model.ExtractionData.rechercheAllArticleParRayon(Main.rayonAffiche));
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           });
       }
    }

    EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            String nomRayon = ((MenuItem)e.getSource()).getText();
            Iterator<Rayon> iteratorRayonList= rayonList.iterator();
            Rayon rayonSelectionne = iteratorRayonList.next();

            while(!("Rayon " + rayonSelectionne.getNom()).equals(nomRayon))
                rayonSelectionne = iteratorRayonList.next();

            Main.rayonAffiche = rayonSelectionne;

            try {
                tableView.getItems().setAll(model.ExtractionData.rechercheAllArticleParRayon(Main.rayonAffiche));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            menuButton.setText("Rayon " + Main.rayonAffiche.getNom());

            if(Main.utilisateurConnecte.getRole() == Role.Manager || Main.rayonAffiche.getResponsable().getID() == Main.utilisateurConnecte.getID()){
                ajouterButton.setVisible(true);
                modifierButton.setVisible(true);
                supprimerButton.setVisible(true);
            }
            else
            {
                ajouterButton.setVisible(false);
                modifierButton.setVisible(false);
                supprimerButton.setVisible(false);
            }
        }
    };

    public void labelClick(MouseEvent mouseEvent) {
        Parent root;
        Stage stage = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/AffichageUtilisateur.fxml"));
            root = fxmlLoader.load();
            stage.setTitle(Main.utilisateurConnecte.getPrenom() + " " + Main.utilisateurConnecte.getNom());
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rayonButton(ActionEvent actionEvent) throws SQLException, IOException {
        if(Main.utilisateurConnecte.getRole().equals(Role.Vendeur)) {
            Main.rayonAffiche = model.ExtractionData.rechercheRayonParResponsable(Main.utilisateurConnecte);

            try {
                tableView.getItems().setAll(model.ExtractionData.rechercheAllArticleParRayon(Main.rayonAffiche));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            menuButton.setText("Rayon " + Main.rayonAffiche.getNom());
        }
        else
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/Affichage des rayons.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    public void gestionUtilisateurButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ihm/Gestion utilisateur.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
