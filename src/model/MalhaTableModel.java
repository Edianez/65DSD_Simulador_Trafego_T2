/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import model.vias.Via;

/**
 *
 * @author Danton e Edianez
 */
public class MalhaTableModel extends AbstractTableModel {

    private Malha malha;

    public MalhaTableModel(Malha malha) {
        this.malha = malha;
    }

    @Override
    public int getRowCount() {
        return malha.getAltura();
    }

    @Override
    public int getColumnCount() {
        return malha.getLargura();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Via v = malha.getViaNaCoordenada(columnIndex, rowIndex);
        if (v != null) {
            return v.getImagem();
        } else {
            return null;
        }
    }

    @Override
    public void setValueAt(Object via, int rowIndex, int columnIndex) {
        super.setValueAt(((Via) via).getImagem(), rowIndex, columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ImageIcon.class;
    }

    public Malha getMalha() {
        return malha;
    }
    

}
