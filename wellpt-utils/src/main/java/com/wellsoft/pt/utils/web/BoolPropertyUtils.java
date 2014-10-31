package com.wellsoft.pt.utils.web;

import java.util.ArrayList;
import java.util.List;

import com.wellsoft.pt.core.dao.BoolProperty;

/**
 * 
* @ClassName: BoolPropertyUtils
* @Description: 返回bool的两个常量，主要用于界面和数据库的对应
* @author lilin
 */

public class BoolPropertyUtils {
	private static BoolProperty trueProperty = new BoolProperty(new Integer(1), true);
	private static BoolProperty falseProperty = new BoolProperty(new Integer(0), false);

	public static BoolProperty getTruePorperty() {
		return trueProperty;
	}

	public static BoolProperty getFalsePorperty() {
		return falseProperty;
	}

	public static List<BoolProperty> getBoolList() {
		List<BoolProperty> boollist = new ArrayList<BoolProperty>();
		boollist.add(trueProperty);
		boollist.add(falseProperty);
		return boollist;
	}
}
