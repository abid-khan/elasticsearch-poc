package com.abid.search.response;

import org.springframework.data.domain.Page;

import java.util.List;

public class SearchResponse<T> extends BaseResponse {

	List<T> items;
	long totalCount;
	long totalPages;

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public SearchResponse buildResponse(Page<T> records) {
		SearchResponse<T> response = new SearchResponse<T>();
		response.setItems(records.getContent());
		response.setTotalCount(records.getTotalElements());
		response.setTotalPages(records.getTotalPages());
		return response;
	}
}
