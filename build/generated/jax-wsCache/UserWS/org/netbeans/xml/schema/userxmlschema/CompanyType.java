
package org.netbeans.xml.schema.userxmlschema;

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
 * &lt;complexType name="companyType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="companyName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="companySymbol" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="sharesPurchased" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="share_price" type="{http://xml.netbeans.org/schema/userXmlSchema}priceType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "companyType", propOrder = {
    "companyName",
    "companySymbol",
    "sharesPurchased",
    "sharePrice"
})
public class CompanyType {

    @XmlElement(required = true)
    protected String companyName;
    @XmlElement(required = true)
    protected String companySymbol;
    protected int sharesPurchased;
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
     * Gets the value of the sharesPurchased property.
     * 
     */
    public int getSharesPurchased() {
        return sharesPurchased;
    }

    /**
     * Sets the value of the sharesPurchased property.
     * 
     */
    public void setSharesPurchased(int value) {
        this.sharesPurchased = value;
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
