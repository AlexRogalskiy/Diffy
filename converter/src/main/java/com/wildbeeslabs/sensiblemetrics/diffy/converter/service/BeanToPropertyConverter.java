/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * <p><code>Transformer</code> that outputs a property value.</p>
 *
 * <p>An implementation of <code>org.apache.commons.collections.Transformer</code> that transforms
 * the object provided by returning the value of a specified property of the object.  The
 * constructor for <code>BeanToPropertyValueTransformer</code> requires the name of the property
 * that will be used in the transformation.  The property can be a simple, nested, indexed, or
 * mapped property as defined by <code>org.apache.commons.beanutils.PropertyUtils</code>. If any
 * object in the property path specified by <code>propertyName</code> is <code>null</code> then the
 * outcome is based on the value of the <code>ignoreNull</code> attribute.
 * </p>
 *
 * <p>
 * A typical usage might look like:
 * <code><pre>
 * // create the transformer
 * BeanToPropertyValueTransformer transformer = new BeanToPropertyValueTransformer( "person.address.city" );
 *
 * // transform the Collection
 * Collection peoplesCities = CollectionUtils.collect( peopleCollection, transformer );
 * </pre></code>
 * </p>
 *
 * <p>
 * This would take a <code>Collection</code> of person objects and return a <code>Collection</code>
 * of objects which represents the cities in which each person lived. Assuming...
 * <ul>
 * <li>
 * The top level object in the <code>peeopleCollection</code> is an object which represents a
 * person.
 * </li>
 * <li>
 * The person object has a <code>getAddress()</code> method which returns an object which
 * represents a person's address.
 * </li>
 * <li>
 * The address object has a <code>getCity()</code> method which returns an object which
 * represents the city in which a person lives.
 * </li>
 * </ul>
 *
 * @version $Id: BeanToPropertyValueTransformer.java 1454597 2013-03-08 21:58:12Z britter $
 * @see PropertyUtils
 * @see Transformer
 */
@Slf4j
@Data
@EqualsAndHashCode
@ToString
public class BeanToPropertyConverter<T, R> implements Converter<T, R> {

    /**
     * The name of the property that will be used in the transformation of the object.
     */
    private final String propertyName;

    /**
     * <p>Should null objects on the property path throw an <code>IllegalArgumentException</code>?</p>
     * <p>
     * Determines whether <code>null</code> objects in the property path will genenerate an
     * <code>IllegalArgumentException</code> or not. If set to <code>true</code> then if any objects
     * in the property path evaluate to <code>null</code> then the
     * <code>IllegalArgumentException</code> throw by <code>PropertyUtils</code> will be logged but
     * not rethrown and <code>null</code> will be returned.  If set to <code>false</code> then if any
     * objects in the property path evaluate to <code>null</code> then the
     * <code>IllegalArgumentException</code> throw by <code>PropertyUtils</code> will be logged and
     * rethrown.
     * </p>
     */
    private final boolean ignoreNull;

    /**
     * Constructs a Transformer which does not ignore nulls.
     * Constructor which takes the name of the property that will be used in the transformation and
     * assumes <code>ignoreNull</code> to be <code>false</code>.
     *
     * @param propertyName The name of the property that will be used in the transformation.
     * @throws IllegalArgumentException If the <code>propertyName</code> is <code>null</code> or
     *                                  empty.
     */
    public BeanToPropertyConverter(final String propertyName) {
        this(propertyName, false);
    }

    /**
     * Constructs a Transformer and sets ignoreNull.
     * Constructor which takes the name of the property that will be used in the transformation and
     * a boolean which determines whether <code>null</code> objects in the property path will
     * genenerate an <code>IllegalArgumentException</code> or not.
     *
     * @param propertyName The name of the property that will be used in the transformation.
     * @param ignoreNull   Determines whether <code>null</code> objects in the property path will
     *                     genenerate an <code>IllegalArgumentException</code> or not.
     * @throws IllegalArgumentException If the <code>propertyName</code> is <code>null</code> or
     *                                  empty.
     */
    public BeanToPropertyConverter(final String propertyName, final boolean ignoreNull) {
        ValidationUtils.isTrue(StringUtils.isBlank(propertyName), "Property name cannot be null or empty");
        this.propertyName = propertyName;
        this.ignoreNull = ignoreNull;
    }

    /**
     * Returns the value of the property named in the transformer's constructor for
     * the object provided. If any object in the property path leading up to the target property is
     * <code>null</code> then the outcome will be based on the value of the <code>ignoreNull</code>
     * attribute. By default, <code>ignoreNull</code> is <code>false</code> and would result in an
     * <code>IllegalArgumentException</code> if an object in the property path leading up to the
     * target property is <code>null</code>.
     *
     * @param object The object to be transformed.
     * @return The value of the property named in the transformer's constructor for the object
     * provided.
     * @throws IllegalArgumentException If an IllegalAccessException, InvocationTargetException, or
     *                                  NoSuchMethodException is thrown when trying to access the property specified on the object
     *                                  provided. Or if an object in the property path provided is <code>null</code> and
     *                                  <code>ignoreNull</code> is set to <code>false</code>.
     */
    @Override
    public R convert(final T object) {
        try {
            return (R) PropertyUtils.getProperty(object, this.propertyName);
        } catch (IllegalArgumentException e) {
            if (this.ignoreNull) {
                final IllegalArgumentException iae = new IllegalArgumentException(String.format("ERROR: problem during transformation, null value encountered in property = {%s}", this.propertyName), e);
                if (!BeanUtils.initCause(iae, e)) {
                    log.error(iae.getMessage(), e);
                }
                throw iae;
            }
        } catch (IllegalAccessException e) {
            final IllegalArgumentException iae = new IllegalArgumentException(String.format("ERROR: unable to access the property provided = {%s}", this.propertyName), e);
            if (!BeanUtils.initCause(iae, e)) {
                log.error(iae.getMessage(), e);
            }
            throw iae;
        } catch (InvocationTargetException e) {
            final IllegalArgumentException iae = new IllegalArgumentException(String.format("ERROR: exception occurred in property's getter = {%s}", this.propertyName), e);
            if (!BeanUtils.initCause(iae, e)) {
                log.error(iae.getMessage(), e);
            }
            throw iae;
        } catch (NoSuchMethodException e) {
            final IllegalArgumentException iae = new IllegalArgumentException(String.format("ERROR: no property found for name = {%s}", this.propertyName), e);
            if (!BeanUtils.initCause(iae, e)) {
                log.error(iae.getMessage(), e);
            }
            throw iae;
        }
        return null;
    }
}
