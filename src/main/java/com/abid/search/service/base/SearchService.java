package com.abid.search.service.base;

import com.abid.search.exception.SearchServiceException;
import com.abid.search.request.SearchRequest;
import com.abid.search.response.SearchResponse;

public interface SearchService {
	/**
	 * @param request
	 * @return
	 * @throws SearchServiceException
	 */
	SearchResponse doSearch(SearchRequest request) throws SearchServiceException;

}
