package com.abid.search.request;


public class    PageRequest implements BaseRequest {
    private int page;
    private int pageSize;
    private Sort sort;

    public PageRequest(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
        this.sort = null;
    }

    public PageRequest(int page, int pageSize, Sort sort) {
        this.page = page;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
