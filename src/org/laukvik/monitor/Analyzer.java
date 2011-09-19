/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author morten
 */
public interface Analyzer {

    public void paint( Graphics g, int width, int height );
    public void run();
    
}