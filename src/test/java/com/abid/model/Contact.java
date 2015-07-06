package com.abid.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "contact_index", type = "contact_type", shards = 1, replicas = 0) public class Contact {

	@Id private String id;

	@Field(type = FieldType.String, index = FieldIndex.analyzed, indexAnalyzer = "standard", searchAnalyzer = "standard", store = true) private String name;

	private Long views;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getViews() {
		return views;
	}

	public void setViews(Long views) {
		this.views = views;
	}
}
