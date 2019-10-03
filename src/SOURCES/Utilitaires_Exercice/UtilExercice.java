/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.Utilitaires_Exercice;


import SOURCES.ModeleTable_Exercice.ModeleListeMonnaie;
import Source.Interface.InterfaceMonnaie;
import Source.Objet.Monnaie;
import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author user
 */
public class UtilExercice {
    public static Color COULEUR_BLEU = new Color(26, 45, 77);       //Pour plus d'infos visiter le lien https://www.colorhexa.com/1a2e4d
    public static Color COULEUR_BLEU_CLAIRE_1 = new Color(68,117,192);    //Une variante claire
    public static Color COULEUR_BLEU_CLAIRE_2 = new Color(141,171,217);    //Une variante claire
    public static Color COULEUR_ORANGE = new Color(251, 155, 12);   //Pour plus d'information, visiter le lien https://www.colorhexa.com/fb9b0c
    public static Color COULEUR_ROUGE = new Color(251,36,12);       //Une variante   
    

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public static long generateSignature(){
      Random randomno = new Random();
      long value = randomno.nextLong();
      return value;
    }

    public static String getLettres(double montant, String NomMonnaie) {
        String texte = "";
        try {
            texte = Nombre.CALCULATE.getLettres(montant, NomMonnaie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return texte;
    }

    public static double getMontantOutPut(ParametreExercice parametre, ModeleListeMonnaie modeleListeMonnaie, long signatureMonnaieInput, double montant) {
        if (modeleListeMonnaie != null && signatureMonnaieInput != -1 && montant != 0 && parametre != null) {
            //Chargement des monnaies Input et Output
            InterfaceMonnaie Minput = modeleListeMonnaie.getMonnaie_signature(signatureMonnaieInput);
            InterfaceMonnaie Moutput = null;
            if (parametre.getMonnaieOutPut() != null) {
                Moutput = parametre.getMonnaieOutPut();
            } else {
                for (InterfaceMonnaie Imon : modeleListeMonnaie.getListeData()) {
                    if (Imon.getNature() == InterfaceMonnaie.NATURE_MONNAIE_ETRANGERE) {
                        Moutput = Imon;
                        break;
                    }
                }
            }
            
            //Conversion en monnaie Output définie
            if (Moutput.getId() == Minput.getId()) {
                return montant;
            } else {
                return (montant * Minput.getTauxMonnaieLocale() / Moutput.getTauxMonnaieLocale());
            }
        } else {
            return 0;
        }
    }
    
    public static Monnaie getMonnaieOutput(ParametreExercice parametre, ModeleListeMonnaie modeleListeMonnaie){
        Monnaie Moutput = null;
        if (parametre.getMonnaieOutPut() != null) {
                Moutput = parametre.getMonnaieOutPut();
            } else {
                for (Monnaie Imon : modeleListeMonnaie.getListeData()) {
                    if (Imon.getNature() == InterfaceMonnaie.NATURE_MONNAIE_ETRANGERE) {
                        Moutput = Imon;
                        break;
                    }
                }
            }
        return Moutput;
    }

    public static double getNombre_jours(Date dateFin, Date dateDebut) {
        try {
            double nb = (int) ((dateFin.getTime() - dateDebut.getTime()) / (1000 * 60 * 60 * 24));
            nb = UtilExercice.round(nb, 0);
            return nb;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Date getDate_AjouterAnnee(Date dateActuelle, int nbAnnee) {
        try {
            int plus = dateActuelle.getYear() + nbAnnee;
            dateActuelle.setYear(plus);
            return dateActuelle;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static double getNombre_jours_from_today(Date dateFin) {
        try {
            double nb = (double) ((dateFin.getTime() - (new Date()).getTime()) / (1000 * 60 * 60 * 24));
            nb = UtilExercice.round(nb, 0);
            return nb;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getDateFrancais(Date date) {
        String dateS = "";
        try {
            String pattern = "dd MMM yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            dateS = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateS;
    }

    public static String getMontantFrancais(double montant) {
        String val = "";
        int ValEntiere = (int) montant;
        char[] valInput = (ValEntiere + "").toCharArray();
        int index = 0;
        for (int i = valInput.length - 1; i >= 0; i--) {
            //System.out.println(" \t *  " + valInput[i]);
            if (index % 3 == 0 && index != 0) {
                val = valInput[i] + "." + val;
            } else {
                val = valInput[i] + val;
            }
            index++;
        }
        int ValApresVirgule = (int) (round(((montant - ValEntiere) * 100), 0));
        //System.out.println("Valeur d'origine = " + montant);
        //System.out.println("Partie entière = " + ValEntiere);
        //System.out.println("Partie décimale = " + ValApresVirgule);
        return val + "," + ValApresVirgule;
    }

    public static void main(String[] args) {
        double origine = 10000.14;
        String res = UtilExercice.getMontantFrancais(origine);
        System.out.println("Résultat = " + res);
    }
}











