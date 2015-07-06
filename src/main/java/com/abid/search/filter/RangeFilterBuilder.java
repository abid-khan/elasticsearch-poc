package com.abid.search.filter;

import com.abid.search.bean.SearchFilter;
import com.abid.search.exception.InvalidFilterException;
import com.abid.type.FilterType;
import com.abid.util.CommonUtil;
import org.elasticsearch.index.query.FilterBuilder;

import static org.elasticsearch.index.query.FilterBuilders.boolFilter;
import static org.elasticsearch.index.query.FilterBuilders.rangeFilter;

public class RangeFilterBuilder implements SearchFilterValidator, SearchFilterBuilder {

	public RangeFilterBuilder() {

	}

	@Override public FilterBuilder buildFilter(SearchFilter searchFilter) throws InvalidFilterException {
		isValid(searchFilter);
		return boolFilter().must(rangeFilter(searchFilter.getName()).from(searchFilter.getValues().get(0)).
				to(searchFilter.getValues().get(1)).includeLower(true).includeUpper(true));
	}

	@Override public boolean isValid(SearchFilter searchFilter) throws InvalidFilterException {
		if (CommonUtil.isNotEmpty(searchFilter.getName()) && CommonUtil.isNotEmpty(searchFilter.getValues()) &&
				searchFilter.getValues().size() == 2 &&
				FilterType.RANGE.toString().equals(searchFilter.getFilterType().toString())) {
			return true;
		} else {
			throw new InvalidFilterException("Invalid Range filter");
		}
	}

}
