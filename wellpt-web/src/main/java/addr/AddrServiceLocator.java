/**
 * AddrServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package addr;

public class AddrServiceLocator extends org.apache.axis.client.Service implements addr.AddrService {

    public AddrServiceLocator() {
    }


    public AddrServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AddrServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for addrPort
    private java.lang.String addrPort_address = "http://util-dev.olerp.net/addr/addr.php";

    public java.lang.String getaddrPortAddress() {
        return addrPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String addrPortWSDDServiceName = "addrPort";

    public java.lang.String getaddrPortWSDDServiceName() {
        return addrPortWSDDServiceName;
    }

    public void setaddrPortWSDDServiceName(java.lang.String name) {
        addrPortWSDDServiceName = name;
    }

    public addr.AddrPortType getaddrPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(addrPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getaddrPort(endpoint);
    }

    public addr.AddrPortType getaddrPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            addr.AddrBindingStub _stub = new addr.AddrBindingStub(portAddress, this);
            _stub.setPortName(getaddrPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setaddrPortEndpointAddress(java.lang.String address) {
        addrPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (addr.AddrPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                addr.AddrBindingStub _stub = new addr.AddrBindingStub(new java.net.URL(addrPort_address), this);
                _stub.setPortName(getaddrPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("addrPort".equals(inputPortName)) {
            return getaddrPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:addr", "addrService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:addr", "addrPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("addrPort".equals(portName)) {
            setaddrPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
