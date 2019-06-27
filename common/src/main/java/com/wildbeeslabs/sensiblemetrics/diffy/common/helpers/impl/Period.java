package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

@Getter
@EqualsAndHashCode
@ToString
public class Period {
    /**
     * Default start {@link Date} period
     */
    private final Date start;
    /**
     * Default end {@link Date} period
     */
    private final Date end;

    /**
     * @param start - pocz№tek odcinka czasu
     * @param end   - koniec odcinka czasu, nie moїe byж wczeњniejszy niї start
     * @throws IllegalArgumentException, jeїeli start jest wiкkszy niї end
     * @throws NullPointerException,     jeїeli start lub end ma wartoњж null.
     */
    public Period(final Date start, final Date end) {
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + " po " + end);
        }
        this.start = start;
        this.end = end;
    }

    private static class SerializationProxy implements Serializable {

        private static final long serialVersionUID = 234098243823485285L;

        private final Date start;
        private final Date end;

        public SerializationProxy(final Period p) {
            this.start = p.start;
            this.end = p.end;
        }

        private Object readResolve() {
            return new Period(start, end);
        }
    }

    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    private void readObject(final ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("ERROR: invalid serialization proxy");
    }
}
