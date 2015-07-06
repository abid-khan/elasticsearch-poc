package com.abid.search.request;

import com.abid.type.SortDirection;

import java.io.Serializable;
import java.util.List;

public class Sort implements Serializable {

	private SortDirection direction;
	private List<String> fields;

	public Sort(SortDirection direction, List<String> fields) {
		this.direction = direction;
		this.fields = fields;
	}

	public SortDirection getDirection() {
		return direction;
	}

	public void setDirection(SortDirection direction) {
		this.direction = direction;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}
}
