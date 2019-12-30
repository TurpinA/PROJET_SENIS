package MODEL;

import CONTROLLER.Connexion;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExtractionData {

    public static Connexion connexion = null;

    public static Rayon rechercheRayonParResponsable(Utilisateur utilisateur) throws SQLException {

        Rayon rayon = null;
        connexion.enableConnexion();
        Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet result = stmt.executeQuery("SELECT * FROM rayon WHERE utilisateur_ID = " + utilisateur.getID());

        if(result.next()){
            rayon = new Rayon();
            rayon.setID(result.getInt(1));
            rayon.setNom(result.getString(2));
            rayon.setMagasin(null);
            rayon.setResponsable(utilisateur);
        }

        stmt.close();
        connexion.stopConnexion();

        return rayon;
    }

    public static Rayon rechercheRayonParNom(String nom) throws SQLException {

        Rayon rayon = null;
        connexion.enableConnexion();
        Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet result = stmt.executeQuery("SELECT * FROM rayon INNER JOIN utilisateur ON rayon.utilisateur_ID = utilisateur.ID WHERE nom = " + "'" + nom + "'");

        if(result.next()){
            rayon = new Rayon();
            rayon.setID(result.getInt(1));
            rayon.setNom(result.getString(2));
            rayon.setMagasin(null);

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setID(result.getInt(5));
            utilisateur.setNom(result.getString(6));
            utilisateur.setPrenom(result.getString(7));
            utilisateur.setAge(result.getInt(8));
            utilisateur.setRole(Role.valueOf(result.getString(9)));
            utilisateur.setMail(result.getString(10));
            utilisateur.setMotDePasse(result.getString(11));

            rayon.setResponsable(utilisateur);
        }

        stmt.close();
        connexion.stopConnexion();

        return rayon;
    }

    public static List<Article> rechercheAllArticleParRayon(Rayon rayon) throws SQLException {

        ArrayList<Article> articleList = new ArrayList<>();

        connexion.enableConnexion();
        Statement stmt = connexion.getConn().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet result = stmt.executeQuery("SELECT * FROM article WHERE rayon_ID = " + rayon.getID());

        while(result.next()){
            Article article = new Article();
            article.setID(result.getInt(1));
            article.setNom(result.getString(2));
            article.setReference(result.getString(3));
            article.setPrix(result.getDouble(4));
            article.setQuantite(result.getInt(5));
            article.setDescription(result.getString(6));
            //article.setPhoto(result.getBlob(7).getBinaryStream());
            article.setPhoto(null);
            article.setRayon(rayon);

            articleList.add(article);
        }

        stmt.close();
        connexion.stopConnexion();

        return articleList;
    }
}
