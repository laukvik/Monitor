/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor.sensor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import org.laukvik.monitor.Analyzer;
import org.laukvik.monitor.Sensor;

/**
 *
 * @author morten
 */
public class Certificate implements Analyzer{
    
    Sensor sensor;
    
    public Certificate( Sensor sensor ){
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
        String formattedUnit = "days";
        int unitWidth = g.getFontMetrics().stringWidth( formattedUnit );
        g.drawString( formattedUnit, 30-(unitWidth/2),50 );

        
        g.drawString( sensor.getTitle(), 64, 20 );
        
        g.drawString( sensor.getDescription() + "", 64, 40 ); 
    }

    @Override
    public void analyze() {
        try {
            Long expires = getExpireDays(new URL(sensor.getTitle()));
            sensor.setValue( expires );
        } catch (Exception e) {
            sensor.setValue( null );
        }
    }
    

    /**
     * Returns the date for which the first certificate expires
     * 
     * @param url
     * @return
     * @throws IOException
     */
    public static Date getExpireDate(URL url) throws IOException {
        if (!url.getProtocol().equalsIgnoreCase("https")) {
            throw new IllegalArgumentException("URL is not secure: " + url.getProtocol());
        }
        // Create the client socket
        int port = url.getPort() == -1 ? url.getDefaultPort() : url.getPort();
        String hostname = url.getHost();
        SSLSocketFactory factory = HttpsURLConnection.getDefaultSSLSocketFactory();
        SSLSocket socket = (SSLSocket) factory.createSocket(hostname, port);

        // Connect to the server
        socket.startHandshake();

        // Close the socket
        socket.close();

        // Retrieve the server's certificate chain
        java.security.cert.Certificate[] serverCerts = socket.getSession().getPeerCertificates();

        Date earliestExpires = null;

        for (java.security.cert.Certificate c : serverCerts) {
            if (c instanceof X509Certificate) {
                X509Certificate cert = (X509Certificate) c;

                /* Set expire date */
                GregorianCalendar expires = new GregorianCalendar();
                expires.setTime(cert.getNotAfter());

                if (earliestExpires == null) {
                    earliestExpires = cert.getNotAfter();
                } else if (cert.getNotAfter().getTime() < earliestExpires.getTime()) {
                    earliestExpires = cert.getNotAfter();
                }
            }
        }

        return earliestExpires;
    }
    
    public static long getExpireDays( URL url ) throws IOException{
    	Date expires = getExpireDate( url );
        Long diff = expires.getTime() - System.currentTimeMillis();
        Long days = diff / (1000*60*60*24);
        return days;
    }
	
    public static Long getExpires( URL url ) throws IOException{
    	Date expires = getExpireDate( url );
	return expires.getTime() - System.currentTimeMillis();
    }
    
}