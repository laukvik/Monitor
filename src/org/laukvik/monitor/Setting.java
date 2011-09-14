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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author morten
 */
@Entity
@Table(name = "setting")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Setting.findAll", query = "SELECT s FROM Setting s"),
    @NamedQuery(name = "Setting.findBySettingid", query = "SELECT s FROM Setting s WHERE s.settingid = :settingid"),
    @NamedQuery(name = "Setting.findByTitle", query = "SELECT s FROM Setting s WHERE s.title = :title"),
    @NamedQuery(name = "Setting.findByValue", query = "SELECT s FROM Setting s WHERE s.value = :value")})
public class Setting implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "settingid")
    private Integer settingid;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Column(name = "value")
    private String value;
    @JoinColumn(name = "sensorid", referencedColumnName = "sensorid")
    @ManyToOne(optional = false)
    private Sensor sensorid;

    public Setting() {
    }

    public Setting(Integer settingid) {
        this.settingid = settingid;
    }

    public Setting(Integer settingid, String title) {
        this.settingid = settingid;
        this.title = title;
    }

    public Integer getSettingid() {
        return settingid;
    }

    public void setSettingid(Integer settingid) {
        this.settingid = settingid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Sensor getSensorid() {
        return sensorid;
    }

    public void setSensorid(Sensor sensorid) {
        this.sensorid = sensorid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (settingid != null ? settingid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Setting)) {
            return false;
        }
        Setting other = (Setting) object;
        if ((this.settingid == null && other.settingid != null) || (this.settingid != null && !this.settingid.equals(other.settingid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.laukvik.monitor.Setting[ settingid=" + settingid + " ]";
    }
    
}
