/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MonitorApp.java
 *
 * Created on Sep 6, 2011, 9:24:46 PM
 */
package org.laukvik.monitor;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

/**
 *
 * @author morten
 */
public class MonitorApp extends javax.swing.JFrame implements SensorListener {

    List<SensorGroup>groups;
    SensorTable table;
    JTabbedPane tabbedPane;
    SensorService sm;
    
    /** Creates new form MonitorApp */
    public MonitorApp() {
        initComponents();
        setSize( 400, 400 );
        sm = new SensorService();
        groups = sm.listGroups();

        for (SensorGroup g : groups){
            for (Sensor s : g.getSensorList()){
                s.addSensorListener( this );
            }
        }
        
        tabbedPane = new JTabbedPane();
        for (SensorGroup group : groups){
            tabbedPane.addTab( group.getTitle(), new SensorGroupPanel(group) );
        }
        
        
        
        setLayout( new BorderLayout() );
        add( tabbedPane );
        pack();
        setVisible( true );
    }

    @Override
    public void statusChanged(SensorEvent evt) {
        System.out.println( "SensorChagned: " + evt.sensor.getValue() );
        sm.createHistory( evt.sensor );
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//        MonitorPU mpu;
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MonitorApp().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
