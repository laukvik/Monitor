/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

/**
 *
 * @author morten
 */
public interface SensorListener {
 
    public void statusChanged( Sensor sensor, int fromStatus, int toStatus );
    
}
