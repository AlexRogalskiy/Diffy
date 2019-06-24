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
package com.wildbeeslabs.sensiblemetrics.diffy.core.property.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Visibility type {@link Enum}
 */
@Getter
@RequiredArgsConstructor
public enum VisibilityType {
    PUBLIC(1),
    PACKAGE_PRIVATE(0),
    PROTECTED(4),
    PRIVATE(2);

    private final int mask;

    public int getRange() {
        return 7;
    }

    public boolean isDefault() {
        return this.equals(PACKAGE_PRIVATE);
    }

    public boolean isPublic() {
        return (this.mask & 1) != 0;
    }

    public boolean isProtected() {
        return (this.mask & 4) != 0;
    }

    public boolean isPackagePrivate() {
        return !this.isPublic() && !this.isPrivate() && !this.isProtected();
    }

    public boolean isPrivate() {
        return (this.mask & 2) != 0;
    }

    public VisibilityType expandTo(final VisibilityType visibility) {
        switch (visibility) {
            case PUBLIC:
                return PUBLIC;
            case PROTECTED:
                return this == PUBLIC ? PUBLIC : visibility;
            case PACKAGE_PRIVATE:
                return this == PRIVATE ? PACKAGE_PRIVATE : this;
            case PRIVATE:
                return this;
            default:
                throw new IllegalStateException("Unexpected visibility: " + visibility);
        }
    }
}
