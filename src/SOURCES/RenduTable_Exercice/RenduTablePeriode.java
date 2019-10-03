/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.RenduTable_Exercice;


import SOURCES.ModeleTable_Exercice.ModeleListePeriode;
import SOURCES.Utilitaires_Exercice.UtilExercice;
import Source.GestionEdition;
import Source.Interface.InterfacePeriode;
import Source.Objet.CouleurBasique;
import Source.Objet.Eleve;
import Source.Objet.Periode;
import Source.UI.CelluleTableauSimple;
import java.awt.Component;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author user
 */

public class RenduTablePeriode implements TableCellRenderer {
    
    private ImageIcon iconeEdition;
    private ModeleListePeriode modeleListePeriode;
    private CouleurBasique couleurBasique;
    private GestionEdition gestionEdition;

    public RenduTablePeriode(GestionEdition gestionEdition, CouleurBasique couleurBasique, ImageIcon iconeEdition, ModeleListePeriode modeleListePeriode) {
        this.couleurBasique = couleurBasique;
        this.iconeEdition = iconeEdition;
        this.modeleListePeriode = modeleListePeriode;
        this.gestionEdition = gestionEdition;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //{"N°", "Nom", "Début", "Fin"};
        CelluleTableauSimple cellule = null;
        
        ImageIcon icone = null;
        if(gestionEdition != null){
            Periode Ieleve = this.modeleListePeriode.getPeriode(row);
            if(Ieleve != null){
                if(gestionEdition.isEditable(Ieleve.getId(), 0)){
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
                cellule = new CelluleTableauSimple(couleurBasique, " " + UtilExercice.getDateFrancais((Date)value) + " ", CelluleTableauSimple.ALIGNE_GAUCHE, icone);
                break;
            case 3:
                cellule = new CelluleTableauSimple(couleurBasique, " " + UtilExercice.getDateFrancais((Date)value) + " ", CelluleTableauSimple.ALIGNE_GAUCHE, icone);
                break;
        }
        cellule.ecouterSelection(isSelected, row, getBeta(row), hasFocus);
        return cellule;
    }
    
    private int getBeta(int row) {
        if (this.modeleListePeriode != null) {
            Periode Iperiode = this.modeleListePeriode.getPeriode(row);
            if (Iperiode != null) {
                return Iperiode.getBeta();
            }
        }
        return InterfacePeriode.BETA_NOUVEAU;
    }
}







