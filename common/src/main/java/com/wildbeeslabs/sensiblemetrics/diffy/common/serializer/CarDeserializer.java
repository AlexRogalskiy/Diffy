//package com.wildbeeslabs.sensiblemetrics.diffy.common.serializer;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonToken;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
//
//import java.io.IOException;
//
//public class CarDeserializer extends StdDeserializer<Car> {
//
//    public CarDeserializer(Class<?> vc) {
//        super(vc);
//    }
//
//    @Override
//    public Car deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
//        Car car = new Car();
//        while(!parser.isClosed()){
//            JsonToken jsonToken = parser.nextToken();
//
//            if(JsonToken.FIELD_NAME.equals(jsonToken)){
//                String fieldName = parser.getCurrentName();
//                System.out.println(fieldName);
//
//                jsonToken = parser.nextToken();
//
//                if("brand".equals(fieldName)){
//                    car.setBrand(parser.getValueAsString());
//                } else if ("doors".equals(fieldName)){
//                    car.setDoors(parser.getValueAsInt());
//                }
//            }
//        }
//        return car;
//    }
//}
