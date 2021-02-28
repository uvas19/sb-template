package com.uvas.pojos.customer;

public enum CustomerTypeEnum {

	PRIMARY("PRIMARY_CUSTOMER"), SECONDARY("SECONDARY_CUSTOMER");

	private final String value;

	CustomerTypeEnum(String value) {
		this.value = value;
	}

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
