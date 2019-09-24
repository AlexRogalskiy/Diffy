package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import java.nio.ByteBuffer;
import java.util.function.Function;

public enum ByteArrayToByteBufferConverter implements Function<byte[], ByteBuffer>{

	INSTANCE;

	@Override
	public ByteBuffer apply(byte[] t) {
		
		if (t == null) {
			return null;
		}
		
		return ByteBuffer.wrap(t);
	}
}
