
package com.abid.search.request;

import java.util.List;


public class IndexRequest<T> implements BaseRequest {

    private Class<T> model;
    private String indexName;
    private String indexType;
    private String id;
    private List<Object> objects;

    public Class<T> getModel() {
        return model;
    }

    public void setModel(Class<T> model) {
        this.model = model;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }
}
