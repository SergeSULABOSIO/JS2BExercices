/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.EditeurTables_Exercice;


import SOURCES.ModeleTable_Exercice.ModeleListeMonnaie;
import Source.Interface.InterfaceMonnaie;
import Source.Objet.Monnaie;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author user
 */
public class EditeurMonnaie extends AbstractCellEditor implements TableCellEditor {

    private JComboBox<String> champEditionCombo = new JComboBox();
    private ModeleListeMonnaie modeleListeMonnaie;

    public EditeurMonnaie(ModeleListeMonnaie modeleListeMonnaie) {
        this.modeleListeMonnaie = modeleListeMonnaie;
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
        if (this.modeleListeMonnaie != null) {
            Vector<Monnaie> listeMonnaies = this.modeleListeMonnaie.getListeData();
            if(listeMonnaies != null){
                for(InterfaceMonnaie monnaie : listeMonnaies){
                    this.champEditionCombo.addItem(monnaie.getCode() + " - " + monnaie.getNom());
                }
            }
        }
    }
    
    private long getSigneture(String monnaieSelected){
        long signature = -1;
        for(InterfaceMonnaie Imonnaie : this.modeleListeMonnaie.getListeData()){
            String monnaieListe = Imonnaie.getCode() + " - " + Imonnaie.getNom();
            if(monnaieListe.trim().equals(monnaieSelected.trim())){
                signature = Imonnaie.getSignature();
                break;
            }
        }
        return signature;
    }
    
    private String getMonnaie(long signature){
        String monnaie = "Inconnu(e)";
        for(InterfaceMonnaie Imonnaie : this.modeleListeMonnaie.getListeData()){
            if(Imonnaie.getSignature() == signature){
                monnaie = Imonnaie.getCode() + " - " + Imonnaie.getNom();
                break;
            }
        }
        return monnaie;
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
        String defaultSelection = getMonnaie(Long.parseLong(value+""));
        champEditionCombo.setSelectedItem(defaultSelection);
        //System.out.println("DefautSelection = " + defaultSelection);
        return champEditionCombo;
    }

}

