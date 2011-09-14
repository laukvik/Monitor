/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor.sensor;

import java.awt.Graphics2D;
import org.laukvik.monitor.Analyzer;
import org.laukvik.monitor.Sensor;

/**
 *
 * @author morten
 */
public class WebSite implements Analyzer {

    Sensor sensor;
    
    public WebSite( Sensor sensor ) {
        this.sensor = sensor;
    }

    @Override
    public void paint( Graphics2D g, int width, int height ) {
        g.clearRect( 0, 0, width, height );

        g.drawString( sensor.getTitle(), 64, 20 );
        
        g.drawString( sensor.getValue() + "", 64, 40 ); 
    }

    @Override
    public void run() {
        sensor.setValue( System.currentTimeMillis() );
        sensor.fireValueChanged();
    }

}