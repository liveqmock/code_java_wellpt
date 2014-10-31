/**
 * AddrPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package addr;

public interface AddrPortType extends java.rmi.Remote {
    public java.lang.Object connectMysql() throws java.rmi.RemoteException;
    public java.math.BigInteger verifyAddr(java.lang.String mailAddress) throws java.rmi.RemoteException;
    public java.math.BigInteger addAddr(java.lang.String mailAddress, java.lang.String user, java.lang.String verifyUser, java.lang.String verifyPsword) throws java.rmi.RemoteException;
    public boolean verifyUser(java.lang.String verifyUser, java.lang.String verifyPsword) throws java.rmi.RemoteException;
    public java.lang.String getMailName(java.lang.String mailAddress) throws java.rmi.RemoteException;
    public java.lang.Object getMail() throws java.rmi.RemoteException;
}
