/**
 * Service1Soap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface Service1Soap extends java.rmi.Remote {
    public java.lang.String helloWorld() throws java.rmi.RemoteException;
    public java.lang.String oa_interface(int ls, java.lang.String ls_emp_id, java.lang.String dt_date, java.lang.String dt_beg, java.lang.String dt_end, java.lang.String ls_nbr, java.lang.String ls_bigtype, int ls_type, java.lang.String userid, javax.xml.rpc.holders.BigDecimalHolder ls_hour, javax.xml.rpc.holders.BigDecimalHolder ls_day) throws java.rmi.RemoteException;
}
