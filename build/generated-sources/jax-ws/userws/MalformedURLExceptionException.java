
package userws;

import javax.xml.ws.WebFault;
import modulewebservices.MalformedURLException;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.11-b150120.1832
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "MalformedURLException", targetNamespace = "http://ModuleWebServices/")
public class MalformedURLExceptionException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private MalformedURLException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public MalformedURLExceptionException(String message, MalformedURLException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public MalformedURLExceptionException(String message, MalformedURLException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: modulewebservices.MalformedURLException
     */
    public MalformedURLException getFaultInfo() {
        return faultInfo;
    }

}
