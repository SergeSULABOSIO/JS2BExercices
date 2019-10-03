/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Utilitaires_Exercice;

import Source.Objet.Agent;
import Source.Objet.Charge;
import Source.Objet.Classe;
import Source.Objet.Cours;
import Source.Objet.Exercice;
import Source.Objet.Frais;
import Source.Objet.Monnaie;
import Source.Objet.Periode;
import Source.Objet.Revenu;
import java.util.Vector;


/**
 *
 * @author HP Pavilion
 */
public class DonneesExercice {
    public Exercice exercice;
    public Vector<Agent> agents;
    public Vector<Charge> charges;
    public Vector<Classe> classes;
    public Vector<Cours> cours;
    public Vector<Frais> frais;
    public Vector<Monnaie> monnaies;
    public Vector<Revenu> revenus;
    public Vector<Periode> periodes;

    public DonneesExercice(Exercice exercice, Vector<Agent> agents, Vector<Charge> charges, Vector<Classe> classes, Vector<Cours> cours, Vector<Frais> frais, Vector<Monnaie> monnaies, Vector<Revenu> revenus, Vector<Periode> periodes) {
        this.exercice = exercice;
        this.agents = agents;
        this.charges = charges;
        this.classes = classes;
        this.cours = cours;
        this.frais = frais;
        this.monnaies = monnaies;
        this.revenus = revenus;
        this.periodes = periodes;
    }

    public Exercice getExercice() {
        return exercice;
    }

    public void setExercice(Exercice exercice) {
        this.exercice = exercice;
    }

    public Vector<Agent> getAgents() {
        return agents;
    }

    public void setAgents(Vector<Agent> agents) {
        this.agents = agents;
    }

    public Vector<Charge> getCharges() {
        return charges;
    }

    public void setCharges(Vector<Charge> charges) {
        this.charges = charges;
    }

    public Vector<Classe> getClasses() {
        return classes;
    }

    public void setClasses(Vector<Classe> classes) {
        this.classes = classes;
    }

    public Vector<Cours> getCours() {
        return cours;
    }

    public void setCours(Vector<Cours> cours) {
        this.cours = cours;
    }

    public Vector<Frais> getFrais() {
        return frais;
    }

    public void setFrais(Vector<Frais> frais) {
        this.frais = frais;
    }

    public Vector<Monnaie> getMonnaies() {
        return monnaies;
    }

    public void setMonnaies(Vector<Monnaie> monnaies) {
        this.monnaies = monnaies;
    }

    public Vector<Revenu> getRevenus() {
        return revenus;
    }

    public void setRevenus(Vector<Revenu> revenus) {
        this.revenus = revenus;
    }

    public Vector<Periode> getPeriodes() {
        return periodes;
    }

    public void setPeriodes(Vector<Periode> periodes) {
        this.periodes = periodes;
    }

    @Override
    public String toString() {
        return "DonneesExercice{" + "exercice=" + exercice + ", agents=" + agents + ", charges=" + charges + ", classes=" + classes + ", cours=" + cours + ", frais=" + frais + ", monnaies=" + monnaies + ", revenus=" + revenus + ", periodes=" + periodes + '}';
    }

    
}


