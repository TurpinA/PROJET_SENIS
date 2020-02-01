package controler;

import controler.ActionBD;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import model.ExtractionData;
import model.Role;
import model.Utilisateur;
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

public class UserManagementTest {

    @Before
    public void viderTables() throws SQLException, FileNotFoundException {
        String mysqlUrl = "jdbc:mysql://127.0.0.1:3306/?serverTimezone=UTC&useSSL=false";
        Connection con = DriverManager.getConnection(mysqlUrl, "root", "");
        ScriptRunner sr = new ScriptRunner(con);
        sr.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/sql/viderLaBase.sql"));
        sr.runScript(reader);
    }

    @Given("user navigates to user creation module")
    public void user_navigates_to_user_creation_module() {
    }
    @When("user fills the fields\\({string},{string},{int},{string},{string},{string}) and add the new user")
    public void user_fills_the_fields_and_add_the_new_user(String name, String surname, Integer age, String role, String email, String password) throws SQLException {
        ActionBD.createUtilisateur(name,surname,age, Role.valueOf(role),email,password);
    }
    @Then("verify that the user can be find in the database by his email : {string}")
    public void verify_that_the_user_can_be_find_in_the_database_by_his_email(String email) throws SQLException {
        assertNotNull(model.ExtractionData.rechercheUtilisateurParMail(email));
    }

    private Utilisateur utilisateur;

    @Given("user selected a user \\(id:{int}) and click on modifying user")
    public void user_selected_a_user_id_and_click_on_modifying_user(Integer id) throws SQLException {
        utilisateur = ExtractionData.rechercheUtilisateurParID(id);
    }
    @When("user change the fill name by {string} and enters")
    public void user_change_the_fill_name_by_and_enters(String name) throws SQLException {
        utilisateur.setNom(name);
        ActionBD.modifierUtilisateur(utilisateur);
    }
    @Then("verify that the user has his new {string}")
    public void verify_that_the_user_has_his_new(String name) throws SQLException {
        Utilisateur modifyUser = ExtractionData.rechercheUtilisateurParID(utilisateur.getId());
        assertEquals(name,modifyUser.getNom());
    }

    @Given("user selected a user \\(id:{int}) and click on deleting")
    public void user_selected_a_user_id_and_click_on_deleting(Integer id) throws SQLException {
        utilisateur = ExtractionData.rechercheUtilisateurParID(id);
    }
    @When("user confirms")
    public void user_confirms() throws SQLException {
        ActionBD.supprimerUtilisateur(utilisateur);
    }
    @Then("verify that the user is not in the database")
    public void verify_that_the_user_is_not_in_the_database() throws SQLException {
        assertNull(ExtractionData.rechercheUtilisateurParID(utilisateur.getId()));
    }




}