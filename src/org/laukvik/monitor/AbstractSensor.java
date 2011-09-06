/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import java.util.Stack;

/**
 *
 * @author morten
 */
public abstract class AbstractSensor implements SensorEnabled{

    Sensor sensor;
    Stack<SensorListener> listeners;
    
    public AbstractSensor( Sensor sensor ){
        this.sensor = sensor;
        this.listeners = new Stack<SensorListener>();
    }
    
    public void fireSensorChanged( int fromStatus, int toStatus ){
        for (SensorListener l : listeners){
            l.statusChanged( new SensorEvent(fromStatus,toStatus,this) );
        }
    }
    
    public void schedule( long milliseconds ){
    }
    

}
