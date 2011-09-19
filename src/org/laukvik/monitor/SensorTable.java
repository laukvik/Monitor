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

    public static Color BACKGROUND = new Color(53,58,63);
    public static Color FOREGROUND = Color.WHITE;
    
    public SensorTable() {
        super();
        setDefaultRenderer( Sensor.class, new SensorTableCellRenderer() );
        setRowHeight( 64 );
        setBackground( BACKGROUND );
        setForeground( FOREGROUND );
        setTableHeader( null );
        setGridColor( BACKGROUND );
        setBorder( null );
        setModel( new SensorTableModel() );
    }

    public SensorTable(SensorGroup group) {
        super();
        setDefaultRenderer( Sensor.class, new SensorTableCellRenderer() );
        setRowHeight( 64 ); 
        setBackground( BACKGROUND );
        setForeground( FOREGROUND );
        setTableHeader( null );
        setGridColor( BACKGROUND );
        setBorder( null );
        setModel( new SensorTableModel(group) );
    }

    public void setGroup(SensorGroup sensorGroup ) {
        ((SensorTableModel)getModel()).setGroup( sensorGroup );
    }

}