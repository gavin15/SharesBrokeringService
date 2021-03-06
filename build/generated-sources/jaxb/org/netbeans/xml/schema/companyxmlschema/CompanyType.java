//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.17 at 04:09:08 AM GMT 
//


package org.netbeans.xml.schema.companyxmlschema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for companyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="companyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="company_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="company_symbol" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="available_shares" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;sequence>
 *           &lt;element name="share_price" type="{http://xml.netbeans.org/schema/companyXmlSchema}priceType"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "companyType", propOrder = {
    "companyName",
    "companySymbol",
    "availableShares",
    "sharePrice"
})
public class CompanyType {

    @XmlElement(name = "company_name", required = true)
    protected String companyName;
    @XmlElement(name = "company_symbol", required = true)
    protected String companySymbol;
    @XmlElement(name = "available_shares")
    protected int availableShares;
    @XmlElement(name = "share_price", required = true)
    protected PriceType sharePrice;

    /**
     * Gets the value of the companyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the value of the companyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyName(String value) {
        this.companyName = value;
    }

    /**
     * Gets the value of the companySymbol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanySymbol() {
        return companySymbol;
    }

    /**
     * Sets the value of the companySymbol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanySymbol(String value) {
        this.companySymbol = value;
    }

    /**
     * Gets the value of the availableShares property.
     * 
     */
    public int getAvailableShares() {
        return availableShares;
    }

    /**
     * Sets the value of the availableShares property.
     * 
     */
    public void setAvailableShares(int value) {
        this.availableShares = value;
    }

    /**
     * Gets the value of the sharePrice property.
     * 
     * @return
     *     possible object is
     *     {@link PriceType }
     *     
     */
    public PriceType getSharePrice() {
        return sharePrice;
    }

    /**
     * Sets the value of the sharePrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceType }
     *     
     */
    public void setSharePrice(PriceType value) {
        this.sharePrice = value;
    }

}
