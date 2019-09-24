//package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;
//
//import java.util.Date;
//import java.util.UUID;
//import java.util.function.Function;
//
//import com.noorq.casser.support.Timeuuid;
//
//public enum TimeuuidToDateConverter implements Function<UUID, Date> {
//
//	INSTANCE;
//
//	@Override
//	public Date apply(UUID source) {
//		return new Date(Timeuuid.getTimestampMillis(source));
//	}
//
//}
