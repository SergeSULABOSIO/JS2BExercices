/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Callback_Exercice;

import SOURCES.Utilitaires_Exercice.SortiesExercice;

/**
 *
 * @author HP Pavilion
 */
public abstract class EcouteurExerice {
    public abstract void onEnregistre(SortiesExercice sortiesFacture);
    public abstract void onDetruitExercice(int idExercice, long signature);
    public abstract void onDetruitElements(int idElement, int index, long signature);
    
}
