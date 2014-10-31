/**
 * AddrService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package addr;

public interface AddrService extends javax.xml.rpc.Service {
    public java.lang.String getaddrPortAddress();

    public addr.AddrPortType getaddrPort() throws javax.xml.rpc.ServiceException;

    public addr.AddrPortType getaddrPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
