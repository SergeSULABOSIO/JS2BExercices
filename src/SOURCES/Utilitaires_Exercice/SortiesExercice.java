/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Utilitaires_Exercice;


import Source.Callbacks.EcouteurEnregistrement;
import Source.Objet.Agent;
import Source.Objet.Charge;
import Source.Objet.Classe;
import Source.Objet.Cours;
import Source.Objet.Annee;
import Source.Objet.Frais;
import Source.Objet.Monnaie;
import Source.Objet.Periode;
import Source.Objet.Revenu;
import java.util.Vector;



/**
 *
 * @author user
 */
public class SortiesExercice {
    private Annee exercice;
    private EcouteurEnregistrement ecouteurEnregistrement;
    private Vector<Monnaie> listeMonnaies;
    private Vector<Classe> listeClasse;
    private Vector<Frais> listeFrais;
    private Vector<Charge> listeCharges;
    private Vector<Revenu> listeRevenus;
    private Vector<Agent> listeAgents;
    private Vector<Cours> listeCours;
    private Vector<Periode> listePeriodes;

    public SortiesExercice(Annee exercice, Vector<Monnaie> listeMonnaies, Vector<Classe> listeClasse, Vector<Frais> listeFrais, Vector<Charge> listeCharges, Vector<Revenu> listeRevenus, Vector<Agent> listeAgents, Vector<Cours> listeCours, Vector<Periode> listePeriodes, EcouteurEnregistrement ecouteurEnregistrement) {
        this.exercice = exercice;
        this.ecouteurEnregistrement = ecouteurEnregistrement;
        this.listeMonnaies = listeMonnaies;
        this.listeClasse = listeClasse;
        this.listeFrais = listeFrais;
        this.listeCharges = listeCharges;
        this.listeRevenus = listeRevenus;
        this.listeAgents = listeAgents;
        this.listeCours = listeCours;
        this.listePeriodes = listePeriodes;
    }

    public Annee getExercice() {
        return exercice;
    }

    public void setExercice(Annee exercice) {
        this.exercice = exercice;
    }

    public EcouteurEnregistrement getEcouteurEnregistrement() {
        return ecouteurEnregistrement;
    }

    public void setEcouteurEnregistrement(EcouteurEnregistrement ecouteurEnregistrement) {
        this.ecouteurEnregistrement = ecouteurEnregistrement;
    }

    public Vector<Monnaie> getListeMonnaies() {
        return listeMonnaies;
    }

    public void setListeMonnaies(Vector<Monnaie> listeMonnaies) {
        this.listeMonnaies = listeMonnaies;
    }

    public Vector<Classe> getListeClasse() {
        return listeClasse;
    }

    public void setListeClasse(Vector<Classe> listeClasse) {
        this.listeClasse = listeClasse;
    }

    public Vector<Frais> getListeFrais() {
        return listeFrais;
    }

    public void setListeFrais(Vector<Frais> listeFrais) {
        this.listeFrais = listeFrais;
    }

    public Vector<Charge> getListeCharges() {
        return listeCharges;
    }

    public void setListeCharges(Vector<Charge> listeCharges) {
        this.listeCharges = listeCharges;
    }

    public Vector<Revenu> getListeRevenus() {
        return listeRevenus;
    }

    public void setListeRevenus(Vector<Revenu> listeRevenus) {
        this.listeRevenus = listeRevenus;
    }

    public Vector<Agent> getListeAgents() {
        return listeAgents;
    }

    public void setListeAgents(Vector<Agent> listeAgents) {
        this.listeAgents = listeAgents;
    }

    public Vector<Cours> getListeCours() {
        return listeCours;
    }

    public void setListeCours(Vector<Cours> listeCours) {
        this.listeCours = listeCours;
    }

    public Vector<Periode> getListePeriodes() {
        return listePeriodes;
    }

    public void setListePeriodes(Vector<Periode> listePeriodes) {
        this.listePeriodes = listePeriodes;
    }

    @Override
    public String toString() {
        return "SortiesExercice{" + "exercice=" + exercice + ", ecouteurEnregistrement=" + ecouteurEnregistrement + ", listeMonnaies=" + listeMonnaies + ", listeClasse=" + listeClasse + ", listeFrais=" + listeFrais + ", listeCharges=" + listeCharges + ", listeRevenus=" + listeRevenus + ", listeAgents=" + listeAgents + ", listeCours=" + listeCours + ", listePeriodes=" + listePeriodes + '}';
    }

    
}











































