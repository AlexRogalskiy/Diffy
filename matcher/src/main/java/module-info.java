/**
 * Diffy API module info
 */
open module com.wildbeeslabs.sensiblemtrics.diffy.matcher {
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
    requires com.wildbeeslabs.sensiblemtrics.diffy.common;

    // exports matcher description
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface;
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.impl;
    // exports matcher entry
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.entry.iface;
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.entry.impl;
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.entry.view;
    // exports matcher enumeration
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration;
    // exports matcher event
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.event;
    // exports matcher exception
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.exception;
    // exports matcher factory
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.factory;
    // exports matcher handler
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.iface;
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.impl;
    // exports matcher service
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.service;
    // exports matcher interfaces
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces;
    // exports matcher listener
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.listener.iface;
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.listener.impl;
    // exports matcher utils
    exports com.wildbeeslabs.sensiblemetrics.diffy.matcher.utils;
}
