package com.jnetty.util;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationImplIterator<E> implements Enumeration<E> {
	
	private Iterator<E> iterator = null;
	
	public EnumerationImplIterator(Iterator<E> iterator) {
		this.iterator = iterator;
	}

	public boolean hasMoreElements() {
		return this.iterator.hasNext();
	}

	public E nextElement() {
		return this.iterator.next();
	}

}
