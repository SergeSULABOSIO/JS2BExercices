/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.RenduTable_Exercice;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import SOURCES.ModeleTable_Exercice.ModeleListeAgent;
import Source.GestionEdition;
import Source.Interface.InterfaceAgent;
import Source.Objet.Agent;
import Source.Objet.CouleurBasique;
import Source.UI.CelluleTableauSimple;

/**
 *
 * @author user
 */
public class RenduTableAgent implements TableCellRenderer {

    private ImageIcon iconeEdition;
    private ModeleListeAgent modeleListeAgent;
    private CouleurBasique couleurBasique;
    private GestionEdition gestionEdition;

    public RenduTableAgent(GestionEdition gestionEdition, CouleurBasique couleurBasique, ImageIcon iconeEdition, ModeleListeAgent modeleListeAgent) {
        this.couleurBasique = couleurBasique;
        this.iconeEdition = iconeEdition;
        this.modeleListeAgent = modeleListeAgent;
        this.gestionEdition = gestionEdition;
    }

    private String getSexe(Object value) {
        String Ssexe = "MASCULIN";
        if ((Integer.parseInt(value + "")) == InterfaceAgent.SEXE_FEMININ) {
            Ssexe = "FEMININ";
        }
        return Ssexe;
    }
    
    private String getCategorie(int categorie) {
        switch(categorie){
            case InterfaceAgent.CATEGORIE_ADMINISTRATION_1:
                return "ADMINISTRATION 1";
            case InterfaceAgent.CATEGORIE_ADMINISTRATION_2:
                return "ADMINISTRATION 2";
            case InterfaceAgent.CATEGORIE_MATERNELLE:
                return "MATERNELLE";
            case InterfaceAgent.CATEGORIE_PARTIEL:
                return "PARTIEL";
            case InterfaceAgent.CATEGORIE_PRIMAIRE:
                return "PRIMAIRE";
            case InterfaceAgent.CATEGORIE_PRIME:
                return "PRIME";
            case InterfaceAgent.CATEGORIE_SECONDAIRE:
                return "SECONDAIRE";
            case InterfaceAgent.CATEGORIE_SURVEILLANT:
                return "SURVEILLANT";
            default:
                return "";
        }
    }
    
    private String getNiveauEtude(Object value) {
        try {
            int indexNiveau = Integer.parseInt(value + "");
            switch(indexNiveau){
                case InterfaceAgent.NIVEAU_ETUDE_DIPLOME_ETAT:
                    return "Diplomé(e) d'Etat";
                case InterfaceAgent.NIVEAU_ETUDE_GRADUE:
                    return "Gradué(e)";
                case InterfaceAgent.NIVEAU_ETUDE_LICENCIE:
                    return "Licencié(e)";
                case InterfaceAgent.NIVEAU_ETUDE_MASTER:
                    return "Master (DES)";
                case InterfaceAgent.NIVEAU_ETUDE_DOCTEUR:
                    return "Docteur";
                default:
                    return "Autres";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Null";
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Niveau d'étude", "Catégorie"};
        CelluleTableauSimple cellule = null;
        
        ImageIcon icone = null;
        if(gestionEdition != null){
            Agent agent = this.modeleListeAgent.getAgent(row);
            if(agent != null){
                if(gestionEdition.isEditable(agent.getId(), 6)){
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
                cellule = new CelluleTableauSimple(couleurBasique, " " + value + " ", CelluleTableauSimple.ALIGNE_GAUCHE, icone);
                break;
            case 4:
                cellule = new CelluleTableauSimple(couleurBasique, " " + getSexe(value) + " ", CelluleTableauSimple.ALIGNE_GAUCHE, icone);
                break;
            case 5:
                cellule = new CelluleTableauSimple(couleurBasique, " " + getNiveauEtude(value) + " ", CelluleTableauSimple.ALIGNE_GAUCHE, icone);
                break;
            case 6:
                cellule = new CelluleTableauSimple(couleurBasique, " " + getCategorie(Integer.parseInt(value+"")) + " ", CelluleTableauSimple.ALIGNE_GAUCHE, icone);
                break;
        }
        cellule.ecouterSelection(isSelected, row, getBeta(row), hasFocus);
        return cellule;
    }
    
    private int getBeta(int row) {
        if (this.modeleListeAgent != null) {
            InterfaceAgent Ieleve = this.modeleListeAgent.getAgent(row);
            if (Ieleve != null) {
                return Ieleve.getBeta();
            }
        }
        return InterfaceAgent.BETA_NOUVEAU;
    }
}













