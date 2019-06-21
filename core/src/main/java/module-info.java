/**
 * Diffy API module info
 */
open module com.wildbeeslabs.sensiblemtrics.diffy.core {
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
    requires org.jetbrains.annotations;
    requires org.apache.commons.text;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires protonpack;
    requires org.apache.commons.codec;
    requires java.compiler;
    requires org.jsoup;
    requires commons.validator;

    // exports core configuration
    exports com.wildbeeslabs.sensiblemetrics.diffy.core.configuration.enumeration;
    exports com.wildbeeslabs.sensiblemetrics.diffy.core.configuration.iface;
    exports com.wildbeeslabs.sensiblemetrics.diffy.core.configuration.impl;
    // exports core entry
    exports com.wildbeeslabs.sensiblemetrics.diffy.core.entry.delta;
    exports com.wildbeeslabs.sensiblemetrics.diffy.core.entry.node;
    exports com.wildbeeslabs.sensiblemetrics.diffy.core.entry.utils;
    // exports core property
    exports com.wildbeeslabs.sensiblemetrics.diffy.core.property.enumeration;
    exports com.wildbeeslabs.sensiblemetrics.diffy.core.property.iface;
    exports com.wildbeeslabs.sensiblemetrics.diffy.core.property.impl;
    // exports core service
    exports com.wildbeeslabs.sensiblemetrics.diffy.core.service.iface;
    exports com.wildbeeslabs.sensiblemetrics.diffy.core.service.impl;
    // exports core sort
    exports com.wildbeeslabs.sensiblemetrics.diffy.core.sort;
    // exports core stream
    exports com.wildbeeslabs.sensiblemetrics.diffy.core.stream.iface;
    exports com.wildbeeslabs.sensiblemetrics.diffy.core.stream.impl;
    // exports core utils
    exports com.wildbeeslabs.sensiblemetrics.diffy.core.utils;
}
