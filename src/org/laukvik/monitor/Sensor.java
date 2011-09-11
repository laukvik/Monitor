/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author morten
 */
@Entity
@Table(name = "sensor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sensor.findAll", query = "SELECT s FROM Sensor s"),
    @NamedQuery(name = "Sensor.findBySensorid", query = "SELECT s FROM Sensor s WHERE s.sensorid = :sensorid"),
    @NamedQuery(name = "Sensor.findByTitle", query = "SELECT s FROM Sensor s WHERE s.title = :title"),
    @NamedQuery(name = "Sensor.findByDescription", query = "SELECT s FROM Sensor s WHERE s.description = :description"),
    @NamedQuery(name = "Sensor.findByClassname", query = "SELECT s FROM Sensor s WHERE s.classname = :classname"),
    @NamedQuery(name = "Sensor.findBySensorGroupid", query = "SELECT s FROM Sensor s WHERE s.sensorgroupid = :sensorgroupid")
    }
    )
    
public class Sensor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sensorid")
    private Integer sensorid;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "classname")
    private String classname;
    @JoinColumn(name = "sensorgroupid", referencedColumnName = "sensorgroupid")
    @ManyToOne(optional = false)
    private SensorGroup sensorgroupid;

    
    /**
     * 
     */
    transient private Analyzer analyzer;
    transient private Timer timer;
    transient private boolean isCompleted;
    transient private Stack<SensorListener> listeners;
    transient private Long value;

    public Sensor() {
        this.listeners = new Stack<SensorListener>();
    }

    public Sensor(Integer sensorid) {
        this();
        this.sensorid = sensorid;
    }

    public Sensor(Integer sensorid, String classname) {
        this();
        this.sensorid = sensorid;
        this.classname = classname;
    }
    
    public void paint( Graphics2D g, int width, int height ){
        analyzer.paint( g, width, height );
    };
    
    public void addSensorListener( SensorListener l ){
        listeners.add( l );
    }
    
    public void removeSensorListener( SensorListener l ){
        listeners.remove( l );
    }
    
    public void fireSensorChanged( int fromStatus, int toStatus ){
        SensorEvent se = new SensorEvent(fromStatus,toStatus,this); 
        for (SensorListener l : listeners){
            l.statusChanged( se );
        }
    }
    
    public void fireValueChanged(){
        SensorEvent se = new SensorEvent(0,0,this); 
        for (SensorListener l : listeners){
            l.statusChanged( se );
        }
    }
    
    private void createAnalyzer(){
        this.analyzer = new HostAnalyzer(this);
    }
    
    public void start(){
        createAnalyzer();
        timer = new Timer();
        long initialDelaySeconds = 0;
        long delaySeconds = 1;
        isCompleted = true;
        timer.schedule( new ScheduledTask(),  initialDelaySeconds, delaySeconds*1000 );
    }
    
    public void stop(){
        timer.cancel();
    }
    
    private class ScheduledTask extends TimerTask {

        @Override
        public void run() {
            /* Make sure that we wait until last run has finished */
            if (isCompleted) {
                isCompleted = false;

                analyzer.run();

                /* Set status to completed after running */
                isCompleted = true;
            }
        }

    }

    public Integer getSensorid() {
        return sensorid;
    }

    public void setSensorid(Integer sensorid) {
        this.sensorid = sensorid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public SensorGroup getSensorgroupid() {
        return sensorgroupid;
    }

    public void setSensorgroupid(SensorGroup sensorgroupid) {
        this.sensorgroupid = sensorgroupid;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sensorid != null ? sensorid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sensor)) {
            return false;
        }
        Sensor other = (Sensor) object;
        if ((this.sensorid == null && other.sensorid != null) || (this.sensorid != null && !this.sensorid.equals(other.sensorid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.laukvik.monitor.Sensor[ sensorid=" + sensorid + " ]";
    }
    
}
