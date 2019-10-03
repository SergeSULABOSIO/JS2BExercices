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
import Source.Interface.InterfaceCharge;
import Source.Interface.InterfaceMonnaie;
import Source.Objet.Charge;
import Source.Objet.CouleurBasique;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author HP Pavilion
 */
public class ModeleListeCharges extends AbstractTableModel {

    private String[] titreColonnes = new String[]{"N°", "Catégorie", "Prévision (12 mois)", "Monnaie"};
    private Vector<Charge> listeData = new Vector<>();
    private JScrollPane parent;
    private EcouteurValeursChangees ecouteurModele;
    private ModeleListeMonnaie modeleListeMonnaie;
    private Bouton btEnreg;
    private RubriqueSimple mEnreg;
    private CouleurBasique colBasique;
    private GestionEdition gestionEdition;

    public ModeleListeCharges(GestionEdition gestionEdition, CouleurBasique colBasique, JScrollPane parent, Bouton btEnreg, RubriqueSimple mEnreg, ModeleListeMonnaie modeleListeMonnaie, EcouteurValeursChangees ecouteurModele) {
        this.colBasique = colBasique;
        this.parent = parent;
        this.ecouteurModele = ecouteurModele;
        this.modeleListeMonnaie = modeleListeMonnaie;
        this.mEnreg = mEnreg;
        this.btEnreg = btEnreg;
        this.gestionEdition = gestionEdition;
    }

    public void setListeCharges(Vector<Charge> listeData) {
        this.listeData = listeData;
        redessinerTable();
    }

    public Charge getCharge(int row) {
        if (row < listeData.size() && row != -1) {
            Charge art = listeData.elementAt(row);
            if (art != null) {
                return art;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Charge getCharge_id(int id) {
        if (id != -1) {
            for (Charge art : listeData) {
                if (id == art.getId()) {
                    return art;
                }
            }
        }
        return null;
    }

    public Vector<Charge> getListeData() {
        return this.listeData;
    }

    public void AjouterCharge(Charge newCharge) {
        this.listeData.add(newCharge);
        mEnreg.setCouleur(colBasique.getCouleur_foreground_objet_nouveau());                                        //mEnreg.setCouleur(Color.blue);
        btEnreg.setForeground(colBasique.getCouleur_foreground_objet_nouveau());                                   //btEnreg.setForeground(Color.blue);
        redessinerTable();
    }

    public void SupprimerCharge(int row, EcouteurSuppressionElement ecouteurSuppressionElement) {
        if (row < listeData.size() && row != -1) {
            Charge articl = listeData.elementAt(row);
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
        System.out.println("actualiser - Charge...");
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
        //{"N°", "Catégorie", "Volume", "Monnaie"};
        if (rowIndex < listeData.size()) {
            Charge Icharge = listeData.get(rowIndex);
            if (Icharge != null) {
                switch (columnIndex) {
                    case 0:
                        return (rowIndex + 1) + "";
                    case 1:
                        return Icharge.getNom();
                    case 2:
                        return Icharge.getLimiteAnnuelle();
                    case 3:
                        return Icharge.getSignatureMonnaie();
                    default:
                        return "null";
                }
            }
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //{"N°", "Catégorie", "Volume", "Monnaie"};
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return Double.class;
            case 3:
                return Long.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        Charge eleve = null;
        boolean canEdit = false;
        if (listeData.size() > rowIndex) {
            eleve = listeData.elementAt(rowIndex);
            canEdit = gestionEdition.isEditable(eleve.getId(), 4);
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
        //{"N°", "Catégorie", "Volume", "Monnaie"};
        if (rowIndex < listeData.size()) {
            Charge Icharge = listeData.get(rowIndex);
            if (Icharge != null) {
                String avant = Icharge.toString();
                switch (columnIndex) {
                    case 1:
                        Icharge.setNom(aValue + "");
                        break;
                    case 2:
                        Icharge.setLimiteAnnuelle(Double.parseDouble(aValue + ""));
                        break;
                    case 3:
                        Icharge.setSignatureMonnaie(Long.parseLong(aValue + ""));
                        updateMonnaie(Icharge);
                        break;
                    default:
                        break;
                }
                String apres = Icharge.toString();
                if (!avant.equals(apres)) {
                    if (Icharge.getBeta() == InterfaceCharge.BETA_EXISTANT) {
                        Icharge.setBeta(InterfaceCharge.BETA_MODIFIE);
                        mEnreg.setCouleur(colBasique.getCouleur_foreground_objet_nouveau());                                        //mEnreg.setCouleur(Color.blue);
                        btEnreg.setForeground(colBasique.getCouleur_foreground_objet_nouveau());                                   //btEnreg.setForeground(Color.blue);
                    }
                }
                listeData.set(rowIndex, Icharge);
                ecouteurModele.onValeurChangee();
                fireTableDataChanged();
            }
        }
    }

    private void updateMonnaie(InterfaceCharge Icharge) {
        if (this.modeleListeMonnaie != null) {
            InterfaceMonnaie New_monnaie = this.modeleListeMonnaie.getMonnaie_signature(Icharge.getSignatureMonnaie());
            Icharge.setMonnaie(New_monnaie.getCode());
            Icharge.setIdMonnaie(New_monnaie.getId());
        }
    }

}


