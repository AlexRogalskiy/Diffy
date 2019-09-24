//package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;
//
//import java.util.Date;
//import java.util.UUID;
//import java.util.function.Function;
//
//import com.noorq.casser.support.Timeuuid;
//
///**
// * Simple Date to TimeUUID Converter
// *
// */
//
//public enum DateToTimeuuidConverter implements Function<Date, UUID> {
//
//	INSTANCE;
//
//	@Override
//	public UUID apply(Date source) {
//		long milliseconds = source.getTime();
//		return Timeuuid.of(milliseconds);
//	}
//
//}
