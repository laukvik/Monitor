/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import java.io.Serializable;
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

/**
 *
 * @author morten
 */
@Entity
@Table(name = "Setting")
@NamedQueries({
    @NamedQuery(name = "Setting.findAll", query = "SELECT s FROM Setting s")})
public class Setting implements Serializable {
 
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "settingid", nullable = false)
    private Long settingid;

    @JoinColumn(name = "sensorid", referencedColumnName = "sensorid", nullable = false)
    @ManyToOne(optional = false)
    private Sensor sensorid;
    @Column(name="name")
    private String name;
    @Column(name="textValue")
    private String value;

    public Setting() {
    }
    
    public Setting( String name, String value ) {
        this.name = name;
        this.value = value;
    }

    public void setSensorID(Sensor sensorid) {
        this.sensorid = sensorid;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}