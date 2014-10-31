package com.wellsoft.pt.dyform.support.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wellsoft.pt.dyform.entity.DyFormDefinition;

/**
 * 动态表单缓存管理
 * 在DyFormDefinitionServiceImpl类中进行了初始化
 * @author hunt
 *
 */
public class DyformCacheUtils {
	private static Map<String, DyFormDefinition> defintionsKeyAsUuid = new HashMap<String, DyFormDefinition>();
	private static Map<String, DyFormDefinition> defintionsKeyAsId = new HashMap<String, DyFormDefinition>();

	private static boolean initOk = false;

	public static void updateOrAdd(DyFormDefinition def) {
		def.setJsonHandler(null);
		synchronized (defintionsKeyAsUuid) {
			defintionsKeyAsUuid.put(def.getUuid(), def);
		}

		synchronized (defintionsKeyAsId) {
			DyFormDefinition dy = defintionsKeyAsId.get(def.getOuterId());
			if (dy == null) {
				defintionsKeyAsId.put(def.getOuterId(), def);
			} else {
				String version = dy.getVersion();
				String version2 = def.getVersion();
				if (Float.parseFloat(version2) > Float.parseFloat(version)) {//高版本替换低版本
					defintionsKeyAsId.put(def.getOuterId(), def);
				}
			}
			//defintionsKeyAsId.put(def.getOuterId(), def);
		}
	}

	public static void delete(String formUuid) {
		synchronized (defintionsKeyAsUuid) {
			defintionsKeyAsUuid.remove(formUuid);
		}

		synchronized (defintionsKeyAsId) {
			String formId = null;
			for (DyFormDefinition dy : defintionsKeyAsId.values()) {

				if (dy.getUuid().equals(formUuid)) {
					formId = dy.getOuterId();
					break;
				}
			}

			if (formId != null) {
				defintionsKeyAsId.remove(formId);
			}

		}

	}

	public static void initDyformDefinitionCache(List<DyFormDefinition> defs) {
		if (initOk) {
			return;
		}
		for (DyFormDefinition def : defs) {
			updateOrAdd(def);
		}
		initOk = true;
	}

	public static DyFormDefinition getDyformDefinitionByUuid(String formUuid) {
		synchronized (defintionsKeyAsUuid) {
			DyFormDefinition dydf = defintionsKeyAsUuid.get(formUuid);
			return dydf;
		}
	}

	public static DyFormDefinition getDyformDefinitionOfMaxVersionById(String formId) {
		synchronized (defintionsKeyAsId) {
			DyFormDefinition dydf = defintionsKeyAsId.get(formId);
			return dydf;
		}
	}

	/**
	 * 是否初始化完成
	 * @return
	 */
	public static boolean isInitOk() {
		return initOk;
	}

}
