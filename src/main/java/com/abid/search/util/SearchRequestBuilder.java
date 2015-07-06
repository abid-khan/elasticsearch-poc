package com.abid.search.util;

import com.abid.search.request.PageRequest;
import com.abid.search.request.SearchRequest;
import com.abid.search.bean.SearchFilter;
import com.abid.search.filter.SearchFilterBuilderImpl;
import com.abid.util.CommonUtil;
import org.elasticsearch.index.query.AndFilterBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

public class SearchRequestBuilder {

	private static final SearchFilterBuilderImpl searchFilterBuilder = new SearchFilterBuilderImpl();

	/**
	 * Builds search query
	 *
	 * @param request
	 * @return
	 */
	public static SearchQuery buildQuery(SearchRequest request) {

		//Build keyword query
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
				.withQuery(buildQuery(request.getKeyword()));
		//Build filters
		FilterBuilder filter = buildFilter(request.getFilters());
		if (CommonUtil.isNotNull(filter)) {
			nativeSearchQueryBuilder.withFilter(filter);
		}

		//Set page metadata
		if (CommonUtil.isNotNull(request.getPageRequest())) {
			nativeSearchQueryBuilder.withPageable(getPageRequest(request.getPageRequest()));
		}

		//Apply indices
		if (CommonUtil.isNotEmpty(request.getIndices())) {
			nativeSearchQueryBuilder
					.withIndices((String[]) request.getIndices().toArray(new String[request.getIndices().size()]));
		}

		//apply types
		if (CommonUtil.isNotEmpty(request.getTypes())) {
			nativeSearchQueryBuilder
					.withTypes((String[]) request.getTypes().toArray(new String[request.getTypes().size()]));
		}

		//Build
		return nativeSearchQueryBuilder.build();
	}

	/**
	 * Return search query based on keyword. If keyword is missing then return query  for all document
	 *
	 * @param keyword
	 * @return
	 */
	public static QueryBuilder buildQuery(String keyword) {
		return CommonUtil.isEmpty(keyword) ?
				matchAllQuery() :
				boolQuery().should(queryString(keyword).analyzeWildcard(true));
	}

	/**
	 * @param pageRequest
	 * @return
	 */
	public static Pageable getPageRequest(PageRequest pageRequest) {
		return new org.springframework.data.domain.PageRequest(pageRequest.getPage(), pageRequest.getPageSize(),
				buildSort(pageRequest.getSort()));
	}

	/**
	 * @param customSort
	 * @return
	 */
	public static Sort buildSort(com.abid.search.request.Sort customSort) {
		if (CommonUtil.isNull(customSort)) {
			return null;
		}
		return new Sort(Sort.Direction.fromString(customSort.getDirection().toString()), customSort.getFields());
	}

	/**
	 * Builds list of filters to be applied to search query
	 *
	 * @param searchFilters
	 * @return
	 */
	public static FilterBuilder buildFilter(List<SearchFilter> searchFilters) {
		if (CommonUtil.isEmpty(searchFilters)) {
			return null;
		}

		//Defined AND filter builder
		AndFilterBuilder andFilterBuilder = null;

		for (SearchFilter searchFilter : searchFilters) {
			FilterBuilder filterBuilder = searchFilterBuilder.buildFilter(searchFilter);
			//Add to and filter builder
			if (CommonUtil.isNull(andFilterBuilder)) {
				andFilterBuilder = new AndFilterBuilder(filterBuilder);
			} else {
				andFilterBuilder.add(filterBuilder);
			}
		}
		return andFilterBuilder;
	}

}
