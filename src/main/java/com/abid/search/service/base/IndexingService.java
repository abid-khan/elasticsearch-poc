package com.abid.search.service.base;

import com.abid.search.exception.IndexingFailedException;
import com.abid.search.request.IndexRequest;
import com.abid.search.response.IndexResponse;

public interface IndexingService {

	String DEFAULT_ID = "id";

	/**
	 * @param request
	 * @return
	 * @throws IndexingFailedException
	 */
	IndexResponse bulkIndex(IndexRequest request) throws IndexingFailedException;

	/**
	 * @param request
	 * @return
	 * @throws IndexingFailedException
	 */
	IndexResponse delete(IndexRequest request) throws IndexingFailedException;

	/**
	 * @param request
	 * @return
	 * @throws IndexingFailedException
	 */
	IndexResponse update(IndexRequest request) throws IndexingFailedException;

	/**
	 * @param indexName
	 * @return
	 * @throws IndexingFailedException
	 */
	IndexResponse deleteIndex(String indexName) throws IndexingFailedException;
}
