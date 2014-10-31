package com.wellsoft.pt.dyform.support;

/**
 * Description: 区块
 *  
 * @author Administrator
 * @date 2014-10-3
 * @version 1.0
 * 
 * <pre>
 * 修改记录:
 * 修改后版本	修改人		修改日期			修改内容
 * 2014-10-3.1	hunt		2014-10-3		Create
 * </pre>
 *
 */
public class DyformBlock {
	private String blockCode;
	private String blockTitle;
	private boolean hide;

	public String getBlockCode() {
		return blockCode;
	}

	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}

	public String getBlockTitle() {
		return blockTitle;
	}

	public void setBlockTitle(String blockTitle) {
		this.blockTitle = blockTitle;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

}
