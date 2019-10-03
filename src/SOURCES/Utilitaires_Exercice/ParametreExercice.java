/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Utilitaires_Exercice;


import Source.Objet.Entreprise;
import Source.Objet.Monnaie;
import Source.Objet.Utilisateur;



/**
 *
 * @author HP Pavilion
 */
public class ParametreExercice {
    public Entreprise entreprise;
    public Utilisateur utilisateur;
    private Monnaie monnaieOutPut;

    public ParametreExercice(Entreprise entreprise, Utilisateur utilisateur, Monnaie monnaieOutPut) {
        this.entreprise = entreprise;
        this.utilisateur = utilisateur;
        this.monnaieOutPut = monnaieOutPut;
    }

    public Monnaie getMonnaieOutPut() {
        return monnaieOutPut;
    }

    public void setMonnaieOutPut(Monnaie monnaieOutPut) {
        this.monnaieOutPut = monnaieOutPut;
    }
    

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public String toString() {
        return "ParametreExercice{" + "entreprise=" + entreprise + ", utilisateur=" + utilisateur + ", monnaieOutPut=" + monnaieOutPut + '}';
    }
}








