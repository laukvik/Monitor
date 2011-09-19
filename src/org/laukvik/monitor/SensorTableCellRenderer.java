/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author morten
 */
public class SensorTableCellRenderer extends JComponent implements TableCellRenderer{

    Sensor sensor;
    
    public SensorTableCellRenderer() {
        super();
        setOpaque( true );
        setBorder( null );
    }
    
    @Override
    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.sensor = (Sensor) value;
        setForeground( isSelected ? table.getSelectionForeground() : table.getForeground() );
        setBackground( isSelected ? table.getSelectionBackground() : table.getBackground() );
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor( getBackground() );
        g.fillRect( 0,0, getWidth(), getHeight() );
        
        g.setColor( new Color(33,38,43,140) );
        g.fillRoundRect( 6,6, 48, 48, 10,10 );
        
        g.setColor( getForeground() );
        sensor.paint( g, getWidth(), getHeight() );

        
        Graphics2D g2d = (Graphics2D)g;
        GradientPaint p = new GradientPaint(
            0, 0, new Color(255, 255, 255, 50), 0, getHeight(), new Color(0, 0, 0, 0)
        );
        g2d.setPaint( p );


        g2d.fillRect(0, 0, getWidth(),  getHeight()/2);

    }

}