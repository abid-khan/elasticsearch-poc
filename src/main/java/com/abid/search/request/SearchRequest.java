package com.abid.search.request;

import com.abid.search.bean.SearchFilter;

import java.util.List;

public class SearchRequest<T> implements BaseRequest {
	private Class<T> model;
	private PageRequest pageRequest;
	private String keyword;
	private List<SearchFilter> filters;
	private List<String> indices;
	private List<String> types;

	public SearchRequest(Class<T> modelClass, String keyword, PageRequest pageRequest, List<SearchFilter> filters) {
		this.model = modelClass;
		this.keyword = keyword;
		this.pageRequest = pageRequest;
		this.filters = filters;
	}

	public SearchRequest(Class<T> modelClass, String keyword, PageRequest pageRequest, List<SearchFilter> filters,
			List<String> indices, List<String> types) {
		this.model = modelClass;
		this.keyword = keyword;
		this.pageRequest = pageRequest;
		this.filters = filters;
		this.indices = indices;
		this.types = types;
	}

	public Class<T> getModel() {
		return model;
	}

	public void setModel(Class<T> model) {
		this.model = model;
	}

	public PageRequest getPageRequest() {
		return pageRequest;
	}

	public void setPageRequest(PageRequest pageRequest) {
		this.pageRequest = pageRequest;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<SearchFilter> getFilters() {
		return filters;
	}

	public void setFilters(List<SearchFilter> filters) {
		this.filters = filters;
	}

	public List<String> getIndices() {
		return indices;
	}

	public void setIndices(List<String> indices) {
		this.indices = indices;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}
}
