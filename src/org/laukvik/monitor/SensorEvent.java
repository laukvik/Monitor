/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

/**
 *
 * @author morten
 */
public class SensorEvent {
    
    public static int DOWN = -1;
    public static int NORMAL = 0;
    public static int WARNING = 1;
    public static int CRITICAL = 2;

    SensorEnabled sensor;
    int oldStatus, newStatus;
    
    public SensorEvent( int oldStatus, int newStatus, SensorEnabled sensor ){
        this.sensor = sensor;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    public int getNewStatus() {
        return newStatus;
    }

    public int getOldStatus() {
        return oldStatus;
    }

    public SensorEnabled getSensor() {
        return sensor;
    }

}