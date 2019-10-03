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
public class EditeurCategorie extends AbstractCellEditor implements TableCellEditor {

    private JComboBox<String> champEditionCombo = new JComboBox();

    public EditeurCategorie() {
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
        this.champEditionCombo.addItem("ADMINISTRATION 1");
        this.champEditionCombo.addItem("ADMINISTRATION 2");
        this.champEditionCombo.addItem("MATERNELLE");
        this.champEditionCombo.addItem("PARTIEL");
        this.champEditionCombo.addItem("PRIMAIRE");
        this.champEditionCombo.addItem("PRIME");
        this.champEditionCombo.addItem("SECONDAIRE");
        this.champEditionCombo.addItem("SURVEILLANT");
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
