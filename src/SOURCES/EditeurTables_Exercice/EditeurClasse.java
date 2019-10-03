/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.EditeurTables_Exercice;

import SOURCES.ModeleTable_Exercice.ModeleListeClasse;
import Source.Interface.InterfaceClasse;
import Source.Objet.Classe;
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
public class EditeurClasse extends AbstractCellEditor implements TableCellEditor {

    private JComboBox<String> champEditionCombo = new JComboBox();
    private ModeleListeClasse modeleListeClasse;

    public EditeurClasse(ModeleListeClasse modeleListeClasse) {
        this.modeleListeClasse = modeleListeClasse;
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
        if (this.modeleListeClasse != null) {
            Vector<Classe> listeClasses = this.modeleListeClasse.getListeData();
            if(listeClasses != null){
                for(InterfaceClasse monnaie : listeClasses){
                    this.champEditionCombo.addItem(monnaie.getNom());
                }
            }
        }
    }
    
    private String getClasse(long signatureSelected){
        String classe = "Inconnue";
        for(InterfaceClasse Iclasse : this.modeleListeClasse.getListeData()){
            if(Iclasse.getSignature() == signatureSelected){
                classe = Iclasse.getNom();
                break;
            }
        }
        return classe;
    }
    
    
    private long getSignature(String classe){
        long signature = -1;
        for(InterfaceClasse Iclasse : this.modeleListeClasse.getListeData()){
            if(Iclasse.getNom().trim().equals(classe.trim())){
                signature = Iclasse.getSignature();
            }
        }
        return signature;
    }

    @Override
    public Object getCellEditorValue() {
        //Après édition de l'utilisateur
        return getSignature(champEditionCombo.getSelectedItem() + "");
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        //Pendant édition de l'utilisateur
        initCombo();
        String DefautSelection = getClasse(Long.parseLong(value+""));
        champEditionCombo.setSelectedItem(DefautSelection);
        //System.out.println("DefautSelection = " + DefautSelection);
        return champEditionCombo;
    }

}

