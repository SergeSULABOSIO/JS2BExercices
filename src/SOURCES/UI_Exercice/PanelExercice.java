/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.UI_Exercice;

import BEAN_BARRE_OUTILS.BarreOutils;
import BEAN_BARRE_OUTILS.Bouton;
import BEAN_BARRE_OUTILS.BoutonListener;
import BEAN_MenuContextuel.MenuContextuel;
import BEAN_MenuContextuel.RubriqueListener;
import BEAN_MenuContextuel.RubriqueSimple;
import ICONES.Icones;
import SOURCES.Callback_Exercice.EcouteurAjoutExercice;
import SOURCES.Callback_Exercice.EcouteurExerice;
import SOURCES.EditeurTables_Exercice.EditeurClasse;
import SOURCES.EditeurTables_Exercice.EditeurAgent;
import SOURCES.EditeurTables_Exercice.EditeurCategorie;
import SOURCES.EditeurTables_Exercice.EditeurDate;
import SOURCES.EditeurTables_Exercice.EditeurMonnaie;
import SOURCES.EditeurTables_Exercice.EditeurNatureMonnaie;
import SOURCES.EditeurTables_Exercice.EditeurNiveauEtude;
import SOURCES.EditeurTables_Exercice.EditeurSexe;
import SOURCES.PDF_Exercice.DocumentPDF_Exercice;
import SOURCES.ModeleTable_Exercice.ModeleListeAgent;
import SOURCES.ModeleTable_Exercice.ModeleListeCharges;
import SOURCES.ModeleTable_Exercice.ModeleListeClasse;
import SOURCES.ModeleTable_Exercice.ModeleListeCours;
import SOURCES.ModeleTable_Exercice.ModeleListeFrais;
import SOURCES.ModeleTable_Exercice.ModeleListeMonnaie;
import SOURCES.ModeleTable_Exercice.ModeleListePeriode;
import SOURCES.ModeleTable_Exercice.ModeleListeRevenu;
import SOURCES.RenduTable_Exercice.RenduTableAgent;
import SOURCES.RenduTable_Exercice.RenduTableCharge;
import SOURCES.RenduTable_Exercice.RenduTableClasse;
import SOURCES.RenduTable_Exercice.RenduTableCours;
import SOURCES.RenduTable_Exercice.RenduTableFrais;
import SOURCES.RenduTable_Exercice.RenduTableMonnaie;
import SOURCES.RenduTable_Exercice.RenduTablePeriode;
import SOURCES.RenduTable_Exercice.RenduTableRevenu;
import SOURCES.Utilitaires_Exercice.SortiesExercice;
import SOURCES.Utilitaires_Exercice.UtilExercice;
import SOURCES.Utilitaires_Exercice.DonneesExercice;
import SOURCES.Utilitaires_Exercice.ParametreExercice;
import Source.Callbacks.EcouteurCrossCanalAgent;
import Source.Callbacks.EcouteurCrossCanalCharge;
import Source.Callbacks.EcouteurCrossCanalRevenu;
import Source.Callbacks.EcouteurEnregistrement;
import Source.Callbacks.EcouteurFreemium;
import Source.Callbacks.EcouteurSuppressionElement;
import Source.Callbacks.EcouteurUpdateClose;
import Source.Callbacks.EcouteurValeursChangees;
import Source.GestionClickDroit;
import Source.GestionEdition;
import Source.Interface.InterfaceAgent;
import Source.Interface.InterfaceCharge;
import Source.Interface.InterfaceClasse;
import Source.Interface.InterfaceCours;
import Source.Interface.InterfaceFrais;
import Source.Interface.InterfaceMonnaie;
import Source.Interface.InterfacePeriode;
import Source.Interface.InterfaceRevenu;
import Source.Interface.InterfaceUtilisateur;
import Source.Objet.Agent;
import Source.Objet.Charge;
import Source.Objet.Classe;
import Source.Objet.CouleurBasique;
import Source.Objet.Cours;
import Source.Objet.Entreprise;
import Source.Objet.Annee;
import Source.Objet.Frais;
import Source.Objet.LiaisonFraisClasse;
import Source.Objet.LiaisonFraisPeriode;
import Source.Objet.Monnaie;
import Source.Objet.Periode;
import Source.Objet.Revenu;
import Source.Objet.UtilObjet;
import Source.Objet.Utilisateur;
import java.awt.Color;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import java.awt.event.KeyEvent;
import java.util.Locale;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import Source.Interface.InterfaceAnnee;

/**
 *
 * @author HP Pavilion
 */
public class PanelExercice extends javax.swing.JPanel {

    /**
     * Creates new form Panel
     */
    public int indexTabSelected = 0;
    private Icones icones = null;
    private Bouton btEnregistrer, btAjouter, btSupprimer, btVider, btImprimer, btPDF, btFermer, btActualiser, btDetruireExercice, btFichePaie, btTresorerie, btEdition;
    private MenuContextuel menuContextuel = null;
    private RubriqueSimple mEnregistrer, mAjouter, mSupprimer, mVider, mImprimer, mPDF, mFermer, mActualiser, mDetruireExercice, mFichePaie, mTresorerie, mEdition;

    private BarreOutils bOutils = null;
    private EcouteurUpdateClose ecouteurClose = null;
    private EcouteurExerice ecouteurExercice = null;
    private EcouteurAjoutExercice ecouteurAjout = null;
    private JTabbedPane parent = null;
    private PanelExercice moi = null;

    public ModeleListeClasse modeleListeClasse = null;
    public ModeleListePeriode modeleListePeriode = null;
    public ModeleListeFrais modeleListeFrais = null;
    public ModeleListeCharges modeleListeCharges = null;
    public ModeleListeMonnaie modeleListeMonnaie = null;
    public ModeleListeRevenu modeleListeRevenu = null;
    public ModeleListeAgent modeleListeAgent = null;
    public ModeleListeCours modeleListeCours = null;

    private RenduTableCours renduTableCours = null;
    private RenduTableCharge renduTableCharge = null;
    private RenduTableRevenu renduTableRevenu = null;

    public ParametreExercice parametreExercice;
    public DonneesExercice donneesExercice;
    private CouleurBasique couleurBasique;
    private EcouteurCrossCanalAgent ecouteurCrossCanalAgent = null;
    private EcouteurCrossCanalCharge ecouteurCrossCanalCharge = null;
    private EcouteurCrossCanalRevenu ecouteurCrossCanalRevenu = null;

    private Periode selectedPeriode = null;
    private Monnaie selectedMonnaie = null;
    private Classe selectedClasse = null;
    private Frais selectedFrais = null;
    private Charge selectedCharge = null;
    private Revenu selectedRevenu = null;
    private Agent selectedAgent = null;
    private Cours selectedCours = null;

    private GestionEdition gestionEdition = new GestionEdition();
    private EcouteurFreemium ecouteurFreemium = null;

    public PanelExercice(EcouteurFreemium ecouteurFreemium, CouleurBasique couleurBasique, JTabbedPane parent, ParametreExercice parametreExercice, DonneesExercice donneesExercice, EcouteurExerice ecouteurExercice) {
        initComponents();
        this.ecouteurFreemium = ecouteurFreemium;
        this.couleurBasique = couleurBasique;
        this.parametreExercice = parametreExercice;
        this.donneesExercice = donneesExercice;
        this.ecouteurExercice = ecouteurExercice;
        this.chNom.setTextInitial("Nom de l'exercice");
        this.init(parent);
        parametrerTablePeriode();
        parametrerTableMonnaies();
        parametrerTableClasses();
        parametrerTableFrais();
        parametrerTableCharge();
        parametrerTableRevenus();
        parametrerTableAgents();
        parametrerTableCours();
        setIconesTabs();
        ecouterSelectionTab();
        ecouterClickDroit();
    }

    private void ecouterClickDroit() {
        new GestionClickDroit(menuContextuel, tableListePeriode, scrollListePeriode).init();
        new GestionClickDroit(menuContextuel, tableListeMonnaie, scrollListeMonnaie).init();
        new GestionClickDroit(menuContextuel, tableListeClasses, scrollListeClasse).init();
        new GestionClickDroit(menuContextuel, tableListeFrais, scrollListeFrais).init();
        new GestionClickDroit(menuContextuel, tableListeCharge, scrollListeCharge).init();
        new GestionClickDroit(menuContextuel, tableListeRevenu, scrollListeRevenu).init();
        new GestionClickDroit(menuContextuel, tableListeAgent, scrollListeAgent).init();
        new GestionClickDroit(menuContextuel, tableListeCours, scrollListeCours).init();
    }

    private void ecouterSelectionTab() {
        tabPrincipal.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                activerBoutons(((JTabbedPane) e.getSource()).getSelectedIndex());
            }
        });
    }

    private void setIconesTabs() {
        this.tabPrincipal.setIconAt(0, icones.getCalendrier_01());  //Périodes
        this.tabPrincipal.setIconAt(1, icones.getArgent_01());  //Monnaies
        this.tabPrincipal.setIconAt(2, icones.getClasse_01());  //Classes
        this.tabPrincipal.setIconAt(3, icones.getTaxes_01());   //Frais
        this.tabPrincipal.setIconAt(4, icones.getVente_01());   //Charge
        this.tabPrincipal.setIconAt(5, icones.getRecette_01()); //Revenu
        this.tabPrincipal.setIconAt(6, icones.getEnseignant_01()); //Enseignant
        this.tabPrincipal.setIconAt(7, icones.getDossier_01()); //Cours
    }

    public String getNomUtilisateur() {
        return this.parametreExercice.getUtilisateur().getNom();
    }

    public String getTitreDoc() {
        return "PROGRAMME ANNUEL - " + this.chNom.getText().toUpperCase(Locale.FRANCE);
    }

    public String getDateDocument() {
        return UtilExercice.getDateFrancais(new Date());
    }

    public Entreprise getEntreprise() {
        return this.parametreExercice.getEntreprise();
    }

    public int getIndexTabSelected() {
        return indexTabSelected;
    }

    private void initModelTableMonnaies() {
        this.modeleListeMonnaie = new ModeleListeMonnaie(gestionEdition, couleurBasique, scrollListeMonnaie, btEnregistrer, mEnregistrer, new EcouteurValeursChangees() {
            @Override
            public void onValeurChangee() {

            }
        });
        this.tableListeMonnaie.setModel(this.modeleListeMonnaie);
    }

    private void initModelTablePeriode() {
        this.modeleListePeriode = new ModeleListePeriode(gestionEdition, couleurBasique, scrollListePeriode, btEnregistrer, mEnregistrer, new EcouteurValeursChangees() {
            @Override
            public void onValeurChangee() {
                redessinerLiaisons();
            }
        });
        this.tableListePeriode.setModel(this.modeleListePeriode);
    }

    private void redessinerLiaisons() {
        if (modeleListeFrais != null) {
            //On reinitialise la liste des Période en redessinnant les liaisons Période-Classe
            Vector<Frais> listData = modeleListeFrais.getListeData();
            initModelTableFrais();
            modeleListeFrais.setListeFrais(listData);
            fixerColonnesTableFrais(true);
        }
    }

    private void chargerDataTableMonnaies() {
        //On charge les données existantes (le cas échéant)
        if (this.donneesExercice != null) {
            Vector<Monnaie> lmonnaies = this.donneesExercice.getMonnaies();
            if (lmonnaies != null) {
                if (!lmonnaies.isEmpty()) {
                    this.modeleListeMonnaie.setListeMonnaie(lmonnaies);
                } else {
                    //On charge les défauts data pour le USER
                    setDefaultMonnaies();
                }
            }
        }
    }

    private void chargerDataTablePeriode() {
        //On charge les données existantes (le cas échéant)
        if (this.donneesExercice != null) {
            Vector<Periode> lperiodes = this.donneesExercice.getPeriodes();
            if (lperiodes != null) {
                if (!lperiodes.isEmpty()) {
                    this.modeleListePeriode.setListePeriodes(lperiodes);
                } else {
                    //On charge les défauts data pour le USER
                    setDefaultPeriodes();
                }
            }
        }
    }

    private void parametrerTablePeriode() {
        initModelTablePeriode();
        chargerDataTablePeriode();
        fixerColonnesTablePeriode(true);
    }

    private void parametrerTableMonnaies() {
        initModelTableMonnaies();
        chargerDataTableMonnaies();
        fixerColonnesTableMonnaie(true);
    }

    private void parametrerTableClasses() {
        initModelTableClasse();
        chargerDataTableClasses();
        fixerColonnesTableClasse(true);
    }

    private void parametrerTableFrais() {
        initModelTableFrais();
        chargerDataTableFrais();
        fixerColonnesTableFrais(true);
    }

    private void chargerDataTableClasses() {
        //On charge les données existantes (le cas échéant)
        if (this.donneesExercice != null) {
            Vector<Classe> lclasses = this.donneesExercice.getClasses();
            if (lclasses != null) {
                if (!lclasses.isEmpty()) {
                    this.modeleListeClasse.setListeClasses(lclasses);
                } else {
                    //On charge les défauts data pour le USER
                    setDefaultClasses();
                }
            }
        }
    }

    private void chargerDataTableFrais() {
        if (this.donneesExercice != null) {
            Vector<Frais> lfrais = this.donneesExercice.getFrais();
            if (lfrais != null) {
                if (!lfrais.isEmpty()) {
                    this.modeleListeFrais.setListeFrais(lfrais);
                } else {
                    //On charge les défauts data pour le USER
                    setDefaultFrais();
                }
            }
        }
    }

    private void initModelTableFrais() {
        this.modeleListeFrais = new ModeleListeFrais(gestionEdition, couleurBasique, scrollListeFrais, btEnregistrer, mEnregistrer, modeleListeClasse, modeleListePeriode, new EcouteurValeursChangees() {
            @Override
            public void onValeurChangee() {

            }
        });
        this.modeleListeClasse.setModeleListeFrais(modeleListeFrais);
        try {
            this.tableListeFrais.setModel(this.modeleListeFrais);
        } catch (Exception e) {
            return;
        }
    }

    private void initModelTableClasse() {
        this.modeleListeClasse = new ModeleListeClasse(gestionEdition, couleurBasique, scrollListeClasse, btEnregistrer, mEnregistrer, modeleListeFrais, new EcouteurValeursChangees() {
            @Override
            public void onValeurChangee() {
                redessinerLiaisons();
            }
        });
        this.tableListeClasses.setModel(this.modeleListeClasse);
    }

    private void fixerColonnesTableMonnaie(boolean resizeTable) {
        this.tableListeMonnaie.setDefaultRenderer(Object.class, new RenduTableMonnaie(gestionEdition, couleurBasique, icones.getModifier_01(), this.modeleListeMonnaie));
        this.tableListeMonnaie.setRowHeight(25);
        setTaille(this.tableListeMonnaie.getColumnModel().getColumn(0), 30, true, null);
        setTaille(this.tableListeMonnaie.getColumnModel().getColumn(1), 260, false, null);
        setTaille(this.tableListeMonnaie.getColumnModel().getColumn(2), 50, true, null);
        setTaille(this.tableListeMonnaie.getColumnModel().getColumn(3), 150, true, new EditeurNatureMonnaie());
        setTaille(this.tableListeMonnaie.getColumnModel().getColumn(4), 100, true, null);

        //On écoute les sélction
        this.tableListeMonnaie.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    selectedMonnaie = modeleListeMonnaie.getMonnaie(tableListeMonnaie.getSelectedRow());
                    if (selectedMonnaie != null) {
                        ecouteurClose.onActualiser(selectedMonnaie.getCode() + ", " + selectedMonnaie.getNom() + ", Taux en Devise loacale : " + UtilExercice.getMontantFrancais(selectedMonnaie.getTauxMonnaieLocale()), icones.getArgent_01());
                    }
                }
            }
        });

        if (resizeTable == true) {
            this.tableListeMonnaie.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
    }

    private void fixerColonnesTablePeriode(boolean resizeTable) {
        this.tableListePeriode.setDefaultRenderer(Object.class, new RenduTablePeriode(gestionEdition, couleurBasique, icones.getModifier_01(), this.modeleListePeriode));
        this.tableListePeriode.setRowHeight(25);
        setTaille(this.tableListePeriode.getColumnModel().getColumn(0), 30, true, null);
        setTaille(this.tableListePeriode.getColumnModel().getColumn(1), 200, false, null);
        setTaille(this.tableListePeriode.getColumnModel().getColumn(2), 150, true, new EditeurDate());
        setTaille(this.tableListePeriode.getColumnModel().getColumn(3), 150, true, new EditeurDate());

        //On écoute les sélction
        this.tableListePeriode.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    selectedPeriode = modeleListePeriode.getPeriode(tableListePeriode.getSelectedRow());
                    if (selectedPeriode != null) {
                        ecouteurClose.onActualiser(selectedPeriode.getNom() + ", du " + UtilExercice.getDateFrancais(selectedPeriode.getDebut()) + " au " + UtilExercice.getDateFrancais(selectedPeriode.getFin()), icones.getCalendrier_01());
                    }
                }
            }
        });

        if (resizeTable == true) {
            this.tableListePeriode.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
    }

    public String getMonnaie(long signature) {
        for (InterfaceMonnaie Imonnaie : modeleListeMonnaie.getListeData()) {
            if (signature == Imonnaie.getSignature()) {
                return Imonnaie.getCode() + " - " + Imonnaie.getNom();
            }
        }
        return "Inconnue";
    }

    public String getCodeMonnaie(long signature) {
        for (InterfaceMonnaie Imonnaie : modeleListeMonnaie.getListeData()) {
            if (signature == Imonnaie.getSignature()) {
                return Imonnaie.getCode();
            }
        }
        return "-";
    }

    private void fixerColonnesTableFrais(boolean resizeTable) {
        this.tableListeFrais.setDefaultRenderer(Object.class, new RenduTableFrais(gestionEdition, couleurBasique, icones.getModifier_01(), modeleListeFrais, modeleListeMonnaie));
        this.tableListeFrais.setRowHeight(25);
        if (this.tableListeFrais.getColumnModel().getColumnCount() != 0) {
            setTaille(this.tableListeFrais.getColumnModel().getColumn(0), 30, true, null);
            setTaille(this.tableListeFrais.getColumnModel().getColumn(1), 200, false, null);
            setTaille(this.tableListeFrais.getColumnModel().getColumn(2), 200, true, new EditeurMonnaie(modeleListeMonnaie));
            setTaille(this.tableListeFrais.getColumnModel().getColumn(3), 100, true, null);

            for (int i = 4; i < modeleListeFrais.getColumnCount(); i++) {
                if (this.tableListeFrais.getColumnModel().getColumnCount() > i) {
                    setTaille(this.tableListeFrais.getColumnModel().getColumn(i), 150, true, null);
                }
            }
        }

        //On écoute les sélction
        this.tableListeFrais.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    selectedFrais = modeleListeFrais.getFrais(tableListeFrais.getSelectedRow());
                    if (selectedFrais != null) {
                        ecouteurClose.onActualiser(selectedFrais.getNom() + ", " + UtilExercice.getMontantFrancais(selectedFrais.getMontantDefaut()) + " " + getCodeMonnaie(selectedFrais.getSignatureMonnaie()) + "", icones.getTaxes_01());
                    }
                }
            }
        });

        if (resizeTable == true) {
            this.tableListeFrais.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
    }

    private void fixerColonnesTableClasse(boolean resizeTable) {
        this.tableListeClasses.setDefaultRenderer(Object.class, new RenduTableClasse(gestionEdition, couleurBasique, icones.getModifier_01(), this.modeleListeClasse));
        this.tableListeClasses.setRowHeight(25);
        setTaille(this.tableListeClasses.getColumnModel().getColumn(0), 30, true, null);
        setTaille(this.tableListeClasses.getColumnModel().getColumn(1), 260, false, null);
        setTaille(this.tableListeClasses.getColumnModel().getColumn(2), 150, true, null);
        setTaille(this.tableListeClasses.getColumnModel().getColumn(3), 150, true, null);

        //On écoute les sélction
        this.tableListeClasses.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    selectedClasse = modeleListeClasse.getClasse(tableListeClasses.getSelectedRow());
                    if (selectedClasse != null) {
                        ecouteurClose.onActualiser(selectedClasse.getNom() + ", nombre des sièges : " + UtilExercice.getMontantFrancais((double) selectedClasse.getCapacite()), icones.getClasse_01());
                    }
                }
            }
        });

        if (resizeTable == true) {
            this.tableListeClasses.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
    }

    private void setTaille(TableColumn column, int taille, boolean fixe, TableCellEditor editor) {
        column.setPreferredWidth(taille);
        if (fixe == true) {
            column.setMaxWidth(taille);
            column.setMinWidth(taille);
        }
        if (editor != null) {
            column.setCellEditor(editor);
        }
    }

    private void setDefaultCharges() {
        if (this.modeleListeCharges != null) {
            this.modeleListeCharges.getListeData().removeAllElements();
            InterfaceMonnaie monnaies = getDefaultMonnaie();
            if (monnaies != null) {
                this.modeleListeCharges.AjouterCharge(new Charge(-1, -1, -1, -1, "SALAIRE DU PERSONNEL", 5000, -1, monnaies.getSignature(), monnaies.getCode(), UtilObjet.getSignature(), InterfaceCharge.BETA_NOUVEAU));
                this.modeleListeCharges.AjouterCharge(new Charge(-1, -1, -1, -1, "ENERGIES (Eau, Electité, etc)", 500, -1, monnaies.getSignature(), monnaies.getCode(), UtilObjet.getSignature(), InterfaceCharge.BETA_NOUVEAU));
                this.modeleListeCharges.AjouterCharge(new Charge(-1, -1, -1, -1, "CONSOMMABLES DU BUREAU", 500, -1, monnaies.getSignature(), monnaies.getCode(), UtilObjet.getSignature(), InterfaceCharge.BETA_NOUVEAU));
                this.modeleListeCharges.AjouterCharge(new Charge(-1, -1, -1, -1, "LOYER", 1000, -1, monnaies.getSignature(), monnaies.getCode(), UtilObjet.getSignature(), InterfaceCharge.BETA_NOUVEAU));
                this.modeleListeCharges.AjouterCharge(new Charge(-1, -1, -1, -1, "SOINS MEDICAUX", 1000, -1, monnaies.getSignature(), monnaies.getCode(), UtilObjet.getSignature(), InterfaceCharge.BETA_NOUVEAU));
                this.modeleListeCharges.AjouterCharge(new Charge(-1, -1, -1, -1, "TRANSPORT", 1000, -1, monnaies.getSignature(), monnaies.getCode(), UtilObjet.getSignature(), InterfaceCharge.BETA_NOUVEAU));
                this.modeleListeCharges.AjouterCharge(new Charge(-1, -1, -1, -1, "AUTRES", 1000, -1, monnaies.getSignature(), monnaies.getCode(), UtilObjet.getSignature(), InterfaceCharge.BETA_NOUVEAU));

                ecouteurClose.onActualiser("Charge par defaut définies.", icones.getInfos_01());
            } else {
                ecouteurClose.onActualiser("Veuillez d'abord définir au moins une monnaie.", icones.getAlert_01());
            }
        }
    }

    private void setDefaultRevenus() {
        if (this.modeleListeRevenu != null) {
            this.modeleListeRevenu.getListeData().removeAllElements();
            InterfaceMonnaie monnaies = getDefaultMonnaie();
            if (monnaies != null) {
                this.modeleListeRevenu.AjouterRevenu(new Revenu(-1, -1, -1, -1, -1, monnaies.getSignature(), "FRAIS SCOLAIRES", monnaies.getCode(), 100000, UtilObjet.getSignature(), InterfaceRevenu.BETA_NOUVEAU));
                this.modeleListeRevenu.AjouterRevenu(new Revenu(-1, -1, -1, -1, -1, monnaies.getSignature(), "AUTRES REVENUS", monnaies.getCode(), 1000, UtilObjet.getSignature(), InterfaceRevenu.BETA_NOUVEAU));

                ecouteurClose.onActualiser("Types des revenus par defaut définis.", icones.getInfos_01());
            } else {
                ecouteurClose.onActualiser("Veuillez d'abord définir au moins une monnaie.", icones.getAlert_01());
            }
        }
    }

    private InterfaceAgent getDefaultEnseignant() {
        if (modeleListeAgent != null) {
            if (!modeleListeAgent.getListeData().isEmpty()) {
                return modeleListeAgent.getListeData().firstElement();
            }
        }
        return null;
    }

    private InterfaceClasse getDefaultClasse() {
        if (modeleListeClasse != null) {
            if (!modeleListeClasse.getListeData().isEmpty()) {
                return modeleListeClasse.getListeData().firstElement();
            }
        }
        return null;
    }

    private void setDefaultEnseignant() {
        if (this.modeleListeAgent != null) {
            this.modeleListeAgent.getListeData().removeAllElements();
            this.modeleListeAgent.AjouterEnseignant(new Agent(-1, -1, -1, -1, "SULA", "BOSIO", "Serge", 0, 3, UtilExercice.generateSignature(), InterfaceAgent.CATEGORIE_ADMINISTRATION_1, InterfaceAgent.BETA_NOUVEAU));

            ecouteurClose.onActualiser("Enseignant par defaut définis.", icones.getInfos_01());
        }
    }

    private void setDefaultCours() {
        if (this.modeleListeCours != null) {
            this.modeleListeCours.getListeData().removeAllElements();
            InterfaceAgent Denseignant = getDefaultEnseignant();
            InterfaceClasse Dclasse = getDefaultClasse();
            if (Denseignant != null && Dclasse != null) {
                this.modeleListeCours.AjouterCours(new Cours(-1, -1, -1, -1, "Français", Denseignant.getPrenom() + " " + Denseignant.getNom(), Denseignant.getSignature(), Denseignant.getId(), Dclasse.getNom(), Dclasse.getSignature(), Dclasse.getId(), UtilObjet.getSignature(), InterfaceCours.BETA_NOUVEAU));
                this.modeleListeCours.AjouterCours(new Cours(-1, -1, -1, -1, "Mathématique", Denseignant.getPrenom() + " " + Denseignant.getNom(), Denseignant.getSignature(), Denseignant.getId(), Dclasse.getNom(), Dclasse.getSignature(), Dclasse.getId(), UtilObjet.getSignature(), InterfaceCours.BETA_NOUVEAU));
            } else {
                this.modeleListeCours.AjouterCours(new Cours(-1, -1, -1, -1, "Français", "", -1, -1, "", -1, -1, UtilObjet.getSignature(), InterfaceCours.BETA_NOUVEAU));
                this.modeleListeCours.AjouterCours(new Cours(-1, -1, -1, -1, "Mathématique", "", -1, -1, "", -1, -1, UtilObjet.getSignature(), InterfaceCours.BETA_NOUVEAU));
            }
            ecouteurClose.onActualiser("Cours par defaut définis.", icones.getInfos_01());
        }
    }

    private void setDefaultClasses() {
        if (this.modeleListeClasse != null) {
            this.modeleListeClasse.getListeData().removeAllElements();
            this.modeleListeClasse.AjouterClasse(new Classe(-1, -1, -1, -1, "1e", 50, "LOCAL 1", UtilExercice.generateSignature(), InterfaceClasse.BETA_NOUVEAU));
            this.modeleListeClasse.AjouterClasse(new Classe(-1, -1, -1, -1, "2e", 50, "LOCAL 2", UtilExercice.generateSignature(), InterfaceClasse.BETA_NOUVEAU));
            this.modeleListeClasse.AjouterClasse(new Classe(-1, -1, -1, -1, "3e", 50, "LOCAL 3", UtilExercice.generateSignature(), InterfaceClasse.BETA_NOUVEAU));
            this.modeleListeClasse.AjouterClasse(new Classe(-1, -1, -1, -1, "4e", 50, "LOCAL 4", UtilExercice.generateSignature(), InterfaceClasse.BETA_NOUVEAU));
            this.modeleListeClasse.AjouterClasse(new Classe(-1, -1, -1, -1, "5e", 50, "LOCAL 5", UtilExercice.generateSignature(), InterfaceClasse.BETA_NOUVEAU));
            this.modeleListeClasse.AjouterClasse(new Classe(-1, -1, -1, -1, "6e", 50, "LOCAL 6", UtilExercice.generateSignature(), InterfaceClasse.BETA_NOUVEAU));
        }
    }

    private void setDefaultMonnaies() {
        if (this.modeleListeMonnaie != null) {
            this.modeleListeMonnaie.getListeData().removeAllElements();
            this.modeleListeMonnaie.AjouterMonnaie(new Monnaie(-1, -1, -1, -1, "DOLLARS AMERICAINS", "$", InterfaceMonnaie.NATURE_MONNAIE_ETRANGERE, 1620, UtilExercice.generateSignature(), InterfaceMonnaie.BETA_NOUVEAU));
            this.modeleListeMonnaie.AjouterMonnaie(new Monnaie(-1, -1, -1, -1, "FRANCS CONGOLAIS", "Fc", InterfaceMonnaie.NATURE_MONNAIE_LOCALE, 1, UtilExercice.generateSignature(), InterfaceMonnaie.BETA_NOUVEAU));

            ecouteurClose.onActualiser("Monnaies par défaut définies.", icones.getInfos_01());
        }
    }

    private void setDefaultPeriodes() {
        if (this.modeleListePeriode != null) {
            this.modeleListePeriode.getListeData().removeAllElements();

            long timeLine = chFin.getDate().getTime() - chDebut.getDate().getTime();
            long intervale = timeLine / 3;

            Date fin01 = new Date(chDebut.getDate().getTime() + intervale);
            Date debut02 = new Date(fin01.getTime());
            Date fin02 = new Date(debut02.getTime() + intervale);
            Date debut03 = new Date(fin02.getTime());

            this.modeleListePeriode.AjouterPeriode(new Periode(-1, -1, -1, -1, "1er Trimestre", chDebut.getDate(), fin01, UtilExercice.generateSignature(), InterfacePeriode.BETA_NOUVEAU));
            this.modeleListePeriode.AjouterPeriode(new Periode(-1, -1, -1, -1, "2ème Trimestre", debut02, fin02, UtilExercice.generateSignature(), InterfacePeriode.BETA_NOUVEAU));
            this.modeleListePeriode.AjouterPeriode(new Periode(-1, -1, -1, -1, "3ème Trimestre", debut03, chFin.getDate(), UtilExercice.generateSignature(), InterfacePeriode.BETA_NOUVEAU));

            ecouteurClose.onActualiser("Périodes par défaut définies.", icones.getInfos_01());
        }
    }

    private void setDefaultFrais() {
        if (this.modeleListeFrais != null) {
            this.modeleListeFrais.getListeData().removeAllElements();
            InterfaceMonnaie monnaies = getDefaultMonnaie();
            if (monnaies != null) {
                Vector<Frais> listeFraisDefault = new Vector<>();
                //listeFraisDefault.add(new XX_Frais(-1, -1, -1, -1, -1, monnaies.getSignature(), "INSCRIPTION", monnaies.getCode(), 1, 50, new Vector<>(), InterfaceFrais.BETA_NOUVEAU));
                //listeFraisDefault.add(new XX_Frais(-1, -1, -1, -1, -1, monnaies.getSignature(), "MINERVALE", monnaies.getCode(), 3, 500, new Vector<>(), InterfaceFrais.BETA_NOUVEAU));
                this.modeleListeFrais.setListeFrais(listeFraisDefault);

                ecouteurClose.onActualiser("Types des frais par defaut définis.", icones.getInfos_01());
            } else {
                ecouteurClose.onActualiser("Veuillez d'abord définir au moins une monnaie.", icones.getAlert_01());
            }
        }
    }

    private void parametrerTableCharge() {
        initModelTableCharge();
        chargerDataTableCharge();
        fixerColonneTableCharges(true);
    }

    private void initModelTableCharge() {
        this.modeleListeCharges = new ModeleListeCharges(gestionEdition, couleurBasique, scrollListeCharge, btEnregistrer, mEnregistrer, modeleListeMonnaie, new EcouteurValeursChangees() {
            @Override
            public void onValeurChangee() {

            }
        });
        this.tableListeCharge.setModel(this.modeleListeCharges);
    }

    private void chargerDataTableCharge() {
        if (this.donneesExercice != null) {
            Vector<Charge> lcharges = this.donneesExercice.getCharges();
            if (lcharges != null) {
                if (!lcharges.isEmpty()) {
                    this.modeleListeCharges.setListeCharges(lcharges);
                } else {
                    //On créée qlq charges par défaut pour le USER
                    setDefaultCharges();
                }
            }
        }
        this.renduTableCharge = new RenduTableCharge(gestionEdition, couleurBasique, icones.getModifier_01(), modeleListeMonnaie, this.modeleListeCharges);
    }

    private void fixerColonneTableCharges(boolean resizeTable) {
        this.tableListeCharge.setDefaultRenderer(Object.class, this.renduTableCharge);
        this.tableListeCharge.setRowHeight(25);
        setTaille(this.tableListeCharge.getColumnModel().getColumn(0), 30, true, null);
        setTaille(this.tableListeCharge.getColumnModel().getColumn(1), 200, false, null);
        setTaille(this.tableListeCharge.getColumnModel().getColumn(2), 120, true, null);
        setTaille(this.tableListeCharge.getColumnModel().getColumn(3), 200, true, new EditeurMonnaie(modeleListeMonnaie));

        //On écoute les sélction
        this.tableListeCharge.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    selectedCharge = modeleListeCharges.getCharge(tableListeCharge.getSelectedRow());
                    if (selectedCharge != null) {
                        ecouteurClose.onActualiser(selectedCharge.getNom() + ", " + UtilExercice.getMontantFrancais(selectedCharge.getLimiteAnnuelle()) + " " + selectedCharge.getMonnaie() + " / An", icones.getVente_01());
                    }
                }
            }
        });

        if (resizeTable == true) {
            this.tableListeCharge.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
    }

    private void parametrerTableRevenus() {
        initModelTableRevenu();
        chargerDataTableRevenus();
        fixerColonnesTableRevenus(true);
    }

    private void initModelTableRevenu() {
        this.modeleListeRevenu = new ModeleListeRevenu(gestionEdition, couleurBasique, scrollListeRevenu, btEnregistrer, mEnregistrer, modeleListeMonnaie, new EcouteurValeursChangees() {
            @Override
            public void onValeurChangee() {

            }
        });
        this.tableListeRevenu.setModel(this.modeleListeRevenu);
    }

    private void chargerDataTableRevenus() {
        if (this.donneesExercice != null) {
            Vector<Revenu> lfrais = this.donneesExercice.getRevenus();
            if (lfrais != null) {
                if (!lfrais.isEmpty()) {
                    this.modeleListeRevenu.setListeRevenue(lfrais);
                } else {
                    //On créée qlq charges par défaut pour le USER
                    setDefaultRevenus();
                }
            }
        }
        this.renduTableRevenu = new RenduTableRevenu(gestionEdition, couleurBasique, icones.getModifier_01(), modeleListeMonnaie, this.modeleListeRevenu);
    }

    private void fixerColonnesTableRevenus(boolean resizeTable) {
        this.tableListeRevenu.setDefaultRenderer(Object.class, this.renduTableRevenu);
        this.tableListeRevenu.setRowHeight(25);
        setTaille(this.tableListeRevenu.getColumnModel().getColumn(0), 30, true, null);
        setTaille(this.tableListeRevenu.getColumnModel().getColumn(1), 200, false, null);
        setTaille(this.tableListeRevenu.getColumnModel().getColumn(2), 150, true, null);
        setTaille(this.tableListeRevenu.getColumnModel().getColumn(3), 200, true, new EditeurMonnaie(modeleListeMonnaie));

        //On écoute les sélction
        this.tableListeRevenu.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    selectedRevenu = modeleListeRevenu.getRevenu(tableListeRevenu.getSelectedRow());
                    if (selectedRevenu != null) {
                        ecouteurClose.onActualiser(selectedRevenu.getNom() + ", évalué à " + UtilExercice.getMontantFrancais(selectedRevenu.getVolumeAnnuel()) + " " + selectedRevenu.getMonnaie() + " par An", icones.getRecette_01());
                    }
                }
            }
        });

        if (resizeTable == true) {
            this.tableListeRevenu.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
    }

    private void parametrerTableAgents() {
        initModelTableAgents();
        chargerDataTableAgents();
        fixerColonnesTableAgents(true);
    }

    private void initModelTableAgents() {
        this.modeleListeAgent = new ModeleListeAgent(gestionEdition, couleurBasique, scrollListeAgent, btEnregistrer, mEnregistrer, new EcouteurValeursChangees() {
            @Override
            public void onValeurChangee() {

            }
        });
        this.tableListeAgent.setModel(this.modeleListeAgent);
    }

    private void chargerDataTableAgents() {
        if (this.donneesExercice != null) {
            Vector<Agent> lagents = this.donneesExercice.getAgents();
            if (lagents != null) {
                if (!lagents.isEmpty()) {
                    this.modeleListeAgent.setListeAgents(lagents);
                } else {
                    //On créée qlq charges par défaut pour le USER
                    setDefaultEnseignant();
                }
            }
        }
    }

    public EcouteurCrossCanalAgent getEcouteurCrossCanalAgent() {
        return ecouteurCrossCanalAgent;
    }

    public void setEcouteurCrossCanalAgent(EcouteurCrossCanalAgent ecouteurCrossCanalAgent) {
        this.ecouteurCrossCanalAgent = ecouteurCrossCanalAgent;
    }

    public EcouteurCrossCanalCharge getEcouteurCrossCanalCharge() {
        return ecouteurCrossCanalCharge;
    }

    public void setEcouteurCrossCanalCharge(EcouteurCrossCanalCharge ecouteurCrossCanalCharge) {
        this.ecouteurCrossCanalCharge = ecouteurCrossCanalCharge;
    }

    public EcouteurCrossCanalRevenu getEcouteurCrossCanalRevenu() {
        return ecouteurCrossCanalRevenu;
    }

    public void setEcouteurCrossCanalRevenu(EcouteurCrossCanalRevenu ecouteurCrossCanalRevenu) {
        this.ecouteurCrossCanalRevenu = ecouteurCrossCanalRevenu;
    }

    private void fixerColonnesTableAgents(boolean resizeTable) {
        this.tableListeAgent.setDefaultRenderer(Object.class, new RenduTableAgent(gestionEdition, couleurBasique, icones.getModifier_01(), this.modeleListeAgent));
        this.tableListeAgent.setRowHeight(25);
        setTaille(this.tableListeAgent.getColumnModel().getColumn(0), 30, true, null);
        setTaille(this.tableListeAgent.getColumnModel().getColumn(1), 200, false, null);
        setTaille(this.tableListeAgent.getColumnModel().getColumn(2), 200, false, null);
        setTaille(this.tableListeAgent.getColumnModel().getColumn(3), 200, false, null);
        setTaille(this.tableListeAgent.getColumnModel().getColumn(4), 130, true, new EditeurSexe());
        setTaille(this.tableListeAgent.getColumnModel().getColumn(5), 200, true, new EditeurNiveauEtude());
        setTaille(this.tableListeAgent.getColumnModel().getColumn(6), 200, true, new EditeurCategorie());

        //On écoute les sélction
        this.tableListeAgent.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    selectedAgent = modeleListeAgent.getAgent(tableListeAgent.getSelectedRow());
                    if (selectedAgent != null) {
                        ecouteurClose.onActualiser(selectedAgent.getNom() + " " + selectedAgent.getPostnom() + " " + selectedAgent.getPrenom(), icones.getEnseignant_01());
                        activerBtCrossPaie(true);
                    }
                }
            }
        });

        if (resizeTable == true) {
            this.tableListeAgent.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
    }

    private void parametrerTableCours() {
        initModelTableCours();
        chargerDataTableCours();
        fixerColonnesTableCours(true);
    }

    private void initModelTableCours() {
        this.modeleListeCours = new ModeleListeCours(gestionEdition, couleurBasique, scrollListeCours, btEnregistrer, mEnregistrer, modeleListeAgent, modeleListeClasse, new EcouteurValeursChangees() {
            @Override
            public void onValeurChangee() {

            }
        });
    }

    private void chargerDataTableCours() {
        if (this.donneesExercice != null) {
            Vector<Cours> lcours = this.donneesExercice.getCours();
            if (lcours != null) {
                if (!lcours.isEmpty()) {
                    this.modeleListeCours.setListeCours(lcours);
                } else {
                    //On créée qlq charges par défaut pour le USER
                    setDefaultCours();
                }
            }
        }
        this.tableListeCours.setModel(this.modeleListeCours);
    }

    private void fixerColonnesTableCours(boolean resizeTable) {
        this.renduTableCours = new RenduTableCours(gestionEdition, couleurBasique, icones.getModifier_01(), modeleListeClasse, modeleListeAgent, this.modeleListeCours);
        this.tableListeCours.setDefaultRenderer(Object.class, this.renduTableCours);
        this.tableListeCours.setRowHeight(25);
        setTaille(this.tableListeCours.getColumnModel().getColumn(0), 30, true, null);
        setTaille(this.tableListeCours.getColumnModel().getColumn(1), 200, false, null);
        setTaille(this.tableListeCours.getColumnModel().getColumn(2), 50, true, new EditeurClasse(modeleListeClasse));
        setTaille(this.tableListeCours.getColumnModel().getColumn(3), 200, true, new EditeurAgent(modeleListeAgent));

        //On écoute les sélction
        this.tableListeCours.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    selectedCours = modeleListeCours.getCours(tableListeCours.getSelectedRow());
                    if (selectedCours != null) {
                        ecouteurClose.onActualiser(selectedCours.getNom(), icones.getDossier_01());
                    }
                }
            }
        });

        if (resizeTable == true) {
            this.tableListeCours.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
    }

    private void actualiserEditeur() {
        gestionEdition.reinitialiser();
        //Actualisation des listes
        modeleListePeriode.actualiser();
        modeleListeMonnaie.actualiser();
        modeleListeClasse.actualiser();
        modeleListeFrais.actualiser();
        modeleListeCharges.actualiser();
        modeleListeRevenu.actualiser();
        modeleListeAgent.actualiser();
        modeleListeCours.actualiser();
    }

    private void setEditionMode() {
        switch (indexTabSelected) {
            case 0:
                if (selectedPeriode != null && gestionEdition != null) {
                    if (gestionEdition.isEditable(selectedPeriode.getId(), indexTabSelected)) {
                        gestionEdition.setModeEdition(selectedPeriode.getId(), indexTabSelected, false);
                    } else {
                        gestionEdition.setModeEdition(selectedPeriode.getId(), indexTabSelected, true);
                    }
                    modeleListePeriode.actualiser();
                }
                break;
            case 1:
                if (selectedMonnaie != null && gestionEdition != null) {
                    if (gestionEdition.isEditable(selectedMonnaie.getId(), indexTabSelected)) {
                        gestionEdition.setModeEdition(selectedMonnaie.getId(), indexTabSelected, false);
                    } else {
                        gestionEdition.setModeEdition(selectedMonnaie.getId(), indexTabSelected, true);
                    }
                    modeleListeMonnaie.actualiser();
                }
                break;
            case 2:
                if (selectedClasse != null && gestionEdition != null) {
                    if (gestionEdition.isEditable(selectedClasse.getId(), indexTabSelected)) {
                        gestionEdition.setModeEdition(selectedClasse.getId(), indexTabSelected, false);
                    } else {
                        gestionEdition.setModeEdition(selectedClasse.getId(), indexTabSelected, true);
                    }
                    modeleListeClasse.actualiser();
                }
                break;
            case 3:
                if (selectedFrais != null && gestionEdition != null) {
                    if (gestionEdition.isEditable(selectedFrais.getId(), indexTabSelected)) {
                        gestionEdition.setModeEdition(selectedFrais.getId(), indexTabSelected, false);
                    } else {
                        gestionEdition.setModeEdition(selectedFrais.getId(), indexTabSelected, true);
                    }
                    modeleListeFrais.actualiser();
                }
                break;
            case 4:
                if (selectedCharge != null && gestionEdition != null) {
                    if (gestionEdition.isEditable(selectedCharge.getId(), indexTabSelected)) {
                        gestionEdition.setModeEdition(selectedCharge.getId(), indexTabSelected, false);
                    } else {
                        gestionEdition.setModeEdition(selectedCharge.getId(), indexTabSelected, true);
                    }
                    modeleListeCharges.actualiser();
                }
                break;
            case 5:
                if (selectedRevenu != null && gestionEdition != null) {
                    if (gestionEdition.isEditable(selectedRevenu.getId(), indexTabSelected)) {
                        gestionEdition.setModeEdition(selectedRevenu.getId(), indexTabSelected, false);
                    } else {
                        gestionEdition.setModeEdition(selectedRevenu.getId(), indexTabSelected, true);
                    }
                    modeleListeRevenu.actualiser();
                }
                break;
            case 6:
                if (selectedAgent != null && gestionEdition != null) {
                    if (gestionEdition.isEditable(selectedAgent.getId(), indexTabSelected)) {
                        gestionEdition.setModeEdition(selectedAgent.getId(), indexTabSelected, false);
                    } else {
                        gestionEdition.setModeEdition(selectedAgent.getId(), indexTabSelected, true);
                    }
                    modeleListeAgent.actualiser();
                }
                break;
            case 7:
                if (selectedCours != null && gestionEdition != null) {
                    if (gestionEdition.isEditable(selectedCours.getId(), indexTabSelected)) {
                        gestionEdition.setModeEdition(selectedCours.getId(), indexTabSelected, false);
                    } else {
                        gestionEdition.setModeEdition(selectedCours.getId(), indexTabSelected, true);
                    }
                    modeleListeCours.actualiser();
                }
                break;
            default:
        }
    }

    public void activerBoutons(int selectedTab) {
        this.indexTabSelected = selectedTab;
        if (indexTabSelected == 4 || indexTabSelected == 5) {
            activerBtCrossTresorerie(true);
        } else {
            activerBtCrossTresorerie(false);
        }
        if (indexTabSelected != 6) {
            selectedAgent = null;
            tableListeAgent.getSelectionModel().setLeadSelectionIndex(-1);
            //Bt cross paie
            activerBtCrossPaie(false);
        } else if (tableListeAgent.getSelectedRow() != -1 && indexTabSelected == 6) {
            //tableListeAgent.getSelectedRow()
            selectedAgent = modeleListeAgent.getAgent(tableListeAgent.getSelectedRow());
            if (selectedAgent != null) {
                activerBtCrossPaie(true);
            }
        } else {
            activerBtCrossPaie(false);
        }
    }

    private void activerBtCrossPaie(boolean activer) {
        btFichePaie.appliquerDroitAccessDynamique(activer);
        mFichePaie.appliquerDroitAccessDynamique(activer);
    }

    private void activerBtCrossTresorerie(boolean activer) {
        btTresorerie.appliquerDroitAccessDynamique(activer);
        mTresorerie.appliquerDroitAccessDynamique(activer);
    }

    public void ajouter() {
        switch (indexTabSelected) {
            case 0: //Tab Période
                this.ecouteurAjout.setAjoutPeriode(modeleListePeriode);
                break;
            case 1: //Tab monnaie
                this.ecouteurAjout.setAjoutMonnaie(modeleListeMonnaie);
                break;
            case 2: //Tab classe
                this.ecouteurAjout.setAjoutClasse(modeleListeClasse);
                break;
            case 3: //Tab frais
                this.ecouteurAjout.setAjoutFrais(modeleListeFrais);
                break;
            case 4: //Tab charge
                this.ecouteurAjout.setAjoutCharge(modeleListeCharges);
                break;
            case 5: //Tab revenu
                this.ecouteurAjout.setAjoutRevenu(modeleListeRevenu);
                break;
            case 6: //Tab Agent
                this.ecouteurAjout.setAjoutAgent(modeleListeAgent);
                break;
            case 7: //Tab Cours
                this.ecouteurAjout.setAjoutCours(modeleListeCours);
                break;
        }
    }

    public void supprimer() {
        switch (indexTabSelected) {
            case 0: //Tab période
                modeleListePeriode.SupprimerPeriode(tableListePeriode.getSelectedRow(), modeleListeFrais, new EcouteurSuppressionElement() {

                    @Override
                    public void onDeletionComplete(int idElement, long signature) {
                        ecouteurExercice.onDetruitElements(idElement, indexTabSelected, signature);
                    }

                    @Override
                    public boolean onCanDelete(int idElement, long signature) {
                        return ecouteurExercice.onCanDelete(idElement, indexTabSelected, signature);
                    }

                });
                break;
            case 1: //Tab monnaie
                modeleListeMonnaie.SupprimerMonnaie(tableListeMonnaie.getSelectedRow(), new EcouteurSuppressionElement() {

                    @Override
                    public void onDeletionComplete(int idElement, long signature) {
                        ecouteurExercice.onDetruitElements(idElement, indexTabSelected, signature);
                    }

                    @Override
                    public boolean onCanDelete(int idElement, long signature) {
                        return ecouteurExercice.onCanDelete(idElement, indexTabSelected, signature);
                    }
                });
                break;
            case 2: //Tab classe
                modeleListeClasse.SupprimerClasse(tableListeClasses.getSelectedRow(), this.modeleListeFrais, new EcouteurSuppressionElement() {

                    @Override
                    public void onDeletionComplete(int idElement, long signature) {
                        ecouteurExercice.onDetruitElements(idElement, indexTabSelected, signature);
                    }

                    @Override
                    public boolean onCanDelete(int idElement, long signature) {
                        return ecouteurExercice.onCanDelete(idElement, indexTabSelected, signature);
                    }
                });
                break;
            case 3: //Tab frais
                modeleListeFrais.SupprimerFrais(tableListeFrais.getSelectedRow(), new EcouteurSuppressionElement() {
                    @Override
                    public void onDeletionComplete(int idElement, long signature) {
                        ecouteurExercice.onDetruitElements(idElement, indexTabSelected, signature);
                    }

                    @Override
                    public boolean onCanDelete(int idElement, long signature) {
                        return ecouteurExercice.onCanDelete(idElement, indexTabSelected, signature);
                    }
                });
                break;
            case 4: //Tab charge
                modeleListeCharges.SupprimerCharge(tableListeCharge.getSelectedRow(), new EcouteurSuppressionElement() {
                    @Override
                    public void onDeletionComplete(int idElement, long signature) {
                        ecouteurExercice.onDetruitElements(idElement, indexTabSelected, signature);
                    }

                    @Override
                    public boolean onCanDelete(int idElement, long signature) {
                        return ecouteurExercice.onCanDelete(idElement, indexTabSelected, signature);
                    }
                });
                break;
            case 5: //Tab revenu
                modeleListeRevenu.SupprimerRevenu(tableListeRevenu.getSelectedRow(), new EcouteurSuppressionElement() {
                    @Override
                    public void onDeletionComplete(int idElement, long signature) {
                        ecouteurExercice.onDetruitElements(idElement, indexTabSelected, signature);
                    }

                    @Override
                    public boolean onCanDelete(int idElement, long signature) {
                        return ecouteurExercice.onCanDelete(idElement, indexTabSelected, signature);
                    }
                });
                break;
            case 6: //Tab Agnt
                modeleListeAgent.SupprimerEnseignant(tableListeAgent.getSelectedRow(), new EcouteurSuppressionElement() {
                    @Override
                    public void onDeletionComplete(int idElement, long signature) {
                        ecouteurExercice.onDetruitElements(idElement, indexTabSelected, signature);
                    }

                    @Override
                    public boolean onCanDelete(int idElement, long signature) {
                        return ecouteurExercice.onCanDelete(idElement, indexTabSelected, signature);
                    }
                });
                break;
            case 7: //Tab Cours
                modeleListeCours.SupprimerCours(tableListeCours.getSelectedRow(), new EcouteurSuppressionElement() {
                    @Override
                    public void onDeletionComplete(int idElement, long signature) {
                        ecouteurExercice.onDetruitElements(idElement, indexTabSelected, signature);
                    }

                    @Override
                    public boolean onCanDelete(int idElement, long signature) {
                        return ecouteurExercice.onCanDelete(idElement, indexTabSelected, signature);
                    }
                });
                break;
        }
    }

    public void vider() {
        this.ecouteurClose.onActualiser("Vidé!", icones.getInfos_01());
        this.chNom.setText("");
        Date date = new Date();
        this.chDebut.setDate(date);
        this.chFin.setDate(UtilExercice.getDate_AjouterAnnee(date, 1));
        this.chNom.setBackground(Color.WHITE);

        switch (indexTabSelected) {
            case 0: //Monnaie
                modeleListeMonnaie.viderListe();
                break;
            case 1: //Classe
                modeleListeClasse.viderListe();
                break;
            case 2: //Frais
                modeleListeFrais.viderListe();
                break;
            case 3: //Charge
                modeleListeCharges.viderListe();
                break;
            case 4: //Revenu
                modeleListeRevenu.viderListe();
                break;
            case 5: //Agent
                modeleListeAgent.viderListe();
                break;
            case 6: //Cours
                modeleListeCours.viderListe();
                break;
        }

    }

    private SortiesExercice getSortieAnneeScolaire(Bouton boutonDeclencheur, RubriqueSimple rubriqueDeclencheur) {
        Annee xX_Exercice = null;
        if (this.donneesExercice.getExercice() != null) {
            Annee OldExercice = this.donneesExercice.getExercice();
            boolean areDeutSame = OldExercice.getDebut().toString().equals(chDebut.getDate().toString());
            boolean areFinSame = OldExercice.getFin().toString().equals(chFin.getDate().toString());

            System.out.println("areDeutSame: " + areDeutSame);
            System.out.println("areFinSame: " + areFinSame);

            if (!chNom.getText().equals(OldExercice.getNom()) || areDeutSame == false || areFinSame == false) {
                // || chDebut.getDate() != OldExercice.getDebut() || chFin.getDate() != OldExercice.getFin()
                //Objet Modifié
                xX_Exercice = new Annee(
                        OldExercice.getId(),
                        OldExercice.getIdEntreprise(),
                        OldExercice.getIdUtilisateur(),
                        chNom.getText(),
                        chDebut.getDate(),
                        chFin.getDate(),
                        UtilObjet.getSignature(),
                        InterfaceAnnee.BETA_MODIFIE
                );
            } else {
                //Objet Non Modifié
                xX_Exercice = new Annee(
                        OldExercice.getId(),
                        OldExercice.getIdEntreprise(),
                        OldExercice.getIdUtilisateur(),
                        OldExercice.getNom(),
                        OldExercice.getDebut(),
                        OldExercice.getFin(),
                        UtilObjet.getSignature(),
                        InterfaceAnnee.BETA_EXISTANT
                );
            }
        } else {
            //Objet Non Enregistré
            xX_Exercice = new Annee(-1, this.parametreExercice.getEntreprise().getId(), -1, this.chNom.getText(), this.chDebut.getDate(), this.chFin.getDate(), UtilObjet.getSignature(), InterfaceAnnee.BETA_NOUVEAU);
        }
        SortiesExercice sortiesAnneeScolaire = new SortiesExercice(
                xX_Exercice,
                this.modeleListeMonnaie.getListeData(),
                this.modeleListeClasse.getListeData(),
                this.modeleListeFrais.getListeData(),
                this.modeleListeCharges.getListeData(),
                this.modeleListeRevenu.getListeData(),
                this.modeleListeAgent.getListeData(),
                this.modeleListeCours.getListeData(),
                this.modeleListePeriode.getListeData(),
                new EcouteurEnregistrement() {
            @Override
            public void onDone(String message) {
                ecouteurClose.onActualiser(message, icones.getAimer_01());
                if (boutonDeclencheur != null) {
                    boutonDeclencheur.appliquerDroitAccessDynamique(true);
                }
                if (rubriqueDeclencheur != null) {
                    rubriqueDeclencheur.appliquerDroitAccessDynamique(true);
                }

                //On redessine les tableau afin que les couleurs se réinitialisent / Tout redevient noire
                /**/
                if (modeleListeAgent != null) {
                    modeleListeAgent.redessinerTable();
                }
                if (modeleListeCharges != null) {
                    modeleListeCharges.redessinerTable();
                }
                if (modeleListeClasse != null) {
                    modeleListeClasse.redessinerTable();
                }
                if (modeleListeCours != null) {
                    modeleListeCours.redessinerTable();
                }
                if (modeleListeFrais != null) {
                    modeleListeFrais.redessinerTable();
                }
                if (modeleListeMonnaie != null) {
                    modeleListeMonnaie.redessinerTable();
                }
                if (modeleListePeriode != null) {
                    modeleListePeriode.redessinerTable();
                }
                if (modeleListeRevenu != null) {
                    modeleListeRevenu.redessinerTable();
                }

                System.out.println("FIN ENREG.");
            }

            @Override
            public void onError(String message) {
                ecouteurClose.onActualiser(message, icones.getAlert_01());
                if (boutonDeclencheur != null) {
                    boutonDeclencheur.appliquerDroitAccessDynamique(true);
                }
                if (rubriqueDeclencheur != null) {
                    rubriqueDeclencheur.appliquerDroitAccessDynamique(true);
                }
            }

            @Override
            public void onUploading(String message) {
                ecouteurClose.onActualiser(message, icones.getInfos_01());
                if (boutonDeclencheur != null) {
                    boutonDeclencheur.appliquerDroitAccessDynamique(false);
                }
                if (rubriqueDeclencheur != null) {
                    rubriqueDeclencheur.appliquerDroitAccessDynamique(false);
                }
            }
        });
        return sortiesAnneeScolaire;
    }

    public void imprimer() {
        if (ecouteurFreemium != null) {
            if (ecouteurFreemium.onVerifieNombre(null) == true) {
                if (this.chNom.getText().trim().length() != 0) {
                    int dialogResult = JOptionPane.showConfirmDialog(this, "Etes-vous sûr de vouloir imprimer ce document?", "Avertissement", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            SortiesExercice sortie = getSortieAnneeScolaire(btImprimer, mImprimer);
                            DocumentPDF_Exercice documentPDF = new DocumentPDF_Exercice(this, DocumentPDF_Exercice.ACTION_IMPRIMER, sortie);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    chNom.setBackground(Color.YELLOW);
                    String message = "Veuillez saisir le nom de l'année scolaire!";
                    ecouteurClose.onActualiser(message, icones.getAlert_01());
                    JOptionPane.showMessageDialog(parent, message, "Le nom de l'années scolaire!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void enregistrer() {
        if (chNom.getText().trim().length() != 0) {
            if (this.ecouteurExercice != null) {
                System.out.println("ENREG...");

                btEnregistrer.setForeground(Color.black);
                mEnregistrer.setCouleur(Color.black);

                SortiesExercice sortie = getSortieAnneeScolaire(btEnregistrer, mEnregistrer);
                this.ecouteurExercice.onEnregistre(sortie);
                this.chNom.setBackground(Color.WHITE);

                actualiserEditeur();
            }
        } else {
            chNom.setBackground(couleurBasique.getCouleur_encadrement_selection());
            String message = "Veuillez saisir le nom de l'année scolaire!";
            ecouteurClose.onActualiser(message, icones.getAlert_01());
            JOptionPane.showMessageDialog(parent, message, "Le nom de l'années scolaire!", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void exporterPDF() {
        if (ecouteurFreemium != null) {
            if (ecouteurFreemium.onVerifieNombre(null) == true) {
                if (this.chNom.getText().trim().length() != 0) {
                    int dialogResult = JOptionPane.showConfirmDialog(this, "Voulez-vous les exporter dans un fichier PDF?", "Avertissement", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            SortiesExercice sortie = getSortieAnneeScolaire(btPDF, mPDF);
                            DocumentPDF_Exercice docpdf = new DocumentPDF_Exercice(this, DocumentPDF_Exercice.ACTION_OUVRIR, sortie);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    chNom.setBackground(couleurBasique.getCouleur_encadrement_selection());
                    String message = "Veuillez saisir le nom de l'année scolaire!";
                    ecouteurClose.onActualiser(message, icones.getAlert_01());
                    JOptionPane.showMessageDialog(parent, message, "Le nom de l'années scolaire!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }

    public void actualiser() {
        switch (indexTabSelected) {
            case 0: //Périodes
                modeleListePeriode.actualiser();
                break;
            case 1: //Monnaie
                modeleListeMonnaie.actualiser();
                break;
            case 2: //Classe
                modeleListeClasse.actualiser();
                break;
            case 3: //Frais
                modeleListeFrais.actualiser();
                break;
            case 4: //Charge
                modeleListeCharges.actualiser();
                break;
            case 5: //Revenu
                modeleListeRevenu.actualiser();
                break;
        }
    }

    public String getNomfichierPreuve() {
        return "Output.pdf";
    }

    private boolean mustBeSaved() {
        boolean rep = false;
        //On vérifie dans la liste d'agents
        for (InterfaceAgent Iagent : this.modeleListeAgent.getListeData()) {
            if (Iagent.getBeta() == InterfaceAgent.BETA_MODIFIE || Iagent.getBeta() == InterfaceAgent.BETA_NOUVEAU) {
                rep = true;
            }
        }

        //On vérifie aussi dans la liste des charges
        for (InterfaceCharge Icharge : this.modeleListeCharges.getListeData()) {
            if (Icharge.getBeta() == InterfaceCharge.BETA_MODIFIE || Icharge.getBeta() == InterfaceCharge.BETA_NOUVEAU) {
                rep = true;
            }
        }

        //On vérifie aussi dans la liste des classes
        for (InterfaceClasse Iclasse : this.modeleListeClasse.getListeData()) {
            if (Iclasse.getBeta() == InterfaceClasse.BETA_MODIFIE || Iclasse.getBeta() == InterfaceClasse.BETA_NOUVEAU) {
                rep = true;
            }
        }

        //On vérifie aussi dans la liste des cours
        for (InterfaceCours Icours : this.modeleListeCours.getListeData()) {
            if (Icours.getBeta() == InterfaceCours.BETA_MODIFIE || Icours.getBeta() == InterfaceCours.BETA_NOUVEAU) {
                rep = true;
            }
        }

        //On vérifie aussi l'exercice
        InterfaceAnnee Iexercice = this.donneesExercice.getExercice();
        if (Iexercice != null) {
            if (Iexercice.getBeta() == InterfaceAnnee.BETA_MODIFIE || Iexercice.getBeta() == InterfaceAnnee.BETA_NOUVEAU) {
                rep = true;
            }
        }

        //On vérifie aussi dans la liste des frais
        for (InterfaceFrais Ifrais : this.modeleListeFrais.getListeData()) {
            if (Ifrais.getBeta() == InterfaceFrais.BETA_MODIFIE || Ifrais.getBeta() == InterfaceFrais.BETA_NOUVEAU) {
                rep = true;
            }
        }

        //On vérifie aussi dans la liste des monnaies
        for (InterfaceMonnaie Imonnaie : this.modeleListeMonnaie.getListeData()) {
            if (Imonnaie.getBeta() == InterfaceMonnaie.BETA_MODIFIE || Imonnaie.getBeta() == InterfaceMonnaie.BETA_NOUVEAU) {
                rep = true;
            }
        }

        //On vérifie aussi dans la liste des revenus
        for (InterfaceRevenu Irevenu : this.modeleListeRevenu.getListeData()) {
            if (Irevenu.getBeta() == InterfaceRevenu.BETA_MODIFIE || Irevenu.getBeta() == InterfaceRevenu.BETA_NOUVEAU) {
                rep = true;
            }
        }

        return rep;
    }

    public void fermer() {
        //Vérifier s'il n'y a rien à enregistrer
        if (mustBeSaved() == true) {
            int dialogResult = JOptionPane.showConfirmDialog(this, "Voulez-vous enregistrer les modifications et/ou ajouts apportés à ces données?", "Avertissement", JOptionPane.YES_NO_CANCEL_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                enregistrer();
                //this.ecouteurExercice.onEnregistre(getSortieAnneeScolaire(btEnregistrer, mEnregistrer));
                //this.ecouteurClose.onFermer();
            } else if (dialogResult == JOptionPane.NO_OPTION) {
                if (ecouteurExercice != null) {
                    ecouteurExercice.onClose();
                }
                this.ecouteurClose.onFermer();
            }
        } else {
            int dialogResult = JOptionPane.showConfirmDialog(this, "Etes-vous sûr de vouloir fermer cette fenêtre?", "Avertissement", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                if (ecouteurExercice != null) {
                    ecouteurExercice.onClose();
                }
                this.ecouteurClose.onFermer();
            }
        }
    }

    private void setMenuContextuel() {
        mAjouter = new RubriqueSimple("Ajouter", 12, false, icones.getAjouter_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                ajouter();
            }
        });

        mSupprimer = new RubriqueSimple("Supprimer", 12, false, icones.getSupprimer_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                supprimer();
            }
        });

        mEnregistrer = new RubriqueSimple("Enregistrer", 12, true, icones.getEnregistrer_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                enregistrer();
            }
        });

        mVider = new RubriqueSimple("Vider", 12, false, icones.getAnnuler_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                vider();
            }
        });

        mImprimer = new RubriqueSimple("Imprimer", 12, false, icones.getImprimer_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                imprimer();
            }
        });

        mFermer = new RubriqueSimple("Fermer", 12, false, icones.getFermer_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                fermer();
            }
        });

        mPDF = new RubriqueSimple("Export. PDF", 12, false, icones.getPDF_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                exporterPDF();
            }
        });

        mActualiser = new RubriqueSimple("Actualiser", 12, false, icones.getSynchroniser_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                actualiser();
            }
        });

        mDetruireExercice = new RubriqueSimple("Detruire Exercice", 12, true, icones.getCouper_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                detruireExercice();
            }
        });

        mFichePaie = new RubriqueSimple("Fiche paie", 12, true, icones.getRecette_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                if (ecouteurCrossCanalAgent != null) {
                    ecouteurCrossCanalAgent.onOuvrirFicheDePaie(selectedAgent);
                }
            }
        });
        mFichePaie.appliquerDroitAccessDynamique(false);

        mTresorerie = new RubriqueSimple("Trésorerie", 12, true, icones.getTableau_de_bord_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                if (indexTabSelected == 4 && ecouteurCrossCanalCharge != null) {
                    ecouteurCrossCanalCharge.onOuvrirCharge(selectedCharge);
                } else if (indexTabSelected == 5 && ecouteurCrossCanalRevenu != null) {
                    ecouteurCrossCanalRevenu.onOuvrirRevenu(selectedRevenu);
                }
            }
        });
        mTresorerie.appliquerDroitAccessDynamique(false);

        mEdition = new RubriqueSimple("Editer", 12, false, icones.getModifier_01(), new RubriqueListener() {
            @Override
            public void OnEcouterLaSelection() {
                setEditionMode();
            }
        });

        //Il faut respecter les droits d'accès attribué à l'utilisateur actuel!
        menuContextuel = new MenuContextuel();
        if (parametreExercice.getUtilisateur() != null) {
            Utilisateur user = parametreExercice.getUtilisateur();

            if (user.getDroitExercice() == InterfaceUtilisateur.DROIT_CONTROLER) {
                menuContextuel.Ajouter(mEnregistrer);
                menuContextuel.Ajouter(mAjouter);
                menuContextuel.Ajouter(mEdition);
                menuContextuel.Ajouter(new JPopupMenu.Separator());
                menuContextuel.Ajouter(mSupprimer);
                menuContextuel.Ajouter(mVider);
                menuContextuel.Ajouter(mDetruireExercice);
            }
            menuContextuel.Ajouter(mActualiser);
            if (user.getDroitPaie() != InterfaceUtilisateur.DROIT_PAS_ACCES) {
                menuContextuel.Ajouter(mFichePaie);
            }
            if (user.getDroitTresorerie() != InterfaceUtilisateur.DROIT_PAS_ACCES) {
                menuContextuel.Ajouter(mTresorerie);
            }
            menuContextuel.Ajouter(new JPopupMenu.Separator());
            menuContextuel.Ajouter(mImprimer);
            menuContextuel.Ajouter(mPDF);
            menuContextuel.Ajouter(new JPopupMenu.Separator());
            menuContextuel.Ajouter(mFermer);
        }
    }

    private void setBoutons() {
        btAjouter = new Bouton(12, "Ajouter", "Infos bulle", false, icones.getAjouter_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                ajouter();
            }
        });

        btSupprimer = new Bouton(12, "Supprimer", "Infos bulle", false, icones.getSupprimer_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                supprimer();
            }
        });

        btEnregistrer = new Bouton(12, "Enregistrer", "Infos bulle", false, icones.getEnregistrer_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                enregistrer();
            }
        });
        btEnregistrer.setGras(true);

        btVider = new Bouton(12, "Vider", "Infos bulle", false, icones.getAnnuler_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                vider();
            }
        });

        btImprimer = new Bouton(12, "Imprimer", "Infos bulle", false, icones.getImprimer_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                imprimer();
            }
        });

        btFermer = new Bouton(12, "Fermer", "Infos bulle", false, icones.getFermer_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                fermer();
            }
        });

        btPDF = new Bouton(12, "Exp. en PDF", "Infos bulle", false, icones.getPDF_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                exporterPDF();
            }
        });

        btActualiser = new Bouton(12, "Actualiser", "Infos bulle", false, icones.getSynchroniser_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                actualiser();
            }
        });

        btDetruireExercice = new Bouton(12, "Detruire Ex.", "Infos bulle", false, icones.getCouper_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                detruireExercice();
            }
        });

        btFichePaie = new Bouton(12, "Fiche Paie", "Ourvrir les rémunérations de l'agent séléctionné", false, icones.getRecette_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                if (ecouteurCrossCanalAgent != null) {
                    ecouteurCrossCanalAgent.onOuvrirFicheDePaie(selectedAgent);
                }
            }
        });
        btFichePaie.appliquerDroitAccessDynamique(false);

        btTresorerie = new Bouton(12, "Trésorerie", "Ouvrir la trésorerie", false, icones.getTableau_de_bord_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                if (indexTabSelected == 4 && ecouteurCrossCanalCharge != null) {
                    ecouteurCrossCanalCharge.onOuvrirCharge(selectedCharge);
                } else if (indexTabSelected == 5 && ecouteurCrossCanalRevenu != null) {
                    ecouteurCrossCanalRevenu.onOuvrirRevenu(selectedRevenu);
                }
            }
        });
        btTresorerie.appliquerDroitAccessDynamique(false);

        btEdition = new Bouton(12, "Edition", "", true, icones.getModifier_02(), new BoutonListener() {
            @Override
            public void OnEcouteLeClick() {
                setEditionMode();
            }
        });

        //Il faut respecter les droits d'accès attribué à l'utilisateur actuel!
        bOutils = new BarreOutils(barreOutils);
        if (parametreExercice.getUtilisateur() != null) {
            Utilisateur user = parametreExercice.getUtilisateur();

            if (user.getDroitExercice() == InterfaceUtilisateur.DROIT_CONTROLER) {
                bOutils.AjouterBouton(btEnregistrer);
                bOutils.AjouterBouton(btAjouter);
                bOutils.AjouterBouton(btEdition);
                bOutils.AjouterSeparateur();
                bOutils.AjouterBouton(btSupprimer);
                bOutils.AjouterBouton(btVider);
                bOutils.AjouterBouton(btDetruireExercice);
            }
            bOutils.AjouterBouton(btActualiser);
            if (user.getDroitPaie() != InterfaceUtilisateur.DROIT_PAS_ACCES) {
                bOutils.AjouterBouton(btFichePaie);
            }
            if (user.getDroitTresorerie() != InterfaceUtilisateur.DROIT_PAS_ACCES) {
                bOutils.AjouterBouton(btTresorerie);
            }
            bOutils.AjouterSeparateur();
            bOutils.AjouterBouton(btImprimer);
            bOutils.AjouterBouton(btPDF);
            bOutils.AjouterSeparateur();
            bOutils.AjouterBouton(btFermer);
        }
    }

    private void detruireExercice() {
        int dialogResult = JOptionPane.showConfirmDialog(parent, "Etes-vous sûr de vouloir supprimer cette Exercice?", "Avertissement", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            if (ecouteurExercice != null && donneesExercice.getExercice() != null) {
                Annee exSup = donneesExercice.getExercice();
                ecouteurExercice.onDetruitExercice(exSup.getId(), exSup.getSignature());
            }
            if (ecouteurClose != null) {
                ecouteurClose.onFermer();
            }
        }
    }

    private void setData(InterfaceAnnee exrcice) {
        if (exrcice != null) {
            this.chNom.setTextInitial("Nom de l'exercice");
            this.chDebut.setDate(exrcice.getDebut());
            this.chFin.setDate(exrcice.getFin());
            this.chNom.setText(exrcice.getNom());
            this.chNom.setForeground(couleurBasique.getCouleur_foreground_objet_existant());
        } else {
            Date date = new Date();
            this.chNom.setTextInitial("Nom de l'exercice");
            this.chDebut.setDate(date);
            this.chFin.setDate(UtilExercice.getDate_AjouterAnnee(date, 1));
        }
    }

    public void init(JTabbedPane parent) {
        this.icones = new Icones();
        this.moi = this;
        this.parent = parent;
        this.chNom.setIcon(icones.getCalendrier_01());
        this.labInfos.setIcon(icones.getInfos_01());
        this.chDebut.setIcon(icones.getCalendrier_01());
        this.chFin.setIcon(icones.getCalendrier_01());
        this.labInfos.setText("Prêt.");
        this.ecouteurClose = new EcouteurUpdateClose() {
            @Override
            public void onFermer() {
                parent.remove(moi);
            }

            @Override
            public void onActualiser(String texte, ImageIcon icone) {
                labInfos.setText(texte);
                labInfos.setIcon(icone);
            }
        };

        ecouteurAjout = new EcouteurAjoutExercice() {
            @Override
            public void setAjoutClasse(ModeleListeClasse modeleListeClasse) {
                if (modeleListeClasse != null) {
                    int index = (modeleListeClasse.getRowCount() + 1);
                    Date date = new Date();
                    modeleListeClasse.AjouterClasse(new Classe(-1, -1, -1, -1, "CLS_" + index, 50, "Local " + index, date.getTime(), InterfaceClasse.BETA_NOUVEAU));
                }
            }

            @Override
            public void setAjoutFrais(ModeleListeFrais modeleListeFrais) {
                if (modeleListeFrais != null) {
                    int index = (modeleListeFrais.getRowCount() + 1);
                    InterfaceMonnaie monnaies = getDefaultMonnaie();
                    if (monnaies != null) {
                        Utilisateur user = parametreExercice.getUtilisateur();
                        Entreprise ese = parametreExercice.getEntreprise();
                        modeleListeFrais.AjouterFrais(new Frais(-1, user.getId(), ese.getId(), -1, monnaies.getId(), 0, "FRS_" + index, monnaies.getCode(), monnaies.getSignature(), UtilExercice.generateSignature(), InterfaceFrais.BETA_NOUVEAU));
                        //modeleListeFrais.AjouterFrais(new Frais(-1, parametreExercice.getUtilisateur().getId(), parametreExercice.getEntreprise().getId(), -1, monnaies.getId(), monnaies.getSignature(), UtilExercice.generateSignature(), "FRS_" + index, monnaies.getCode(), 3, new Vector<LiaisonFraisClasse>(), new Vector<LiaisonFraisPeriode>(), 0, InterfaceFrais.BETA_NOUVEAU));
                    }
                }
            }

            @Override
            public void setAjoutCharge(ModeleListeCharges modeleListeCharges) {
                if (modeleListeCharges != null) {
                    int index = (modeleListeCharges.getRowCount() + 1);
                    InterfaceMonnaie monnaies = getDefaultMonnaie();
                    if (monnaies != null) {
                        modeleListeCharges.AjouterCharge(new Charge(-1, -1, -1, -1, "CHARGE_" + index, 1000, -1, monnaies.getSignature(), monnaies.getCode(), UtilObjet.getSignature(), InterfaceCharge.BETA_NOUVEAU));
                    }
                }
            }

            @Override
            public void setAjoutMonnaie(ModeleListeMonnaie modeleListeMonnaie) {
                if (modeleListeMonnaie != null) {
                    int index = (modeleListeMonnaie.getRowCount() + 1);
                    Date date = new Date();
                    modeleListeMonnaie.AjouterMonnaie(new Monnaie(-1, -1, -1, -1, "Monnaie_" + index, "Um", InterfaceMonnaie.NATURE_MONNAIE_ETRANGERE, 1620, date.getTime(), InterfaceMonnaie.BETA_NOUVEAU));
                }
            }

            @Override
            public void setAjoutRevenu(ModeleListeRevenu modeleListeRevenu) {
                if (modeleListeRevenu != null) {
                    int index = (modeleListeRevenu.getRowCount() + 1);
                    InterfaceMonnaie monnaies = getDefaultMonnaie();
                    if (monnaies != null) {
                        modeleListeRevenu.AjouterRevenu(new Revenu(-1, -1, -1, -1, monnaies.getId(), monnaies.getSignature(), "Revenu_" + index, monnaies.getCode(), 10000, UtilObjet.getSignature(), InterfaceRevenu.BETA_NOUVEAU));
                    }
                }
            }

            @Override
            public void setAjoutAgent(ModeleListeAgent modeleListeAgent) {
                if (modeleListeAgent != null) {
                    int index = (modeleListeAgent.getRowCount() + 1);
                    Date date = new Date();
                    modeleListeAgent.AjouterEnseignant(new Agent(-1, -1, -1, -1, "Enseignant_" + index, "", "", 0, 3, date.getTime(), InterfaceAgent.CATEGORIE_ADMINISTRATION_1, InterfaceAgent.BETA_NOUVEAU));
                }
            }

            @Override
            public void setAjoutCours(ModeleListeCours modeleListeCours) {
                if (modeleListeCours != null) {
                    int index = (modeleListeCours.getRowCount() + 1);
                    modeleListeCours.AjouterCours(new Cours(-1, -1, -1, -1, "Cours_" + index, "", -1, -1, "", -1, -1, UtilObjet.getSignature(), InterfaceCours.BETA_NOUVEAU));
                }
            }

            @Override
            public void setAjoutPeriode(ModeleListePeriode modeleListePeriode) {
                if (modeleListePeriode != null) {
                    int index = (modeleListePeriode.getRowCount() + 1);
                    modeleListePeriode.AjouterPeriode(new Periode(-1, -1, -1, -1, index + "ème Trimestre", chDebut.getDate(), chFin.getDate(), new Date().getTime(), InterfacePeriode.BETA_NOUVEAU));
                }
            }
        };

        setData(this.donneesExercice.getExercice());
        setBoutons();
        setMenuContextuel();
    }

    private InterfaceMonnaie getDefaultMonnaie() {
        if (this.modeleListeMonnaie != null) {
            if (!this.modeleListeMonnaie.getListeData().isEmpty()) {
                InterfaceMonnaie monnaies = this.modeleListeMonnaie.getListeData().firstElement();
                if (monnaies != null) {
                    return monnaies;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        barreOutils = new javax.swing.JToolBar();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        chNom = new UI.JS2bTextField();
        chDebut = new com.toedter.calendar.JDateChooser();
        chFin = new com.toedter.calendar.JDateChooser();
        labInfos = new javax.swing.JLabel();
        tabPrincipal = new javax.swing.JTabbedPane();
        scrollListePeriode = new javax.swing.JScrollPane();
        tableListePeriode = new javax.swing.JTable();
        scrollListeMonnaie = new javax.swing.JScrollPane();
        tableListeMonnaie = new javax.swing.JTable();
        scrollListeClasse = new javax.swing.JScrollPane();
        tableListeClasses = new javax.swing.JTable();
        scrollListeFrais = new javax.swing.JScrollPane();
        tableListeFrais = new javax.swing.JTable();
        scrollListeCharge = new javax.swing.JScrollPane();
        tableListeCharge = new javax.swing.JTable();
        scrollListeRevenu = new javax.swing.JScrollPane();
        tableListeRevenu = new javax.swing.JTable();
        scrollListeAgent = new javax.swing.JScrollPane();
        tableListeAgent = new javax.swing.JTable();
        scrollListeCours = new javax.swing.JScrollPane();
        tableListeCours = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        barreOutils.setBackground(new java.awt.Color(255, 255, 255));
        barreOutils.setFloatable(false);
        barreOutils.setRollover(true);
        barreOutils.setPreferredSize(new java.awt.Dimension(59, 61));

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG_Exercice/Facture02.png"))); // NOI18N
        jButton5.setText("Ajouter");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barreOutils.add(jButton5);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        chNom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG_Exercice/Facture01.png"))); // NOI18N
        chNom.setTextInitial("Nom de l'exercice");
        chNom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                chNomKeyReleased(evt);
            }
        });

        chDebut.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        chFin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chNom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chDebut, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chFin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chDebut, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addComponent(chFin, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addComponent(chNom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labInfos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG_Exercice/Facture01.png"))); // NOI18N
        labInfos.setText("Prêt.");

        tabPrincipal.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabPrincipalStateChanged(evt);
            }
        });

        scrollListePeriode.setBackground(new java.awt.Color(255, 255, 255));
        scrollListePeriode.setAutoscrolls(true);
        scrollListePeriode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollListePeriodeMouseClicked(evt);
            }
        });

        tableListePeriode.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Article", "Qunatité", "Unités", "Prix Unitaire HT", "Tva %", "Tva", "Total TTC"
            }
        ));
        tableListePeriode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableListePeriodeMouseClicked(evt);
            }
        });
        scrollListePeriode.setViewportView(tableListePeriode);

        tabPrincipal.addTab("Périodes", scrollListePeriode);

        scrollListeMonnaie.setBackground(new java.awt.Color(255, 255, 255));
        scrollListeMonnaie.setAutoscrolls(true);
        scrollListeMonnaie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollListeMonnaieMouseClicked(evt);
            }
        });

        tableListeMonnaie.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Article", "Qunatité", "Unités", "Prix Unitaire HT", "Tva %", "Tva", "Total TTC"
            }
        ));
        tableListeMonnaie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableListeMonnaieMouseClicked(evt);
            }
        });
        scrollListeMonnaie.setViewportView(tableListeMonnaie);

        tabPrincipal.addTab("Monnaies", scrollListeMonnaie);

        scrollListeClasse.setBackground(new java.awt.Color(255, 255, 255));
        scrollListeClasse.setAutoscrolls(true);
        scrollListeClasse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollListeClasseMouseClicked(evt);
            }
        });

        tableListeClasses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Article", "Qunatité", "Unités", "Prix Unitaire HT", "Tva %", "Tva", "Total TTC"
            }
        ));
        tableListeClasses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableListeClassesMouseClicked(evt);
            }
        });
        scrollListeClasse.setViewportView(tableListeClasses);

        tabPrincipal.addTab("Classes", scrollListeClasse);

        scrollListeFrais.setBackground(new java.awt.Color(255, 255, 255));
        scrollListeFrais.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                scrollListeFraisMouseDragged(evt);
            }
        });
        scrollListeFrais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollListeFraisMouseClicked(evt);
            }
        });

        tableListeFrais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Date", "Dépositaire", "Montant"
            }
        ));
        tableListeFrais.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tableListeFraisMouseDragged(evt);
            }
        });
        tableListeFrais.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableListeFraisMouseClicked(evt);
            }
        });
        tableListeFrais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableListeFraisKeyReleased(evt);
            }
        });
        scrollListeFrais.setViewportView(tableListeFrais);

        tabPrincipal.addTab("Frais", scrollListeFrais);

        scrollListeCharge.setBackground(new java.awt.Color(255, 255, 255));
        scrollListeCharge.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                scrollListeChargeMouseDragged(evt);
            }
        });
        scrollListeCharge.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollListeChargeMouseClicked(evt);
            }
        });

        tableListeCharge.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Date", "Dépositaire", "Montant"
            }
        ));
        tableListeCharge.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tableListeChargeMouseDragged(evt);
            }
        });
        tableListeCharge.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableListeChargeMouseClicked(evt);
            }
        });
        tableListeCharge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableListeChargeKeyReleased(evt);
            }
        });
        scrollListeCharge.setViewportView(tableListeCharge);

        tabPrincipal.addTab("Charges", scrollListeCharge);

        scrollListeRevenu.setBackground(new java.awt.Color(255, 255, 255));
        scrollListeRevenu.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                scrollListeRevenuMouseDragged(evt);
            }
        });
        scrollListeRevenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollListeRevenuMouseClicked(evt);
            }
        });

        tableListeRevenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Date", "Dépositaire", "Montant"
            }
        ));
        tableListeRevenu.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tableListeRevenuMouseDragged(evt);
            }
        });
        tableListeRevenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableListeRevenuMouseClicked(evt);
            }
        });
        tableListeRevenu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableListeRevenuKeyReleased(evt);
            }
        });
        scrollListeRevenu.setViewportView(tableListeRevenu);

        tabPrincipal.addTab("Revenus", scrollListeRevenu);

        scrollListeAgent.setBackground(new java.awt.Color(255, 255, 255));
        scrollListeAgent.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                scrollListeAgentMouseDragged(evt);
            }
        });
        scrollListeAgent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollListeAgentMouseClicked(evt);
            }
        });

        tableListeAgent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Date", "Dépositaire", "Montant"
            }
        ));
        tableListeAgent.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tableListeAgentMouseDragged(evt);
            }
        });
        tableListeAgent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableListeAgentMouseClicked(evt);
            }
        });
        tableListeAgent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableListeAgentKeyReleased(evt);
            }
        });
        scrollListeAgent.setViewportView(tableListeAgent);

        tabPrincipal.addTab("Agents", scrollListeAgent);

        scrollListeCours.setBackground(new java.awt.Color(255, 255, 255));
        scrollListeCours.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                scrollListeCoursMouseDragged(evt);
            }
        });
        scrollListeCours.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollListeCoursMouseClicked(evt);
            }
        });

        tableListeCours.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Date", "Dépositaire", "Montant"
            }
        ));
        tableListeCours.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tableListeCoursMouseDragged(evt);
            }
        });
        tableListeCours.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableListeCoursMouseClicked(evt);
            }
        });
        tableListeCours.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableListeCoursKeyReleased(evt);
            }
        });
        scrollListeCours.setViewportView(tableListeCours);

        tabPrincipal.addTab("Cours", scrollListeCours);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(barreOutils, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labInfos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(tabPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(barreOutils, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tabPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(labInfos)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tableListeClassesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeClassesMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 2);
    }//GEN-LAST:event_tableListeClassesMouseClicked

    private void scrollListeClasseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeClasseMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 2);
    }//GEN-LAST:event_scrollListeClasseMouseClicked

    private void tableListeFraisMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeFraisMouseDragged
        // TODO add your handling code here:
        //ecouterSelectionPaiement();
    }//GEN-LAST:event_tableListeFraisMouseDragged

    private void tableListeFraisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeFraisMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 3);
        //ecouterSelectionPaiement();
    }//GEN-LAST:event_tableListeFraisMouseClicked

    private void tableListeFraisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableListeFraisKeyReleased
        // TODO add your handling code here:
        //ecouterSelectionPaiement();
    }//GEN-LAST:event_tableListeFraisKeyReleased

    private void scrollListeFraisMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeFraisMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_scrollListeFraisMouseDragged

    private void scrollListeFraisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeFraisMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 3);
    }//GEN-LAST:event_scrollListeFraisMouseClicked

    private void tabPrincipalStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabPrincipalStateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_tabPrincipalStateChanged

    private void tableListeChargeMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeChargeMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_tableListeChargeMouseDragged

    private void tableListeChargeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeChargeMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 4);
    }//GEN-LAST:event_tableListeChargeMouseClicked

    private void tableListeChargeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableListeChargeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tableListeChargeKeyReleased

    private void scrollListeChargeMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeChargeMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_scrollListeChargeMouseDragged

    private void scrollListeChargeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeChargeMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 4);
    }//GEN-LAST:event_scrollListeChargeMouseClicked

    private void tableListeMonnaieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeMonnaieMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 1);
    }//GEN-LAST:event_tableListeMonnaieMouseClicked

    private void scrollListeMonnaieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeMonnaieMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 1);
    }//GEN-LAST:event_scrollListeMonnaieMouseClicked

    private void tableListeRevenuMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeRevenuMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_tableListeRevenuMouseDragged

    private void tableListeRevenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeRevenuMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 5);
    }//GEN-LAST:event_tableListeRevenuMouseClicked

    private void tableListeRevenuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableListeRevenuKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tableListeRevenuKeyReleased

    private void scrollListeRevenuMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeRevenuMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_scrollListeRevenuMouseDragged

    private void scrollListeRevenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeRevenuMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 5);
    }//GEN-LAST:event_scrollListeRevenuMouseClicked

    private void tableListeAgentMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeAgentMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_tableListeAgentMouseDragged

    private void tableListeAgentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeAgentMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 6);
    }//GEN-LAST:event_tableListeAgentMouseClicked

    private void tableListeAgentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableListeAgentKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tableListeAgentKeyReleased

    private void scrollListeAgentMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeAgentMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_scrollListeAgentMouseDragged

    private void scrollListeAgentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeAgentMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 6);
    }//GEN-LAST:event_scrollListeAgentMouseClicked

    private void tableListeCoursMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeCoursMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_tableListeCoursMouseDragged

    private void tableListeCoursMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListeCoursMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 7);
    }//GEN-LAST:event_tableListeCoursMouseClicked

    private void tableListeCoursKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableListeCoursKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tableListeCoursKeyReleased

    private void scrollListeCoursMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeCoursMouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_scrollListeCoursMouseDragged

    private void scrollListeCoursMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListeCoursMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 7);
    }//GEN-LAST:event_scrollListeCoursMouseClicked

    private void tableListePeriodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableListePeriodeMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 0);
    }//GEN-LAST:event_tableListePeriodeMouseClicked

    private void scrollListePeriodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollListePeriodeMouseClicked
        // TODO add your handling code here:
        //ecouterMenContA(evt, 0);
    }//GEN-LAST:event_scrollListePeriodeMouseClicked

    private void chNomKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chNomKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            enregistrer();
        }
    }//GEN-LAST:event_chNomKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barreOutils;
    private com.toedter.calendar.JDateChooser chDebut;
    private com.toedter.calendar.JDateChooser chFin;
    private UI.JS2bTextField chNom;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labInfos;
    private javax.swing.JScrollPane scrollListeAgent;
    private javax.swing.JScrollPane scrollListeCharge;
    private javax.swing.JScrollPane scrollListeClasse;
    private javax.swing.JScrollPane scrollListeCours;
    private javax.swing.JScrollPane scrollListeFrais;
    private javax.swing.JScrollPane scrollListeMonnaie;
    private javax.swing.JScrollPane scrollListePeriode;
    private javax.swing.JScrollPane scrollListeRevenu;
    private javax.swing.JTabbedPane tabPrincipal;
    private javax.swing.JTable tableListeAgent;
    private javax.swing.JTable tableListeCharge;
    private javax.swing.JTable tableListeClasses;
    private javax.swing.JTable tableListeCours;
    private javax.swing.JTable tableListeFrais;
    private javax.swing.JTable tableListeMonnaie;
    private javax.swing.JTable tableListePeriode;
    private javax.swing.JTable tableListeRevenu;
    // End of variables declaration//GEN-END:variables
}
