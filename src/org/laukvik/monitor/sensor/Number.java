/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor.sensor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.laukvik.monitor.Analyzer;
import org.laukvik.monitor.Sensor;

/**
 *
 * @author morten
 */
public class Number implements Analyzer{

    Sensor sensor;
    
    public Number( Sensor sensor ){
        this.sensor = sensor;
    }

    @Override
    public void paint(Graphics g, int width, int height) {
        if (g == null){
            return;
        }
        
        Font f = g.getFont();
        Color c = g.getColor();
        
        g.setFont( new Font( f.getFontName(), Font.BOLD, 32 ) );
        
        
        String formattedNumber = sensor.getValue() + "";
        int formattedWidth = g.getFontMetrics().stringWidth( formattedNumber );
        
        g.setColor( new Color(0,0,0,50) );
        g.drawString( formattedNumber, 30-(formattedWidth/2)+1,41 );
        g.setColor( c );
        g.drawString( formattedNumber, 30-(formattedWidth/2),40 );

        
        g.setColor( c );
        g.setFont( f );
//        String formattedUnit = getUnit();
//        int unitWidth = g.getFontMetrics().stringWidth( formattedUnit );
//        g.drawString( formattedUnit, 30-(unitWidth/2),50 );

        
        g.drawString( sensor.getTitle(), 64, 20 );
        
        g.drawString( sensor.getDescription() + "", 64, 40 ); 
    }

    @Override
    public void analyze() {
        try {
            String s = getString( new URL( sensor.getTitle() ) );
            sensor.setValue( Long.parseLong( s ) );
        } catch (IOException ex) {
            sensor.setValue( null );
        }
    }
    
    public static String getString( URL url ) throws IOException{
        return new String( loadBytesFromStream( url.openStream() )  );
    }
    
    private static byte[] loadBytesFromStream(InputStream in) throws IOException {
        int chunkSize = 1024;
        int count;
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        byte[] b = new byte[chunkSize];
        try {
            while ((count = in.read(b, 0, chunkSize)) > 0) {
                bo.write(b, 0, count);
            }
            byte[] thebytes = bo.toByteArray();
            return thebytes;
        } finally {
            bo.close();
            bo = null;
        }
    }
    
}