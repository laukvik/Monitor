/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author morten
 */
public class SensorManager {

    @PersistenceUnit
    EntityManagerFactory emf;
    EntityManager em;
    
    
    public SensorManager() {
        emf = Persistence.createEntityManagerFactory("MonitorPU");
        emf.createEntityManager();
        assert emf != null;  //Make sure injection went through correctly.
        em = emf.createEntityManager();
    }
    
    public List<SensorEnabled> listSensorEnabled( SensorGroup sensorGroup ){
        List<SensorEnabled> items = new ArrayList<SensorEnabled>();
        for (Sensor s: listSensors(sensorGroup)){
            items.add( new HostSensor(s) );
        }
        return items;
    }
    
    public List<Sensor> listSensors( SensorGroup sensorGroup ){   
        List<Sensor> sensors = em.createNamedQuery("Sensor.findBySensorGroupid").setParameter( "sensorgroupid", sensorGroup ).getResultList();
        return sensors;
    }
    
    public List<SensorGroup> listGroups(){   
        List<SensorGroup> sensors = em.createNamedQuery("SensorGroup.findAll").getResultList();
        return sensors;
    }
    
    public void save( Sensor sensor ){
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge( sensor );
        em.getTransaction().commit();        
    }
    
    public void add( Sensor sensor ){
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist( sensor );
        em.getTransaction().commit();        
    }
    
    public void remove( Sensor sensor ){
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove( sensor );
        em.getTransaction().commit();        
    }
    
    public static void main( String [] args ){
        SensorManager sm = new SensorManager();

        for (SensorGroup g : sm.listGroups()){
            System.out.println( "Group: " + g.getTitle() );
            
            for (Sensor s : g.getSensorCollection()){
                System.out.println( "\tSensor: " + s.getTitle() );
            }
//            for (Sensor s : sm.listSensors(g)){
//                System.out.println( "\tSensor: " + s.getTitle() );
//                s.setDescription( "Endret innhold fra kode" );
//                sm.save( s );
//            }
        }
        
    
        
    }
    
}
