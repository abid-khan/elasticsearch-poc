package com.abid.type;

import com.abid.util.CommonUtil;

public enum FilterType {
	IN("In"), NOT_IN("NotIn"), RANGE("Range"), GTE("gte"), LTE("lte"), LIKE("like"), CONTAINS("contains");

	private String code;

	private FilterType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 * @return
	 */
	public FilterType getByCode(String code) {
		if (CommonUtil.isEmpty(code)) {
			return null;
		}

		for (FilterType filterType : FilterType.values()) {
			if (filterType.getCode().equals(code)) {
				return filterType;
			}
		}

		return null;
	}
}
