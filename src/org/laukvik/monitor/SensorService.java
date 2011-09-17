/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import org.laukvik.monitor.sensor.WebSite;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author morten
 */
public class SensorService {

    @PersistenceUnit
    EntityManagerFactory emf;
    EntityManager em;
    
    
    public SensorService() {
        emf = Persistence.createEntityManagerFactory("MonitorPU");
        emf.createEntityManager();
        assert emf != null;  //Make sure injection went through correctly.
        em = emf.createEntityManager();
    }
    
//    public List<Analyzer> listSensorEnabled( SensorGroup sensorGroup ){
//        List<Analyzer> items = new ArrayList<Analyzer>();
//        for (Sensor s: listSensors(sensorGroup)){
//            items.add( new WebSite(s) );
//        }
//        return items;
//    }
    
    public List<Sensor> listSensors( SensorGroup sensorGroup ){   
        List<Sensor> sensors = em.createNamedQuery("Sensor.findBySensorGroupid").setParameter( "sensorgroupid", sensorGroup ).getResultList();
        return sensors;
    }
    
    public List<SensorGroup> listGroups(){   
        List<SensorGroup> sensors = em.createNamedQuery("SensorGroup.findAll").getResultList();
        return sensors;
    }
    
    /**
     * Creates a new history entry the specified sensor
     * 
     * @param sensor 
     */
    public void createHistory( Sensor sensor ){
        em = emf.createEntityManager();
        em.getTransaction().begin();
        
        History h = new History();
        h.setSensorid(  sensor );
        h.setCreated( new Date() ); 
        h.setValue( sensor.getValue() );
                
        em.persist( h );
        em.getTransaction().commit();     
    }

    
    public void add( Sensor sensor ){
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist( sensor );
        em.getTransaction().commit();        
    }
    
    public void update( Sensor sensor ){
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge( sensor );
        em.getTransaction().commit();        
    }
    
    public void remove( Sensor sensor ){
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove( sensor );
        em.getTransaction().commit();        
    }
    
    public static void main( String [] args ){
        SensorService sm = new SensorService();

        for (SensorGroup g : sm.listGroups()){
            System.out.println( "Group: " + g.getTitle() );
            
            for (Sensor s : g.getSensorList()){
                System.out.println( "\tSensor: " + s.getTitle() );

                for (History h : s.getHistoryList()){
                    System.out.println( "\t\tHistory: " + h.getCreated() );
                }   
            }
        }    
        
    }
    
}