/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOURCES.EditeurTables_Exercice;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author user
 */
public class EditeurNiveauEtude extends AbstractCellEditor implements TableCellEditor {

    private JComboBox<String> champEditionCombo = new JComboBox();

    public EditeurNiveauEtude() {
        initCombo();
    }

    private void initCombo() {
        this.champEditionCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Clic: " + e.getActionCommand());
                fireEditingStopped();
            }
        });
        this.champEditionCombo.removeAllItems();
        this.champEditionCombo.addItem("DIPLOME(E) D'ETAT");
        this.champEditionCombo.addItem("GRADUE(E)");
        this.champEditionCombo.addItem("LICENCIE(E)");
        this.champEditionCombo.addItem("MASTER (DEA)");
        this.champEditionCombo.addItem("DOCTEUR");
        this.champEditionCombo.addItem("AUTRE");
    }

    @Override
    public Object getCellEditorValue() {
        //Après édition de l'utilisateur
        return champEditionCombo.getSelectedIndex();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        //Pendant édition de l'utilisateur
        champEditionCombo.setSelectedIndex(Integer.parseInt(value+""));
        return champEditionCombo;
    }

}

