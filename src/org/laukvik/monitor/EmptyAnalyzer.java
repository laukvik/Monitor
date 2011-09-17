/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import java.awt.Graphics2D;

/**
 *
 * @author morten
 */
public class EmptyAnalyzer implements Analyzer {

    Sensor sensor;
    
    public EmptyAnalyzer( Sensor sensor ) {
        this.sensor = sensor;
    }

    @Override
    public void paint( Graphics2D g, int width, int height ) {
        g.clearRect( 0, 0, width, height );
        g.drawString( "(Configuration error)", 64, 20 );
    }

    @Override
    public void run() {
    }

}