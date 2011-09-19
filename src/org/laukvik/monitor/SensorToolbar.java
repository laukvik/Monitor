/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 *
 * @author morten
 */
public class SensorToolbar extends JToolBar{

    public SensorToolbar() {
        super();
        setOrientation( JToolBar.HORIZONTAL );
        add( new JButton("Save") );
    }
    
}