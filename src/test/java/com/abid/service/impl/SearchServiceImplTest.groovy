package com.abid.service.impl

import com.abid.SearchApplication
import com.abid.model.Contact
import com.abid.search.request.IndexRequest
import com.abid.search.request.PageRequest
import com.abid.search.request.SearchRequest
import com.abid.search.request.Sort
import com.abid.search.response.IndexResponse
import com.abid.search.bean.SearchFilter
import com.abid.search.service.base.IndexingService
import com.abid.search.service.base.SearchService
import com.abid.type.FilterType
import com.abid.util.DataGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Stepwise
import com.abid.type.SortDirection


@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = [SearchApplication.class])
@Stepwise
@ActiveProfiles("test")
class SearchServiceImplTest extends Specification {


    @Autowired
    private SearchService searchService;

    @Autowired
    private IndexingService indexingService;


    def INDEX_NAME = "contact_index";

    def INDEX_TYPE = "contact_type";

    /**
     * Run before first feature method
     */
    def setupSpec() {
        //TODO
    }

    /**
     * Runs after last feature method. Cleanup resources
     * @return
     */
    def cleanupSpec() {
        //TODO
    }


    def "Search for keyword "() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getTotalCount() == expectedCount;
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);
        where:
        searchRequest                                                                          || expectedCount
        new SearchRequest<Contact>(Contact.class, "contact one", new PageRequest(0, 10), null) || 1
        // new SearchRequest<Contact>(Contact.class, "car one", new PageRequest(0, 10), null) || 1
    }


    def "Search with in filter "() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;
        and:
        List<SearchFilter> filters = new ArrayList<SearchFilter>();
        List<Object> values = new ArrayList<Object>();
        values.add("contact")
        SearchFilter searchFilter = new SearchFilter("name", values, FilterType.IN);
        filters.add(searchFilter);
        SearchRequest<Contact> searchRequest = new SearchRequest<Contact>(Contact.class, "contact one", new PageRequest(0, 10), filters);
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getTotalCount() == 1;
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);

    }


    def "Search with not in filter "() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;
        and:
        List<SearchFilter> filters = new ArrayList<SearchFilter>();
        List<Object> values = new ArrayList<Object>();
        values.add("two")
        SearchFilter searchFilter = new SearchFilter("name", values, FilterType.NOT_IN);
        filters.add(searchFilter);
        SearchRequest<Contact> searchRequest = new SearchRequest<Contact>(Contact.class, "contact one", new PageRequest(0, 10), filters);
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getTotalCount() == 1;
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);

    }


    def "Search with not in filter,excluding existing one "() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;
        and:
        List<SearchFilter> filters = new ArrayList<SearchFilter>();
        List<Object> values = new ArrayList<Object>();
        values.add("contact")
        SearchFilter searchFilter = new SearchFilter("name", values, FilterType.NOT_IN);
        filters.add(searchFilter);
        SearchRequest<Contact> searchRequest = new SearchRequest<Contact>(Contact.class, "contact one", new PageRequest(0, 10), filters);
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getTotalCount() == 0;
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);

    }


    def "Range filter "() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def contact2 = DataGenerator.getContact("contact one", 15);
        def contact3 = DataGenerator.getContact("contact one", 20);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        objects.add(contact2);
        objects.add(contact3);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;
        and:
        List<SearchFilter> filters = new ArrayList<SearchFilter>();
        List<Object> values = new ArrayList<Object>();
        values.add(10);
        values.add(15);
        SearchFilter searchFilter = new SearchFilter("views", values, FilterType.RANGE);
        filters.add(searchFilter);
        SearchRequest<Contact> searchRequest = new SearchRequest<Contact>(Contact.class, "contact one", new PageRequest(0, 10), filters);
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getTotalCount() == 2;
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);

    }


    def "GreaterThanEqual filter, one record should be present "() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def contact2 = DataGenerator.getContact("contact one", 15);
        def contact3 = DataGenerator.getContact("contact one", 20);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        objects.add(contact2);
        objects.add(contact3);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;
        and:
        List<SearchFilter> filters = new ArrayList<SearchFilter>();
        List<Object> values = new ArrayList<Object>();
        values.add(16);
        SearchFilter searchFilter = new SearchFilter("views", values, FilterType.GTE);
        filters.add(searchFilter);
        SearchRequest<Contact> searchRequest = new SearchRequest<Contact>(Contact.class, "contact one", new PageRequest(0, 10), filters);
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getTotalCount() == 1;
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);

    }


    def "GreaterThanEqual filter, no record should be present "() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def contact2 = DataGenerator.getContact("contact one", 15);
        def contact3 = DataGenerator.getContact("contact one", 20);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        objects.add(contact2);
        objects.add(contact3);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;
        and:
        List<SearchFilter> filters = new ArrayList<SearchFilter>();
        List<Object> values = new ArrayList<Object>();
        values.add(21);
        SearchFilter searchFilter = new SearchFilter("views", values, FilterType.GTE);
        filters.add(searchFilter);
        SearchRequest<Contact> searchRequest = new SearchRequest<Contact>(Contact.class, "contact one", new PageRequest(0, 10), filters);
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getTotalCount() == 0;
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);

    }


    def "LessThanEqual filter, One record should be present "() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def contact2 = DataGenerator.getContact("contact one", 15);
        def contact3 = DataGenerator.getContact("contact one", 20);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        objects.add(contact2);
        objects.add(contact3);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;
        and:
        List<SearchFilter> filters = new ArrayList<SearchFilter>();
        List<Object> values = new ArrayList<Object>();
        values.add(11);
        SearchFilter searchFilter = new SearchFilter("views", values, FilterType.LTE);
        filters.add(searchFilter);
        SearchRequest<Contact> searchRequest = new SearchRequest<Contact>(Contact.class, "contact one", new PageRequest(0, 10), filters);
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getTotalCount() == 1;
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);

    }


    def "LessThanEqual filter, no record should be present "() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def contact2 = DataGenerator.getContact("contact one", 15);
        def contact3 = DataGenerator.getContact("contact one", 20);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        objects.add(contact2);
        objects.add(contact3);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;
        and:
        List<SearchFilter> filters = new ArrayList<SearchFilter>();
        List<Object> values = new ArrayList<Object>();
        values.add(9);
        SearchFilter searchFilter = new SearchFilter("views", values, FilterType.LTE);
        filters.add(searchFilter);
        SearchRequest<Contact> searchRequest = new SearchRequest<Contact>(Contact.class, "contact one", new PageRequest(0, 10), filters);
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getTotalCount() == 0;
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);

    }

    def "Sort views  DESC"() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def contact2 = DataGenerator.getContact("contact one", 15);
        def contact3 = DataGenerator.getContact("contact one", 20);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        objects.add(contact2);
        objects.add(contact3);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;
        and:
        List<SearchFilter> filters = null;
        List<String> fields = new ArrayList<String>();
        fields.add("views");
        Sort sort = new Sort(SortDirection.DESC, fields);
        SearchRequest<Contact> searchRequest = new SearchRequest<Contact>(Contact.class, "contact one", new PageRequest(0, 10, sort), filters);
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getTotalCount() == 3;
        ((Contact) response.getItems().get(0)).getViews() == 20;
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);

    }

    def "Sort views ASC"() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def contact2 = DataGenerator.getContact("contact one", 15);
        def contact3 = DataGenerator.getContact("contact one", 20);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        objects.add(contact2);
        objects.add(contact3);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;
        and:
        List<SearchFilter> filters = null;
        List<String> fields = new ArrayList<String>();
        fields.add("views");
        Sort sort = new Sort(SortDirection.ASC, fields);
        SearchRequest<Contact> searchRequest = new SearchRequest<Contact>(Contact.class, "contact one", new PageRequest(0, 10, sort), filters);
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getTotalCount() == 3;
        ((Contact) response.getItems().get(0)).getViews() == 10;
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);

    }

    def "Page size 1"() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def contact2 = DataGenerator.getContact("contact one", 15);
        def contact3 = DataGenerator.getContact("contact one", 20);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        objects.add(contact2);
        objects.add(contact3);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;
        and:
        List<SearchFilter> filters = null;
        List<String> fields = new ArrayList<String>();
        fields.add("views");
        Sort sort = new Sort(SortDirection.ASC, fields);
        SearchRequest<Contact> searchRequest = new SearchRequest<Contact>(Contact.class, "contact one", new PageRequest(0, 1, sort), filters);
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getItems().size() == 1
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);

    }

    def "Like filter. Single record"() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def contact2 = DataGenerator.getContact("contact two", 15);
        def contact3 = DataGenerator.getContact("contact three", 20);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        objects.add(contact2);
        objects.add(contact3);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;
        and:
        List<SearchFilter> filters = new ArrayList<SearchFilter>();
        List<Object> values = new ArrayList<Object>();
        values.add("two");
        SearchFilter searchFilter = new SearchFilter("name", values, FilterType.LIKE);
        filters.add(searchFilter);



        List<String> fields = new ArrayList<String>();
        fields.add("views");
        Sort sort = new Sort(SortDirection.ASC, fields);
        SearchRequest<Contact> searchRequest = new SearchRequest<Contact>(Contact.class, null, new PageRequest(0, 10, sort), filters);
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getItems().size() == 1
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);

    }

    def "Like filter. Multiple records "() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def contact2 = DataGenerator.getContact("contact two", 15);
        def contact3 = DataGenerator.getContact("contact three", 20);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        objects.add(contact2);
        objects.add(contact3);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;
        and:
        List<SearchFilter> filters = new ArrayList<SearchFilter>();
        List<Object> values = new ArrayList<Object>();
        values.add("contact");
        SearchFilter searchFilter = new SearchFilter("name", values, FilterType.LIKE);
        filters.add(searchFilter);



        List<String> fields = new ArrayList<String>();
        fields.add("views");
        Sort sort = new Sort(SortDirection.ASC, fields);
        SearchRequest<Contact> searchRequest = new SearchRequest<Contact>(Contact.class, null, new PageRequest(0, 10, sort), filters);
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getItems().size() == 3
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);

    }

    def "Contains"() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def contact2 = DataGenerator.getContact("contact two", 15);
        def contact3 = DataGenerator.getContact("contact three", 20);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        objects.add(contact2);
        objects.add(contact3);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;
        and:
        List<SearchFilter> filters = new ArrayList<SearchFilter>();
        List<Object> values = new ArrayList<Object>();
        values.add("contact");
        SearchFilter searchFilter = new SearchFilter("name", values, FilterType.CONTAINS);
        filters.add(searchFilter);



        List<String> fields = new ArrayList<String>();
        fields.add("views");
        Sort sort = new Sort(SortDirection.ASC, fields);
        SearchRequest<Contact> searchRequest = new SearchRequest<Contact>(Contact.class, null, new PageRequest(0, 10, sort), filters);
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getItems().size() == 3
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);

    }


    def "Multiple filter "() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def contact2 = DataGenerator.getContact("contact two", 15);
        def contact3 = DataGenerator.getContact("contact three", 20);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        objects.add(contact2);
        objects.add(contact3);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;
        and:
        List<SearchFilter> filters = new ArrayList<SearchFilter>();
        List<Object> values = new ArrayList<Object>();
        values.add(11);
        SearchFilter searchFilter = new SearchFilter("views", values, FilterType.LTE);
        filters.add(searchFilter);

        List<Object> values2 = new ArrayList<Object>();
        values2.add("contact");
        SearchFilter searchFilter2 = new SearchFilter("name", values2, FilterType.IN);
        filters.add(searchFilter2);


        List<String> fields = new ArrayList<String>();
        fields.add("views");
        Sort sort = new Sort(SortDirection.ASC, fields);

        List<String> indices = new ArrayList<String>();
        indices.add("contact_index");
        List<String> types = new ArrayList<String>();


        SearchRequest<Contact> searchRequest = new SearchRequest<Contact>(Contact.class, "one", new PageRequest(0, 1, sort), filters, indices, null);
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getItems().size() == 1
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);

    }


    def "Default sorting `"() {
        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def contact2 = DataGenerator.getContact("contact two", 15);
        def contact3 = DataGenerator.getContact("contact three", 20);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        objects.add(contact2);
        objects.add(contact3);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        expect:
        indexResponse;
        indexResponse.isSuccess == true;

        and:


        List<String> fields = new ArrayList<String>();
        fields.add("views");
        Sort sort = new Sort(SortDirection.DESC, fields);




        SearchRequest<Contact> searchRequest = new SearchRequest<Contact>(Contact.class, null, new PageRequest(0, 10, sort), null);
        when:
        def response = searchService.doSearch(searchRequest);
        then:
        response;
        response.getItems().size() == 3;
        ((Contact) response.getItems().get(0)).getViews() == 20;
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);

    }


}
