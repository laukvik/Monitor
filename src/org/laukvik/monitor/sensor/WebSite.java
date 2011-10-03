/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor.sensor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.net.URL;
import java.util.concurrent.TimeUnit;
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
    public void paint(  Graphics g, int width, int height  ) {
        if (g == null){
            return;
        }
        
        Font f = g.getFont();
        Color c = g.getColor();
        
        g.setFont( new Font( f.getFontName(), Font.BOLD, 32 ) );
        
        
        String formattedNumber = getFormattedNumber();
        int formattedWidth = g.getFontMetrics().stringWidth( formattedNumber );
        
        g.setColor( new Color(0,0,0,50) );
        g.drawString( formattedNumber, 30-(formattedWidth/2)+1,41 );
        g.setColor( c );
        g.drawString( formattedNumber, 30-(formattedWidth/2),40 );

        
        g.setColor( c );
        g.setFont( f );
        String formattedUnit = getUnit();
        int unitWidth = g.getFontMetrics().stringWidth( formattedUnit );
        g.drawString( formattedUnit, 30-(unitWidth/2),50 );

        
        g.drawString( sensor.getTitle(), 64, 20 );
        
        g.drawString( sensor.getDescription() + "", 64, 40 ); 
    }

    @Override
    public void analyze() {
        long start = System.currentTimeMillis(); 
        try {
            URL url = new URL( sensor.getSetting( "url" ).getValue() );
            Object s = url.getContent();
            sensor.setValue( System.currentTimeMillis() - start );
        } catch (Exception ex) {
            ex.printStackTrace();
            sensor.setValue( null );
        }
    }

    public String getFormattedNumber(){
        if (sensor.getValue() == null){
            return "";
        } else if (sensor.getValue() < 100){
            return (TimeUnit.MILLISECONDS.toMillis( sensor.getValue() )  +"");
        } else {
            return (TimeUnit.MILLISECONDS.toSeconds( sensor.getValue() )  +"");
        }
    }
    
    public String getUnit(){
        if (sensor.getValue() == null){
        } else if (sensor.getValue() < 100){
            return "ms";
        }
        return "s";
    }
    
}