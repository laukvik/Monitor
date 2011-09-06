/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author morten
 */
public class SensorTableModel implements javax.swing.table.TableModel{

    SensorGroup group;
    List<TableModelListener> listeners;
    
    public SensorTableModel() {
        listeners = new ArrayList<TableModelListener>();
        group = new SensorGroup();
    }

    public void setGroup(SensorGroup group) {
        this.group = group;
    }
    
    public void fireTableChanged(){
        TableModelEvent evt = new TableModelEvent( this );
        for (TableModelListener l : listeners){
            l.tableChanged( evt );
        }
    }

    @Override
    public int getRowCount() {
        return group.getSensorCollection().size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex){
            case 0 : return "SensorID";
            case 1 : return "Title";
            default : return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0 : return Integer.class;
            case 1 : return String.class;
            default : return null;
        }
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return false;
    }

    @Override
    public Object getValueAt( int rowIndex , int columnIndex) {
        Sensor s = group.getSensorCollection().get( rowIndex );
        switch(columnIndex){
            case 0 : return s.getSensorid();
            case 1 : return s.getTitle();
            default : return null;
        }
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addTableModelListener(TableModelListener l ) {
        listeners.add( l );
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove( l );
    }
    
}
