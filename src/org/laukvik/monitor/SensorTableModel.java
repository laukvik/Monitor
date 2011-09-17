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
public class SensorTableModel implements javax.swing.table.TableModel, SensorListener{

    SensorGroup group;
    List<TableModelListener> listeners;
    
    public SensorTableModel( SensorGroup group ) {
        listeners = new ArrayList<TableModelListener>();
        setGroup( group );
    }
    
    public SensorTableModel() {
        this(new SensorGroup());
    }

    public void setGroup(SensorGroup group) {
        this.group = group;
        for (Sensor s : group.getSensorList()){
            s.addSensorListener( this );
        }
    }
    
    @Override
    public void statusChanged(SensorEvent evt) {
        int tableRowIndex = group.getSensorList().indexOf( evt.sensor );
        TableModelEvent tme = new TableModelEvent( this, tableRowIndex );
        for (TableModelListener l : listeners){
            l.tableChanged( tme );
        }
    }
    
    public void fireTableChanged(){
        TableModelEvent evt = new TableModelEvent( this );
        for (TableModelListener l : listeners){
            l.tableChanged( evt );
        }
    }

    @Override
    public int getRowCount() {
        return group.getSensorList().size();
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex){
            case 0 : return "Sensor";
//            case 1 : return "Title";
//            case 2 : return "Value";
            default : return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0 : return Sensor.class;
//            case 1 : return String.class;
//            case 2 : return Long.class;
            default : return null;
        }
    }

    @Override
    public boolean isCellEditable( int rowIndex , int columnIndex ) {
        
        return false;
    }

    @Override
    public Object getValueAt( int rowIndex , int columnIndex) {
        Sensor s = group.getSensorList().get(rowIndex);
        switch(columnIndex){
            case 0 : return s;
//            case 1 : return s.getTitle();
            default : return null;
        }
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
        
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
