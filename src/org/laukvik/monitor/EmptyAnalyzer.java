/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import java.awt.Graphics;

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
    public void paint(  Graphics g, int width, int height  ) {
        g.drawString( "(Configuration error)", 64, 20 );
    }

    @Override
    public void analyze() {
    }

}