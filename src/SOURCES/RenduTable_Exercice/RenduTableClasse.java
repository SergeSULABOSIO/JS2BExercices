/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.RenduTable_Exercice;


import SOURCES.ModeleTable_Exercice.ModeleListeClasse;
import Source.GestionEdition;
import Source.Interface.InterfaceClasse;
import Source.Objet.Charge;
import Source.Objet.Classe;
import Source.Objet.CouleurBasique;
import Source.UI.CelluleTableauSimple;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author user
 */
public class RenduTableClasse implements TableCellRenderer {

    private ImageIcon iconeEdition;
    private ModeleListeClasse modeleListeClasse;
    private CouleurBasique couleurBasique;
    private GestionEdition gestionEdition;

    public RenduTableClasse(GestionEdition gestionEdition, CouleurBasique couleurBasique, ImageIcon iconeEdition, ModeleListeClasse modeleListeClasse) {
        this.couleurBasique = couleurBasique;
        this.iconeEdition = iconeEdition;
        this.modeleListeClasse = modeleListeClasse;
        this.gestionEdition = gestionEdition;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //{"N°", "Nom", "Local", "Capacité"};
        CelluleTableauSimple cellule = null;
        
        ImageIcon icone = null;
        if(gestionEdition != null){
            Classe Ieleve = this.modeleListeClasse.getClasse(row);
            if(Ieleve != null){
                if(gestionEdition.isEditable(Ieleve.getId(), 2)){
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
                String eleves = "étudiants";
                if ((Integer.parseInt(value + "")) < 2) {
                    eleves = "étudiant";
                }
                cellule = new CelluleTableauSimple(couleurBasique, " " + value + " " + eleves, CelluleTableauSimple.ALIGNE_DROITE, icone);
                break;
        }
        cellule.ecouterSelection(isSelected, row, getBeta(row), hasFocus);
        return cellule;
    }
    
    private int getBeta(int row) {
        if (this.modeleListeClasse != null) {
            Classe Ieleve = this.modeleListeClasse.getClasse(row);
            if (Ieleve != null) {
                return Ieleve.getBeta();
            }
        }
        return InterfaceClasse.BETA_NOUVEAU;
    }
}











