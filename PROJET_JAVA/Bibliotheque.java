import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Bibliotheque extends JFrame implements ActionListener {
     // Déclaration des boutons
    private JButton btnGestionLivres;
    private JButton btnGestionUtilisateurs;
    private JButton btnGestionEmprunts;
    private JButton btnQuitter;
    private JButton btnTotalLivres;

    // Liste des emprunts
    private List<Emprunt> listeEmprunts;

    private JButton btnStatsEmprunts;

    public Bibliotheque() {
        // Configuration de la fenêtre principale
        setTitle("Bibliothèque");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
         
         // Création des boutons
        btnGestionLivres = new JButton("Gestion des Livres");
        btnGestionUtilisateurs = new JButton("Gestion des Utilisateurs");
        btnGestionEmprunts = new JButton("Gestion des Emprunts");
        btnQuitter = new JButton("Quitter");
        btnTotalLivres = new JButton("Nombre Total de Livres");
        btnStatsEmprunts = new JButton("Statistiques des Emprunts");
           
           // Ajout des écouteurs d'événements aux boutons
        btnGestionLivres.addActionListener(this);
        btnGestionUtilisateurs.addActionListener(this);
        btnGestionEmprunts.addActionListener(this);
        btnQuitter.addActionListener(this);
        btnTotalLivres.addActionListener(this);
        btnStatsEmprunts.addActionListener(this);


         // Configuration du panneau principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        panel.add(btnGestionLivres);
        panel.add(btnGestionUtilisateurs);
        panel.add(btnGestionEmprunts);
        panel.add(btnTotalLivres);
        panel.add(btnStatsEmprunts);
        panel.add(btnQuitter);

        add(panel);

        // Charger les emprunts depuis le fichier lors de la création de la bibliothèque
        chargerEmprunts();
         
         // Rendre la fenêtre visible
        setVisible(true);
    }
    public Bibliotheque(List<Emprunt> listeEmprunts) {
        this.listeEmprunts = listeEmprunts;
        // Autres initialisations de la bibliothèque
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGestionLivres) {
            new InterfaceGestionLivres();
            dispose();
        } else if (e.getSource() == btnGestionUtilisateurs) {
            new InterfaceUtilisateur();
            dispose();
        } else if (e.getSource() == btnGestionEmprunts) { // Condition pour le nouveau bouton
            new InterfaceGestionEmprunts(); // Crée une nouvelle instance de l'interface de gestion des emprunts
            dispose(); // Ferme la fenêtre actuelle
        } else if (e.getSource() == btnQuitter) {
            System.exit(0);
        } else if (e.getSource() == btnStatsEmprunts) {
        afficherStatistiquesEmprunts();
        }else if (e.getSource() == btnTotalLivres) { // Si le bouton pour afficher le nombre total de livres est cliqué
            afficherNombreTotalLivres();
        } 
    }

    // Méthode pour charger les emprunts depuis un fichier
    @SuppressWarnings("unchecked")
    private void chargerEmprunts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("emprunts.ser"))) {
            listeEmprunts = (List<Emprunt>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            listeEmprunts = new ArrayList<>(); // Initialiser la liste si le chargement échoue
        }
    }

    // Méthode pour afficher les statistiques des emprunts 
    private void afficherStatistiquesEmprunts() {
        // Vérifier si la liste des emprunts est vide
        if (listeEmprunts == null || listeEmprunts.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aucune donnée d'emprunt disponible.", "Statistiques des Emprunts", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Calcul des statistiques d'emprunts
        int totalEmprunts = listeEmprunts.size();

        // Affichage des statistiques
        StringBuilder statsMessage = new StringBuilder();
        statsMessage.append("Statistiques des Emprunts :\n");
        statsMessage.append("Nombre total d'emprunts : ").append(totalEmprunts).append("\n");
        JOptionPane.showMessageDialog(null, statsMessage.toString(), "Statistiques des Emprunts", JOptionPane.INFORMATION_MESSAGE);
    }

        // Fonction pour compter et afficher le nombre total de livres
    private void afficherNombreTotalLivres() {
        // Charger les livres et compter leur nombre total
        List<Livres> livres = chargerLivres();
        int totalLivres = livres.size();

        // Afficher un message avec le nombre total de livres
        JOptionPane.showMessageDialog(null, "Nombre total de livres : " + totalLivres, "Nombre Total de Livres", JOptionPane.INFORMATION_MESSAGE);
    }

   // Méthode pour charger les livres depuis un fichier
@SuppressWarnings("unchecked") // Pour ignorer cet avertissement spécifique
private List<Livres> chargerLivres() {
    List<Livres> livres = new ArrayList<>();
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("livres.ser"))) {
        Object obj = ois.readObject();
        if (obj instanceof List) {
            livres = (List<Livres>) obj; // Utilisation du cast sûr avec <?>
        } else {
            System.err.println("Le fichier ne contient pas une liste de livres.");
        }
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    return livres;
}



    public static void main(String[] args) {
        new Bibliotheque();

    }
}

