package CONTROLLER;

import MODEL.Article;
import MODEL.Rayon;
import MODEL.Role;
import MODEL.Utilisateur;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ActionBD {

    public static Connexion connexion = null;

    public static Article CreateArticle(String nom, String reference, double prix, int quantite, String description, InputStream photo , Rayon rayon) throws SQLException {
        connexion.enableConnexion();

        String sql = "INSERT INTO article VALUES (NULL, '"+nom+"', '"+reference+"',"+prix+","+quantite+",'"+description+"',?,"+rayon.getID()+");";
        PreparedStatement pstmt = connexion.getConn().prepareStatement(sql);

        if(photo == null)
            pstmt.setNull(1,0);
        else
            pstmt.setBinaryStream(1,photo);

        pstmt.execute();

        Statement stmt = connexion.getConn().createStatement();
        ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID();");
        result.next();
        Article article = new Article();
        article.setID(result.getInt(1));
        article.setNom(nom);
        article.setDescription(description);
        article.setPhoto(photo);
        article.setPrix(prix);
        article.setQuantite(quantite);
        article.setReference(reference);
        article.setRayon(rayon);
        stmt.close();

        connexion.stopConnexion();

        return article;
    }

    public static Utilisateur CreateUtilisateur(String nom, String prenom, int age, Role role, String mail, String motDePasse) throws SQLException {
        connexion.enableConnexion();
        Statement stm1 = connexion.getConn().createStatement();
        String sql = "INSERT INTO utilisateur VALUES (NULL, '"+nom+"', '"+prenom+"',"+age+",'"+role+"','"+mail+"','"+motDePasse+"');";
        stm1.executeUpdate(sql);
        stm1.close();

        Statement stmt = connexion.getConn().createStatement();
        ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID();");
        result.next();
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setID(result.getInt(1));
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setAge(age);
        utilisateur.setRole(role);
        utilisateur.setMail(mail);
        utilisateur.setMotDePasse(motDePasse);

        stmt.close();
        connexion.stopConnexion();

        utilisateur.setRayon(MODEL.ExtractionData.rechercheRayonParResponsable(utilisateur));

        return utilisateur;
    }

    public static Rayon Createrayon(String nom, Utilisateur utilisateur) throws SQLException {
        connexion.enableConnexion();
        Statement stm1 = connexion.getConn().createStatement();
        String sql = "INSERT INTO rayon VALUES (NULL, '"+nom+"', 1,"+ utilisateur.getID() + ");";
        stm1.executeUpdate(sql);
        stm1.close();

        Statement stmt = connexion.getConn().createStatement();
        ResultSet result = stmt.executeQuery("SELECT LAST_INSERT_ID();");
        result.next();
        Rayon rayon = new Rayon();
        rayon.setID(result.getInt(1));
        rayon.setMagasin(null);
        rayon.setNom(nom);
        rayon.setResponsable(utilisateur);

        stmt.close();
        connexion.stopConnexion();

        return rayon;
    }

    public static void supprimerArticle(Article article) throws SQLException {
        connexion.enableConnexion();
        Statement stmt = connexion.getConn().createStatement();
        String sql = "DELETE FROM article WHERE ID = " + article.getID();
        stmt.executeUpdate(sql);
        stmt.close();
        connexion.stopConnexion();
    }

    public static void supprimerUtilisateur(Utilisateur utilisateur) throws SQLException {
        connexion.enableConnexion();
        Statement stmt = connexion.getConn().createStatement();
        String sql = "DELETE FROM utilisateur WHERE ID = " + utilisateur.getID();
        stmt.executeUpdate(sql);
        stmt.close();
        connexion.stopConnexion();
    }

    public static void modifierArticle(Article article) throws SQLException {
        connexion.enableConnexion();

        Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet rs = stmt.executeQuery("SELECT * FROM article  WHERE ID = " + article.getID());

        while (rs.next()) {
            rs.updateString("nom",article.getNom());
            rs.updateString("reference",article.getReference());
            rs.updateDouble("prix",article.getPrix());
            rs.updateInt("quantite",article.getQuantite());
            rs.updateString("description",article.getDescription());
            rs.updateBlob("image",article.getPhoto());
            rs.updateInt("rayon_ID",article.getRayon().getID());
            rs.updateRow();
        }

        stmt.close();

        connexion.stopConnexion();
    }

    public static void modifierRayon(Rayon rayon) throws SQLException {
        connexion.enableConnexion();

        Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet rs = stmt.executeQuery("SELECT * FROM rayon  WHERE ID = " + rayon.getID());

        while (rs.next()) {
            rs.updateString("nom",rayon.getNom());
            rs.updateInt("utilisateur_ID",rayon.getResponsable().getID());
            rs.updateRow();
        }

        stmt.close();

        connexion.stopConnexion();
    }

    public static void modifierUtilisateur(Utilisateur utilisateur) throws SQLException {
        connexion.enableConnexion();

        Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet rs = stmt.executeQuery("SELECT * FROM utilisateur  WHERE ID = " + utilisateur.getID());

        while (rs.next()) {
            rs.updateString("nom",utilisateur.getNom());
            rs.updateString("prenom",utilisateur.getPrenom());
            rs.updateInt("age",utilisateur.getAge());
            rs.updateString("responsabilite",String.valueOf(utilisateur.getRole()));
            rs.updateString("email",utilisateur.getMail());
            rs.updateString("motdepasse",utilisateur.getMotDePasse());
            rs.updateRow();
        }

        stmt.close();

        connexion.stopConnexion();
    }

    public static void supprimerRayon(Rayon rayonASupprimer) throws SQLException {
        connexion.enableConnexion();
        Statement stmt = connexion.getConn().createStatement();
        String sql = "DELETE FROM rayon WHERE ID = " + rayonASupprimer.getID();
        stmt.executeUpdate(sql);
        stmt.close();
        connexion.stopConnexion();
    }
}
