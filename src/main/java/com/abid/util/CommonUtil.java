package com.abid.util;

import java.util.Collection;

public class CommonUtil {

	/**
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return (null == collection || collection.size() == 0) ? true : false;
	}

	/**
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	/**
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		return (null == string || string.length() == 0) ? true : false;
	}

	/**
	 * @param string
	 * @return
	 */
	public static boolean isNotEmpty(String string) {
		return !isEmpty(string);
	}

	/**
	 * @param object
	 * @return
	 */
	public static boolean isNull(Object object) {
		return (null == object) ? true : false;
	}

	/**
	 * @param object
	 * @return
	 */
	public static boolean isNotNull(Object object) {
		return !isNull(object);
	}

}
