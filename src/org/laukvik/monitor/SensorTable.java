/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import javax.swing.JTable;

/**
 *
 * @author morten
 */
public class SensorTable extends JTable{

    public SensorTable() {
        super();
        setModel( new SensorTableModel() );
    }

    public SensorTable(SensorGroup group) {
        super();
        setModel( new SensorTableModel(group) );
    }

    void setGroup(SensorGroup sensorGroup ) {
        ((SensorTableModel)getModel()).setGroup( sensorGroup );
    }
 
    
    
}