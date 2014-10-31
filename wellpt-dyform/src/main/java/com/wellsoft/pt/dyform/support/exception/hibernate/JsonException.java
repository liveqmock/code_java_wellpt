package com.wellsoft.pt.dyform.support.exception.hibernate;

import org.hibernate.HibernateException;

public class JsonException extends HibernateException {

	public JsonException(String message) {
		super(message);
	}

}
