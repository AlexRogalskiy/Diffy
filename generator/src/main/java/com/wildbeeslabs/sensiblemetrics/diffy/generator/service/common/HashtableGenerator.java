//package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.common;
//
//import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
//
//import java.util.Hashtable;
//import java.util.List;
//
///**
// * Produces values of type {@link Hashtable}.
// */
//public class HashtableGenerator extends MapGenerator<Hashtable> {
//    public HashtableGenerator() {
//        super(Hashtable.class);
//    }
//
//    @Override
//    protected boolean okToAdd(Object key, Object value) {
//        return key != null && value != null;
//    }
//
//    @Override
//    public void addComponentGenerators(List<Generator<?>> newComponents) {
//        super.addComponentGenerators(newComponents);
//    }
//}
