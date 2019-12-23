/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TESTS_EXEMPLES_Exercice;

import SOURCES.Callback_Exercice.EcouteurExerice;
import SOURCES.UI_Exercice.PanelExercice;
import SOURCES.Utilitaires_Exercice.DonneesExercice;
import SOURCES.Utilitaires_Exercice.ParametreExercice;
import SOURCES.Utilitaires_Exercice.SortiesExercice;
import SOURCES.Utilitaires_Exercice.UtilExercice;
import Source.Callbacks.EcouteurEnregistrement;
import Source.Callbacks.EcouteurFreemium;
import Source.Interface.InterfaceAgent;
import Source.Interface.InterfaceCharge;
import Source.Interface.InterfaceClasse;
import Source.Interface.InterfaceCours;
import Source.Interface.InterfaceFrais;
import Source.Interface.InterfaceMonnaie;
import Source.Interface.InterfacePeriode;
import Source.Interface.InterfaceRevenu;
import Source.Interface.InterfaceUtilisateur;
import Source.Objet.Agent;
import Source.Objet.Charge;
import Source.Objet.Classe;
import Source.Objet.CouleurBasique;
import Source.Objet.Cours;
import Source.Objet.Entreprise;
import Source.Objet.Annee;
import Source.Objet.Frais;
import Source.Objet.Monnaie;
import Source.Objet.Periode;
import Source.Objet.Revenu;
import Source.Objet.Utilisateur;
import static java.lang.Thread.sleep;
import java.util.Date;
import java.util.Vector;
import Source.Interface.InterfaceAnnee;

/**
 *
 * @author HP Pavilion
 */
public class Principal_Exercice extends javax.swing.JFrame {

    /**
     * Creates new form TEST_Principal
     */
    
    public PanelExercice gestionnaireExercice = null;
    public Entreprise entreprise = new Entreprise(1, "ECOLE CARESIENNE DE KINSHASA", "7e Rue Limeté Industrielle, Kinshasa/RDC", "+243844803514", "infos@cartesien.org", "wwww.cartesien.org", "logo.png", "RCCM/KD/CD/4513", "IDN00111454", "IMP00124100", "Equity Bank Congo SA", "AIB RDC Sarl", "000000121212400", "IBANNN0012", "SWIFTCDK");
    public Monnaie MONNAIE_USD_OUTPUT = new Monnaie(20, entreprise.getId(), 1, -1, "Dollars Américains", "$", InterfaceMonnaie.NATURE_MONNAIE_ETRANGERE, 1620, new Date().getTime(), InterfaceMonnaie.BETA_EXISTANT);
    public Utilisateur utilisateur = new Utilisateur(12, entreprise.getId(), "SULA", "BOSIO", "Serge", "sulabosiog@gmail.com", "abc", InterfaceUtilisateur.TYPE_ADMIN, UtilExercice.generateSignature(), InterfaceUtilisateur.DROIT_CONTROLER, InterfaceUtilisateur.DROIT_CONTROLER, InterfaceUtilisateur.DROIT_CONTROLER, InterfaceUtilisateur.DROIT_CONTROLER, InterfaceUtilisateur.DROIT_CONTROLER, InterfaceUtilisateur.DROIT_CONTROLER, InterfaceUtilisateur.DROIT_CONTROLER, InterfaceUtilisateur.BETA_EXISTANT);
    public DonneesExercice donneesExercice = null;
    public ParametreExercice parametreExercice = null;

    public Principal_Exercice() {
        initComponents();
    }

    private void initParams() {
        parametreExercice = new ParametreExercice(entreprise, utilisateur, MONNAIE_USD_OUTPUT);
    }

    private void initDonnees() {
        Annee exerciceExistant = null;
        //exerciceExistant = new TEST_Exercice(12, 10, 15, "Année scolaire 2019 - 2020", new Date(), Util.getDate_AjouterAnnee(new Date(), 1));

        Vector<Agent> agents = new Vector<>();
        Vector<Charge> charges = new Vector<>();
        Vector<Classe> classes = new Vector<>();
        Vector<Cours> cours = new Vector<>();
        Vector<Frais> frais = new Vector<>();
        Vector<Monnaie> monnaies = new Vector<>();
        Vector<Revenu> revenus = new Vector<>();
        Vector<Periode> periodes = new Vector<>();
        //RAS
        //RAS encore
        donneesExercice = new DonneesExercice(exerciceExistant, agents, charges, classes, cours, frais, monnaies, revenus, periodes);
    }

    private void initParametres() {
        initDonnees();
        initParams();
        
        gestionnaireExercice = new PanelExercice(new EcouteurFreemium() {
            @Override
            public boolean onVerifie() {
                return true;
            }
        }, new CouleurBasique(), tabPrincipale, parametreExercice, donneesExercice, new EcouteurExerice() {
            @Override
            public void onEnregistre(SortiesExercice sortiesExercice) {

                Thread th = new Thread() {
                    @Override
                    public void run() {
                        try {
                            EcouteurEnregistrement ee = sortiesExercice.getEcouteurEnregistrement();
                            ee.onUploading("Chargement...");
                            sleep(50);

                            //Listage des elements produits par le gestionnaire de l'exercice
                            Annee newExerc = sortiesExercice.getExercice();
                            
                            if (newExerc.getBeta() == InterfaceAnnee.BETA_MODIFIE || newExerc.getBeta() == InterfaceAnnee.BETA_NOUVEAU) {
                                System.out.println(" * EXERCICE: " + newExerc.toString());
                                
                                newExerc.setBeta(InterfaceAnnee.BETA_EXISTANT);
                                newExerc.setId(10);
                                newExerc.setIdUtilisateur(10);
                                newExerc.setIdEntreprise(10);
                                
                                //Après enregistrement
                                System.out.println(" * EXERCICE: " + newExerc.toString());
                                donneesExercice.setExercice(newExerc);
                                
                            }

                            sortiesExercice.getListeMonnaies().forEach((clss) -> {
                                if (clss.getBeta() == InterfaceMonnaie.BETA_MODIFIE || clss.getBeta() == InterfaceMonnaie.BETA_NOUVEAU) {
                                    System.out.println(" * MONNAIE: " + clss.toString());

                                    //Après enregistrement
                                    clss.setId(new Date().getSeconds());
                                    clss.setBeta(InterfaceMonnaie.BETA_EXISTANT);
                                }
                            });

                            sortiesExercice.getListeClasse().forEach((clss) -> {
                                if (clss.getBeta() == InterfaceClasse.BETA_MODIFIE || clss.getBeta() == InterfaceClasse.BETA_NOUVEAU) {
                                    System.out.println(" * CLASSE: " + clss.toString());

                                    //Après enregistrement
                                    clss.setId(new Date().getSeconds());
                                    clss.setBeta(InterfaceClasse.BETA_EXISTANT);
                                }
                            });
                            
                            sortiesExercice.getListeFrais().forEach((frs) -> {
                                if (frs.getBeta() == InterfaceFrais.BETA_MODIFIE || frs.getBeta() == InterfaceFrais.BETA_NOUVEAU) {
                                    System.out.println(" * FRAIS: " + frs.toString());
                                    frs.getLiaisonsClasses().forEach((lia) -> {
                                        System.out.println("\t LIAISON CLASSES: " + lia.toString());
                                    });
                                    
                                    frs.getLiaisonsPeriodes().forEach((lia) -> {
                                        System.out.println("\t LIAISON PERIODE: " + lia.toString());
                                    });
                                    
                                    //Après enregistrement
                                    frs.setId(new Date().getSeconds());
                                    frs.setBeta(InterfaceFrais.BETA_EXISTANT);
                                }
                            });
                            
                            sortiesExercice.getListeCharges().forEach((ch) -> {
                                if(ch.getBeta() == InterfaceCharge.BETA_MODIFIE || ch.getBeta() == InterfaceCharge.BETA_NOUVEAU){
                                    System.out.println(" * CHARGE: " + ch.toString());
                                    
                                    //Après enregistrement
                                    ch.setId(new Date().getSeconds());
                                    ch.setBeta(InterfaceCharge.BETA_EXISTANT);
                                }
                            });

                            sortiesExercice.getListeRevenus().forEach((clss) -> {
                                if(clss.getBeta() == InterfaceRevenu.BETA_MODIFIE || clss.getBeta() == InterfaceRevenu.BETA_NOUVEAU){
                                    System.out.println(" * REVENUS: " + clss.toString());
                                    
                                    //Après enregistrment
                                    clss.setId(new Date().getSeconds());
                                    clss.setBeta(InterfaceRevenu.BETA_EXISTANT);
                                }
                            });

                            sortiesExercice.getListeAgents().forEach((clss) -> {
                                if(clss.getBeta() == InterfaceAgent.BETA_MODIFIE || clss.getBeta() == InterfaceAgent.BETA_NOUVEAU){
                                    System.out.println(" * AGENTS: " + clss.toString());
                                    
                                    //Après enregistrment
                                    clss.setId(new Date().getSeconds());
                                    clss.setBeta(InterfaceAgent.BETA_EXISTANT);
                                }
                                
                            });

                            sortiesExercice.getListeCours().forEach((clss) -> {
                                if(clss.getBeta() == InterfaceCours.BETA_MODIFIE || clss.getBeta() == InterfaceCours.BETA_NOUVEAU){
                                    System.out.println(" * COURS: " + clss.toString());
                                    
                                    //Après enregistrement
                                    clss.setId(new Date().getSeconds());
                                    clss.setBeta(InterfaceCours.BETA_EXISTANT);
                                }
                                
                            });
                            
                            sortiesExercice.getListePeriodes().forEach((clss) -> {
                                if(clss.getBeta() == InterfacePeriode.BETA_MODIFIE || clss.getBeta() == InterfacePeriode.BETA_NOUVEAU){
                                    System.out.println(" * PERIODE: " + clss.toString());
                                    
                                    //Après enregistrement
                                    clss.setId(new Date().getSeconds());
                                    clss.setBeta(InterfacePeriode.BETA_EXISTANT);
                                }
                                
                            });

                            sortiesExercice.getEcouteurEnregistrement().onDone("Enregistré!");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                th.start();

            }

            @Override
            public void onDetruitExercice(int idExercice, long signature) {
                System.out.println("DESTRUCTION DE L'EXERCICE " + idExercice);
            }

            @Override
            public void onDetruitElements(int idElement, int index, long signature) {
                System.out.println("DESCTRUCTION DE L'ELEMENT " + idElement + ", INDEX " + index);
            }

            
            
            
            
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        tabPrincipale = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Ouvrir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(510, Short.MAX_VALUE))
            .addComponent(tabPrincipale, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabPrincipale, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        initParametres();

        //Chargement du gestionnaire sur l'onglet
        tabPrincipale.addTab("New Année Scolaire", gestionnaireExercice);

        //On séléctionne l'onglet actuel
        tabPrincipale.setSelectedComponent(gestionnaireExercice);


    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal_Exercice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal_Exercice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal_Exercice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal_Exercice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal_Exercice().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JTabbedPane tabPrincipale;
    // End of variables declaration//GEN-END:variables
}
