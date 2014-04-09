//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.03.26 at 04:30:56 PM EET 
//


package org.dg.iati.api.jaxb.iatiImportRules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Data type for an element that refers to a business object that
 * 				can have unique identifier
 * 			
 * 
 * <p>Java class for test complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="test">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="term1" type="{}action"/>
 *         &lt;element name="term2" type="{}action"/>
 *         &lt;element name="comparison" type="{}type-comparison"/>
 *         &lt;element name="on-true" type="{}action" maxOccurs="unbounded"/>
 *         &lt;element name="on-false" type="{}action" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "test", propOrder = {
    "content"
})
public class Test {

    @XmlElementRefs({
        @XmlElementRef(name = "term1", type = JAXBElement.class),
        @XmlElementRef(name = "term2", type = JAXBElement.class),
        @XmlElementRef(name = "on-true", type = JAXBElement.class),
        @XmlElementRef(name = "on-false", type = JAXBElement.class),
        @XmlElementRef(name = "comparison", type = JAXBElement.class)
    })
    @XmlMixed
    protected List<Serializable> content;

    /**
     * 
     * 				Data type for an element that refers to a business object that
     * 				can have unique identifier
     * 			Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Action }{@code >}
     * {@link JAXBElement }{@code <}{@link Action }{@code >}
     * {@link String }
     * {@link JAXBElement }{@code <}{@link Action }{@code >}
     * {@link JAXBElement }{@code <}{@link Action }{@code >}
     * {@link JAXBElement }{@code <}{@link TypeComparison }{@code >}
     * 
     * 
     */
    public List<Serializable> getContent() {
        if (content == null) {
            content = new ArrayList<Serializable>();
        }
        return this.content;
    }

}