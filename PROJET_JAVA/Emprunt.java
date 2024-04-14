import java.io.Serializable;

public class Emprunt implements Serializable {
    private String nomUtilisateur;
    private String titreLivre;
    private String dateEmprunt;

    public Emprunt(String nomUtilisateur, String titreLivre, String dateEmprunt) {
        this.nomUtilisateur = nomUtilisateur;
        this.titreLivre = titreLivre;
        this.dateEmprunt = dateEmprunt;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public String getTitreLivre() {
        return titreLivre;
    }

    public String getDateEmprunt() {
        return dateEmprunt;
    }
}
