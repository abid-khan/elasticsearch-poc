package com.abid.search.filter;

import com.abid.search.bean.SearchFilter;
import com.abid.search.exception.InvalidFilterException;

public interface SearchFilterValidator {

	/**
	 * @param searchFilter
	 * @return
	 * @throws InvalidFilterException
	 */
	boolean isValid(SearchFilter searchFilter) throws InvalidFilterException;
}
