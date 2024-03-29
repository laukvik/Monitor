/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author morten
 */
@Entity
@Table(name = "SensorGroup")
@NamedQueries({
    @NamedQuery(name = "SensorGroup.findAll", query = "SELECT s FROM SensorGroup s"),
    @NamedQuery(name = "SensorGroup.findBySensorgroupid", query = "SELECT s FROM SensorGroup s WHERE s.sensorgroupid = :sensorgroupid"),
    @NamedQuery(name = "SensorGroup.findByTitle", query = "SELECT s FROM SensorGroup s WHERE s.title = :title"),
    @NamedQuery(name = "SensorGroup.findByDescription", query = "SELECT s FROM SensorGroup s WHERE s.description = :description")})
public class SensorGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "sensorgroupid", nullable = false)
    private Long sensorgroupid;
    @Column(name = "title", length = 256)
    private String title;
    @Basic(optional = true)
    @Column(name = "description", length = 1024)
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sensorgroupid")
    private List<Sensor> sensorList;

    public SensorGroup(){
    }

    public SensorGroup(Long sensorgroupid) {
        this.sensorgroupid = sensorgroupid;
    }

    public Long getSensorgroupid() {
        return sensorgroupid;
    }

    public void setSensorgroupid(Long sensorgroupid) {
        this.sensorgroupid = sensorgroupid;
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

    @XmlTransient
    public List<Sensor> getSensorList() {
        return sensorList;
    }

    public void setSensorList(List<Sensor> sensorList) {
        this.sensorList = sensorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sensorgroupid != null ? sensorgroupid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SensorGroup)) {
            return false;
        }
        SensorGroup other = (SensorGroup) object;
        if ((this.sensorgroupid == null && other.sensorgroupid != null) || (this.sensorgroupid != null && !this.sensorgroupid.equals(other.sensorgroupid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.laukvik.monitor.SensorGroup[ sensorgroupid=" + sensorgroupid + " ]";
    }
    
}
