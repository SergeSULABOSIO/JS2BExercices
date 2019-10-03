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
import Source.Interface.InterfaceClasse;
import Source.Interface.InterfaceFrais;
import Source.Interface.InterfacePeriode;
import Source.Objet.CouleurBasique;
import Source.Objet.LiaisonFraisPeriode;
import Source.Objet.Periode;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author HP Pavilion
 */
public class ModeleListePeriode extends AbstractTableModel {

    private String[] titreColonnes = {"N°", "Nom", "Début", "Fin"};
    private Vector<Periode> listeData = new Vector<>();
    private JScrollPane parent;
    private EcouteurValeursChangees ecouteurModele;
    private Bouton btEnreg;
    private RubriqueSimple mEnreg;
    private CouleurBasique colBasique;
    private GestionEdition gestionEdition;

    public ModeleListePeriode(GestionEdition gestionEdition, CouleurBasique colBasique, JScrollPane parent, Bouton btEnreg, RubriqueSimple mEnreg, EcouteurValeursChangees ecouteurModele) {
        this.colBasique = colBasique;
        this.parent = parent;
        this.ecouteurModele = ecouteurModele;
        this.mEnreg = mEnreg;
        this.btEnreg = btEnreg;
        this.gestionEdition = gestionEdition;
    }

    public void setListePeriodes(Vector<Periode> listeData) {
        this.listeData = listeData;
        redessinerTable();
    }

    public Periode getPeriode(int row) {
        if (row < listeData.size() && row != -1) {
            Periode art = listeData.elementAt(row);
            if (art != null) {
                return art;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public InterfacePeriode getPeriode_id(int id) {
        if (id != -1) {
            for (InterfacePeriode art : listeData) {
                if (id == art.getId()) {
                    return art;
                }
            }
        }
        return null;
    }

    public InterfacePeriode getPeriode_signature(long signature) {
        if (signature != -1) {
            for (InterfacePeriode art : listeData) {
                if (signature == art.getSignature()) {
                    return art;
                }
            }
        }
        return null;
    }

    public Vector<Periode> getListeData() {
        return this.listeData;
    }

    public void actualiser() {
        System.out.println("actualiser - Periode...");
        redessinerTable();
    }

    public void AjouterPeriode(Periode newPeriode) {
        this.listeData.add(newPeriode);
        ecouteurModele.onValeurChangee();
        mEnreg.setCouleur(colBasique.getCouleur_foreground_objet_nouveau());                                        //mEnreg.setCouleur(Color.blue);
        btEnreg.setForeground(colBasique.getCouleur_foreground_objet_nouveau());                                   //btEnreg.setForeground(Color.blue);
        redessinerTable();
    }

    public void SupprimerPeriode(int row, ModeleListeFrais modeleListeFrais, EcouteurSuppressionElement ecouteurSuppressionElement) {
        if (row < listeData.size() && row != -1) {
            Periode IperiodeASup = listeData.elementAt(row);
            if (IperiodeASup != null) {
                int idASupp = IperiodeASup.getId();
                int dialogResult = JOptionPane.showConfirmDialog(parent, "Etes-vous sûr de vouloir supprimer cette liste?", "Avertissement", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    if (row <= listeData.size()) {
                        this.listeData.removeElementAt(row);

                        if (modeleListeFrais != null) {
                            for (InterfaceFrais ff : modeleListeFrais.getListeData()) {
                                int indexASup = -1;
                                Vector<LiaisonFraisPeriode> listLiaisons = ff.getLiaisonsPeriodes();
                                for (int i = 0; i < listLiaisons.size(); i++) {
                                    if (listLiaisons.elementAt(i).getNomPeriode().equals(IperiodeASup.getNom())) {
                                        indexASup = i;
                                    }
                                }
                                if (indexASup != -1) {
                                    listLiaisons.removeElementAt(indexASup);
                                }
                            }
                            modeleListeFrais.redessinerTable();
                        }
                        ecouteurSuppressionElement.onSuppressionConfirmee(idASupp, IperiodeASup.getSignature());
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
        //{"N°", "Nom", "Début", "Fin"};
        if (rowIndex < listeData.size()) {
            InterfacePeriode Iperiode = listeData.elementAt(rowIndex);
            if (Iperiode != null) {
                switch (columnIndex) {
                    case 0:
                        return (rowIndex + 1) + "";
                    case 1:
                        return Iperiode.getNom();
                    case 2:
                        return Iperiode.getDebut();
                    case 3:
                        return Iperiode.getFin();
                    default:
                        return "Null";
                }
            }
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //{"N°", "Nom", "Local", "Capacité"};
        switch (columnIndex) {
            case 0:
                return String.class;//N°
            case 1:
                return String.class;//Nom
            case 2:
                return Date.class;//Debut
            case 3:
                return Date.class;//Fin
            default:
                return Object.class;
        }

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        Periode eleve = null;
        boolean canEdit = false;
        if (listeData.size() > rowIndex) {
            eleve = listeData.elementAt(rowIndex);
            canEdit = gestionEdition.isEditable(eleve.getId(), 0);
        }
        if (canEdit == true) {
            if (columnIndex != 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //{"N°", "Nom", "Début", "Fin"};
        if (rowIndex < listeData.size()) {
            Periode Iclasse = listeData.get(rowIndex);
            if (Iclasse != null) {
                String avant = Iclasse.toString();
                switch (columnIndex) {
                    case 1:
                        Iclasse.setNom(aValue + "");
                        break;
                    case 2:
                        Iclasse.setDebut((Date) aValue);
                        break;
                    case 3:
                        Iclasse.setFin((Date) aValue);
                        break;
                    default:
                        break;
                }
                String apres = Iclasse.toString();
                if (!avant.equals(apres)) {
                    if (Iclasse.getBeta() == InterfaceClasse.BETA_EXISTANT) {
                        Iclasse.setBeta(InterfaceClasse.BETA_MODIFIE);
                        mEnreg.setCouleur(colBasique.getCouleur_foreground_objet_nouveau());                                        //mEnreg.setCouleur(Color.blue);
                        btEnreg.setForeground(colBasique.getCouleur_foreground_objet_nouveau());                                   //btEnreg.setForeground(Color.blue);
                    }
                }
                listeData.set(rowIndex, Iclasse);
                ecouteurModele.onValeurChangee();
                fireTableDataChanged();
            }
        }
    }

}



