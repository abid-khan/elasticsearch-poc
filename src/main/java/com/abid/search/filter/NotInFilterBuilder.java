package com.abid.search.filter;

import com.abid.search.bean.SearchFilter;
import com.abid.search.exception.InvalidFilterException;
import com.abid.type.FilterType;
import com.abid.util.CommonUtil;
import org.elasticsearch.index.query.FilterBuilder;

import static org.elasticsearch.index.query.FilterBuilders.boolFilter;
import static org.elasticsearch.index.query.FilterBuilders.termFilter;

public class NotInFilterBuilder implements SearchFilterValidator, SearchFilterBuilder {

	public NotInFilterBuilder() {

	}

	@Override public FilterBuilder buildFilter(SearchFilter searchFilter) throws InvalidFilterException {
		isValid(searchFilter);
		return boolFilter().mustNot(termFilter(searchFilter.getName(), searchFilter.getValues().get(0)));

	}

	@Override public boolean isValid(SearchFilter searchFilter) throws InvalidFilterException {
		if (CommonUtil.isNotEmpty(searchFilter.getName()) && CommonUtil.isNotEmpty(searchFilter.getValues())
				&& CommonUtil.isNotNull(searchFilter.getValues().get(0)) &&
				FilterType.NOT_IN.toString().equals(searchFilter.getFilterType().toString())) {
			return true;
		} else {
			throw new InvalidFilterException("Invalid NotIn filter");
		}
	}
}
