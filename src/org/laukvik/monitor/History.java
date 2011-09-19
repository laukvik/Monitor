/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.monitor;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author morten
 */
@Entity
@Table(name = "History")
@NamedQueries({
    @NamedQuery(name = "History.findAll", query = "SELECT h FROM History h"),
    @NamedQuery(name = "History.findByHistoryid", query = "SELECT h FROM History h WHERE h.historyid = :historyid"),
    @NamedQuery(name = "History.findBySensorid", query = "SELECT h FROM History h WHERE h.sensorid = :sensorid"),
    @NamedQuery(name = "History.findByCreated", query = "SELECT h FROM History h WHERE h.created = :created")})
public class History implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "historyid", nullable = false)
    private Long historyid;
    
    @JoinColumn(name = "sensorid", referencedColumnName = "sensorid", nullable = false)
    @ManyToOne(optional = false)
    private Sensor sensorid;
    
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = false)
    private Date created;
    
    @Basic(optional = false)
    @Column(name="numberValue")
    private Long value;
    
    public History() { 
    }

    public History(Long historyid) {
        this.historyid = historyid;
    }

    public Long getHistoryid() {
        return historyid;
    }

    public void setHistoryid(Long historyid) {
        this.historyid = historyid;
    }

    public Sensor getSensorid() {
        return sensorid;
    }

    public void setSensorid(Sensor sensorid) {
        this.sensorid = sensorid;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historyid != null ? historyid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof History)) {
            return false;
        }
        History other = (History) object;
        if ((this.historyid == null && other.historyid != null) || (this.historyid != null && !this.historyid.equals(other.historyid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.laukvik.monitor.History[ historyid=" + historyid + " ]";
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    
}
