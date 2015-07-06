package com.abid.search.service.impl;

import com.abid.search.exception.SearchServiceException;
import com.abid.search.request.SearchRequest;
import com.abid.search.response.SearchResponse;
import com.abid.search.util.SearchRequestBuilder;
import com.abid.search.service.base.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional(readOnly = true) public class SearchServiceImpl implements SearchService {

	@Autowired private ElasticsearchTemplate elasticsearchTemplate;

	@Override public SearchResponse doSearch(SearchRequest request) throws SearchServiceException {
		SearchResponse response = null;
		try {

			//TODO add logger
			Page<?> records = elasticsearchTemplate
					.queryForPage(SearchRequestBuilder.buildQuery(request), request.getModel());
			return new SearchResponse().buildResponse(records);

		} catch (Exception ex) {
			//TODO add logger
			throw new SearchServiceException("Failed to search due to {}", ex);
		}

	}
}
