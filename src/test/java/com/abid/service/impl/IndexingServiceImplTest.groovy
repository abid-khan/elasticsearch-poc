package com.abid.service.impl

import com.abid.SearchApplication
import com.abid.model.Contact
import com.abid.search.request.IndexRequest
import com.abid.search.response.IndexResponse
import com.abid.search.service.base.IndexingService
import com.abid.util.DataGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Stepwise


@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = [SearchApplication.class])
@Stepwise
@ActiveProfiles("test")
class IndexingServiceImplTest extends Specification {


    @Autowired
    private IndexingService indexingService;

    def INDEX_NAME = "contact_index";

    def INDEX_TYPE = "contact_type";

    def "index one entry"() {

        setup:
        indexingService.deleteIndex(INDEX_NAME);
        and:
        def contact1 = DataGenerator.getContact("contact one", 10);
        def objects = new ArrayList<Object>();
        objects.add(contact1);
        def IndexRequest indexRequest = DataGenerator.getIndexRequest(INDEX_NAME, INDEX_TYPE, objects, Contact.class);

        when:
        IndexResponse indexResponse = indexingService.bulkIndex(indexRequest);
        then:
        indexResponse;
        indexResponse.isSuccess == true;
        cleanup:
        indexingService.deleteIndex(INDEX_NAME);
    }


}
