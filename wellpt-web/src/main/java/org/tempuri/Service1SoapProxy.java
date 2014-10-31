package org.tempuri;

public class Service1SoapProxy implements org.tempuri.Service1Soap {
  private String _endpoint = null;
  private org.tempuri.Service1Soap service1Soap = null;
  
  public Service1SoapProxy() {
    _initService1SoapProxy();
  }
  
  public Service1SoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initService1SoapProxy();
  }
  
  private void _initService1SoapProxy() {
    try {
      service1Soap = (new org.tempuri.Service1Locator()).getService1Soap();
      if (service1Soap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)service1Soap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)service1Soap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (service1Soap != null)
      ((javax.xml.rpc.Stub)service1Soap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.Service1Soap getService1Soap() {
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap;
  }
  
  public java.lang.String helloWorld() throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.helloWorld();
  }
  
  public java.lang.String oa_interface(int ls, java.lang.String ls_emp_id, java.lang.String dt_date, java.lang.String dt_beg, java.lang.String dt_end, java.lang.String ls_nbr, java.lang.String ls_bigtype, int ls_type, java.lang.String userid, javax.xml.rpc.holders.BigDecimalHolder ls_hour, javax.xml.rpc.holders.BigDecimalHolder ls_day) throws java.rmi.RemoteException{
    if (service1Soap == null)
      _initService1SoapProxy();
    return service1Soap.oa_interface(ls, ls_emp_id, dt_date, dt_beg, dt_end, ls_nbr, ls_bigtype, ls_type, userid, ls_hour, ls_day);
  }
  
  
}