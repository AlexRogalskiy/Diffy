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
package com.wildbeeslabs.sensiblemetrics.diffy.common.enumeration;    // Nested types

/**
 * Default url type {@link Enum}
 */
public enum UrlType {

    SCHEME {
        @Override
        public boolean isAllowed(int c) {
            return this.isAlpha(c) || this.isDigit(c) || '+' == c || '-' == c || '.' == c;
        }
    },
    AUTHORITY {
        @Override
        public boolean isAllowed(int c) {
            return this.isUnreserved(c) || this.isSubDelimiter(c) || ':' == c || '@' == c;
        }
    },
    USER_INFO {
        @Override
        public boolean isAllowed(int c) {
            return this.isUnreserved(c) || this.isSubDelimiter(c) || ':' == c;
        }
    },
    HOST_IPV4 {
        @Override
        public boolean isAllowed(int c) {
            return this.isUnreserved(c) || this.isSubDelimiter(c);
        }
    },
    HOST_IPV6 {
        @Override
        public boolean isAllowed(int c) {
            return this.isUnreserved(c) || this.isSubDelimiter(c) || '[' == c || ']' == c || ':' == c;
        }
    },
    PORT {
        @Override
        public boolean isAllowed(int c) {
            return this.isDigit(c);
        }
    },
    PATH {
        @Override
        public boolean isAllowed(int c) {
            return this.isPchar(c) || '/' == c;
        }
    },
    PATH_SEGMENT {
        @Override
        public boolean isAllowed(int c) {
            return this.isPchar(c);
        }
    },
    QUERY {
        @Override
        public boolean isAllowed(int c) {
            return this.isPchar(c) || '/' == c || '?' == c;
        }
    },
    QUERY_PARAM {
        @Override
        public boolean isAllowed(int c) {
            if ('=' == c || '&' == c) {
                return false;
            }
            return this.isPchar(c) || '/' == c || '?' == c;
        }
    },
    FRAGMENT {
        @Override
        public boolean isAllowed(int c) {
            return this.isPchar(c) || '/' == c || '?' == c;
        }
    },
    URI {
        @Override
        public boolean isAllowed(int c) {
            return this.isUnreserved(c);
        }
    };

    /**
     * Indicates whether the given character is allowed in this URI component.
     *
     * @return {@code true} if the character is allowed; {@code false} otherwise
     */
    public abstract boolean isAllowed(int c);

    /**
     * Indicates whether the given character is in the {@code ALPHA} set.
     *
     * @see <a href="https://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
     */
    protected boolean isAlpha(int c) {
        return (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z');
    }

    /**
     * Indicates whether the given character is in the {@code DIGIT} set.
     *
     * @see <a href="https://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
     */
    protected boolean isDigit(int c) {
        return (c >= '0' && c <= '9');
    }

    /**
     * Indicates whether the given character is in the {@code gen-delims} set.
     *
     * @see <a href="https://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
     */
    protected boolean isGenericDelimiter(int c) {
        return (':' == c || '/' == c || '?' == c || '#' == c || '[' == c || ']' == c || '@' == c);
    }

    /**
     * Indicates whether the given character is in the {@code sub-delims} set.
     *
     * @see <a href="https://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
     */
    protected boolean isSubDelimiter(int c) {
        return ('!' == c || '$' == c || '&' == c || '\'' == c || '(' == c || ')' == c || '*' == c || '+' == c || ',' == c || ';' == c || '=' == c);
    }

    /**
     * Indicates whether the given character is in the {@code reserved} set.
     *
     * @see <a href="https://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
     */
    protected boolean isReserved(int c) {
        return (this.isGenericDelimiter(c) || this.isSubDelimiter(c));
    }

    /**
     * Indicates whether the given character is in the {@code unreserved} set.
     *
     * @see <a href="https://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
     */
    protected boolean isUnreserved(int c) {
        return (this.isAlpha(c) || this.isDigit(c) || '-' == c || '.' == c || '_' == c || '~' == c);
    }

    /**
     * Indicates whether the given character is in the {@code pchar} set.
     *
     * @see <a href="https://www.ietf.org/rfc/rfc3986.txt">RFC 3986, appendix A</a>
     */
    protected boolean isPchar(int c) {
        return (this.isUnreserved(c) || this.isSubDelimiter(c) || ':' == c || '@' == c);
    }
}
