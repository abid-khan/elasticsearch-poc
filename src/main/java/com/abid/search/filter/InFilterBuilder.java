package com.abid.search.filter;

import com.abid.search.bean.SearchFilter;
import com.abid.search.exception.InvalidFilterException;
import com.abid.type.FilterType;
import com.abid.util.CommonUtil;
import org.elasticsearch.index.query.FilterBuilder;

import static org.elasticsearch.index.query.FilterBuilders.boolFilter;
import static org.elasticsearch.index.query.FilterBuilders.termFilter;

public class InFilterBuilder implements SearchFilterValidator, SearchFilterBuilder {

	public InFilterBuilder() {

	}

	@Override public FilterBuilder buildFilter(SearchFilter searchFilter) throws InvalidFilterException {
		isValid(searchFilter);
		return boolFilter().must(termFilter(searchFilter.getName(), searchFilter.getValues().get(0)));
	}

	@Override public boolean isValid(SearchFilter searchFilter) throws InvalidFilterException {
		if (CommonUtil.isNotEmpty(searchFilter.getName()) && CommonUtil.isNotEmpty(searchFilter.getValues())
				&& CommonUtil.isNotNull(searchFilter.getValues().get(0)) &&
				FilterType.IN.toString().equals(searchFilter.getFilterType().toString())) {

			return true;
		} else {
			throw new InvalidFilterException("Invalid In filter");
		}
	}
}
