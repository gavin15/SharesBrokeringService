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
import userws.IOExceptionException;
import userws.MalformedURLExceptionException;
import userws.ProtocolExceptionException;
import userws.UserWS_Service;

/**
 *
 * @author gavin
 */
@WebService(serviceName = "SharesBrokeringWS")
public class SharesBrokeringWS {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_13797/UserService1/UserWS.wsdl")
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

    /**
     * This method lists the companies that offer the shares for sale.
     */
    @WebMethod(operationName = "ListCompanies")
    public List<CompanyType> ListCompanies() {
        CompanyShares companyList = new CompanyShares();
        List<CompanyType> companies = companyList.getCompany();
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(companyList.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            companyList = (CompanyShares) unmarshaller.unmarshal(new java.io.File("test.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        
        // Do not display the company if the available shares are 0.
        for (int i = 0; i < companyList.getCompany().size(); i++) {
            if (companyList.getCompany().get(i).getAvailableShares()>0) {
                companies.add(companyList.getCompany().get(i));
            }
        }
        
        return companies;
    }

    /**
     * This method gets the currency codes from CurrencyConverter service.
     */
    @WebMethod(operationName = "GetCurrencyCodes")
    public java.util.List<java.lang.String> getCurrencyCodes() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        modulewebservices.CurrencyConversionWS port = service.getCurrencyConversionWSPort();
        return port.getCurrencyCodes();
    }

    /**
     * This method allows users to buy shares.
     */
    @WebMethod(operationName = "BuyShare")
    public List<CompanyType> BuyShare(@WebParam(name = "Symbol") String company, @WebParam(name = "shareQty") String qty, @WebParam(name = "user") String user) throws FileNotFoundException, FileNotFoundException_Exception, IOExceptionException, ProtocolExceptionException, MalformedURLExceptionException {
        int shares = Integer.valueOf(qty);
        CompanyShares companyList = new CompanyShares();
        // unmarshalls the file.
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

        // perform logic
        int i = 0;
        for (i = 0; i < companyList.getCompany().size(); i++) {
            if (companyList.getCompany().get(i).getCompanySymbol().equalsIgnoreCase(company.trim())) {
                companyList.getCompany().get(i).setAvailableShares(companyList.getCompany().get(i).getAvailableShares() - shares);
                companyList.getCompany().get(i).getSharePrice().setLastUpdate(dateFormat.format(date));
                break;
            }
        }

        // Adds the shares against the user who puchased.
        buyUserShare(user, companyList.getCompany().get(i).getCompanySymbol(), companyList.getCompany().get(i).getCompanyName(), shares, companyList.getCompany().get(i).getSharePrice().getCurrency(), companyList.getCompany().get(i).getSharePrice().getValue());

        // Marshals back to the file.
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(companyList.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //marshaller.marshal(companyList, System.out);
            OutputStream os = new FileOutputStream("test.xml");
            marshaller.marshal(companyList, os);
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }

        // Returns the updated list.
        return companyList.getCompany();
    }

    /**
     * Gets the total amount for the selected shares.
     */
    @WebMethod(operationName = "GetTotal")
    public String GetTotal(@WebParam(name = "shareQty") String shareQty, @WebParam(name = "sharePrice") String sharePrice, @WebParam(name = "currency1") String currency1, @WebParam(name = "currency2") String currency2) throws IOException_Exception, MalformedURLException_Exception, ProtocolException_Exception {
        int shareQuantity = Integer.valueOf(shareQty);
        double sharesPrice = Integer.valueOf(sharePrice);
        double totalAmount = Double.valueOf(shareQuantity * sharesPrice);

        double conversionRate = Double.valueOf(getConversionRate(currency1, currency2));
        double newTotalAmount = totalAmount * conversionRate;
        return String.valueOf(newTotalAmount);
    }

    private String getConversionRate(java.lang.String arg0, java.lang.String arg1) throws IOException_Exception, MalformedURLException_Exception, ProtocolException_Exception {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        modulewebservices.CurrencyConversionWS port = service.getCurrencyConversionWSPort();
        return port.getConversionRate(arg0, arg1);
    }

    /**
     * This method creates a new user and stores it.
     */
    @WebMethod(operationName = "CreateUser")
    public void createUser(java.lang.String userName, java.lang.String password, java.lang.String currency) throws FileNotFoundException_Exception {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        userws.UserWS port = service_1.getUserWSPort();
        port.createUser(userName, password, currency);
    }

    /**
     * Allows users to sell shares back to the market.
     */
    @WebMethod(operationName = "SellShare")
    public List<CompanyType> SellShare(@WebParam(name = "userName") String userName, @WebParam(name = "companySymbol") String companySymbol, @WebParam(name = "shareQty") String shareQty) throws FileNotFoundException, FileNotFoundException_Exception, ProtocolExceptionException, MalformedURLExceptionException, IOExceptionException {
        int shares = Integer.valueOf(shareQty);
        CompanyShares companyList = new CompanyShares();

        //Unmarshals the file.
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

        // Perform logic.
        int i = 0;
        for (i = 0; i < companyList.getCompany().size(); i++) {
            if (companyList.getCompany().get(i).getCompanySymbol().equalsIgnoreCase(companySymbol.trim())) {
                companyList.getCompany().get(i).setAvailableShares(companyList.getCompany().get(i).getAvailableShares() + shares);
                companyList.getCompany().get(i).getSharePrice().setLastUpdate(dateFormat.format(date));
                break;
            }
        }

        // Commit the changes to the user.
        sellUserShare(userName, companyList.getCompany().get(i).getCompanyName(), companySymbol, shares, companyList.getCompany().get(i).getSharePrice().getCurrency(), companyList.getCompany().get(i).getSharePrice().getValue());

        // Marshal back to the file.
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(companyList.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //marshaller.marshal(companyList, System.out);
            OutputStream os = new FileOutputStream("test.xml");
            marshaller.marshal(companyList, os);
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }

        //Return the updated list.
        return companyList.getCompany();
    }

    private String buyUserShare(java.lang.String userName, java.lang.String companySymbol, java.lang.String companyName, int noOfShares, java.lang.String currency, float sharePrice) throws IOExceptionException, FileNotFoundException_Exception, ProtocolExceptionException, MalformedURLExceptionException {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        userws.UserWS port = service_1.getUserWSPort();
        return port.buyUserShare(userName, companySymbol, companyName, noOfShares, currency, sharePrice);
    }

    private java.util.List<org.netbeans.xml.schema.userxmlschema.CompanyType> sellUserShare(java.lang.String userName, java.lang.String companyName, java.lang.String companySymbol, int noOfShares, java.lang.String currency, float sharePrice) throws ProtocolExceptionException, MalformedURLExceptionException, FileNotFoundException_Exception, IOExceptionException {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        userws.UserWS port = service_1.getUserWSPort();
        return port.sellUserShare(userName, companyName, companySymbol, noOfShares, currency, sharePrice);
    }

    /**
     * Get the currency of the user.
     */
    @WebMethod(operationName = "GetUserCurrency")
    public String getUserCurrency(java.lang.String userName, java.lang.String password) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        userws.UserWS port = service_1.getUserWSPort();
        return port.getUserCurrency(userName, password);
    }

    /**
     * Check if the logged in user exists.
     */
    @WebMethod(operationName = "CheckUserExists")
    public boolean checkUserExists(java.lang.String userName, java.lang.String password) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        userws.UserWS port = service_1.getUserWSPort();
        return port.checkUserExists(userName, password);
    }

    /**
     * Get the amount available for a user.
     */
    @WebMethod(operationName = "GetUserAmount")
    public String getUserAmount(java.lang.String userName) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        userws.UserWS port = service_1.getUserWSPort();
        return port.getUserAmount(userName);
    }

    /**
     * This method lists the shares owned by a user.
     */
    @WebMethod(operationName = "ListUserShares")
    public java.util.List<org.netbeans.xml.schema.userxmlschema.CompanyType> listUserShares(java.lang.String userName) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        userws.UserWS port = service_1.getUserWSPort();
        return port.listUserShares(userName);
    }
}
