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
import Source.Interface.InterfaceMonnaie;
import Source.Objet.CouleurBasique;
import Source.Objet.Monnaie;
import Source.Objet.Periode;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author HP Pavilion
 */
public class ModeleListeMonnaie extends AbstractTableModel {

    private String[] titreColonnes = new String[]{"N°", "Nom", "Code", "Nature", "Taux"};
    private Vector<Monnaie> listeData = new Vector<>();
    private JScrollPane parent;
    private EcouteurValeursChangees ecouteurModele;
    private Bouton btEnreg;
    private RubriqueSimple mEnreg;
    private CouleurBasique colBasique;
    private GestionEdition gestionEdition;

    public ModeleListeMonnaie(GestionEdition gestionEdition, CouleurBasique colBasique, JScrollPane parent, Bouton btEnreg, RubriqueSimple mEnreg, EcouteurValeursChangees ecouteurModele) {
        this.colBasique = colBasique;
        this.parent = parent;
        this.ecouteurModele = ecouteurModele;
        this.mEnreg = mEnreg;
        this.btEnreg = btEnreg;
        this.gestionEdition = gestionEdition;
    }

    public void setListeMonnaie(Vector<Monnaie> listeData) {
        this.listeData = listeData;
        redessinerTable();
    }

    public Monnaie getMonnaie(int row) {
        if (row < listeData.size() && row != -1) {
            Monnaie art = listeData.elementAt(row);
            if (art != null) {
                return art;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Monnaie getMonnaie_id(int id) {
        if (id != -1) {
            for (Monnaie art : listeData) {
                if (id == art.getId()) {
                    return art;
                }
            }
        }
        return null;
    }

    public Monnaie getMonnaie_signature(long signature) {
        if (signature != -1) {
            for (Monnaie art : listeData) {
                if (signature == art.getSignature()) {
                    return art;
                }
            }
        }
        return null;
    }

    public Vector<Monnaie> getListeData() {
        return this.listeData;
    }

    public void AjouterMonnaie(Monnaie newMonnaie) {
        this.listeData.add(newMonnaie);
        mEnreg.setCouleur(colBasique.getCouleur_foreground_objet_nouveau());                                        //mEnreg.setCouleur(Color.blue);
        btEnreg.setForeground(colBasique.getCouleur_foreground_objet_nouveau());                                   //btEnreg.setForeground(Color.blue);
        redessinerTable();
    }

    public void SupprimerMonnaie(int row, EcouteurSuppressionElement ecouteurSuppressionElement) {
        if (row < listeData.size() && row != -1) {
            Monnaie articl = listeData.elementAt(row);
            if (articl != null) {
                int idASupp = articl.getId();
                int dialogResult = JOptionPane.showConfirmDialog(parent, "Etes-vous sûr de vouloir supprimer cette liste?", "Avertissement", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    if (row <= listeData.size()) {
                        this.listeData.removeElementAt(row);
                        ecouteurSuppressionElement.onSuppressionConfirmee(idASupp, articl.getSignature());
                    }
                    redessinerTable();
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

    public void actualiser() {
        System.out.println("actualiser - Monnaie...");
        redessinerTable();
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
        //{"N°", "Nom", "Code", "Nature", "Taux"};
        if (rowIndex < listeData.size()) {
            InterfaceMonnaie Imonnaie = listeData.get(rowIndex);
            if (Imonnaie != null) {
                switch (columnIndex) {
                    case 0:
                        return (rowIndex + 1) + "";
                    case 1:
                        return listeData.elementAt(rowIndex).getNom();
                    case 2:
                        return listeData.elementAt(rowIndex).getCode();
                    case 3:
                        return listeData.elementAt(rowIndex).getNature();
                    case 4:
                        return listeData.elementAt(rowIndex).getTauxMonnaieLocale();
                    default:
                        return "null";
                }
            }
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //{"N°", "Nom", "Code", "Nature", "Taux"};
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return Integer.class;
            default:
                return Object.class;
        }

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        Monnaie eleve = null;
        boolean canEdit = false;
        if (listeData.size() > rowIndex) {
            eleve = listeData.elementAt(rowIndex);
            canEdit = gestionEdition.isEditable(eleve.getId(), 1);
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
        //{"N°", "Nom", "Code", "Nature", "Taux"};
        if (rowIndex < listeData.size()) {
            Monnaie Imonnaie = listeData.get(rowIndex);
            if (Imonnaie != null) {
                String avant = Imonnaie.toString();
                switch (columnIndex) {
                    case 1:
                        Imonnaie.setNom(aValue + "");
                        break;
                    case 2:
                        Imonnaie.setCode(aValue + "");
                        break;
                    case 3:
                        Imonnaie.setNature(Integer.parseInt(aValue + ""));
                        break;
                    case 4:
                        Imonnaie.setTauxMonnaieLocale(Double.parseDouble(aValue + ""));
                        break;
                    default:
                        break;
                }
                String apres = Imonnaie.toString();
                if (!avant.equals(apres)) {
                    if (Imonnaie.getBeta() == InterfaceMonnaie.BETA_EXISTANT) {
                        Imonnaie.setBeta(InterfaceMonnaie.BETA_MODIFIE);
                        mEnreg.setCouleur(colBasique.getCouleur_foreground_objet_nouveau());                                        //mEnreg.setCouleur(Color.blue);
                        btEnreg.setForeground(colBasique.getCouleur_foreground_objet_nouveau());                                   //btEnreg.setForeground(Color.blue);
                    }
                }
                listeData.set(rowIndex, Imonnaie);
                ecouteurModele.onValeurChangee();
                fireTableDataChanged();
            }
        }
    }
}


