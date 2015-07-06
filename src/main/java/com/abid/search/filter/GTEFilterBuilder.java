package com.abid.search.filter;

import com.abid.search.bean.SearchFilter;
import com.abid.search.exception.InvalidFilterException;
import com.abid.type.FilterType;
import com.abid.util.CommonUtil;
import org.elasticsearch.index.query.FilterBuilder;

import static org.elasticsearch.index.query.FilterBuilders.boolFilter;
import static org.elasticsearch.index.query.FilterBuilders.rangeFilter;

public class GTEFilterBuilder implements SearchFilterValidator, SearchFilterBuilder {

	@Override public FilterBuilder buildFilter(SearchFilter searchFilter) throws InvalidFilterException {
		isValid(searchFilter);
		return boolFilter().must(rangeFilter(searchFilter.getName()).from(searchFilter.getValues().get(0)).
				to(null).includeLower(true).includeUpper(true));
	}

	@Override public boolean isValid(SearchFilter searchFilter) throws InvalidFilterException {
		if (CommonUtil.isNotEmpty(searchFilter.getName()) && CommonUtil.isNotEmpty(searchFilter.getValues())
				&& CommonUtil.isNotNull(searchFilter.getValues().get(0)) && FilterType.GTE.toString()
				.equals(searchFilter.getFilterType().toString())) {
			return true;
		} else {
			throw new InvalidFilterException("Invalid GreaterThenEqual filter");
		}
	}

}
