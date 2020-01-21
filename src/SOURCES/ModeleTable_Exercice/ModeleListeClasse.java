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
import Source.Objet.Classe;
import Source.Objet.CouleurBasique;
import Source.Objet.LiaisonFraisClasse;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author HP Pavilion
 */
public class ModeleListeClasse extends AbstractTableModel {

    private String[] titreColonnes = {"N°", "Nom", "Local", "Capacité"};
    private Vector<Classe> listeData = new Vector<>();
    private JScrollPane parent;
    private EcouteurValeursChangees ecouteurModele;
    private ModeleListeFrais modeleListeFrais;
    private Bouton btEnreg;
    private RubriqueSimple mEnreg;
    private CouleurBasique colBasique;
    private GestionEdition gestionEdition;

    public ModeleListeClasse(GestionEdition gestionEdition, CouleurBasique colBasique, JScrollPane parent, Bouton btEnreg, RubriqueSimple mEnreg, ModeleListeFrais modeleListeFrais, EcouteurValeursChangees ecouteurModele) {
        this.colBasique = colBasique;
        this.parent = parent;
        this.ecouteurModele = ecouteurModele;
        this.modeleListeFrais = modeleListeFrais;
        this.mEnreg = mEnreg;
        this.btEnreg = btEnreg;
        this.gestionEdition = gestionEdition;
    }

    public void setListeClasses(Vector<Classe> listeData) {
        this.listeData = listeData;
        redessinerTable();
    }

    public Classe getClasse(int row) {
        if (row < listeData.size() && row != -1) {
            Classe art = listeData.elementAt(row);
            if (art != null) {
                return art;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void setModeleListeFrais(ModeleListeFrais modeleListeFrais) {
        this.modeleListeFrais = modeleListeFrais;
    }

    public InterfaceClasse getClasse_id(int id) {
        if (id != -1) {
            for (InterfaceClasse art : listeData) {
                if (id == art.getId()) {
                    return art;
                }
            }
        }
        return null;
    }

    public InterfaceClasse getClasse_signature(long signature) {
        if (signature != -1) {
            for (InterfaceClasse art : listeData) {
                if (signature == art.getSignature()) {
                    return art;
                }
            }
        }
        return null;
    }

    public Vector<Classe> getListeData() {
        return this.listeData;
    }

    public void actualiser() {
        System.out.println("actualiser - Classe...");
        redessinerTable();
    }

    public void AjouterClasse(Classe newClasse) {
        ajouterLiaison(newClasse);
        this.listeData.add(newClasse);
        ecouteurModele.onValeurChangee();
        mEnreg.setCouleur(colBasique.getCouleur_foreground_objet_nouveau());                                        //mEnreg.setCouleur(Color.blue);
        btEnreg.setForeground(colBasique.getCouleur_foreground_objet_nouveau());                                   //btEnreg.setForeground(Color.blue);
        redessinerTable();
    }

    private void ajouterLiaison(InterfaceClasse newClasse) {
        if (this.modeleListeFrais != null) {
            //System.out.println("AJOUT LIAISON:");
            this.modeleListeFrais.getListeData().forEach((frais) -> {
                LiaisonFraisClasse newLiaison = new LiaisonFraisClasse(newClasse.getId(), newClasse.getNom(), newClasse.getSignature(), frais.getMontantDefaut());
                frais.ajouterLiaisonsClasse(newLiaison);
            });
            this.modeleListeFrais.actualiser();
        }
    }

    public void SupprimerClasse(int row, ModeleListeFrais modeleListeFrais, EcouteurSuppressionElement ecouteurSuppressionElement) {
        if (row < listeData.size() && row != -1) {
            Classe IclasseASup = listeData.elementAt(row);
            if (IclasseASup != null) {
                int idASupp = IclasseASup.getId();
                if (ecouteurSuppressionElement.onCanDelete(idASupp, IclasseASup.getSignature()) == true) {
                    int dialogResult = JOptionPane.showConfirmDialog(parent, "Etes-vous sûr de vouloir supprimer cette liste?", "Avertissement", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        if (row <= listeData.size()) {
                            this.listeData.removeElementAt(row);

                            if (modeleListeFrais != null) {
                                for (InterfaceFrais ff : modeleListeFrais.getListeData()) {
                                    int indexASup = -1;
                                    Vector<LiaisonFraisClasse> listLiaisons = ff.getLiaisonsClasses();
                                    for (int i = 0; i < listLiaisons.size(); i++) {
                                        if (listLiaisons.elementAt(i).getNomClasse().equals(IclasseASup.getNom())) {
                                            indexASup = i;
                                        }
                                    }
                                    if (indexASup != -1) {
                                        listLiaisons.removeElementAt(indexASup);
                                    }
                                }
                                modeleListeFrais.redessinerTable();
                            }
                            ecouteurSuppressionElement.onDeletionComplete(idASupp, IclasseASup.getSignature());
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
        //{"N°", "Nom", "Local", "Capacité"};
        if (rowIndex < listeData.size()) {
            InterfaceClasse Iclasse = listeData.get(rowIndex);
            if (Iclasse != null) {
                switch (columnIndex) {
                    case 0:
                        return (rowIndex + 1) + "";
                    case 1:
                        return Iclasse.getNom();
                    case 2:
                        return Iclasse.getNomLocal();
                    case 3:
                        return Iclasse.getCapacite();
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
                return String.class;//Local
            case 3:
                return Integer.class;//Capacité
            default:
                return Object.class;
        }

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        Classe eleve = null;
        boolean canEdit = false;
        if (listeData.size() > rowIndex) {
            eleve = listeData.elementAt(rowIndex);
            canEdit = gestionEdition.isEditable(eleve.getId(), 2);
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
        //{"N°", "Nom", "Local", "Capacité"};
        if (rowIndex < listeData.size()) {
            Classe Iclasse = listeData.get(rowIndex);
            if (Iclasse != null) {
                String avant = Iclasse.toString();
                switch (columnIndex) {
                    case 1:
                        Iclasse.setNom(aValue + "");
                        break;
                    case 2:
                        Iclasse.setNomLocal(aValue + "");
                        break;
                    case 3:
                        Iclasse.setCapacite(Integer.parseInt(aValue + ""));
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
