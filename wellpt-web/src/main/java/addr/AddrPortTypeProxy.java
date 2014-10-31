package addr;

public class AddrPortTypeProxy implements addr.AddrPortType {
  private String _endpoint = null;
  private addr.AddrPortType addrPortType = null;
  
  public AddrPortTypeProxy() {
    _initAddrPortTypeProxy();
  }
  
  public AddrPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initAddrPortTypeProxy();
  }
  
  private void _initAddrPortTypeProxy() {
    try {
      addrPortType = (new addr.AddrServiceLocator()).getaddrPort();
      if (addrPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)addrPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)addrPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (addrPortType != null)
      ((javax.xml.rpc.Stub)addrPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public addr.AddrPortType getAddrPortType() {
    if (addrPortType == null)
      _initAddrPortTypeProxy();
    return addrPortType;
  }
  
  public java.lang.Object connectMysql() throws java.rmi.RemoteException{
    if (addrPortType == null)
      _initAddrPortTypeProxy();
    return addrPortType.connectMysql();
  }
  
  public java.math.BigInteger verifyAddr(java.lang.String mailAddress) throws java.rmi.RemoteException{
    if (addrPortType == null)
      _initAddrPortTypeProxy();
    return addrPortType.verifyAddr(mailAddress);
  }
  
  public java.math.BigInteger addAddr(java.lang.String mailAddress, java.lang.String user, java.lang.String verifyUser, java.lang.String verifyPsword) throws java.rmi.RemoteException{
    if (addrPortType == null)
      _initAddrPortTypeProxy();
    return addrPortType.addAddr(mailAddress, user, verifyUser, verifyPsword);
  }
  
  public boolean verifyUser(java.lang.String verifyUser, java.lang.String verifyPsword) throws java.rmi.RemoteException{
    if (addrPortType == null)
      _initAddrPortTypeProxy();
    return addrPortType.verifyUser(verifyUser, verifyPsword);
  }
  
  public java.lang.String getMailName(java.lang.String mailAddress) throws java.rmi.RemoteException{
    if (addrPortType == null)
      _initAddrPortTypeProxy();
    return addrPortType.getMailName(mailAddress);
  }
  
  public java.lang.Object getMail() throws java.rmi.RemoteException{
    if (addrPortType == null)
      _initAddrPortTypeProxy();
    return addrPortType.getMail();
  }
  
  
}