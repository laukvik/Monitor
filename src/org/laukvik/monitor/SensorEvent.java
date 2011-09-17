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

    Sensor sensor;
    Long oldValue, newValue;
    
    public SensorEvent( Long oldValue, Long newValue, Sensor sensor ){
        this.sensor = sensor;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

//    public int getNewStatus() {
//        return newValue;
//    }
//
//    public int getOldStatus() {
//        return oldValue;
//    }

    public Long getNewValue() {
        return newValue;
    }

    public Long getOldValue() {
        return oldValue;
    }

    public Sensor getSensor() {
        return sensor;
    }

}