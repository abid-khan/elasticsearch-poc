package com.abid.util;

import com.abid.model.Contact;
import com.abid.search.request.IndexRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

public class DataGenerator {

	public static Contact getContact(String title, Long views) {
		Contact contact1 = new Contact();
		contact1.setId(UUID.randomUUID().toString());
		contact1.setName(title);
		contact1.setViews(views);
		return contact1;
	}

	public static IndexRequest getIndexRequest(String index, String type, List<?> objects, Class model)
			throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		IndexRequest indexRequest = new IndexRequest();
		indexRequest.setIndexName(index);
		indexRequest.setIndexType(type);
		indexRequest.setObjects(objects);
		indexRequest.setModel(model);
		return indexRequest;
	}
}
