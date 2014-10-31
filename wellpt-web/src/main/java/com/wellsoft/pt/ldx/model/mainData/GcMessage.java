package com.wellsoft.pt.ldx.model.mainData;

import java.util.ArrayList;
import java.util.List;

public class GcMessage {
	private String factory;
	private String name;
	private List<String[]> stores = new ArrayList<String[]>();

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String[]> getStores() {
		return stores;
	}

	public void setStores(List<String[]> stores) {
		this.stores = stores;
	}

}
