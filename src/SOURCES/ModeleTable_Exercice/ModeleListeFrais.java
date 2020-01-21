/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.ModeleTable_Exercice;

import BEAN_BARRE_OUTILS.Bouton;
import BEAN_MenuContextuel.RubriqueSimple;
import SOURCES.Utilitaires_Exercice.UtilExercice;
import Source.Callbacks.EcouteurSuppressionElement;
import Source.Callbacks.EcouteurValeursChangees;
import Source.GestionEdition;
import Source.Interface.InterfaceFrais;
import Source.Objet.Classe;
import Source.Objet.CouleurBasique;
import Source.Objet.Frais;
import Source.Objet.LiaisonFraisClasse;
import Source.Objet.LiaisonFraisPeriode;
import Source.Objet.Periode;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author HP Pavilion
 */
public class ModeleListeFrais extends AbstractTableModel {

    private String[] titreColonnes = null;  //N°, NOM, MONNAIE, TOTAL, 
    private Vector<Frais> listeData = new Vector<>();
    private JScrollPane parent;
    private EcouteurValeursChangees ecouteurModele;
    private ModeleListeClasse modeleListeClasse;
    private ModeleListePeriode modeleListePeriode;
    private Bouton btEnreg;
    private RubriqueSimple mEnreg;
    private CouleurBasique colBasique;
    private GestionEdition gestionEdition;

    public ModeleListeFrais(GestionEdition gestionEdition, CouleurBasique colBasique, JScrollPane parent, Bouton btEnreg, RubriqueSimple mEnreg, ModeleListeClasse modeleListeClasse, ModeleListePeriode modeleListePeriode, EcouteurValeursChangees ecouteurModele) {
        this.colBasique = colBasique;
        this.parent = parent;
        this.ecouteurModele = ecouteurModele;
        this.modeleListeClasse = modeleListeClasse;
        this.modeleListePeriode = modeleListePeriode;
        this.mEnreg = mEnreg;
        this.btEnreg = btEnreg;
        this.gestionEdition = gestionEdition;
    }

    public Frais getFrais(int row) {
        if (row < listeData.size() && row != -1) {
            Frais art = listeData.elementAt(row);
            if (art != null) {
                return art;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Frais getFrais_id(int id) {
        if (id != -1) {
            for (Frais art : listeData) {
                if (id == art.getId()) {
                    return art;
                }
            }
        }
        return null;
    }

    public Vector<Frais> getListeData() {
        return this.listeData;
    }

    public void setListeFrais(Vector<Frais> listeData) {
        listeData.stream().filter((Ifr) -> (Ifr != null)).forEachOrdered((Ifr) -> {
            lier(Ifr);
        });
        redessinerTable();
    }

    private void lier(Frais newFrais) {
        this.chargerLiaisonsClasses(newFrais);
        this.chargerLiaisonsPeriodes(newFrais);
        this.listeData.add(newFrais);
    }

    public void AjouterFrais(Frais newFrais) {
        lier(newFrais);
        mEnreg.setCouleur(colBasique.getCouleur_foreground_objet_nouveau());                                        //mEnreg.setCouleur(Color.blue);
        btEnreg.setForeground(colBasique.getCouleur_foreground_objet_nouveau());                                   //btEnreg.setForeground(Color.blue);
        redessinerTable();
        //lister();
    }

    private void chargerLiaisonsClasses(InterfaceFrais frais) {
        //On charge d'abord les liaisons possibles
        modeleListeClasse.getListeData().forEach((Iclasse) -> {
            LiaisonFraisClasse newLiaison = new LiaisonFraisClasse(Iclasse.getId(), Iclasse.getNom(), Iclasse.getSignature(), frais.getMontantDefaut());
            boolean ajoute = true;
            for (LiaisonFraisClasse lienExistant : frais.getLiaisonsClasses()) {
                if (lienExistant.getSignatureClasse() == Iclasse.getSignature()) {
                    ajoute = false;
                    break;
                }
            }
            if (ajoute == true) {
                System.out.println("Ajout Liaison Classe!");
                frais.ajouterLiaisonsClasse(newLiaison);
            }

        });
    }

    private void chargerLiaisonsPeriodes(InterfaceFrais frais) {
        //On charge d'abord les liaisons possibles
        modeleListePeriode.getListeData().forEach((Iperiode) -> {
            LiaisonFraisPeriode newLiaison = new LiaisonFraisPeriode(Iperiode.getId(), Iperiode.getNom(), Iperiode.getSignature(), 0);
            boolean ajoute = true;
            for (LiaisonFraisPeriode lienExistant : frais.getLiaisonsPeriodes()) {
                if (lienExistant.getSignaturePeriode() == Iperiode.getSignature()) {
                    ajoute = false;
                    break;
                }
            }
            if (ajoute == true) {
                System.out.println("Ajout Liaison Periode!");
                frais.ajouterLiaisonsPeriode(newLiaison);
            }
        });
    }

    public void SupprimerFrais(int row, EcouteurSuppressionElement ecouteurSuppressionElement) {
        if (row < listeData.size() && row != -1) {
            Frais articl = listeData.elementAt(row);
            if (articl != null) {
                int idASupp = articl.getId();
                if (ecouteurSuppressionElement.onCanDelete(idASupp, articl.getSignature()) == true) {
                    int dialogResult = JOptionPane.showConfirmDialog(parent, "Etes-vous sûr de vouloir supprimer cette liste?", "Avertissement", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        if (row <= listeData.size()) {
                            this.listeData.removeElementAt(row);
                            ecouteurSuppressionElement.onDeletionComplete(idASupp, articl.getSignature());
                        }
                        redessinerTable();
                    }
                }

            }
        }
    }

    public void viderListe() {
        int dialogResult = JOptionPane.showConfirmDialog(parent, "Etes-vous sûr de vouloir vider cette liste?", "Avertissement", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            this.listeData.removeAllElements();
            redessinerTable();
        }
    }

    public void redessinerTable() {
        ecouteurModele.onValeurChangee();
        if (!listeData.isEmpty()) {
            System.out.println("Liaisons classes: " + listeData.firstElement().getLiaisonsClasses().size());
            System.out.println("Liaisons périodes: " + listeData.firstElement().getLiaisonsPeriodes().size());
        }
        fireTableDataChanged();
    }

    public void actualiser() {
        redessinerTable();
    }

    @Override
    public int getRowCount() {
        return listeData.size();
    }

    private void initTitresColonnes() {
        //{"N°", "Nom", "Total", [Périodes], [Classes]};
        Vector titresColonnes = new Vector();
        titresColonnes.add("N°");
        titresColonnes.add("Nom");
        titresColonnes.add("Monnaie");
        titresColonnes.add("Total (100%)");

        //CHARGEMENT - PERIODES
        if (this.modeleListePeriode != null) {
            for (Periode periode : this.modeleListePeriode.getListeData()) {
                titresColonnes.add(periode.getNom());
            }
        }

        //CHARGEMENT - CLASSES
        if (this.modeleListeClasse != null) {
            for (Classe classe : this.modeleListeClasse.getListeData()) {
                titresColonnes.add(classe.getNom());
            }
        }

        //On verse les titres dans le tableau static
        this.titreColonnes = new String[titresColonnes.size()];
        for (int i = 0; i < titreColonnes.length; i++) {
            this.titreColonnes[i] = titresColonnes.elementAt(i) + "";
        }
    }

    @Override
    public int getColumnCount() {
        initTitresColonnes();
        return titreColonnes.length;
    }

    @Override
    public String getColumnName(int column) {
        initTitresColonnes();
        return titreColonnes[column];
    }

    private void appliquerMontantClassesDefaut(InterfaceFrais Ifrais) {
        if (Ifrais != null) {
            //On charge les valeurs pour chaque classe
            for (LiaisonFraisClasse lfc : Ifrais.getLiaisonsClasses()) {
                lfc.setMontant(Ifrais.getMontantDefaut());
            }

            //On charge les valeurs pour chaque période
            for (LiaisonFraisPeriode lpf : Ifrais.getLiaisonsPeriodes()) {
                lpf.setPourcentage(UtilExercice.round(100 / Ifrais.getLiaisonsPeriodes().size(), 2));
            }
        }

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //{"N°", "Nom", "Monnaie", "Total (100%)", [Périodes], [Classes]};
        if (rowIndex < listeData.size()) {
            Frais Ifrais = listeData.get(rowIndex);
            if (Ifrais != null) {
                int nbLiPeriode = Ifrais.getLiaisonsPeriodes().size();
                if (columnIndex <= 3) {
                    switch (columnIndex) {
                        case 0:
                            return (rowIndex + 1) + "";
                        case 1:
                            return Ifrais.getNom();
                        case 2:
                            return Ifrais.getSignatureMonnaie();
                        case 3:
                            return Ifrais.getMontantDefaut();
                        default:
                            return null;
                    }
                } else if (columnIndex > 3 && columnIndex < (4 + nbLiPeriode)) {
                    Vector<LiaisonFraisPeriode> liaisonsPeriodes = Ifrais.getLiaisonsPeriodes();
                    int ii = columnIndex - 4;
                    if (ii < liaisonsPeriodes.size()) {
                        return liaisonsPeriodes.elementAt(ii).getPourcentage();
                    } else {
                        return -100;
                    }
                } else {
                    Vector<LiaisonFraisClasse> liaisonsClasses = Ifrais.getLiaisonsClasses();
                    int ii = columnIndex - 4 - nbLiPeriode;
                    if (ii < liaisonsClasses.size()) {
                        return liaisonsClasses.elementAt(ii).getMontant();
                    } else {
                        return -10;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //{"N°", "Nom", "Monnaie", "Total (100%)", [Périodes], [Classes]};
        switch (columnIndex) {
            case 0:
                return String.class;//N°
            case 1:
                return String.class;//Nom
            case 2:
                return Long.class;//Monnaie
            case 3:
                return Double.class;//Total
            default:
                return Double.class;
        }

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        Frais eleve = null;
        boolean canEdit = false;
        if (listeData.size() > rowIndex) {
            eleve = listeData.elementAt(rowIndex);
            canEdit = gestionEdition.isEditable(eleve.getId(), 3);
        }
        if (canEdit == true) {
            if (columnIndex == 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private void recalculerPourcentagesRestants(int indexActual, double pourcentageAvant, InterfaceFrais IfraisEncours) {
        if (indexActual < IfraisEncours.getLiaisonsPeriodes().size() - 1) {
            double partie01 = 0;
            for (int i = 0; i <= indexActual; i++) {
                partie01 += IfraisEncours.getLiaisonsPeriodes().elementAt(i).getPourcentage();
            }
            //System.out.println("Partie A: " + partie01);
            double reste = (100 - partie01);
            int nbLiaisonRestant = IfraisEncours.getLiaisonsPeriodes().size() - indexActual - 1;
            //System.out.println("Reste: " + reste);
            if (reste > 0) {
                //System.out.println("nbLiaisonRestant: " + nbLiaisonRestant);
                for (int i = indexActual + 1; i < IfraisEncours.getLiaisonsPeriodes().size(); i++) {
                    if (nbLiaisonRestant != 0) {
                        IfraisEncours.getLiaisonsPeriodes().elementAt(i).setPourcentage(reste / nbLiaisonRestant);
                    } else {
                        IfraisEncours.getLiaisonsPeriodes().elementAt(i).setPourcentage(reste);
                    }
                }
            } else if (reste < 0) {
                JOptionPane.showMessageDialog(parent, ""
                        + "Désolé, la sommes des Pourcentages définie est supérieure à 100%.\n"
                        + "Merci de revoir les valeurs", "Erreur - Pourcentage !", JOptionPane.ERROR_MESSAGE);
                IfraisEncours.getLiaisonsPeriodes().elementAt(indexActual).setPourcentage(pourcentageAvant);
            } else {
                for (int i = indexActual + 1; i < IfraisEncours.getLiaisonsPeriodes().size(); i++) {
                    IfraisEncours.getLiaisonsPeriodes().elementAt(i).setPourcentage(0);
                }
            }
        } else {
            double total = 0;
            for (int i = 0; i < IfraisEncours.getLiaisonsPeriodes().size(); i++) {
                total += IfraisEncours.getLiaisonsPeriodes().elementAt(i).getPourcentage();
            }
            if (total != 100) {
                JOptionPane.showMessageDialog(parent, ""
                        + "Désolé, la sommes des Pourcentages définie est différente de 100% (" + total + "%).\n"
                        + "Merci de revoir les valeurs", "Erreur - Pourcentage !", JOptionPane.ERROR_MESSAGE);
                IfraisEncours.getLiaisonsPeriodes().elementAt(indexActual).setPourcentage(pourcentageAvant);
            }
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //{"N°", "Nom", "Monnaie", "Total (100%)", [Périodes], [Classes]};
        if (rowIndex < listeData.size()) {
            Frais Ifrais = listeData.get(rowIndex);
            if (Ifrais != null) {
                int nbLiPeriode = Ifrais.getLiaisonsPeriodes().size();
                String avant = Ifrais.toString() + "" + Ifrais.getLiaisonsClasses().toString();
                if (columnIndex <= 3) {
                    switch (columnIndex) {
                        case 1:
                            Ifrais.setNom(aValue + "");
                            break;
                        case 2:
                            Ifrais.setSignatureMonnaie(Long.parseLong(aValue + ""));
                            break;
                        case 3:
                            Ifrais.setMontantDefaut(Double.parseDouble(aValue + ""));
                            appliquerMontantClassesDefaut(Ifrais);
                            break;
                        default:
                            break;
                    }
                } else if (columnIndex > 3 && columnIndex < (4 + nbLiPeriode)) {
                    //Definition des valeurs Périodes - Frais
                    Vector<LiaisonFraisPeriode> liaisonsPeriodes = Ifrais.getLiaisonsPeriodes();
                    int indexLiaison = columnIndex - 4;
                    if (indexLiaison < liaisonsPeriodes.size()) {
                        double valeurPourcentage = Double.parseDouble(aValue + "");
                        double valeurPourcentageAvant = liaisonsPeriodes.elementAt(indexLiaison).getPourcentage();
                        liaisonsPeriodes.elementAt(indexLiaison).setPourcentage(valeurPourcentage);
                        recalculerPourcentagesRestants(indexLiaison, valeurPourcentageAvant, Ifrais);
                    }
                } else {
                    //Definition des valeurs Classes - Frais
                    Vector<LiaisonFraisClasse> liaisonsClasses = Ifrais.getLiaisonsClasses();
                    int indexLiaison = columnIndex - 4 - nbLiPeriode;
                    if (indexLiaison < liaisonsClasses.size()) {
                        liaisonsClasses.elementAt(indexLiaison).setMontant(Double.parseDouble(aValue + ""));
                    }
                }

                String apres = Ifrais.toString() + "" + Ifrais.getLiaisonsClasses().toString();
                if (!avant.equals(apres)) {
                    if (Ifrais.getBeta() == InterfaceFrais.BETA_EXISTANT) {
                        Ifrais.setBeta(InterfaceFrais.BETA_MODIFIE);
                        mEnreg.setCouleur(colBasique.getCouleur_foreground_objet_nouveau());                                        //mEnreg.setCouleur(Color.blue);
                        btEnreg.setForeground(colBasique.getCouleur_foreground_objet_nouveau());                                   //btEnreg.setForeground(Color.blue);
                    }
                }
                listeData.set(rowIndex, Ifrais);
                ecouteurModele.onValeurChangee();
                fireTableDataChanged();
            }
        }
    }

}
