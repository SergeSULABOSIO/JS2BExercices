/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.RenduTable_Exercice;


import SOURCES.ModeleTable_Exercice.ModeleListeCharges;
import SOURCES.ModeleTable_Exercice.ModeleListeMonnaie;
import SOURCES.Utilitaires_Exercice.UtilExercice;
import Source.GestionEdition;
import Source.Interface.InterfaceCharge;
import Source.Objet.Charge;
import Source.Objet.CouleurBasique;
import Source.Objet.Eleve;
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

public class RenduTableCharge implements TableCellRenderer {
    
    private ImageIcon iconeEdition;
    private ModeleListeMonnaie modeleListeMonnaie;
    private ModeleListeCharges modeleListeCharges;
    private CouleurBasique couleurBasique;
    private GestionEdition gestionEdition;

    public RenduTableCharge(GestionEdition gestionEdition, CouleurBasique couleurBasique, ImageIcon iconeEdition, ModeleListeMonnaie modeleListeMonnaie, ModeleListeCharges modeleListeCharges) {
        this.couleurBasique = couleurBasique;
        this.iconeEdition = iconeEdition;
        this.modeleListeMonnaie = modeleListeMonnaie;
        this.modeleListeCharges = modeleListeCharges;
        this.gestionEdition = gestionEdition;
    }
    
    private String getMonnaie(long signature) {
        String monnaie = "";
        if (this.modeleListeMonnaie != null) {
            for (Monnaie Imonnaie : this.modeleListeMonnaie.getListeData()) {
                if (Imonnaie.getSignature() == signature) {
                    monnaie = Imonnaie.getCode() + " - " + Imonnaie.getNom();
                }
            }
        }
        return monnaie;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //{"N°", "Catégorie", "Volume", "Monnaie"};
        CelluleTableauSimple cellule = null;
        
        ImageIcon icone = null;
        if(gestionEdition != null){
            Charge Ieleve = this.modeleListeCharges.getCharge(row);
            if(Ieleve != null){
                if(gestionEdition.isEditable(Ieleve.getId(), 4)){
                    icone = iconeEdition;
                }
            }
        }
        
        switch (column) {
            case 0:
                cellule = new CelluleTableauSimple(couleurBasique, " " + value + " ", CelluleTableauSimple.ALIGNE_CENTRE, null);
                break;
            case 1:
                cellule = new CelluleTableauSimple(couleurBasique, " " + value + " ", CelluleTableauSimple.ALIGNE_GAUCHE, icone);
                break;
            case 2:
                double mont = Double.parseDouble(value+"");
                cellule = new CelluleTableauSimple(couleurBasique, " " + UtilExercice.getMontantFrancais(mont) + " ", CelluleTableauSimple.ALIGNE_DROITE, icone);
                break;
            case 3:
                String monnaieS = getMonnaie(Long.parseLong(value + ""));
                cellule = new CelluleTableauSimple(couleurBasique, " " + monnaieS + " ", CelluleTableauSimple.ALIGNE_DROITE, icone);
                break;
        }
        cellule.ecouterSelection(isSelected, row, getBeta(row), hasFocus);
        return cellule;
    }
    
    private int getBeta(int row) {
        if (this.modeleListeCharges != null) {
            InterfaceCharge Ieleve = this.modeleListeCharges.getCharge(row);
            if (Ieleve != null) {
                return Ieleve.getBeta();
            }
        }
        return InterfaceCharge.BETA_NOUVEAU;
    }
}










