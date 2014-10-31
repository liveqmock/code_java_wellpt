
package com.augurit.xm.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.augurit.xm.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetCompletedTasksResponse_QNAME = new QName("http://service.xm.augurit.com/", "getCompletedTasksResponse");
    private final static QName _GetCommonTask_QNAME = new QName("http://service.xm.augurit.com/", "getCommonTask");
    private final static QName _StartProc_QNAME = new QName("http://service.xm.augurit.com/", "startProc");
    private final static QName _GetDZbTasks_QNAME = new QName("http://service.xm.augurit.com/", "getDZbTasks");
    private final static QName _GetCompletedTasks_QNAME = new QName("http://service.xm.augurit.com/", "getCompletedTasks");
    private final static QName _SendTask_QNAME = new QName("http://service.xm.augurit.com/", "sendTask");
    private final static QName _SendTaskResponse_QNAME = new QName("http://service.xm.augurit.com/", "sendTaskResponse");
    private final static QName _GetDZbTasksResponse_QNAME = new QName("http://service.xm.augurit.com/", "getDZbTasksResponse");
    private final static QName _StartProcResponse_QNAME = new QName("http://service.xm.augurit.com/", "startProcResponse");
    private final static QName _GetCommonTaskResponse_QNAME = new QName("http://service.xm.augurit.com/", "getCommonTaskResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.augurit.xm.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCommonTaskResponse }
     * 
     */
    public GetCommonTaskResponse createGetCommonTaskResponse() {
        return new GetCommonTaskResponse();
    }

    /**
     * Create an instance of {@link GetDZbTasks }
     * 
     */
    public GetDZbTasks createGetDZbTasks() {
        return new GetDZbTasks();
    }

    /**
     * Create an instance of {@link SendTaskResponse }
     * 
     */
    public SendTaskResponse createSendTaskResponse() {
        return new SendTaskResponse();
    }

    /**
     * Create an instance of {@link GetDZbTasksResponse }
     * 
     */
    public GetDZbTasksResponse createGetDZbTasksResponse() {
        return new GetDZbTasksResponse();
    }

    /**
     * Create an instance of {@link GetCommonTask }
     * 
     */
    public GetCommonTask createGetCommonTask() {
        return new GetCommonTask();
    }

    /**
     * Create an instance of {@link StartProc }
     * 
     */
    public StartProc createStartProc() {
        return new StartProc();
    }

    /**
     * Create an instance of {@link GetCompletedTasksResponse }
     * 
     */
    public GetCompletedTasksResponse createGetCompletedTasksResponse() {
        return new GetCompletedTasksResponse();
    }

    /**
     * Create an instance of {@link SendTask }
     * 
     */
    public SendTask createSendTask() {
        return new SendTask();
    }

    /**
     * Create an instance of {@link GetCompletedTasks }
     * 
     */
    public GetCompletedTasks createGetCompletedTasks() {
        return new GetCompletedTasks();
    }

    /**
     * Create an instance of {@link StartProcResponse }
     * 
     */
    public StartProcResponse createStartProcResponse() {
        return new StartProcResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCompletedTasksResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xm.augurit.com/", name = "getCompletedTasksResponse")
    public JAXBElement<GetCompletedTasksResponse> createGetCompletedTasksResponse(GetCompletedTasksResponse value) {
        return new JAXBElement<GetCompletedTasksResponse>(_GetCompletedTasksResponse_QNAME, GetCompletedTasksResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommonTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xm.augurit.com/", name = "getCommonTask")
    public JAXBElement<GetCommonTask> createGetCommonTask(GetCommonTask value) {
        return new JAXBElement<GetCommonTask>(_GetCommonTask_QNAME, GetCommonTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StartProc }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xm.augurit.com/", name = "startProc")
    public JAXBElement<StartProc> createStartProc(StartProc value) {
        return new JAXBElement<StartProc>(_StartProc_QNAME, StartProc.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDZbTasks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xm.augurit.com/", name = "getDZbTasks")
    public JAXBElement<GetDZbTasks> createGetDZbTasks(GetDZbTasks value) {
        return new JAXBElement<GetDZbTasks>(_GetDZbTasks_QNAME, GetDZbTasks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCompletedTasks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xm.augurit.com/", name = "getCompletedTasks")
    public JAXBElement<GetCompletedTasks> createGetCompletedTasks(GetCompletedTasks value) {
        return new JAXBElement<GetCompletedTasks>(_GetCompletedTasks_QNAME, GetCompletedTasks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xm.augurit.com/", name = "sendTask")
    public JAXBElement<SendTask> createSendTask(SendTask value) {
        return new JAXBElement<SendTask>(_SendTask_QNAME, SendTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xm.augurit.com/", name = "sendTaskResponse")
    public JAXBElement<SendTaskResponse> createSendTaskResponse(SendTaskResponse value) {
        return new JAXBElement<SendTaskResponse>(_SendTaskResponse_QNAME, SendTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDZbTasksResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xm.augurit.com/", name = "getDZbTasksResponse")
    public JAXBElement<GetDZbTasksResponse> createGetDZbTasksResponse(GetDZbTasksResponse value) {
        return new JAXBElement<GetDZbTasksResponse>(_GetDZbTasksResponse_QNAME, GetDZbTasksResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StartProcResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xm.augurit.com/", name = "startProcResponse")
    public JAXBElement<StartProcResponse> createStartProcResponse(StartProcResponse value) {
        return new JAXBElement<StartProcResponse>(_StartProcResponse_QNAME, StartProcResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommonTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.xm.augurit.com/", name = "getCommonTaskResponse")
    public JAXBElement<GetCommonTaskResponse> createGetCommonTaskResponse(GetCommonTaskResponse value) {
        return new JAXBElement<GetCommonTaskResponse>(_GetCommonTaskResponse_QNAME, GetCommonTaskResponse.class, null, value);
    }

}
