/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Callback_Exercice;

import SOURCES.ModeleTable_Exercice.ModeleListeCharges;
import SOURCES.ModeleTable_Exercice.ModeleListeClasse;
import SOURCES.ModeleTable_Exercice.ModeleListeCours;
import SOURCES.ModeleTable_Exercice.ModeleListeAgent;
import SOURCES.ModeleTable_Exercice.ModeleListeFrais;
import SOURCES.ModeleTable_Exercice.ModeleListeMonnaie;
import SOURCES.ModeleTable_Exercice.ModeleListePeriode;
import SOURCES.ModeleTable_Exercice.ModeleListeRevenu;

/**
 *
 * @author HP Pavilion
 */
public abstract class EcouteurAjoutExercice {
    public abstract void setAjoutPeriode(ModeleListePeriode modeleListePeriode);
    public abstract void setAjoutMonnaie(ModeleListeMonnaie modeleListeMonnaie);
    public abstract void setAjoutClasse(ModeleListeClasse modeleListeClasse);
    public abstract void setAjoutFrais(ModeleListeFrais modeleListeFrais);
    public abstract void setAjoutCharge(ModeleListeCharges modeleListeCharges);
    public abstract void setAjoutRevenu(ModeleListeRevenu modeleListeRevenu);
    public abstract void setAjoutAgent(ModeleListeAgent modeleListeAgent);
    public abstract void setAjoutCours(ModeleListeCours modeleListeCours);
}
