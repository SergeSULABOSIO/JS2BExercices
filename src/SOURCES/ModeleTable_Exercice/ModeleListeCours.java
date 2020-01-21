/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.ModeleTable_Exercice;

import BEAN_BARRE_OUTILS.Bouton;
import BEAN_MenuContextuel.RubriqueSimple;
import Source.Callbacks.EcouteurSuppressionElement;
import Source.Callbacks.EcouteurValeursChangees;
import Source.GestionEdition;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;
import Source.Interface.InterfaceAgent;
import Source.Interface.InterfaceClasse;
import Source.Interface.InterfaceCours;
import Source.Objet.CouleurBasique;
import Source.Objet.Cours;

/**
 *
 * @author HP Pavilion
 */
public class ModeleListeCours extends AbstractTableModel {

    private String[] titreColonnes = {"N°", "Intitulé", "Classe", "Enseignant"};
    private Vector<Cours> listeData = new Vector<>();
    private JScrollPane parent;
    private EcouteurValeursChangees ecouteurModele;
    private ModeleListeAgent modeleListeEnseignant;
    private ModeleListeClasse modeleListeClasse;
    private Bouton btEnreg;
    private RubriqueSimple mEnreg;
    private CouleurBasique colBasique;
    private GestionEdition gestionEdition;

    public ModeleListeCours(GestionEdition gestionEdition, CouleurBasique colBasique, JScrollPane parent, Bouton btEnreg, RubriqueSimple mEnreg, ModeleListeAgent modeleListeEnseignant, ModeleListeClasse modeleListeClasse, EcouteurValeursChangees ecouteurModele) {
        this.colBasique = colBasique;
        this.parent = parent;
        this.ecouteurModele = ecouteurModele;
        this.modeleListeClasse = modeleListeClasse;
        this.modeleListeEnseignant = modeleListeEnseignant;
        this.mEnreg = mEnreg;
        this.btEnreg = btEnreg;
        this.gestionEdition = gestionEdition;
    }

    public void setListeCours(Vector<Cours> listeData) {
        this.listeData = listeData;
        redessinerTable();
    }

    private void updateClasse(InterfaceCours cours) {
        if (this.modeleListeClasse != null) {
            InterfaceClasse New_classe = this.modeleListeClasse.getClasse_signature(cours.getSignatureClasse());
            if (New_classe != null) {
                cours.setClasse(New_classe.getNom());
                cours.setIdClasse(New_classe.getId());
            }

        }
    }

    private void updateEnseignant(InterfaceCours cours) {
        if (this.modeleListeEnseignant != null) {
            InterfaceAgent New_enseignant = this.modeleListeEnseignant.getEnseignant_signature(cours.getSignatureEnseignant());
            if (New_enseignant != null) {
                cours.setEnseignant(New_enseignant.getPrenom() + " " + New_enseignant.getNom());
                cours.setIdEnseignant(New_enseignant.getId());
            }

        }
    }

    public Cours getCours(int row) {
        if (row < listeData.size() && row != -1) {
            Cours art = listeData.elementAt(row);
            if (art != null) {
                return art;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public InterfaceCours getCours_id(int id) {
        if (id != -1) {
            for (InterfaceCours art : listeData) {
                if (id == art.getId()) {
                    return art;
                }
            }
        }
        return null;
    }

    public Vector<Cours> getListeData() {
        return this.listeData;
    }

    public void actualiser() {
        System.out.println("actualiser - Cours...");
        redessinerTable();
    }

    public void AjouterCours(Cours newCours) {
        this.listeData.add(newCours);
        ecouteurModele.onValeurChangee();
        mEnreg.setCouleur(colBasique.getCouleur_foreground_objet_nouveau());                                        //mEnreg.setCouleur(Color.blue);
        btEnreg.setForeground(colBasique.getCouleur_foreground_objet_nouveau());                                   //btEnreg.setForeground(Color.blue);
        redessinerTable();
    }

    public void SupprimerCours(int row, EcouteurSuppressionElement ecouteurSuppressionElement) {
        if (row < listeData.size() && row != -1) {
            Cours articl = listeData.elementAt(row);
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
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return listeData.size();
    }

    @Override
    public int getColumnCount() {
        return titreColonnes.length;
    }

    @Override
    public String getColumnName(int column) {
        return titreColonnes[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //{"N°", "Intitulé", "Classe", "Enseignant"};
        if (rowIndex < listeData.size()) {
            InterfaceCours Icours = listeData.get(rowIndex);
            if (Icours != null) {
                switch (columnIndex) {
                    case 0:
                        return (rowIndex + 1) + "";
                    case 1:
                        return listeData.elementAt(rowIndex).getNom();
                    case 2:
                        return listeData.elementAt(rowIndex).getSignatureClasse();
                    case 3:
                        return listeData.elementAt(rowIndex).getSignatureEnseignant();
                    default:
                        return "Null";
                }
            }
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //{"N°", "Intitulé", "Classe", "Enseignant"};
        switch (columnIndex) {
            case 0:
                return String.class;//N°
            case 1:
                return String.class;//Intitulé
            case 2:
                return Long.class;//Classe
            case 3:
                return Long.class;//Enseignant
            default:
                return Object.class;
        }

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //{"N°", "Intitulé", "Classe", "Enseignant"};
        Cours eleve = null;
        boolean canEdit = false;
        if (listeData.size() > rowIndex) {
            eleve = listeData.elementAt(rowIndex);
            canEdit = gestionEdition.isEditable(eleve.getId(), 7);
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

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //{"N°", "Intitulé", "Classe", "Enseignant"};
        if (rowIndex < listeData.size()) {
            Cours Icours = listeData.get(rowIndex);
            if (Icours != null) {
                String avant = Icours.toString();
                switch (columnIndex) {
                    case 1:
                        Icours.setNom(aValue + "");
                        break;
                    case 2:
                        Icours.setSignatureClasse(Long.parseLong(aValue + ""));
                        updateClasse(Icours);
                        break;
                    case 3:
                        Icours.setSignatureEnseignant(Long.parseLong(aValue + ""));
                        updateEnseignant(Icours);
                        break;
                    default:
                        break;
                }
                String apres = Icours.toString();
                if (!avant.equals(apres)) {
                    if (Icours.getBeta() == InterfaceCours.BETA_EXISTANT) {
                        Icours.setBeta(InterfaceCours.BETA_MODIFIE);
                        mEnreg.setCouleur(colBasique.getCouleur_foreground_objet_nouveau());                                        //mEnreg.setCouleur(Color.blue);
                        btEnreg.setForeground(colBasique.getCouleur_foreground_objet_nouveau());                                   //btEnreg.setForeground(Color.blue);
                    }
                }
                listeData.set(rowIndex, Icours);
                ecouteurModele.onValeurChangee();
                fireTableDataChanged();
            }
        }
    }
}
