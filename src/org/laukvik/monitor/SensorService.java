/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

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

    public List<Sensor> listSensors( SensorGroup sensorGroup ){   
        List<Sensor> sensors = em.createNamedQuery("Sensor.findBySensorGroupid").setParameter( "sensorgroupid", sensorGroup ).getResultList();
        return sensors;
    }
    
    public List<SensorGroup> listGroups(){   
        List<SensorGroup> sensors = em.createNamedQuery("SensorGroup.findAll").getResultList();
        return sensors;
    }
    
    public List<Setting> listSettings(){   
        List<Setting> settings = em.createNamedQuery("Setting.findAll").getResultList();
        return settings;
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
        h.setCreated( new java.util.Date() ); 
        h.setValue( sensor.getValue() );
                
        em.persist( h );
        em.getTransaction().commit();     
    }

    public void addGroup( SensorGroup group ){
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist( group );
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
        SensorService srv = new SensorService();
        
        srv.listSettings();
        
            SensorGroup group = new SensorGroup();
            group.setTitle( "WebSites" );
            group.setDescription( "" );
            srv.addGroup( group );
            
            /* First example */
            Sensor s1 = new Sensor();
            s1.setTitle( "Google" );
            s1.setDescription( "Connects to Googles webpage and checks its response time" );
            s1.setClassname( "org.laukvik.monitor.sensor.WebSite" );
            s1.setDelay( 60L );
            s1.setSensorgroupid( group );
            s1.add( new Setting( "url", "http://www.google.com" ) );
            srv.add( s1 );
        
            /* First example */
            Sensor s2 = new Sensor();
            s2.setTitle( "Microsoft" );
            s2.setDescription( "Connects to Microsofts webpage and checks its response time" );
            s2.setClassname( "org.laukvik.monitor.sensor.WebSite" );
            s2.setDelay( 60L );
            s2.setSensorgroupid( group );
            s2.add( new Setting( "url", "http://www.google.com" ) );
            srv.add( s2 );
            
            /* First example */
            Sensor s3 = new Sensor();
            s3.setTitle( "Oracle" );
            s3.setDescription( "Connects to Oracles webpage and checks its response time" );
            s3.setClassname( "org.laukvik.monitor.sensor.WebSite" );
            s3.setDelay( 60L );
            s3.setSensorgroupid( group );
            s3.add( new Setting( "url", "http://www.oracle.com" ) );
            srv.add( s3 );

        for (SensorGroup g : srv.listGroups()){
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