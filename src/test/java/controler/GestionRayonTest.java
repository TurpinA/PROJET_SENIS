package controler;

import controler.ActionBD;

import controler.Connexion;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.*;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Before;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class GestionRayonTest {

    private List<Rayon>  ArrayListRayon = new ArrayList<>();
    private List<Rayon> ExtractRayon = new ArrayList<>();

    @Before
    public void before(Scenario scenario) throws SQLException, FileNotFoundException {
        String mysqlUrl = "jdbc:mysql://127.0.0.1:3306/?serverTimezone=UTC&useSSL=false";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "");
        ScriptRunner sr = new ScriptRunner(con);
        sr.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/sql/viderLaBase.sql"));
        sr.runScript(reader);
    }
    @Given("un rayon :")
    public void un_rayon(io.cucumber.datatable.DataTable dataTable) throws SQLException {
        List<Map<String,String>> list = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> map :
                list) {

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(map.get("responsable"));
            utilisateur.setPrenom("");
            utilisateur.setAge(0);
            utilisateur.setRole(Role.MANAGER);
            utilisateur.setMail("");
            utilisateur.setMotDePasse("");

            utilisateur = ActionBD.createUtilisateur(utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getAge(), utilisateur.getRole(), utilisateur.getMail(), utilisateur.getMotDePasse());

            Rayon rayon = new Rayon();
            rayon.setNom(map.get("nom"));
            rayon.setResponsable(utilisateur);

            ArrayListRayon.add(rayon);
        }
    }
    @When("je clique sur le bouton ajouter un rayon")
    public void je_clique_sur_le_bouton_ajouter_un_rayon() throws SQLException {
        for (Rayon rayon : ArrayListRayon) {
            ExtractRayon.add(ActionBD.createRayon(rayon.getNom(),rayon.getResponsable()));
        }
    }
    @Then("Je vérifie les entrées et j'ajoute le rayon à la base de donnée")
    public void je_vérifie_les_entrées_et_j_ajoute_le_rayon_à_la_base_de_donnée() throws SQLException {
        for (Rayon rayon : ExtractRayon) {
            assertNotNull(model.ExtractionData.rechercheRayonParID(rayon.getId()));
            assertNotNull(model.ExtractionData.rechercheRayonParNom(rayon.getNom()));
        }
    }

    @Given("un rayon sélectionné à supprimer :")
    public void un_rayon_sélectionné_à_supprimer(io.cucumber.datatable.DataTable dataTable) throws SQLException {
        List<Map<String,String>> list = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> map :
                list) {

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(map.get("responsable"));
            utilisateur.setPrenom("");
            utilisateur.setAge(0);
            utilisateur.setRole(Role.MANAGER);
            utilisateur.setMail("");
            utilisateur.setMotDePasse("");

            utilisateur = ActionBD.createUtilisateur(utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getAge(), utilisateur.getRole(), utilisateur.getMail(), utilisateur.getMotDePasse());

            Rayon rayon = new Rayon();
            rayon.setNom(map.get("nom"));
            rayon.setResponsable(utilisateur);
            ExtractRayon.clear();
            ExtractRayon.add(ActionBD.createRayon(rayon.getNom(),rayon.getResponsable()));
        }
    }
    @When("je clique sur le bouton supprimé le rayon")
    public void je_clique_sur_le_bouton_supprimé_le_rayon() throws SQLException {
        ActionBD.supprimerRayon(ExtractRayon.get(0));
    }
    @Then("je supprime le rayon de la base de donnée")
    public void je_supprime_le_rayon_de_la_base_de_donnée() throws SQLException {
        assertNull(model.ExtractionData.rechercheRayonParID(ExtractRayon.get(0).getId()));
    }

    @Given("un rayon sélectionné à modifier :")
    public void un_rayon_sélectionné_à_modifier(io.cucumber.datatable.DataTable dataTable) throws SQLException {
        List<Map<String,String>> list = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> map :
                list) {

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(map.get("responsable"));
            utilisateur.setPrenom("");
            utilisateur.setAge(0);
            utilisateur.setRole(Role.MANAGER);
            utilisateur.setMail("");
            utilisateur.setMotDePasse("");

            utilisateur = ActionBD.createUtilisateur(utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getAge(), utilisateur.getRole(), utilisateur.getMail(), utilisateur.getMotDePasse());

            Rayon rayon = new Rayon();
            rayon.setNom(map.get("nom"));
            rayon.setResponsable(utilisateur);
            ExtractRayon.clear();
            ExtractRayon.add(ActionBD.createRayon(rayon.getNom(),rayon.getResponsable()));
        }
    }
    @When("je clique sur le bouton modifié le rayon")
    public void je_clique_sur_le_bouton_modifié() throws SQLException {
        Rayon rayonModifie = ExtractRayon.get(0);
        rayonModifie.setNom("Gymnastique");
        ActionBD.modifierRayon(rayonModifie);
    }
    @Then("j'attend les modifications du rayon et je modifie le rayon dans la base de donnée")
    public void j_attend_les_modifications_du_rayon_et_je_modifie_le_rayon_dans_la_base_de_donnée_le_rayon() throws SQLException {
        assertEquals(model.ExtractionData.rechercheRayonParID(ExtractRayon.get(0).getId()).getNom(),"Gymnastique");
    }


}