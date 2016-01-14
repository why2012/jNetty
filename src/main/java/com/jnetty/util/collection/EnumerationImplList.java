package com.jnetty.util.collection;

import java.util.Enumeration;
import java.util.List;

public class EnumerationImplList<E> implements Enumeration<E> {
	private List<E> list = null;
	private int size = -1;
	private int curIndex = 0;
	
	public EnumerationImplList(List<E> list) {
		this.list = list;
		size = list.size();
	}

	public boolean hasMoreElements() {
		return curIndex < size;
	}

	public E nextElement() {
		return list.get(curIndex++);
	}

}
