/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.RenduTable_Exercice;


import SOURCES.ModeleTable_Exercice.ModeleListeClasse;
import SOURCES.ModeleTable_Exercice.ModeleListeAgent;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import SOURCES.ModeleTable_Exercice.ModeleListeCours;
import Source.GestionEdition;
import Source.Interface.InterfaceCours;
import Source.Objet.Agent;
import Source.Objet.Classe;
import Source.Objet.CouleurBasique;
import Source.Objet.Cours;
import Source.UI.CelluleTableauSimple;

/**
 *
 * @author user
 */
public class RenduTableCours implements TableCellRenderer {

    private ImageIcon iconeEdition;
    private ModeleListeClasse modeleListeClasse;
    private ModeleListeAgent modeleListeEnseignant;
    private ModeleListeCours modeleListeCours;
    private CouleurBasique couleurBasique;
    private GestionEdition gestionEdition;

    public RenduTableCours(GestionEdition gestionEdition, CouleurBasique couleurBasique, ImageIcon iconeEdition, ModeleListeClasse modeleListeClasse, ModeleListeAgent modeleListeEnseignant, ModeleListeCours modeleListeCours) {
        this.couleurBasique = couleurBasique;
        this.iconeEdition = iconeEdition;
        this.modeleListeClasse = modeleListeClasse;
        this.modeleListeEnseignant = modeleListeEnseignant;
        this.modeleListeCours = modeleListeCours;
        this.gestionEdition = gestionEdition;
    }


    private String getEnseignant(long signature) {
        String enseignant = "Inconnu(e)";
        //System.out.println("Selection = " + signature);
        if (this.modeleListeEnseignant != null) {
            for (Agent Oenseignant : this.modeleListeEnseignant.getListeData()) {
                //System.out.println(" * " + Oenseignant.getSignature());
                if (Oenseignant.getSignature() == signature) {
                    enseignant = Oenseignant.getPrenom() + " " + Oenseignant.getNom();
                }
            }
        }
        return enseignant;
    }
    
    private String getClasse(long signature) {
        String classe = "Inconnu(e)";
        if (this.modeleListeClasse != null) {
            for (Classe Oclasse : this.modeleListeClasse.getListeData()) {
                if (Oclasse.getSignature() == signature) {
                    classe = Oclasse.getNom();
                }
            }
        }
        return classe;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //{"N°", "Intitulé", "Classe", "Enseignant"};
        CelluleTableauSimple cellule = null;
        
        ImageIcon icone = null;
        if(gestionEdition != null){
            Cours Ieleve = this.modeleListeCours.getCours(row);
            if(Ieleve != null){
                if(gestionEdition.isEditable(Ieleve.getId(), 7)){
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
                cellule = new CelluleTableauSimple(couleurBasique, " " + getClasse(Long.parseLong(value+"")) + " ", CelluleTableauSimple.ALIGNE_GAUCHE, icone);
                break;
            case 3:
                cellule = new CelluleTableauSimple(couleurBasique, " " + getEnseignant(Long.parseLong(value + "")) + " ", CelluleTableauSimple.ALIGNE_GAUCHE, icone);
                break;
        }
        cellule.ecouterSelection(isSelected, row, getBeta(row), hasFocus);
        return cellule;
    }
    
    private int getBeta(int row) {
        if (this.modeleListeCours != null) {
            InterfaceCours Ieleve = this.modeleListeCours.getCours(row);
            if (Ieleve != null) {
                return Ieleve.getBeta();
            }
        }
        return InterfaceCours.BETA_NOUVEAU;
    }
}










