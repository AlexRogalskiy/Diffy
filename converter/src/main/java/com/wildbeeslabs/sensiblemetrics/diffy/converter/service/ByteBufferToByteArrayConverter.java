package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import java.nio.ByteBuffer;
import java.util.function.Function;

public enum ByteBufferToByteArrayConverter implements Function<ByteBuffer, byte[]>{

	INSTANCE;
	
	@Override
	public byte[] apply(ByteBuffer t) {
		
		if (t == null) {
			return null;
		}
		
		return t.array();
	}

}
