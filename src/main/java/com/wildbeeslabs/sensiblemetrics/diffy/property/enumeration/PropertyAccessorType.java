/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.sensiblemetrics.diffy.property.enumeration;

/**
 * Property accessor type {@linkn Enum}
 */
public enum PropertyAccessorType {
    /**
     * Getters are methods used to get a POJO field value for serialization,
     * or, under certain conditions also for de-serialization. Latter
     * can be used for effectively setting Collection or Map values
     * in absence of setters, iff returned value is not a copy but
     * actual value of the logical property.
     * <p>
     * Since version 1.3, this does <b>NOT</b> include "is getters" (methods
     * that return boolean and named 'isXxx' for property 'xxx'); instead,
     * {@link #IS_GETTER} is used}.
     */
    GETTER,
    /**
     * Setters are methods used to set a POJO value for deserialization.
     */
    SETTER,
    /**
     * Creators are constructors and (static) factory methods used to
     * construct POJO instances for deserialization
     */
    CREATOR,
    /**
     * Field refers to fields of regular Java objects. Although
     * they are not really methods, addition of optional field-discovery
     * in version 1.1 meant that there was need to enable/disable
     * their auto-detection, and this is the place to add it in.
     */
    FIELD,
    /**
     * "Is getters" are getter-like methods that are named "isXxx"
     * (instead of "getXxx" for getters) and return boolean value
     * (either primitive, or {@link java.lang.Boolean}).
     */
    IS_GETTER,
    /**
     * This pseudo-type indicates that none of accessors if affected.
     */
    NONE,
    /**
     * This pseudo-type indicates that all accessors are affected.
     */
    ALL;

    /**
     * Returns binary flag based on property accessor type {@code CREATOR} or {@code ALL}
     *
     * @return true - if current property accessor type is {@code CREATOR} or {@code ALL}, false - otherwise
     */
    public boolean creatorEnabled() {
        return (this.equals(CREATOR) || this.isAll());
    }

    /**
     * Returns binary flag based on property accessor type {@code GETTER} or {@code ALL}
     *
     * @return true - if current property accessor type is {@code GETTER} or {@code ALL}, false - otherwise
     */
    public boolean getterEnabled() {
        return (this.equals(GETTER) || this.isAll());
    }

    /**
     * Returns binary flag based on property accessor type {@code IS_GETTER} or {@code ALL}
     *
     * @return true - if current property accessor type is {@code IS_GETTER} or {@code ALL}, false - otherwise
     */
    public boolean isGetterEnabled() {
        return (this.equals(IS_GETTER) || this.isAll());
    }

    /**
     * Returns binary flag based on property accessor type {@code SETTER} or {@code ALL}
     *
     * @return true - if current property accessor type is {@code SETTER} or {@code ALL}, false - otherwise
     */
    public boolean setterEnabled() {
        return (this.equals(SETTER) || this.isAll());
    }

    /**
     * Returns binary flag based on property accessor type {@code FIELD} or {@code ALL}
     *
     * @return true - if current property accessor type is {@code SETTER} or {@code ALL}, false - otherwise
     */
    public boolean fieldEnabled() {
        return (this.equals(FIELD) || this.isAll());
    }

    /**
     * Returns binary flag based on property accessor type is {@code ALL}
     *
     * @return true - if current property accessor type is {@code ALL}, false - otherwise
     */
    public boolean isAll() {
        return this.equals(ALL);
    }
}
