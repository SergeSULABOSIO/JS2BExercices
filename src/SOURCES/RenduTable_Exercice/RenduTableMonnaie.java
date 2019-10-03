/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.RenduTable_Exercice;


import SOURCES.ModeleTable_Exercice.ModeleListeMonnaie;
import SOURCES.Utilitaires_Exercice.UtilExercice;
import Source.GestionEdition;
import Source.Interface.InterfaceMonnaie;
import Source.Objet.CouleurBasique;
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
public class RenduTableMonnaie implements TableCellRenderer {

    private ImageIcon iconeEdition;
    private ModeleListeMonnaie modeleListeMonnaie;
    private CouleurBasique couleurBasique;
    private GestionEdition gestionEdition;

    public RenduTableMonnaie(GestionEdition gestionEdition, CouleurBasique couleurBasique, ImageIcon iconeEdition, ModeleListeMonnaie modeleListeMonnaie) {
        this.couleurBasique = couleurBasique;
        this.iconeEdition = iconeEdition;
        this.modeleListeMonnaie = modeleListeMonnaie;
        this.gestionEdition = gestionEdition;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //{"N°", "Nom", "Code", "Nature", "Taux"};
        CelluleTableauSimple cellule = null;
        
        ImageIcon icone = null;
        if(gestionEdition != null){
            Monnaie Ieleve = this.modeleListeMonnaie.getMonnaie(row);
            if(Ieleve != null){
                if(gestionEdition.isEditable(Ieleve.getId(), 1)){
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
                cellule = new CelluleTableauSimple(couleurBasique, " " + value + " ", CelluleTableauSimple.ALIGNE_GAUCHE, icone);
                break;
            case 3:
                String nature = "Devise locale";
                if ((value + "").trim().equals(InterfaceMonnaie.NATURE_MONNAIE_ETRANGERE + "")) {
                    nature = "Devise étrangère";
                }
                cellule = new CelluleTableauSimple(couleurBasique, " " + nature + " ", CelluleTableauSimple.ALIGNE_GAUCHE, icone);
                break;
            case 4:
                double montant = Double.parseDouble(value+"");
                cellule = new CelluleTableauSimple(couleurBasique, " " + UtilExercice.getMontantFrancais(montant) + " ", CelluleTableauSimple.ALIGNE_DROITE, icone);
                break;
        }
        cellule.ecouterSelection(isSelected, row, getBeta(row), hasFocus);
        
        return cellule;
    }
    
    private int getBeta(int row) {
        if (this.modeleListeMonnaie != null) {
            InterfaceMonnaie Ieleve = this.modeleListeMonnaie.getMonnaie(row);
            if (Ieleve != null) {
                return Ieleve.getBeta();
            }
        }
        return InterfaceMonnaie.BETA_NOUVEAU;
    }
}











