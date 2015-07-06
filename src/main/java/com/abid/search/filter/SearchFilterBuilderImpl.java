package com.abid.search.filter;

import com.abid.search.bean.SearchFilter;
import com.abid.search.exception.InvalidFilterException;
import com.abid.type.FilterType;
import org.elasticsearch.index.query.FilterBuilder;

public class SearchFilterBuilderImpl implements SearchFilterBuilder {

	@Override public FilterBuilder buildFilter(SearchFilter searchFilter) throws InvalidFilterException {
		if (FilterType.IN.toString().equals(searchFilter.getFilterType().toString())) {
			return new com.abid.search.filter.InFilterBuilder().buildFilter(searchFilter);
		} else if (FilterType.NOT_IN.toString().equals(searchFilter.getFilterType().toString())) {
			return new com.abid.search.filter.NotInFilterBuilder().buildFilter(searchFilter);
		} else if (FilterType.RANGE.toString().equals(searchFilter.getFilterType().toString())) {
			return new com.abid.search.filter.RangeFilterBuilder().buildFilter(searchFilter);
		} else if (FilterType.GTE.toString().equals(searchFilter.getFilterType().toString())) {
			return new com.abid.search.filter.GTEFilterBuilder().buildFilter(searchFilter);
		} else if (FilterType.LTE.toString().equals(searchFilter.getFilterType().toString())) {
			return new com.abid.search.filter.LTEFilterBuilder().buildFilter(searchFilter);
		} else if (FilterType.LIKE.toString().equals(searchFilter.getFilterType().toString())) {
			return new com.abid.search.filter.LikeFilterBuilder().buildFilter(searchFilter);
		} else if (FilterType.CONTAINS.toString().equals(searchFilter.getFilterType().toString())) {
			return new com.abid.search.filter.ContainsFilterBuilder().buildFilter(searchFilter);
		} else {
			return null;

		}
	}
}
