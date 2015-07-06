package com.abid.search.bean;

import com.abid.type.FilterType;

import java.util.List;

public class SearchFilter {
	private String name;
	private List<Object> values;
	private FilterType filterType;

	public SearchFilter(String name, List<Object> values, FilterType filterType) {
		this.name = name;
		this.values = values;
		this.filterType = filterType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}

	public FilterType getFilterType() {
		return filterType;
	}

	public void setFilterType(FilterType filterType) {
		this.filterType = filterType;
	}
}
