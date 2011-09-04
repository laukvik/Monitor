/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

/**
 *
 * @author morten
 */
public interface Sensor {
    
    public void run();
    public void paintIcon();
    public void paintText();
    public float getPercent();
    
    public Object getValue();
    
}
