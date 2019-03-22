/**
 * Diffy API module configuration file
 */
open module com.wildbeeslabs.sensiblemtrics.diffy {
    requires static lombok;
    requires slf4j.api;
    requires jsr305;
    requires com.fasterxml.jackson.annotation;
    requires org.apache.commons.lang3;
    requires com.fasterxml.jackson.databind;
    requires commons.collections;
    requires com.google.common;
    requires java.sql;
    requires org.mongodb.bson;
    requires gson;
    requires com.fasterxml.jackson.core;
    requires commons.beanutils;
    requires java.desktop;

    exports com.wildbeeslabs.sensiblemetrics.diffy.comparator;
    exports com.wildbeeslabs.sensiblemetrics.diffy.converter;
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher;
    exports com.wildbeeslabs.sensiblemetrics.diffy.entry;
}
