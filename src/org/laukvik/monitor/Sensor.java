/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.List;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author morten
 */
@Entity
@Table(name = "Sensor")
@NamedQueries({
    @NamedQuery(name = "Sensor.findAll", query = "SELECT s FROM Sensor s"),
    @NamedQuery(name = "Sensor.findBySensorid", query = "SELECT s FROM Sensor s WHERE s.sensorid = :sensorid"),
    @NamedQuery(name = "Sensor.findByTitle", query = "SELECT s FROM Sensor s WHERE s.title = :title"),
    @NamedQuery(name = "Sensor.findByDescription", query = "SELECT s FROM Sensor s WHERE s.description = :description"),
    @NamedQuery(name = "Sensor.findBySensorGroupid", query = "SELECT s FROM Sensor s WHERE s.sensorgroupid = :sensorgroupid"),
    @NamedQuery(name = "Sensor.findByClassname", query = "SELECT s FROM Sensor s WHERE s.classname = :classname")})

public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "sensorid", nullable = false)
    private Long sensorid;
    @Column(name = "title", length = 256)
    private String title;
    @Basic(optional = true)
    @Column(name = "description", length = 1024)
    private String description;
    @Basic(optional = false)
    @Column(name = "classname", nullable = false, length = 1024)
    private String classname;
    @JoinColumn(name = "sensorgroupid", referencedColumnName = "sensorgroupid", nullable = false)
    @ManyToOne(optional = false)
    private SensorGroup sensorgroupid;
    @Basic(optional = true)
    @Column(name = "numberValue")
    private Long value;
    @Column(name="delay")
    Long delay;
    @OneToMany(targetEntity=Setting.class, cascade = CascadeType.ALL, mappedBy = "sensorid")
    private List<Setting> settingList;
    @Transient
    private List<History> historyList;

    public Sensor() {
        this.historyList = new Stack<History>();
        this.settingList = new Stack<Setting>();
        this.listeners = new Stack<SensorListener>();
    }

    public Sensor(Long sensorid) {
        this();
        this.sensorid = sensorid;
    }

    public Sensor(Long sensorid, String classname) {
        this();
        this.sensorid = sensorid;
        this.classname = classname;
    }

    public Long getSensorid() {
        return sensorid;
    }

    public void setSensorid(Long sensorid) {
        this.sensorid = sensorid;
    }
    
    public void add( Setting setting ){
        setting.setSensorID( this );
        settingList.add(setting);
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

    public void setValue(Long value) {
        boolean fireChanged = true;
        Long old = this.value;
        if (this.value == value){
            
        } else {
            this.value = value;
        }
        if (fireChanged){
            fireValueChanged( old, value );
        }
    }

    public Long getValue() {
        return value;
    }
    
    public SensorGroup getSensorgroupid() {
        return sensorgroupid;
    }

    public void setSensorgroupid(SensorGroup sensorgroupid) {
        this.sensorgroupid = sensorgroupid;
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

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }
    
    transient private Analyzer analyzer;
    transient private Timer timer;
    transient private boolean isCompleted;
    transient private Stack<SensorListener> listeners;
    
    public void paint(  Graphics g, int width, int height   ){
//        Graphics g  = component.getGraphics();
        analyzer.paint( g, width, height  );
    };
    
    public void addSensorListener( SensorListener l ){
        listeners.add( l );
    }
    
    public void removeSensorListener( SensorListener l ){
        listeners.remove( l );
    }
    
    public void fireValueChanged( Long oldValue, Long newValue ){
        SensorEvent se = new SensorEvent( oldValue, newValue, this ); 
        for (SensorListener l : listeners){
            l.statusChanged( se );
        }
    }
    
    private void createAnalyzer() throws PluginException{
//        this.analyzer = new HostAnalyzer(this);
        try{
            Class cl = Class.forName( getClassname() );
//            System.out.println( "Sensor: Class=" + getClassname() );
            // get the constructor  
            java.lang.reflect.Constructor constructor = cl.getConstructor( Sensor.class  );
//            System.out.println( "Sensor: constructor=" + constructor.getName() );
            // create an instance     
            Object invoker = constructor.newInstance( this );
//            System.out.println( "Sensor: invoker=" + invoker.toString() );
            this.analyzer = (Analyzer)invoker;
        } catch(Exception e){
            throw new PluginException( this );
        }
        
    }
    
    public void start(){
        try {
            createAnalyzer();
        } catch (PluginException ex) {
            this.analyzer = new EmptyAnalyzer( this );
        }
        timer = new Timer();
        long initialDelaySeconds = 0;
        long delaySeconds = getDelay();
        isCompleted = true;
        timer.schedule( new ScheduledTask(),  initialDelaySeconds, delaySeconds*1000 );
    }
    
    public void stop(){
        timer.cancel();
    }
    
    private class ScheduledTask extends TimerTask {

        @Override
        public void run() {
            /* Make sure that we wait until last analyze has finished */
            if (isCompleted) {
                isCompleted = false;

                analyzer.analyze();

                /* Set status to completed after running */
                isCompleted = true;
            }
        }

    }

    @XmlTransient
    public List<History> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<History> historyList) {
        this.historyList = historyList;
    }
    
    public Setting getSetting( String name ){
        for (Setting s : settingList){
            String n = s.getName();
            if (n == null){
            } else if (n.equalsIgnoreCase( name )){
                return s;
            }
        }
        return null;
    }
    
    

}