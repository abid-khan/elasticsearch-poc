package com.abid.search.service.impl;

import com.abid.search.exception.IndexingFailedException;
import com.abid.search.request.IndexRequest;
import com.abid.search.response.IndexResponse;
import com.abid.search.service.base.IndexingService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional @Service public class IndexingServiceImpl implements IndexingService {

	@Autowired private ElasticsearchTemplate elasticsearchTemplate;

	@Override public IndexResponse bulkIndex(IndexRequest request) throws IndexingFailedException {
		try {

			List<IndexQuery> queries = new ArrayList<IndexQuery>();

			for (Object object : request.getObjects()) {
				queries.add(new IndexQueryBuilder().withObject(object).withIndexName(request.getIndexName())
						.withId((String) PropertyUtils.getProperty(object, DEFAULT_ID)).withType(request.getIndexType())
						.build());
			}

			elasticsearchTemplate.bulkIndex(queries);
			elasticsearchTemplate.refresh(request.getIndexName(), true);
			return new IndexResponse(true, null);
		} catch (Exception ex) {

			throw new IndexingFailedException("Failed to index due to ", ex);
		}
	}

	@Override public IndexResponse delete(IndexRequest request) throws IndexingFailedException {
		try {
			String response = elasticsearchTemplate.delete(request.getIndexName(), request.getIndexType(),
					(String) PropertyUtils.getProperty(request.getObjects().get(0), DEFAULT_ID));
			return new IndexResponse(true, response);
		} catch (Exception ex) {
			throw new IndexingFailedException("Failed to delete document from index due to ", ex);
		}
	}

	@Override public IndexResponse update(IndexRequest request) throws IndexingFailedException {
		try {
			UpdateQuery query = new UpdateQueryBuilder().withClass(request.getModel())
					.withId((String) PropertyUtils.getProperty(request.getObjects().get(0), DEFAULT_ID)).build();

			elasticsearchTemplate.update(query);
			elasticsearchTemplate.refresh(request.getModel(), true);
			return new IndexResponse(true, null);
		} catch (Exception ex) {

			throw new IndexingFailedException("Failed to update index due to ", ex);
		}
	}

	@Override public IndexResponse deleteIndex(String indexName) throws IndexingFailedException {
		try {

			boolean response = elasticsearchTemplate.deleteIndex(indexName);
			return new IndexResponse(response, null);
		} catch (Exception ex) {

			throw new IndexingFailedException("Failed to index due to ", ex);
		}
	}
}