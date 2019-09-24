package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import java.util.function.Function;

/**
 * Enum to String Converter
 * 
 */

public enum EnumToStringConverter implements Function<Enum, String> {

	INSTANCE;

	@Override
	public String apply(Enum source) {
		return source.name();
	}

}
