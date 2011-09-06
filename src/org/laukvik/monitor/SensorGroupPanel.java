/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author morten
 */
public class SensorGroupPanel extends JPanel{

    SensorTable table;
    
    public SensorGroupPanel( SensorGroup group ) {
        super();
        setLayout( new BorderLayout() );
        table = new SensorTable( group );
        add( new JScrollPane(table) );
    }
 
    
    
}