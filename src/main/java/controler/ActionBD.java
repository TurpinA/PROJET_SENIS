package controler;

import model.Article;
import model.Rayon;
import model.Role;
import model.Utilisateur;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionBD {

    public static final String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID();";
    public static final String LOGGER = "logger";

    private ActionBD(){throw new IllegalStateException("Utility class");}

    public static Connexion connexion = null;

    public static void setConnexion(Connexion connexion) {
        ActionBD.connexion = connexion;
    }

    public static Article createArticle(String nom, String reference, double prix, int quantite, String description, InputStream photo , Rayon rayon) throws SQLException {
        connexion.enableConnexion();

        String sql = "INSERT INTO article VALUES (NULL, '"+nom+"', '"+reference+"',"+prix+","+quantite+",'"+description+"',?,"+rayon.getId()+");";
        PreparedStatement pstmt = null;
        try {
            pstmt = connexion.getConn().prepareStatement(sql);

            if (photo == null)
                pstmt.setNull(1, 0);
            else
                pstmt.setBinaryStream(1, photo);

            pstmt.execute();
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
        finally {
            if(pstmt != null)
                pstmt.close();
        }

        Article article = new Article();


        try(Statement stmt = connexion.getConn().createStatement();
            ResultSet result = stmt.executeQuery(SELECT_LAST_INSERT_ID)) {
            result.next();
            article.setId(result.getInt(1));
            article.setNom(nom);
            article.setDescription(description);
            article.setPhoto(photo);
            article.setPrix(prix);
            article.setQuantite(quantite);
            article.setReference(reference);
            article.setRayon(rayon);
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }

        connexion.stopConnexion();

        return article;
    }

    public static Utilisateur createUtilisateur(String nom, String prenom, int age, Role role, String mail, String motDePasse) throws SQLException {
        connexion.enableConnexion();

        Statement stm1 = null;

        try {
            stm1 = connexion.getConn().createStatement();
            String sql = "INSERT INTO utilisateur VALUES (NULL, '" + nom + "', '" + prenom + "'," + age + ",'" + role + "','" + mail + "','" + motDePasse + "');";
            stm1.executeUpdate(sql);
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
        finally {
            if(stm1 != null)
                stm1.close();
        }

        Utilisateur utilisateur = new Utilisateur();

        try(Statement stmt = connexion.getConn().createStatement();
        ResultSet result = stmt.executeQuery(SELECT_LAST_INSERT_ID)) {
            result.next();
            utilisateur.setId(result.getInt(1));
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setAge(age);
            utilisateur.setRole(role);
            utilisateur.setMail(mail);
            utilisateur.setMotDePasse(motDePasse);
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }

        connexion.stopConnexion();

        utilisateur.setRayon(model.ExtractionData.rechercheRayonParResponsable(utilisateur));

        return utilisateur;
    }

    public static Rayon createRayon(String nom, Utilisateur utilisateur) throws SQLException {
        connexion.enableConnexion();
        String sql;
        if(utilisateur != null)
            sql = "INSERT INTO rayon VALUES (NULL, '"+nom+"', 1,"+ utilisateur.getId() + ");";
        else
            sql = "INSERT INTO rayon VALUES (NULL, '"+nom+"', 1,NULL);";

        Statement stm1 = null;
        try {
            stm1 = connexion.getConn().createStatement();
            stm1.executeUpdate(sql);
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
        finally {
            if(stm1 != null)
                stm1.close();
        }

        Rayon rayon = new Rayon();
        try(Statement stmt = connexion.getConn().createStatement();
            ResultSet result = stmt.executeQuery(SELECT_LAST_INSERT_ID);) {
            result.next();
            rayon.setId(result.getInt(1));
            rayon.setMagasin(null);
            rayon.setNom(nom);
            rayon.setResponsable(utilisateur);
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
        connexion.stopConnexion();

        return rayon;
    }

    public static void supprimerArticle(Article article) throws SQLException {
        connexion.enableConnexion();
        Statement stmt = null;
        try {
            stmt = connexion.getConn().createStatement();
            String sql = "DELETE FROM article WHERE ID = " + article.getId();
            stmt.executeUpdate(sql);
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
        finally {
            if (stmt != null)
                stmt.close();
        }
        connexion.stopConnexion();
    }

    public static void supprimerUtilisateur(Utilisateur utilisateur) throws SQLException {
        connexion.enableConnexion();
        Statement stmt = null;
        try {
            stmt = connexion.getConn().createStatement();
            String sql = "DELETE FROM utilisateur WHERE ID = " + utilisateur.getId();
            stmt.executeUpdate(sql);
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
        finally {
            if (stmt != null)
                stmt.close();
        }
        connexion.stopConnexion();
    }

    public static void modifierArticle(Article article) throws SQLException {
        connexion.enableConnexion();

        try(Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM article  WHERE ID = " + article.getId());) {
            while (rs.next()) {
                rs.updateString("nom", article.getNom());
                rs.updateString("reference", article.getReference());
                rs.updateDouble("prix", article.getPrix());
                rs.updateInt("quantite", article.getQuantite());
                rs.updateString("description", article.getDescription());
                rs.updateBlob("image", article.getPhoto());
                rs.updateInt("rayon_ID", article.getRayon().getId());
                rs.updateRow();
            }
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
        connexion.stopConnexion();
    }

    public static void modifierRayon(Rayon rayon) throws SQLException {
        connexion.enableConnexion();

        try(Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM rayon  WHERE ID = " + rayon.getId());) {
            while (rs.next()) {
                rs.updateString("nom", rayon.getNom());
                if (rayon.getResponsable() != null)
                    rs.updateInt("utilisateur_ID", rayon.getResponsable().getId());
                else
                    rs.updateNull("utilisateur_ID");
                rs.updateRow();
            }
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
        connexion.stopConnexion();
    }

    public static void modifierUtilisateur(Utilisateur utilisateur) throws SQLException {
        connexion.enableConnexion();

        try(Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); ResultSet rs = stmt.executeQuery("SELECT * FROM utilisateur  WHERE ID = " + utilisateur.getId())) {


            while (rs.next()) {
                rs.updateString("nom", utilisateur.getNom());
                rs.updateString("prenom", utilisateur.getPrenom());
                rs.updateInt("age", utilisateur.getAge());
                rs.updateString("responsabilite", String.valueOf(utilisateur.getRole()));
                rs.updateString("email", utilisateur.getMail());
                rs.updateString("motdepasse", utilisateur.getMotDePasse());
                rs.updateRow();
            }
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }

        connexion.stopConnexion();
    }

    public static void supprimerRayon(Rayon rayonASupprimer) throws SQLException {
        connexion.enableConnexion();
        Statement stmt = null;
        try {
            stmt = connexion.getConn().createStatement();
            String sql = "DELETE FROM rayon WHERE ID = " + rayonASupprimer.getId();
            stmt.executeUpdate(sql);
        }
        catch (Exception e){
            Logger.getLogger(LOGGER).log(Level.WARNING,"",e);
        }
        finally {
            if (stmt != null)
                stmt.close();
        }
        connexion.stopConnexion();
    }
}
