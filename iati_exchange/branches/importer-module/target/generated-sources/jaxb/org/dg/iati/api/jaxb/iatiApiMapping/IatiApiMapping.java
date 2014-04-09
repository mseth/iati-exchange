//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.03.26 at 04:30:56 PM EET 
//


package org.dg.iati.api.jaxb.iatiApiMapping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mapping-name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}datasource"/>
 *         &lt;element ref="{}generated-datetime"/>
 *         &lt;element ref="{}iati-activity"/>
 *         &lt;element ref="{}global-query"/>
 *         &lt;element ref="{}mapping"/>
 *         &lt;element ref="{}global-settings"/>
 *         &lt;element name="csv-mapping-filename" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "mappingName",
    "datasource",
    "generatedDatetime",
    "iatiActivity",
    "globalQuery",
    "mapping",
    "globalSettings",
    "csvMappingFilename"
})
@XmlRootElement(name = "iati-api-mapping")
public class IatiApiMapping {

    @XmlElement(name = "mapping-name", required = true)
    protected String mappingName;
    @XmlElement(required = true)
    protected Datasource datasource;
    @XmlElement(name = "generated-datetime", required = true)
    protected TypeType generatedDatetime;
    @XmlElement(name = "iati-activity", required = true)
    protected TypeType iatiActivity;
    @XmlElement(name = "global-query", required = true)
    protected TypeType globalQuery;
    @XmlElement(required = true)
    protected Mapping mapping;
    @XmlElement(name = "global-settings", required = true)
    protected GlobalSettings globalSettings;
    @XmlElement(name = "csv-mapping-filename", required = true)
    protected String csvMappingFilename;

    /**
     * Gets the value of the mappingName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMappingName() {
        return mappingName;
    }

    /**
     * Sets the value of the mappingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMappingName(String value) {
        this.mappingName = value;
    }

    /**
     * Gets the value of the datasource property.
     * 
     * @return
     *     possible object is
     *     {@link Datasource }
     *     
     */
    public Datasource getDatasource() {
        return datasource;
    }

    /**
     * Sets the value of the datasource property.
     * 
     * @param value
     *     allowed object is
     *     {@link Datasource }
     *     
     */
    public void setDatasource(Datasource value) {
        this.datasource = value;
    }

    /**
     * Gets the value of the generatedDatetime property.
     * 
     * @return
     *     possible object is
     *     {@link TypeType }
     *     
     */
    public TypeType getGeneratedDatetime() {
        return generatedDatetime;
    }

    /**
     * Sets the value of the generatedDatetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeType }
     *     
     */
    public void setGeneratedDatetime(TypeType value) {
        this.generatedDatetime = value;
    }

    /**
     * Gets the value of the iatiActivity property.
     * 
     * @return
     *     possible object is
     *     {@link TypeType }
     *     
     */
    public TypeType getIatiActivity() {
        return iatiActivity;
    }

    /**
     * Sets the value of the iatiActivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeType }
     *     
     */
    public void setIatiActivity(TypeType value) {
        this.iatiActivity = value;
    }

    /**
     * Gets the value of the globalQuery property.
     * 
     * @return
     *     possible object is
     *     {@link TypeType }
     *     
     */
    public TypeType getGlobalQuery() {
        return globalQuery;
    }

    /**
     * Sets the value of the globalQuery property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeType }
     *     
     */
    public void setGlobalQuery(TypeType value) {
        this.globalQuery = value;
    }

    /**
     * Gets the value of the mapping property.
     * 
     * @return
     *     possible object is
     *     {@link Mapping }
     *     
     */
    public Mapping getMapping() {
        return mapping;
    }

    /**
     * Sets the value of the mapping property.
     * 
     * @param value
     *     allowed object is
     *     {@link Mapping }
     *     
     */
    public void setMapping(Mapping value) {
        this.mapping = value;
    }

    /**
     * Gets the value of the globalSettings property.
     * 
     * @return
     *     possible object is
     *     {@link GlobalSettings }
     *     
     */
    public GlobalSettings getGlobalSettings() {
        return globalSettings;
    }

    /**
     * Sets the value of the globalSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link GlobalSettings }
     *     
     */
    public void setGlobalSettings(GlobalSettings value) {
        this.globalSettings = value;
    }

    /**
     * Gets the value of the csvMappingFilename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCsvMappingFilename() {
        return csvMappingFilename;
    }

    /**
     * Sets the value of the csvMappingFilename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCsvMappingFilename(String value) {
        this.csvMappingFilename = value;
    }

}
