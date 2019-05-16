/**
 * Diffy API module info
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
    requires com.fasterxml.classmate;
    requires commons.lang;

    // exports comparator interfaces
    exports com.wildbeeslabs.sensiblemetrics.diffy.comparator.iface;
    // exports converter interfaces
    exports com.wildbeeslabs.sensiblemetrics.diffy.converter.iface;
    // exports matcher interfaces
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface;
    // exports entry interfaces
    exports com.wildbeeslabs.sensiblemetrics.diffy.entry.iface;
}
