/**
 * Diffy API module info
 */
open module com.wildbeeslabs.sensiblemtrics.diffy.common {
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

    // exports common annotation
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.annotation;
    // exports common context
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.context;
    // exports common entry
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface;
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.entry.impl;
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.entry.view;
    // exports common enumeration
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.enumeration;
    // exports common event
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface;
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.event.impl;
    // exports common exception
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.exception;
    // exports common helpers
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface;
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;
    // exports common messaging
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.messaging;
    // exports common service
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.service;
    // exports common sort
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.sort;
    // exports common stream
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.stream.iface;
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.stream.impl;
    // exports common utils
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.utils;
    // exports common interfaces
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.interfaces;
    // exports common property
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.property.enumeration;
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.property.impl;
    // exports common executor
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.executor.configuration;
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.executor.factory;
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.executor.handler;
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.executor.iface;
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.executor.impl;
    exports com.wildbeeslabs.sensiblemetrics.diffy.common.executor.property;
}
