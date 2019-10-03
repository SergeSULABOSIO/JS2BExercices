/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.RenduTable_Exercice;

import SOURCES.ModeleTable_Exercice.ModeleListeFrais;
import SOURCES.ModeleTable_Exercice.ModeleListeMonnaie;
import SOURCES.Utilitaires_Exercice.UtilExercice;
import Source.GestionEdition;
import Source.Interface.InterfaceFrais;
import Source.Objet.CouleurBasique;
import Source.Objet.Eleve;
import Source.Objet.Frais;
import Source.Objet.Monnaie;
import Source.UI.CelluleTableauSimple;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author user
 */
public class RenduTableFrais implements TableCellRenderer {

    private ImageIcon iconeEdition;
    private ModeleListeFrais modeleListeFrais;
    private ModeleListeMonnaie modeleListeMonnaie;
    private CouleurBasique couleurBasique;
    private GestionEdition gestionEdition;

    public RenduTableFrais(GestionEdition gestionEdition, CouleurBasique couleurBasique, ImageIcon iconeEdition, ModeleListeFrais modeleListeFrais, ModeleListeMonnaie modeleListeMonnaie) {
        this.couleurBasique = couleurBasique;
        this.iconeEdition = iconeEdition;
        this.modeleListeFrais = modeleListeFrais;
        this.modeleListeMonnaie = modeleListeMonnaie;
        this.gestionEdition = gestionEdition;
    }

    private String getMonnaie(long signature) {
        for (Monnaie Imonnaie : modeleListeMonnaie.getListeData()) {
            if (signature == Imonnaie.getSignature()) {
                return Imonnaie.getCode() + " - " + Imonnaie.getNom();
            }
        }
        return "Inconnue";
    }

    private String getCodeMonnaie(long signature) {
        for (Monnaie Imonnaie : modeleListeMonnaie.getListeData()) {
            if (signature == Imonnaie.getSignature()) {
                return Imonnaie.getCode();
            }
        }
        return "Inconnue";
    }

    private String getMonnaie(int row) {
        InterfaceFrais Ifrais = modeleListeFrais.getFrais(row);
        if (Ifrais != null) {
            return getCodeMonnaie(Ifrais.getSignatureMonnaie());
        } else {
            return "-";
        }
    }
    
    private String getPourcentage(double pourcentage, InterfaceFrais Ifrais, String monnaie){
        if(Ifrais != null){
            double montant = ((Ifrais.getMontantDefaut() * pourcentage) / 100);
            return pourcentage + " % (" + UtilExercice.getMontantFrancais(montant) + " " + monnaie + ") ";
        }
        return "";
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //{"N°", "Nom", "Monnaie", "Total", [Périodes], [Classes]};
        CelluleTableauSimple cellule = null;
        
        ImageIcon icone = null;
        if(gestionEdition != null){
            Frais Ieleve = this.modeleListeFrais.getFrais(row);
            if(Ieleve != null){
                if(gestionEdition.isEditable(Ieleve.getId(), 3)){
                    icone = iconeEdition;
                }
            }
        }
        
        
        String monnaie = getMonnaie(row);
        InterfaceFrais Ifrais = modeleListeFrais.getFrais(row);
        int nbLiPeriode = Ifrais.getLiaisonsPeriodes().size();
        if (column > 3 && column < (4 + nbLiPeriode)) {
            cellule = new CelluleTableauSimple(couleurBasique, getPourcentage(Double.parseDouble(value + ""), Ifrais, monnaie), CelluleTableauSimple.ALIGNE_DROITE, icone);
        } else {
            switch (column) {
                case 0:
                    cellule = new CelluleTableauSimple(couleurBasique, " " + value + " ", CelluleTableauSimple.ALIGNE_CENTRE, null);
                    break;
                case 1:
                    cellule = new CelluleTableauSimple(couleurBasique, " " + value + " ", CelluleTableauSimple.ALIGNE_GAUCHE, icone);
                    break;
                case 2:
                    cellule = new CelluleTableauSimple(couleurBasique, " " + getMonnaie(Long.parseLong(value + "")) + " ", CelluleTableauSimple.ALIGNE_GAUCHE, icone);
                    break;
                case 3:
                    cellule = new CelluleTableauSimple(couleurBasique, " " + UtilExercice.getMontantFrancais(Double.parseDouble(value + "")) + " " + monnaie, CelluleTableauSimple.ALIGNE_DROITE, icone);
                    break;
                default:
                    cellule = new CelluleTableauSimple(couleurBasique, " " + UtilExercice.getMontantFrancais(Double.parseDouble(value + "")) + " " + monnaie, CelluleTableauSimple.ALIGNE_DROITE, icone);
                    break;
            }
        }
        cellule.ecouterSelection(isSelected, row, getBeta(row), hasFocus);
        return cellule;
    }

    private int getBeta(int row) {
        if (this.modeleListeFrais != null) {
            InterfaceFrais Ieleve = this.modeleListeFrais.getFrais(row);
            if (Ieleve != null) {
                return Ieleve.getBeta();
            }
        }
        return InterfaceFrais.BETA_NOUVEAU;
    }
}










