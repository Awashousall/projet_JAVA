import javax.swing.JOptionPane;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utilisateur implements Serializable {
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String tel;
    private String numeroIdentification;
    private int nbEmp;

    private static List<Utilisateur> utilisateurs;

    static {
        chargerUtilisateurs();
    }

    public Utilisateur() {}

    public Utilisateur(String nom, String prenom, String dateNaissance, String tel, int nbEmp) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.tel = tel;
        this.nbEmp = nbEmp;
        this.numeroIdentification = genererNumeroIdentification();
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public String getTel() {
        return tel;
    }

    public String getNumeroIdentification() {
        return numeroIdentification;
    }

    public int getNbEmp() {
        return nbEmp;
    }

    public static List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public static void ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
        sauvegarderUtilisateurs();
    }

    public static void supprimerUtilisateur(String numeroIdentification) {
        Utilisateur utilisateurTrouve = null;
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getNumeroIdentification().equals(numeroIdentification)) {
                utilisateurTrouve = utilisateur;
                break;
            }
        }
        if (utilisateurTrouve != null) {
            utilisateurs.remove(utilisateurTrouve);
            sauvegarderUtilisateurs();
            JOptionPane.showMessageDialog(null, "Utilisateur supprimé avec succès !");
        } else {
            JOptionPane.showMessageDialog(null, "Utilisateur introuvable !");
        }
    }

    private static void chargerUtilisateurs() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("utilisateurs.ser"))) {
            utilisateurs = (List<Utilisateur>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            utilisateurs = new ArrayList<>();
        }
    }

    private static void sauvegarderUtilisateurs() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("utilisateurs.ser"))) {
            oos.writeObject(utilisateurs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Nom : " + nom + "\n" +
                "Prenom : " + prenom + "\n" +
                "Date de naissance : " + dateNaissance + "\n" +
                "Numero de telephone : " + tel + "\n" +
                "Numero d'identification : " + numeroIdentification + "\n" +
                "Nombre d'ouvrages empruntes : " + nbEmp;
    }

    private String genererNumeroIdentification() {
        return String.valueOf(utilisateurs.size() + 1);
    }
}
