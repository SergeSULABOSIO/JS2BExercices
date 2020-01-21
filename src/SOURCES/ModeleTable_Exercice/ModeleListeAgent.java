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
import Source.Objet.Agent;
import Source.Objet.CouleurBasique;

/**
 *
 * @author HP Pavilion
 */
public class ModeleListeAgent extends AbstractTableModel {

    private String[] titreColonnes = {"N°", "Nom", "Postnom", "Prénom", "Sexe", "Niveau d'étude", "Catégorie"};
    private Vector<Agent> listeData = new Vector<>();
    private JScrollPane parent;
    private EcouteurValeursChangees ecouteurModele;
    private Bouton btEnreg;
    private RubriqueSimple mEnreg;
    private CouleurBasique colBasique;
    private GestionEdition gestionEdition;

    public ModeleListeAgent(GestionEdition gestionEdition, CouleurBasique colBasique, JScrollPane parent, Bouton btEnreg, RubriqueSimple mEnreg, EcouteurValeursChangees ecouteurModele) {
        this.colBasique = colBasique;
        this.parent = parent;
        this.ecouteurModele = ecouteurModele;
        this.mEnreg = mEnreg;
        this.btEnreg = btEnreg;
        this.gestionEdition = gestionEdition;
    }

    public void setListeAgents(Vector<Agent> listeData) {
        this.listeData = listeData;
        redessinerTable();
    }

    public Agent getAgent(int row) {
        if (row < listeData.size() && row != -1) {
            Agent art = listeData.elementAt(row);
            if (art != null) {
                return art;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Agent getEnseignant_id(int id) {
        if (id != -1) {
            for (Agent art : listeData) {
                if (id == art.getId()) {
                    return art;
                }
            }
        }
        return null;
    }

    public Agent getEnseignant_signature(long signature) {
        if (signature != -1) {
            for (Agent art : listeData) {
                if (signature == art.getSignature()) {
                    return art;
                }
            }
        }
        return null;
    }

    public Vector<Agent> getListeData() {
        return this.listeData;
    }

    public void actualiser() {
        System.out.println("actualiser - Enseignant...");
        redessinerTable();
    }

    public void AjouterEnseignant(Agent newEnseignant) {
        this.listeData.add(newEnseignant);
        ecouteurModele.onValeurChangee();
        mEnreg.setCouleur(colBasique.getCouleur_foreground_objet_nouveau());                                        //mEnreg.setCouleur(Color.blue);
        btEnreg.setForeground(colBasique.getCouleur_foreground_objet_nouveau());                                   //btEnreg.setForeground(Color.blue);
        redessinerTable();
    }

    public void SupprimerEnseignant(int row, EcouteurSuppressionElement ecouteurSuppressionElement) {
        if (row < listeData.size() && row != -1) {
            Agent articl = listeData.elementAt(row);
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
        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Niveau d'étude", "Catégorie"};
        if (rowIndex < listeData.size()) {
            InterfaceAgent Iagent = listeData.get(rowIndex);
            if (Iagent != null) {
                switch (columnIndex) {
                    case 0:
                        return (rowIndex + 1) + "";
                    case 1:
                        return Iagent.getNom();
                    case 2:
                        return Iagent.getPostnom();
                    case 3:
                        return Iagent.getPrenom();
                    case 4:
                        return Iagent.getSexe();
                    case 5:
                        return Iagent.getNiveauEtude();
                    case 6:
                        return Iagent.getCategorie();
                    default:
                        return "Null";
                }
            }
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Niveau d'étude", "Catégorie"};
        switch (columnIndex) {
            case 0:
                return String.class;//N°
            case 1:
                return String.class;//Nom
            case 2:
                return String.class;//postnom
            case 3:
                return String.class;//prenom
            case 4:
                return Integer.class;//sexe
            case 5:
                return Integer.class;//niveau d'étude
            case 6:
                return Integer.class;//niveau d'étude
            default:
                return Object.class;
        }

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        Agent eleve = null;
        boolean canEdit = false;
        if (listeData.size() > rowIndex) {
            eleve = listeData.elementAt(rowIndex);
            canEdit = gestionEdition.isEditable(eleve.getId(), 6);
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
        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Niveau d'étude", "Catégorie"};
        if (rowIndex < listeData.size()) {
            Agent Iagent = listeData.get(rowIndex);
            if (Iagent != null) {
                String avant = Iagent.toString();
                switch (columnIndex) {
                    case 1:
                        Iagent.setNom(aValue + "");
                        break;
                    case 2:
                        Iagent.setPostnom(aValue + "");
                        break;
                    case 3:
                        Iagent.setPrenom(aValue + "");
                        break;
                    case 4:
                        Iagent.setSexe(Integer.parseInt(aValue + ""));
                        break;
                    case 5:
                        Iagent.setNiveauEtude(Integer.parseInt(aValue + ""));
                        break;
                    case 6:
                        Iagent.setCategorie(Integer.parseInt(aValue + ""));
                        break;
                    default:
                        break;
                }
                String apres = Iagent.toString();
                if (!avant.equals(apres)) {
                    if (Iagent.getBeta() == InterfaceAgent.BETA_EXISTANT) {
                        Iagent.setBeta(InterfaceAgent.BETA_MODIFIE);
                        mEnreg.setCouleur(colBasique.getCouleur_foreground_objet_nouveau());                                        //mEnreg.setCouleur(Color.blue);
                        btEnreg.setForeground(colBasique.getCouleur_foreground_objet_nouveau());                                   //btEnreg.setForeground(Color.blue);
                    }
                }
                listeData.set(rowIndex, Iagent);
                ecouteurModele.onValeurChangee();
                fireTableDataChanged();
            }
        }
    }

}
