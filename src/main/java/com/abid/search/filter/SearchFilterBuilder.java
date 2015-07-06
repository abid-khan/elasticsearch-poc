package com.abid.search.filter;

import com.abid.search.bean.SearchFilter;
import com.abid.search.exception.InvalidFilterException;
import org.elasticsearch.index.query.FilterBuilder;

public interface SearchFilterBuilder {

	/**
	 * This method generate filters to be used for elasticsearch
	 *
	 * @param searchFilter
	 * @return
	 * @throws InvalidFilterException
	 */
	FilterBuilder buildFilter(SearchFilter searchFilter) throws InvalidFilterException;
}
