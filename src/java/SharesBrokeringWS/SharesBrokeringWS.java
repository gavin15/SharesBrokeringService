/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SharesBrokeringWS;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.WebServiceRef;
import modulewebservices.CurrencyConversionWSService;
import modulewebservices.IOException_Exception;
import modulewebservices.MalformedURLException_Exception;
import modulewebservices.ProtocolException_Exception;
import org.netbeans.xml.schema.companyxmlschema.CompanyShares;
import org.netbeans.xml.schema.companyxmlschema.CompanyType;
import userws.FileNotFoundException_Exception;
import userws.UserWS_Service;

/**
 *
 * @author gavin
 */
@WebService(serviceName = "SharesBrokeringWS")
public class SharesBrokeringWS {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_13797/UserService/UserWS.wsdl")
    private UserWS_Service service_1;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_13797/CurrencyConvertor/CurrencyConversionWSService.wsdl")
    private CurrencyConversionWSService service;

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    @WebMethod(operationName = "ListCompanies")
    public List<CompanyType> ListCompanies()
    {
        CompanyShares companyList= new CompanyShares();
        List<CompanyType> companies=null;
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(companyList.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            companyList = (CompanyShares) unmarshaller.unmarshal(new java.io.File("test.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        return companyList.getCompany();
    }

    @WebMethod(operationName = "GetCurrencyCodes")
    public java.util.List<java.lang.String> getCurrencyCodes() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        modulewebservices.CurrencyConversionWS port = service.getCurrencyConversionWSPort();
        return port.getCurrencyCodes();
    }
    
    @WebMethod(operationName = "BuyShare")
    public List<CompanyType> BuyShare(@WebParam(name = "Symbol") String company, @WebParam(name = "shareQty") String qty, @WebParam(name = "user") String user) throws FileNotFoundException, FileNotFoundException_Exception
    {
        int shares = Integer.valueOf(qty);
        CompanyShares companyList= new CompanyShares();
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(companyList.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            companyList = (CompanyShares) unmarshaller.unmarshal(new java.io.File("test.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        int i =0;
        for (i= 0; i < companyList.getCompany().size(); i++) {
            if (companyList.getCompany().get(i).getCompanySymbol().equalsIgnoreCase(company.trim())){
                companyList.getCompany().get(i).setAvailableShares(companyList.getCompany().get(i).getAvailableShares()-shares);
                companyList.getCompany().get(i).getSharePrice().setLastUpdate(dateFormat.format(date));
                break;
            }
        }
        
        buyUserShare(user, companyList.getCompany().get(i).getCompanySymbol(), companyList.getCompany().get(i).getCompanyName(), shares);
        
        try {            
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(companyList.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //marshaller.marshal(companyList, System.out);
            OutputStream os = new FileOutputStream( "test.xml" );
            marshaller.marshal( companyList, os );
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        
        return companyList.getCompany();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "GetTotal")
    public String GetTotal(@WebParam(name = "shareQty") String shareQty,@WebParam(name = "sharePrice") String sharePrice, @WebParam(name = "currency1") String currency1, @WebParam(name = "currency2") String currency2) throws IOException_Exception, MalformedURLException_Exception, ProtocolException_Exception {
        int shareQuantity = Integer.valueOf(shareQty);
        double sharesPrice = Integer.valueOf(sharePrice);
        double totalAmount = Double.valueOf(shareQuantity * sharesPrice);
        
        double conversionRate = Double.valueOf( getConversionRate(currency1,currency2));
        double newTotalAmount = totalAmount*conversionRate;
        return String.valueOf(newTotalAmount);
    }

    private String getConversionRate(java.lang.String arg0, java.lang.String arg1) throws IOException_Exception, MalformedURLException_Exception, ProtocolException_Exception {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        modulewebservices.CurrencyConversionWS port = service.getCurrencyConversionWSPort();
        return port.getConversionRate(arg0, arg1);
    }    
    
    @WebMethod(operationName = "CreateUser")
    public void createUser(java.lang.String userName, java.lang.String password, java.lang.String currency) throws FileNotFoundException_Exception {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        userws.UserWS port = service_1.getUserWSPort();
        port.createUser(userName, password, currency);
    }

    @WebMethod(operationName = "ListUserShares")
    public java.util.List<org.netbeans.xml.schema.userxmlschema.CompanyType> listUserShares(java.lang.String userName) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        userws.UserWS port = service_1.getUserWSPort();
        return port.listUserShares(userName);
    }

    private String buyUserShare(java.lang.String userName, java.lang.String companySymbol, java.lang.String companyName, int noOfShares) throws FileNotFoundException_Exception {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        userws.UserWS port = service_1.getUserWSPort();
        return port.buyUserShare(userName, companySymbol, companyName, noOfShares);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "SellShare")
    public List<CompanyType> SellShare(@WebParam(name = "userName") String userName, @WebParam(name = "companySymbol") String companySymbol, @WebParam(name = "shareQty") String shareQty) throws FileNotFoundException, FileNotFoundException_Exception {
         int shares = Integer.valueOf(shareQty);
        CompanyShares companyList= new CompanyShares();
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(companyList.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            companyList = (CompanyShares) unmarshaller.unmarshal(new java.io.File("test.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        int i =0;
        for (i= 0; i < companyList.getCompany().size(); i++) {
            if (companyList.getCompany().get(i).getCompanySymbol().equalsIgnoreCase(companySymbol.trim())){
                companyList.getCompany().get(i).setAvailableShares(companyList.getCompany().get(i).getAvailableShares()+shares);
                companyList.getCompany().get(i).getSharePrice().setLastUpdate(dateFormat.format(date));
                break;
            }
        }
         
        sellUserShare(userName, companyList.getCompany().get(i).getCompanyName(),companySymbol, shares);
        
        try {            
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(companyList.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //marshaller.marshal(companyList, System.out);
            OutputStream os = new FileOutputStream( "test.xml" );
            marshaller.marshal( companyList, os );
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        
        return companyList.getCompany();
    }

    private java.util.List<org.netbeans.xml.schema.userxmlschema.CompanyType> sellUserShare(java.lang.String userName, java.lang.String companyName, java.lang.String companySymbol, int noOfShares) throws FileNotFoundException_Exception {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        userws.UserWS port = service_1.getUserWSPort();
        return port.sellUserShare(userName, companyName, companySymbol, noOfShares);
    }
}
