/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import java.awt.Color;
import javax.swing.JTable;

/**
 *
 * @author morten
 */
public class SensorTable extends JTable{

    public SensorTable() {
        super();
        setDefaultRenderer( Sensor.class, new SensorTableCellRenderer() );
        setRowHeight( 64 );
        setBackground( new Color(53,58,63) );
        setForeground( Color.WHITE );
        setModel( new SensorTableModel() );
    }

    public SensorTable(SensorGroup group) {
        super();
        setDefaultRenderer( Sensor.class, new SensorTableCellRenderer() );
        setRowHeight( 64 ); 
        setBackground( new Color(53,58,63) );
        setForeground( Color.WHITE );
        setModel( new SensorTableModel(group) );
    }

    public void setGroup(SensorGroup sensorGroup ) {
        ((SensorTableModel)getModel()).setGroup( sensorGroup );
    }

}