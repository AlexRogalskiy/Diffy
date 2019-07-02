package com.wildbeeslabs.sensiblemetrics.diffy.common.enumeration;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Default endianness type {@link Enum}
 *
 * @author alexander.rogalskiy
 * @version 1.0
 * @since 2017-12-12
 */
public enum Endianness {
    LITTLE {
        @Override
        public short readShort(final InputStream stream) throws IOException {
            final int low = readByte(stream) & 0xff;
            final int high = readByte(stream);
            return (short) ((high << 8) | low);
        }
    },
    BIG {
        @Override
        public short readShort(final InputStream stream) throws IOException {
            final int high = readByte(stream);
            final int low = readByte(stream) & 0xff;
            return (short) ((high << 8) | low);
        }
    };

    private static int readByte(final InputStream stream) throws IOException {
        final DataInputStream dataStream = new DataInputStream(stream);
        return dataStream.readByte();
    }

    public abstract short readShort(final InputStream stream) throws IOException;
}
