/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author morten
 */
public class SensorGroupPanel extends JPanel{

    SensorTable table;
    JScrollPane scroll;
    
    public SensorGroupPanel( SensorGroup group ) {
        super();
        setBorder( null );
        setLayout( new BorderLayout() );
        setOpaque( false );
        table = new SensorTable( group );
        
        scroll = new JScrollPane(table);
        scroll.setBorder( null );

        add( scroll );
        
        for (Sensor s : group.getSensorList()){
            s.start();
        }
    }
 
    
    
}