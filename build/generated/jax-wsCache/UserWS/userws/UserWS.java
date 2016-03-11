
package userws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.11-b150120.1832
 * Generated source version: 2.2
 * 
 */
@WebService(name = "UserWS", targetNamespace = "http://UserWS/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface UserWS {


    /**
     * 
     * @param password
     * @param currency
     * @param userName
     * @throws FileNotFoundException_Exception
     */
    @WebMethod(operationName = "CreateUser")
    @RequestWrapper(localName = "CreateUser", targetNamespace = "http://UserWS/", className = "userws.CreateUser")
    @ResponseWrapper(localName = "CreateUserResponse", targetNamespace = "http://UserWS/", className = "userws.CreateUserResponse")
    @Action(input = "http://UserWS/UserWS/CreateUserRequest", output = "http://UserWS/UserWS/CreateUserResponse", fault = {
        @FaultAction(className = FileNotFoundException_Exception.class, value = "http://UserWS/UserWS/CreateUser/Fault/FileNotFoundException")
    })
    public void createUser(
        @WebParam(name = "userName", targetNamespace = "")
        String userName,
        @WebParam(name = "password", targetNamespace = "")
        String password,
        @WebParam(name = "currency", targetNamespace = "")
        String currency)
        throws FileNotFoundException_Exception
    ;

    /**
     * 
     * @param name
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "hello", targetNamespace = "http://UserWS/", className = "userws.Hello")
    @ResponseWrapper(localName = "helloResponse", targetNamespace = "http://UserWS/", className = "userws.HelloResponse")
    @Action(input = "http://UserWS/UserWS/helloRequest", output = "http://UserWS/UserWS/helloResponse")
    public String hello(
        @WebParam(name = "name", targetNamespace = "")
        String name);

}
