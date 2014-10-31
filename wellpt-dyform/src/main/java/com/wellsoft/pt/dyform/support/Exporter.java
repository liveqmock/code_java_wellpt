/*
 * @(#)2013-2-8 V1.0
 * 
 * Copyright 2013 WELL-SOFT, Inc. All rights reserved.
 */
package com.wellsoft.pt.dyform.support;

/**
 * Description: 如何描述该类
 *  
 * @author zhulh
 * @date 2013-2-8
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2013-2-8.1	zhulh		2013-2-8		Create
 * </pre>
 *
 */
public interface Exporter {
	public boolean acceptsImportScripts();

	public void export(String string) throws Exception;

	public void release() throws Exception;
}