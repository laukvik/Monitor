/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

/**
 *
 * @author morten
 */
public class PluginException extends java.lang.Exception{

    public PluginException( Sensor sensor ){
        super( "Could not create sensor with class: " + sensor.getClassname() );
    }

}