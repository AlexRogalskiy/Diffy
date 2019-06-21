/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
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
package com.wildbeeslabs.sensiblemetrics.diffy.common.utils.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Represents a collection of 64 boolean (on/off) flags.  Individual flags
 * are represented by powers of 2.  For example,<br>
 * Flag 1 = 1<br>
 * Flag 2 = 2<br>
 * Flag 3 = 4<br>
 * Flag 4 = 8<br><br>
 * or using shift operator to make numbering easier:<br>
 * Flag 1 = 1 &lt;&lt; 0<br>
 * Flag 2 = 1 &lt;&lt; 1<br>
 * Flag 3 = 1 &lt;&lt; 2<br>
 * Flag 4 = 1 &lt;&lt; 3<br>
 *
 * <p>
 * There cannot be a flag with a value of 3 because that represents Flag 1
 * and Flag 2 both being on/true.
 * </p>
 *
 * @version $Revision: 1739356 $
 */
@Data
@EqualsAndHashCode
public class Flags implements Serializable, Cloneable {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -2597453921103632108L;

    /**
     * Represents the current flag state.
     */
    private long flags = 0;

    /**
     * Create a new Flags object.
     */
    public Flags() {
        super();
    }

    /**
     * Initialize a new Flags object with the given flags.
     *
     * @param flags collection of boolean flags to represent.
     */
    public Flags(long flags) {
        super();
        this.flags = flags;
    }

    /**
     * Tests whether the given flag is on.  If the flag is not a power of 2
     * (ie. 3) this tests whether the combination of flags is on.
     *
     * @param flag Flag value to check.
     * @return whether the specified flag value is on.
     */
    public boolean isOn(long flag) {
        return (this.flags & flag) == flag;
    }

    /**
     * Tests whether the given flag is off.  If the flag is not a power of 2
     * (ie. 3) this tests whether the combination of flags is off.
     *
     * @param flag Flag value to check.
     * @return whether the specified flag value is off.
     */
    public boolean isOff(long flag) {
        return (this.flags & flag) == 0;
    }

    /**
     * Turns on the given flag.  If the flag is not a power of 2 (ie. 3) this
     * turns on multiple flags.
     *
     * @param flag Flag value to turn on.
     */
    public void turnOn(long flag) {
        this.flags |= flag;
    }

    /**
     * Turns off the given flag.  If the flag is not a power of 2 (ie. 3) this
     * turns off multiple flags.
     *
     * @param flag Flag value to turn off.
     */
    public void turnOff(long flag) {
        this.flags &= ~flag;
    }

    /**
     * Turn off all flags.
     */
    public void turnOffAll() {
        this.flags = 0;
    }

    /**
     * Turn off all flags.  This is a synonym for <code>turnOffAll()</code>.
     *
     * @since Validator 1.1.1
     */
    public void clear() {
        this.flags = 0;
    }

    /**
     * Turn on all 64 flags.
     */
    public void turnOnAll() {
        this.flags = 0xFFFFFFFFFFFFFFFFl;
    }

    /**
     * Clone this Flags object.
     *
     * @return a copy of this object.
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Couldn't clone Flags object.");
        }
    }

    /**
     * Returns a 64 length String with the first flag on the right and the
     * 64th flag on the left.  A 1 indicates the flag is on, a 0 means it's
     * off.
     *
     * @return string representation of this object.
     */
    @Override
    public String toString() {
        StringBuilder bin = new StringBuilder(Long.toBinaryString(this.flags));
        for (int i = 64 - bin.length(); i > 0; i--) {
            bin.insert(0, "0");
        }
        return bin.toString();
    }
}
