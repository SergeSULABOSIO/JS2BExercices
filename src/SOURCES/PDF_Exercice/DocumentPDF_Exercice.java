/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.PDF_Exercice;

import SOURCES.UI_Exercice.PanelExercice;
import SOURCES.Utilitaires_Exercice.SortiesExercice;
import SOURCES.Utilitaires_Exercice.UtilExercice;
import Source.Interface.InterfaceAgent;
import Source.Interface.InterfaceCharge;
import Source.Interface.InterfaceClasse;
import Source.Interface.InterfaceCours;
import Source.Interface.InterfaceFrais;
import Source.Interface.InterfaceMonnaie;
import Source.Interface.InterfaceRevenu;
import Source.Objet.Agent;
import Source.Objet.Charge;
import Source.Objet.Classe;
import Source.Objet.Cours;
import Source.Objet.Entreprise;
import Source.Objet.Frais;
import Source.Objet.Monnaie;
import Source.Objet.Revenu;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Gateway
 */
public class DocumentPDF_Exercice extends PdfPageEventHelper {

    private Document document = new Document(PageSize.A4);
    private Font Font_Titre1 = null;
    private Font Font_Titre2 = null;
    private Font Font_Titre3 = null;
    private Font Font_TexteSimple = null;
    private Font Font_TexteSimple_petit, Font_TexteSimple_petit_Gras = null;
    private Font Font_TexteSimple_Gras = null;
    private Font Font_TexteSimple_Italique = null;
    private Font Font_TexteSimple_Gras_Italique = null;
    public static final int ACTION_IMPRIMER = 0;
    public static final int ACTION_OUVRIR = 1;
    private SortiesExercice sortiesAnneeScolaire = null;
    private PanelExercice gestionnaireAnnee;
    private String monnaie = "";

    public DocumentPDF_Exercice(PanelExercice panel, int action, SortiesExercice sortiesAnneeScolaire) {
        try {
            init(panel, action, sortiesAnneeScolaire);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(PanelExercice panel, int action, SortiesExercice sortiesAnneeScolaire) {
        this.gestionnaireAnnee = panel;
        this.sortiesAnneeScolaire = sortiesAnneeScolaire;
        parametre_initialisation_fichier();
        parametre_construire_fichier();
        if (action == ACTION_OUVRIR) {
            parametres_ouvrir_fichier();
        } else if (action == ACTION_IMPRIMER) {
            parametres_imprimer_fichier();
        }
    }

    private void parametre_initialisation_fichier() {
        //Les titres du document
        this.Font_Titre1 = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD, BaseColor.DARK_GRAY);
        this.Font_Titre2 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD, BaseColor.BLACK);
        this.Font_Titre3 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);

        //Les textes simples
        this.Font_TexteSimple = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL, BaseColor.BLACK);
        this.Font_TexteSimple_petit = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.NORMAL, BaseColor.BLACK);
        this.Font_TexteSimple_petit_Gras = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD, BaseColor.BLACK);
        this.Font_TexteSimple_Gras = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLD, BaseColor.BLACK);
        this.Font_TexteSimple_Italique = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.ITALIC, BaseColor.BLACK);
        this.Font_TexteSimple_Gras_Italique = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLDITALIC, BaseColor.BLACK);
    }

    private void parametre_construire_fichier() {
        try {
            String nomFichier = this.gestionnaireAnnee.getNomfichierPreuve();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nomFichier));
            writer.setPageEvent(new MarqueS2B_Exercice());
            this.document.open();
            setDonneesBibliographiques();
            setContenuDeLaPage();
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.gestionnaireAnnee, "Impossible d'effectuer cette opération.\nAssurez vous qu'aucun fichier du même nom ne soit actuellement ouvert.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void parametres_ouvrir_fichier() {
        String nomFichier = "Output.pdf";
        if (this.gestionnaireAnnee != null) {
            nomFichier = this.gestionnaireAnnee.getNomfichierPreuve();
        }
        File fic = new File(nomFichier);
        if (fic.exists() == true) {
            try {
                Desktop.getDesktop().open(fic);
                if (sortiesAnneeScolaire != null) {
                    sortiesAnneeScolaire.getEcouteurEnregistrement().onDone("PDF ouvert avec succès!");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                String message = "Impossible d'ouvrir le fichier !";
                JOptionPane.showMessageDialog(this.gestionnaireAnnee, message, "Erreur", JOptionPane.ERROR_MESSAGE);
                if (sortiesAnneeScolaire != null) {
                    sortiesAnneeScolaire.getEcouteurEnregistrement().onError(message);
                }
            }
        }
    }

    private void parametres_imprimer_fichier() {
        String nomFichier = "Output.pdf";
        if (this.gestionnaireAnnee != null) {
            nomFichier = this.gestionnaireAnnee.getNomfichierPreuve();
        }
        File fic = new File(nomFichier);
        if (fic.exists() == true) {
            try {
                Desktop.getDesktop().print(fic);
                if (sortiesAnneeScolaire != null) {
                    sortiesAnneeScolaire.getEcouteurEnregistrement().onDone("Impression effectuée avec succès!");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                String message = "Impossible d'imprimer les données ";
                JOptionPane.showMessageDialog(this.gestionnaireAnnee, message, "Erreur", JOptionPane.ERROR_MESSAGE);
                if (sortiesAnneeScolaire != null) {
                    sortiesAnneeScolaire.getEcouteurEnregistrement().onError(message);
                }
            }
        }
    }

    private void setDonneesBibliographiques() {
        this.document.addTitle("Document généré par JS2BFacture");
        this.document.addSubject("Etat");
        this.document.addKeywords("Java, PDF, Facture");
        this.document.addAuthor("S2B. Simple.Intuitif");
        this.document.addCreator("SULA BOSIO Serge, S2B, sulabosiog@gmail.com");
    }

    private void ajouterLigne(int number) throws Exception {
        Paragraph paragraphe = new Paragraph();
        for (int i = 0; i < number; i++) {
            paragraphe.add(new Paragraph(" "));
        }
        this.document.add(paragraphe);
    }

    private void setTitreEtDateDocument() throws Exception {
        Paragraph preface = new Paragraph();
        String titre = this.gestionnaireAnnee.getTitreDoc() + "";

        if (this.gestionnaireAnnee != null) {
            preface.add(getParagraphe("Date: " + this.gestionnaireAnnee.getDateDocument(), Font_Titre3, Element.ALIGN_RIGHT));
            preface.add(getParagraphe(titre, Font_Titre1, Element.ALIGN_CENTER));
        } else {
            preface.add(getParagraphe("Date: " + UtilExercice.getDateFrancais(new Date()), Font_Titre3, Element.ALIGN_RIGHT));
            preface.add(getParagraphe("Facture n°XXXXXXXXX/2018", Font_Titre1, Element.ALIGN_CENTER));
        }
        this.document.add(preface);
    }

    private void setSignataire() throws Exception {
        if (this.gestionnaireAnnee != null) {
            this.document.add(getParagraphe(""
                    + "Produit par " + this.gestionnaireAnnee.getNomUtilisateur() + "\n"
                    + "Validé par :..............................................\n\n"
                    + "Signature", Font_TexteSimple, Element.ALIGN_RIGHT));
        } else {
            this.document.add(getParagraphe(""
                    + "Produit par Serge SULA BOSIO\n"
                    + "Validé par :..............................................\n\n"
                    + "Signature", Font_TexteSimple, Element.ALIGN_RIGHT));
        }

    }

    private void setBasDePage() throws Exception {
        if (this.gestionnaireAnnee != null) {
            Entreprise entreprise = this.gestionnaireAnnee.getEntreprise();
            if (entreprise != null) {
                this.document.add(getParagraphe(entreprise.getNom() + "\n" + entreprise.getAdresse() + " | " + entreprise.getTelephone() + " | " + entreprise.getEmail() + " | " + entreprise.getSiteWeb(), Font_TexteSimple, Element.ALIGN_CENTER));
            } else {
                addDefaultEntreprise();
            }
        } else {
            addDefaultEntreprise();
        }
    }

    private void addDefaultEntreprise() throws Exception {
        this.document.add(getParagraphe(""
                + "UAP RDC Sarl. Courtier d’Assurances n°0189\n"
                + "Prins van Luikschool, Av de la Gombe, Gombe, Kinshasa, DRC | (+243) 975 33 88 33 | info@aib-brokers.com", Font_TexteSimple, Element.ALIGN_CENTER));

    }

    private Paragraph getParagraphe(String texte, Font font, int alignement) {
        Paragraph par = new Paragraph(texte, font);
        par.setAlignment(alignement);
        return par;
    }

    private Phrase getPhrase(String texte, Font font) {
        Phrase phrase = new Phrase(texte, font);
        return phrase;
    }

    private void setLogoEtDetailsEntreprise() {
        try {
            PdfPTable tableauEnteteFacture = new PdfPTable(2);
            int[] dimensionsWidthHeight = {320, 1460};
            tableauEnteteFacture.setWidths(dimensionsWidthHeight);
            tableauEnteteFacture.setHorizontalAlignment(Element.ALIGN_LEFT);

            //CELLULE DU LOGO DE L'ENTREPRISE
            PdfPCell celluleLogoEntreprise = null;
            String logo = "";
            if (this.gestionnaireAnnee != null) {
                logo = this.gestionnaireAnnee.getEntreprise().getLogo();
                System.out.println("Fic logo: " + logo);
            }
            File ficLogo = new File(new File(logo).getName());
            System.out.println("Fichier Logo: " + ficLogo.getAbsolutePath());
            if (ficLogo.exists() == true) {
                System.out.println("Fichier Logo: " + ficLogo.getAbsolutePath()+ " - Trouvé!");
                //Chargement du logo et redimensionnement afin que celui-ci convienne dans l'espace qui lui est accordé
                Image Imglogo = Image.getInstance(ficLogo.getName());
                Imglogo.scaleAbsoluteWidth(70);
                Imglogo.scaleAbsoluteHeight(70);
                celluleLogoEntreprise = new PdfPCell(Imglogo);
            } else {
                celluleLogoEntreprise = new PdfPCell();
            }
            celluleLogoEntreprise.setPadding(2);
            celluleLogoEntreprise.setBorderWidth(0);
            celluleLogoEntreprise.setBorderColor(BaseColor.BLACK);
            tableauEnteteFacture.addCell(celluleLogoEntreprise);

            //CELLULE DES DETAILS SUR L'ENTREPRISE - TEXTE (Nom, Adresse, Téléphone, Email, etc)
            PdfPCell celluleDetailsEntreprise = new PdfPCell();
            celluleDetailsEntreprise.setPadding(2);
            celluleDetailsEntreprise.setPaddingLeft(5);
            celluleDetailsEntreprise.setBorderWidth(0);
            celluleDetailsEntreprise.setBorderWidthLeft(1);
            celluleDetailsEntreprise.setBorderColor(BaseColor.BLACK);
            celluleDetailsEntreprise.setHorizontalAlignment(Element.ALIGN_TOP);

            if (this.gestionnaireAnnee != null) {
                Entreprise entreprise = this.gestionnaireAnnee.getEntreprise();
                if (entreprise != null) {
                    celluleDetailsEntreprise.addElement(getParagraphe(entreprise.getNom(), Font_Titre2, Element.ALIGN_LEFT));
                    celluleDetailsEntreprise.addElement(getParagraphe(entreprise.getAdresse(), Font_TexteSimple_petit, Element.ALIGN_LEFT));
                    celluleDetailsEntreprise.addElement(getParagraphe(entreprise.getSiteWeb() + " | " + entreprise.getEmail() + " | " + entreprise.getTelephone(), Font_TexteSimple_petit, Element.ALIGN_LEFT));
                    celluleDetailsEntreprise.addElement(getParagraphe("RCC : " + entreprise.getRccm()+ "\nID. NAT : " + entreprise.getIdnat()+ "\nNIF : " + entreprise.getNumeroImpot(), Font_TexteSimple_petit, Element.ALIGN_LEFT));
                }
            } else {
                celluleDetailsEntreprise.addElement(getParagraphe("UAP RDC Sarl, Courtier d'Assurances n°0189", Font_Titre2, Element.ALIGN_LEFT));
                celluleDetailsEntreprise.addElement(getParagraphe("Avenue de la Gombe, Kinshasa/Gombe", Font_TexteSimple_petit, Element.ALIGN_LEFT));
                celluleDetailsEntreprise.addElement(getParagraphe("https://www.aib-brokers.com | info@aib-brokers.com | (+243)84 480 35 14 - (+243)82 87 27 706", Font_TexteSimple_petit, Element.ALIGN_LEFT));
                celluleDetailsEntreprise.addElement(getParagraphe("RCC : CDF/KIN/2015-1245\nID. NAT : 0112487789\nNIF : 012245", Font_TexteSimple_petit, Element.ALIGN_LEFT));
            }
            tableauEnteteFacture.addCell(celluleDetailsEntreprise);

            //On insère le le tableau entete (logo et détails de l'entreprise) dans la page
            document.add(tableauEnteteFacture);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PdfPCell getCelluleTableau(String texte, float BorderWidth, BaseColor background, BaseColor textColor, int alignement, Font font) {
        PdfPCell cellule = new PdfPCell();
        cellule.setBorderWidth(BorderWidth);
        if (background != null) {
            cellule.setBackgroundColor(background);
        } else {
            cellule.setBackgroundColor(BaseColor.WHITE);
        }
        if (textColor != null) {
            font.setColor(textColor);
        } else {
            font.setColor(BaseColor.BLACK);
        }
        cellule.setHorizontalAlignment(alignement);
        cellule.setPhrase(getPhrase(texte, font));
        return cellule;
    }

    private PdfPTable getTableau(float totalWidth, String[] titres, int[] widths, int alignement, float borderWidth) {
        try {
            PdfPTable tableau = new PdfPTable(widths.length);
            if (totalWidth != -1) {
                tableau.setTotalWidth(totalWidth);
            } else {
                tableau.setTotalWidth(PageSize.A4.getWidth() - 72);
            }
            tableau.setLockedWidth(true);
            tableau.setWidths(widths);
            tableau.setHorizontalAlignment(alignement);
            if (titres != null) {
                tableau.setSpacingBefore(3);
                for (String titre : titres) {
                    tableau.addCell(getCelluleTableau(titre, borderWidth, BaseColor.LIGHT_GRAY, null, Element.ALIGN_CENTER, Font_TexteSimple_Gras));
                }
            }

            return tableau;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setTableauMonnaies() {
        //{"N°", "Nom", "Code", "Nature", "Taux"};
        try {
            document.add(getParagraphe("Monnaies de change", Font_TexteSimple_Gras_Italique, Element.ALIGN_LEFT));
            PdfPTable tableMonnaies = getTableau(
                    -1,
                    new String[]{"N°", "Nom", "Code", "Nature", "Taux (en M. locale)"},
                    new int[]{40, 350, 100, 200, 200},
                    Element.ALIGN_CENTER,
                    0.2f
            );
            if (this.gestionnaireAnnee != null) {
                Vector<Monnaie> listeMonnaies = this.sortiesAnneeScolaire.getListeMonnaies();
                int i = 0;
                for (Monnaie monnaie : listeMonnaies) {
                    String Snature = (monnaie.getNature() == InterfaceMonnaie.NATURE_MONNAIE_ETRANGERE) ? "Dévise étrangère" : "Dévise locale";
                    //écriture dans chaque cellule de la ligne
                    tableMonnaies.addCell(getCelluleTableau("" + (i + 1), 0.2f, BaseColor.WHITE, null, Element.ALIGN_RIGHT, Font_TexteSimple));
                    tableMonnaies.addCell(getCelluleTableau(monnaie.getNom(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                    tableMonnaies.addCell(getCelluleTableau(monnaie.getCode(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_CENTER, Font_TexteSimple));
                    tableMonnaies.addCell(getCelluleTableau(Snature, 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                    tableMonnaies.addCell(getCelluleTableau(UtilExercice.getMontantFrancais(monnaie.getTauxMonnaieLocale()) + "", 0.2f, BaseColor.WHITE, null, Element.ALIGN_RIGHT, Font_TexteSimple));
                    //Incrémentation du compteur des lignes
                    i++;
                }
                //setDerniereLigneTabEcheance(tableEchances, Util.round(totDu, 2), Util.round(totPaye, 2)); pas de ligne "TOTAL"
            }
            document.add(tableMonnaies);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTableauClasses() {
        //{"N°", "Nom", "Local", "Capacité"};
        try {
            document.add(getParagraphe("Classes", Font_TexteSimple_Gras_Italique, Element.ALIGN_LEFT));
            PdfPTable tableClasses = getTableau(
                    -1,
                    new String[]{"N°", "Nom", "Local", "Capacité"},
                    new int[]{30, 350, 100, 100},
                    Element.ALIGN_CENTER,
                    0.2f
            );
            if (this.gestionnaireAnnee != null) {
                Vector<Classe> listeClasses = this.sortiesAnneeScolaire.getListeClasse();
                int i = 0;
                int cumulCapacié = 0;
                for (InterfaceClasse classe : listeClasses) {
                    //écriture dans chaque cellule de la ligne
                    tableClasses.addCell(getCelluleTableau("" + (i + 1), 0.2f, BaseColor.WHITE, null, Element.ALIGN_RIGHT, Font_TexteSimple));
                    tableClasses.addCell(getCelluleTableau(classe.getNom(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                    tableClasses.addCell(getCelluleTableau(classe.getNomLocal(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                    tableClasses.addCell(getCelluleTableau(classe.getCapacite() + " élève(s)", 0.2f, BaseColor.WHITE, null, Element.ALIGN_CENTER, Font_TexteSimple));
                    //Incrémentation du compteur des lignes
                    cumulCapacié += classe.getCapacite();
                    i++;
                }
                //La dernière ligne du table
                tableClasses.addCell(getCelluleTableau("", 0, BaseColor.LIGHT_GRAY, null, Element.ALIGN_LEFT, Font_TexteSimple_Gras));
                tableClasses.addCell(getCelluleTableau("Total", 0, BaseColor.LIGHT_GRAY, null, Element.ALIGN_LEFT, Font_TexteSimple_Gras));
                tableClasses.addCell(getCelluleTableau("", 0, BaseColor.LIGHT_GRAY, null, Element.ALIGN_LEFT, Font_TexteSimple_Gras));
                tableClasses.addCell(getCelluleTableau(cumulCapacié + " élève(s)", 0.2f, BaseColor.LIGHT_GRAY, null, Element.ALIGN_CENTER, Font_TexteSimple_Gras));
            }
            document.add(tableClasses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTableauCharge() {
        //{"N°", "Catégorie", "Volume (par An)", "Monnaie"};
        try {
            document.add(getParagraphe("Charges", Font_TexteSimple_Gras_Italique, Element.ALIGN_LEFT));
            PdfPTable tableCharge = getTableau(
                    -1,
                    new String[]{"N°", "Catégorie", "Prévision (12 mois)"},
                    new int[]{30, 350, 100},
                    Element.ALIGN_CENTER,
                    0.2f
            );
            if (this.gestionnaireAnnee != null) {
                Vector<Charge> listeCharge = this.sortiesAnneeScolaire.getListeCharges();
                int i = 0;
                double cumulLimite = 0;
                for (InterfaceCharge charge : listeCharge) {
                    //écriture dans chaque cellule de la ligne
                    tableCharge.addCell(getCelluleTableau("" + (i + 1), 0.2f, BaseColor.WHITE, null, Element.ALIGN_RIGHT, Font_TexteSimple));
                    tableCharge.addCell(getCelluleTableau(charge.getNom(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                    tableCharge.addCell(getCelluleTableau(UtilExercice.getMontantFrancais(charge.getLimiteAnnuelle()) + " " + charge.getMonnaie(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_RIGHT, Font_TexteSimple));
                    //Incrémentation du compteur des lignes
                    cumulLimite += charge.getLimiteAnnuelle();
                    monnaie = charge.getMonnaie();
                    i++;
                }
                //La dernière ligne du table
                tableCharge.addCell(getCelluleTableau("", 0, BaseColor.LIGHT_GRAY, null, Element.ALIGN_LEFT, Font_TexteSimple_Gras));
                tableCharge.addCell(getCelluleTableau("Total", 0, BaseColor.LIGHT_GRAY, null, Element.ALIGN_LEFT, Font_TexteSimple_Gras));
                tableCharge.addCell(getCelluleTableau(UtilExercice.getMontantFrancais(cumulLimite) + " " + monnaie, 0.2f, BaseColor.LIGHT_GRAY, null, Element.ALIGN_RIGHT, Font_TexteSimple_Gras));
            }
            document.add(tableCharge);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTableauRevenu() {
        //{"N°", "Catégorie", "Prévision (12 mois)", "Monnaie"};
        try {
            document.add(getParagraphe("Revenus", Font_TexteSimple_Gras_Italique, Element.ALIGN_LEFT));
            PdfPTable tableRevenu = getTableau(
                    -1,
                    new String[]{"N°", "Catégorie", "Prévision (12 mois)"},
                    new int[]{30, 350, 100},
                    Element.ALIGN_CENTER,
                    0.2f
            );
            if (this.gestionnaireAnnee != null) {
                Vector<Revenu> listeRevenu = this.sortiesAnneeScolaire.getListeRevenus();
                int i = 0;
                double cumulLimite = 0;
                for (InterfaceRevenu revenu : listeRevenu) {
                    //écriture dans chaque cellule de la ligne
                    tableRevenu.addCell(getCelluleTableau("" + (i + 1), 0.2f, BaseColor.WHITE, null, Element.ALIGN_RIGHT, Font_TexteSimple));
                    tableRevenu.addCell(getCelluleTableau(revenu.getNom(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                    tableRevenu.addCell(getCelluleTableau(UtilExercice.getMontantFrancais(revenu.getVolumeAnnuel()) + " " + revenu.getMonnaie(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_RIGHT, Font_TexteSimple));
                    //Incrémentation du compteur des lignes
                    cumulLimite += revenu.getVolumeAnnuel();
                    monnaie = revenu.getMonnaie();
                    i++;
                }
                //La dernière ligne du table
                tableRevenu.addCell(getCelluleTableau("", 0, BaseColor.LIGHT_GRAY, null, Element.ALIGN_LEFT, Font_TexteSimple_Gras));
                tableRevenu.addCell(getCelluleTableau("Total", 0, BaseColor.LIGHT_GRAY, null, Element.ALIGN_LEFT, Font_TexteSimple_Gras));
                tableRevenu.addCell(getCelluleTableau(UtilExercice.getMontantFrancais(cumulLimite) + " " + monnaie, 0.2f, BaseColor.LIGHT_GRAY, null, Element.ALIGN_RIGHT, Font_TexteSimple_Gras));
            }
            document.add(tableRevenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getNiveauEtude(int indexNiveau) {
        try {
            switch (indexNiveau) {
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

    private String getCategorie(int categorie) {
        switch (categorie) {
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

    private void setTableauAgents() {
        //{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Niveau d'étude", "Catégorie"};
        try {
            document.add(getParagraphe("Agents", Font_TexteSimple_Gras_Italique, Element.ALIGN_LEFT));
            PdfPTable tableEnseignant = getTableau(
                    -1,
                    new String[]{"N°", "Nom", "Postnom", "Prénom", "Sexe", "Niveau d'étude", "Catégorie"},
                    new int[]{30, 100, 100, 100, 100, 120, 120},
                    Element.ALIGN_CENTER,
                    0.2f
            );
            if (this.gestionnaireAnnee != null) {
                Vector<Agent> listeEnseignants = this.sortiesAnneeScolaire.getListeAgents();
                int i = 0;
                for (InterfaceAgent enseignant : listeEnseignants) {
                    //écriture dans chaque cellule de la ligne
                    tableEnseignant.addCell(getCelluleTableau("" + (i + 1), 0.2f, BaseColor.WHITE, null, Element.ALIGN_RIGHT, Font_TexteSimple));
                    tableEnseignant.addCell(getCelluleTableau(enseignant.getNom(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                    tableEnseignant.addCell(getCelluleTableau(enseignant.getPostnom(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                    tableEnseignant.addCell(getCelluleTableau(enseignant.getPrenom(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                    tableEnseignant.addCell(getCelluleTableau(((enseignant.getSexe() == InterfaceAgent.SEXE_FEMININ) ? "FEMININ" : "MASCULIN"), 0.2f, BaseColor.WHITE, null, Element.ALIGN_CENTER, Font_TexteSimple));
                    tableEnseignant.addCell(getCelluleTableau(getNiveauEtude(enseignant.getNiveauEtude()), 0.2f, BaseColor.WHITE, null, Element.ALIGN_CENTER, Font_TexteSimple));
                    tableEnseignant.addCell(getCelluleTableau(getCategorie(enseignant.getCategorie()), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                    //Incrémentation du compteur des lignes
                    i++;
                }
                //La dernière ligne du table
            }
            document.add(tableEnseignant);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTableauCours() {
        //{"N°", "Intitulé", "Classe", "Enseignant"};
        try {
            document.add(getParagraphe("Enseignants", Font_TexteSimple_Gras_Italique, Element.ALIGN_LEFT));
            PdfPTable tableCours = getTableau(
                    -1,
                    new String[]{"N°", "Intitulé", "Classe", "Enseignant"},
                    new int[]{20, 100, 100, 100},
                    Element.ALIGN_CENTER,
                    0.2f
            );
            if (this.gestionnaireAnnee != null) {
                Vector<Cours> listeCours = this.sortiesAnneeScolaire.getListeCours();
                int i = 0;
                for (InterfaceCours cours : listeCours) {
                    //écriture dans chaque cellule de la ligne
                    tableCours.addCell(getCelluleTableau("" + (i + 1), 0.2f, BaseColor.WHITE, null, Element.ALIGN_RIGHT, Font_TexteSimple));
                    tableCours.addCell(getCelluleTableau(cours.getNom(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                    tableCours.addCell(getCelluleTableau(cours.getClasse(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                    tableCours.addCell(getCelluleTableau(cours.getEnseignant(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                    //Incrémentation du compteur des lignes
                    i++;
                }
            }
            document.add(tableCours);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTableauFrais(boolean pourClasse) {
        //Les colonnes se construisent dynamiquement
        try {
            if (pourClasse == true) {
                document.add(getParagraphe("Frais Scolaires par rapport aux classes", Font_TexteSimple_Gras_Italique, Element.ALIGN_LEFT));
            } else {
                document.add(getParagraphe("Frais Scolaires par rapport aux périodes", Font_TexteSimple_Gras_Italique, Element.ALIGN_LEFT));
            }
            //Construction dynamique des colonne du tableau
            Vector titresCols = new Vector();
            titresCols.add("N°");
            titresCols.add("Nom");
            titresCols.add("Valeur");

            Vector<Integer> titresColsWitdh = new Vector();
            titresColsWitdh.add(50);
            titresColsWitdh.add(200);
            titresColsWitdh.add(100);

            if (this.gestionnaireAnnee != null) {
                if (sortiesAnneeScolaire != null) {
                    if (pourClasse == true) {
                        this.sortiesAnneeScolaire.getListeClasse().forEach((classe) -> {
                            titresCols.add(classe.getNom());
                            titresColsWitdh.add(80);
                        });
                    } else {
                        this.sortiesAnneeScolaire.getListePeriodes().forEach((periode) -> {
                            titresCols.add(periode.getNom());
                            titresColsWitdh.add(80);
                        });
                    }
                }

            }
            //On verse les titres dans le tableau static
            String[] titreColonnes = new String[titresCols.size()];
            int[] titreColonnesWidth = new int[titresColsWitdh.size()];
            for (int i = 0; i < titreColonnes.length; i++) {
                titreColonnes[i] = titresCols.elementAt(i) + "";
                titreColonnesWidth[i] = titresColsWitdh.elementAt(i);
            }

            PdfPTable tableClasses = getTableau(
                    -1,
                    titreColonnes,
                    titreColonnesWidth,
                    Element.ALIGN_CENTER,
                    0.2f
            );
            if (this.gestionnaireAnnee != null) {
                if (sortiesAnneeScolaire != null) {
                    Vector<Frais> listeFrais = this.sortiesAnneeScolaire.getListeFrais();
                    int i = 0;
                    double totalMonOutPut = 0;
                    InterfaceMonnaie monnaieOutput = UtilExercice.getMonnaieOutput(gestionnaireAnnee.parametreExercice, gestionnaireAnnee.modeleListeMonnaie);
                    for (InterfaceFrais frais : listeFrais) {
                        //écriture dans chaque cellule de la ligne
                        tableClasses.addCell(getCelluleTableau("" + (i + 1), 0.2f, BaseColor.WHITE, null, Element.ALIGN_RIGHT, Font_TexteSimple));
                        tableClasses.addCell(getCelluleTableau(frais.getNom(), 0.2f, BaseColor.WHITE, null, Element.ALIGN_LEFT, Font_TexteSimple));
                        tableClasses.addCell(getCelluleTableau(" " + gestionnaireAnnee.getCodeMonnaie(frais.getSignatureMonnaie()) + " " + UtilExercice.getMontantFrancais(frais.getMontantDefaut()) + " ", 0.2f, BaseColor.WHITE, null, Element.ALIGN_RIGHT, Font_TexteSimple));
                        
                        if (pourClasse == true) {
                            frais.getLiaisonsClasses().forEach((liaison) -> {
                                tableClasses.addCell(getCelluleTableau(" " + gestionnaireAnnee.getCodeMonnaie(frais.getSignatureMonnaie()) + " " + UtilExercice.getMontantFrancais(liaison.getMontant()) + " ", 0.2f, BaseColor.WHITE, null, Element.ALIGN_RIGHT, Font_TexteSimple));
                            });
                        } else {
                            frais.getLiaisonsPeriodes().forEach((liaison) -> {
                                tableClasses.addCell(getCelluleTableau(" " + UtilExercice.getMontantFrancais(liaison.getPourcentage()) + " %", 0.2f, BaseColor.WHITE, null, Element.ALIGN_RIGHT, Font_TexteSimple));
                            });
                        }
                        //Incrémentation du compteur des lignes
                        //totalMonOutPut += frais.getMontant();
                        totalMonOutPut += UtilExercice.getMontantOutPut(gestionnaireAnnee.parametreExercice, gestionnaireAnnee.modeleListeMonnaie, frais.getSignatureMonnaie(), frais.getMontantDefaut());
                        i++;
                    }
                    //La dernière ligne du table
                    tableClasses.addCell(getCelluleTableau("", 0, BaseColor.LIGHT_GRAY, null, Element.ALIGN_LEFT, Font_TexteSimple_Gras));
                    tableClasses.addCell(getCelluleTableau("Total", 0, BaseColor.LIGHT_GRAY, null, Element.ALIGN_LEFT, Font_TexteSimple_Gras));
                    tableClasses.addCell(getCelluleTableau(monnaieOutput.getCode() + " " + UtilExercice.getMontantFrancais(totalMonOutPut), 0.2f, BaseColor.LIGHT_GRAY, null, Element.ALIGN_RIGHT, Font_TexteSimple_Gras));

                    if (pourClasse == true) {
                        this.sortiesAnneeScolaire.getListeClasse().forEach((classeT) -> {
                            tableClasses.addCell(getCelluleTableau(" ", 0.2f, BaseColor.LIGHT_GRAY, null, Element.ALIGN_RIGHT, Font_TexteSimple_Gras));
                        });
                    }else{
                        this.sortiesAnneeScolaire.getListePeriodes().forEach((periodeT) -> {
                            //tableClasses.addCell(getCelluleTableau(Util.getMontantFrancais(getTotalMontant(classeT)) + " MonOut", 0.2f, BaseColor.LIGHT_GRAY, null, Element.ALIGN_RIGHT, Font_TexteSimple_Gras));
                            tableClasses.addCell(getCelluleTableau(" ", 0.2f, BaseColor.LIGHT_GRAY, null, Element.ALIGN_RIGHT, Font_TexteSimple_Gras));
                        });
                    }
                }
            }
            document.add(tableClasses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double getTotalMontant(InterfaceClasse classe) {
        double montant = 0;
        if (sortiesAnneeScolaire != null) {
            for (InterfaceFrais frais : this.sortiesAnneeScolaire.getListeFrais()) {
                montant += frais.getLiaisonClasses(classe).getMontant();
            }
        }

        return montant;
    }

    private void setLigneSeparateur() {
        try {
            Chunk linebreak = new Chunk(new DottedLineSeparator());
            document.add(linebreak);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setContenuDeLaPage() throws Exception {
        if (sortiesAnneeScolaire != null) {
            sortiesAnneeScolaire.getEcouteurEnregistrement().onUploading("Construction du contenu...");
        }
        setLogoEtDetailsEntreprise();
        setTitreEtDateDocument();
        //Corps
        setTableauMonnaies();
        ajouterLigne(1);
        setTableauClasses();
        ajouterLigne(1);
        setTableauFrais(true);
        ajouterLigne(1);
        setTableauFrais(false);
        ajouterLigne(1);
        setTableauCharge();
        ajouterLigne(1);
        setTableauRevenu();
        ajouterLigne(1);
        setTableauAgents();
        ajouterLigne(1);
        setTableauCours();
        //Fin du corps
        ajouterLigne(1);
        setSignataire();
        setBasDePage();
        if (sortiesAnneeScolaire != null) {
            sortiesAnneeScolaire.getEcouteurEnregistrement().onUploading("Finalisation...");
        }
    }

    public static void main(String[] a) {
        //Exemple
        DocumentPDF_Exercice docpdf = new DocumentPDF_Exercice(null, ACTION_OUVRIR, null);
    }

}
