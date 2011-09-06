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
public class HostSensor extends AbstractSensor {

    public HostSensor( Sensor sensor ) {
        super( sensor );
    }

    @Override
    public void paint(Graphics2D g) {
            g.drawString( sensor.getTitle(), 5, 5 );
    }


}