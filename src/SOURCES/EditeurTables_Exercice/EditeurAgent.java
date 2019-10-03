/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.EditeurTables_Exercice;


import SOURCES.ModeleTable_Exercice.ModeleListeAgent;
import Source.Interface.InterfaceAgent;
import Source.Objet.Agent;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author user
 */
public class EditeurAgent extends AbstractCellEditor implements TableCellEditor {
    
    private JComboBox<String> champEditionCombo = new JComboBox();
    private ModeleListeAgent modeleListeEnseignant;

    public EditeurAgent(ModeleListeAgent modeleListeEnseignant) {
        this.modeleListeEnseignant = modeleListeEnseignant;
        initCombo();
    }

    public void initCombo() {
        this.champEditionCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Clic: " + e.getActionCommand());
                fireEditingStopped();
            }
        });
        this.champEditionCombo.removeAllItems();
        if (this.modeleListeEnseignant != null) {
            Vector<Agent> listeEnseignants = this.modeleListeEnseignant.getListeData();
            if(listeEnseignants != null){
                for(InterfaceAgent enseignant : listeEnseignants){
                    this.champEditionCombo.addItem(enseignant.getPrenom() + " " + enseignant.getNom());
                }
            }
        }
    }
    
    private long getSigneture(String enseignant){
        long signature = -1;
        for(InterfaceAgent Ienseignant : this.modeleListeEnseignant.getListeData()){
            String enseignantListe = Ienseignant.getPrenom() + " " + Ienseignant.getNom();
            if(enseignantListe.trim().equals(enseignant.trim())){
                signature = Ienseignant.getSignature();
                break;
            }
        }
        return signature;
    }
    
    private String getEnseignant(long signatureSelected){
        String enseignant = "Inconnue";
        for(InterfaceAgent Ienseignant : this.modeleListeEnseignant.getListeData()){
            if(Ienseignant.getSignature() == signatureSelected){
                enseignant = Ienseignant.getPrenom() + " " + Ienseignant.getNom();
                break;
            }
        }
        return enseignant;
    }

    @Override
    public Object getCellEditorValue() {
        //Après édition de l'utilisateur
        return getSigneture(champEditionCombo.getSelectedItem() + "");
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        //Pendant édition de l'utilisateur
        initCombo();
        String DefautSelection = getEnseignant(Long.parseLong(value+""));
        champEditionCombo.setSelectedItem(DefautSelection);
        //System.out.println("DefautSelection = " + DefautSelection);
        return champEditionCombo;
    }

}















