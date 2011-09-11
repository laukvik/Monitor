/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import java.awt.Component;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author morten
 */
public class SensorTableCellRenderer implements TableCellRenderer{

    JComponent com;
    
    public SensorTableCellRenderer() {
        super();
        com = new JLabel();
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Sensor sensor = (Sensor) value;
        Graphics2D g = (Graphics2D) table.getGraphics();
                
        if (g == null){
            System.out.println(  "null" );
        } else {
            g.setColor( isSelected ? table.getSelectionForeground() : table.getForeground() );
            g.setBackground( isSelected ? table.getSelectionBackground() : table.getBackground() );
            sensor.paint( g,  table.getWidth(), table.getRowHeight() );
        }

        return com;
    }
    
}