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
public interface Analyzer {

    public void paint( Graphics2D g, int width, int height );
    public void run();
    
}