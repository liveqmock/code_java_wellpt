/*
 * @(#)2014-3-14 V1.0
 * 
 * Copyright 2014 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.basicdata.printtemplate.support;

import java.io.File;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * Description: 如何描述该类
 *  
 * @author Administrator
 * @date 2014-3-14
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-3-14.1	Administrator		2014-3-14		Create
 * </pre>
 *
 */
public class PrinttemplateUtil {

	/**
	 * 
	 * html转为word
	 * 
	 * @param htmlFullPath
	 * @param docFullPath
	 */
	public static void htmlToWord(String htmlFullPath, String docFullPath) {
		ActiveXComponent word = getActiveXComponent(); // 启动word线程      
		try {
			word.setProperty("Visible", new Variant(false));
			//			Dispatch docs = word.getProperty("Documents").toDispatch();
			Dispatch docs = getWordDispatchDocument(word);
			Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method,
					new Object[] { htmlFullPath, new Variant(false), new Variant(true) }, new int[1]).toDispatch();
			Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { docFullPath, new Variant(1) }, new int[1]);
			Variant f = new Variant(false);
			Dispatch.call(doc, "Close", f);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeActiveXComponent(word);//关闭word线程
		}
	}

	/**
	 * 
	 * xml转为word
	 * 
	 * @param htmlFullPath
	 * @param docFullPath
	 */
	public static void xmlToWord(String xmlFullPath, String docFullPath) {
		ActiveXComponent app = new ActiveXComponent("Word.Application"); // 启动word      
		try {
			app.setProperty("Visible", new Variant(false));
			Dispatch docs = app.getProperty("Documents").toDispatch();
			Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method,
					new Object[] { xmlFullPath, new Variant(false), new Variant(true) }, new int[1]).toDispatch();
			Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { docFullPath, new Variant(1) }, new int[1]);
			Variant f = new Variant(false);
			Dispatch.call(doc, "Close", f);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			app.invoke("Quit", new Variant[] {});
			ComThread.Release();
		}
	}

	/**
	 * 
	 * word转为html
	 * 
	 * @param htmlFullPath
	 * @param docFullPath
	 */
	public static void wordToHtml(String docFullPath, String htmlFullPath) {
		ActiveXComponent word = new ActiveXComponent("Word.Application"); // 启动word      
		try {
			word.setProperty("Visible", new Variant(false));
			Dispatch docs = getWordDispatchDocument(word);
			Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method,
					new Object[] { docFullPath, new Variant(false), new Variant(true) }, new int[1]).toDispatch();
			Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { htmlFullPath, new Variant(8) }, new int[1]);
			Variant f = new Variant(false);
			Dispatch.call(doc, "Close", f);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeActiveXComponent(word);
		}
	}

	public static void main(String[] args) {
		String fileName = "D:\\a.xml";
		String content = "D:\\a.doc";
		xmlToWord(fileName, content);
	}

	/**
	 * 将word文件转为xml格式
	 * 
	 * 
	 */
	public static File wordToXml(String inputDocPath, String outPutDocPath) {
		//初始化com的线程
		ComThread.InitSTA();
		//word运行程序对象
		ActiveXComponent word = new ActiveXComponent("Word.Application");
		//文档对象
		Dispatch wordObject = (Dispatch) word.getObject();
		//设置属性  Variant(true)表示word应用程序可见
		Dispatch.put((Dispatch) wordObject, "Visible", new Variant(false));
		//word所有文档
		Dispatch documents = word.getProperty("Documents").toDispatch();
		//打开模板文档
		Dispatch document = PrinttemplateUtil.open(documents, inputDocPath);

		//另存为指定格式的文档  
		Dispatch.invoke(document, "SaveAs", Dispatch.Method, new Object[] { outPutDocPath, new Variant(19) },
				new int[1]);

		//		PrinttemplateUtil.close(document);
		word.invoke("Quit", new Variant[0]);
		//关闭com的线程
		ComThread.Release();
		return new File(outPutDocPath + ".ftl");
	}

	/**
	 * 
	 * 启动word线程
	 * 
	 * @return
	 */
	public static ActiveXComponent getActiveXComponent() {
		//初始化com的线程
		ComThread.InitSTA();
		ActiveXComponent word = new ActiveXComponent("Word.Application");
		return word;
	}

	/**
	 * 
	 * 关闭word线程
	 * @return 
	 *
	 */
	public static void closeActiveXComponent(ActiveXComponent word) {
		word.invoke("Quit", new Variant[0]);
		//关闭com的线程
		ComThread.Release();
	}

	/**
	 * 
	 * 获取word的Dispatch对象
	 *
	 */
	public static Dispatch getWordDispatchDocument(ActiveXComponent word) {
		//文档对象
		Dispatch wordObject = (Dispatch) word.getObject();
		//设置属性  Variant(true)表示word应用程序可见
		Dispatch.put((Dispatch) wordObject, "Visible", new Variant(false));
		//word dispatch对象
		Dispatch dispatchDocument = word.getProperty("Documents").toDispatch();
		return dispatchDocument;
	}

	/**
	 * 打开文件
	 * @param documents	
	 * 							word的Dispath对象
	 * @param inputDocPath
	 * 							需打开的word文件的完整路径
	 * @return
	 */
	public static Dispatch open(Dispatch doc, String inputDocPath) {
		//打开word
		Dispatch document = Dispatch.call(doc, "Open", inputDocPath).toDispatch();
		return document;
	}

	/**
	 * 关闭文件
	 * @param doc
	 */
	public static void close(Dispatch doc) {
		Dispatch.call(doc, "Close", new Variant(true));
	}

	/**
	* 文件保存或另存为
	* 
	* @param savePath
	*            一定要记得加上扩展名 .doc 保存或另存为路径
	*/
	public static void save(ActiveXComponent word, String savePath) {
		Dispatch.call(Dispatch.call(word, "WordBasic").getDispatch(), "FileSaveAs", savePath);
	}

	/**    
	 * 设置页面方向和页边距    
	 *    
	 * @param orientation    
	 *            可取值0或1，分别代表横向和纵向    
	 * @param pageSize    
	 *            A3是8，A4是9，A5是11等等
	 * @param leftMargin    
	 *            左边距的值    
	 * @param rightMargin    
	 *            右边距的值    
	 * @param topMargin    
	 *            上边距的值    
	 * @param buttomMargin    
	 *            下边距的值    
	 */
	public static void setPageSetup(Dispatch doc, int orientation, int pageSize, int leftMargin, int rightMargin,
			int topMargin, int buttomMargin) {
		Dispatch pageSetup = Dispatch.get(doc, "PageSetup").toDispatch();
		Dispatch.put(pageSetup, "Orientation", orientation);
		Dispatch.put(pageSetup, "PaperSize", new Integer(pageSize));
		Dispatch.put(pageSetup, "LeftMargin", leftMargin);
		Dispatch.put(pageSetup, "RightMargin", rightMargin);
		Dispatch.put(pageSetup, "TopMargin", topMargin);
		Dispatch.put(pageSetup, "BottomMargin", buttomMargin);
	}
}
