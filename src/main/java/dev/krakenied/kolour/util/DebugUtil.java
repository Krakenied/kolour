package dev.krakenied.kolour.util;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DebugUtil {

    private static final String BINARY_STRING_FORMAT = "%32s";

    private DebugUtil() {
        throw new UnsupportedOperationException("Utility classes cannot be instanced");
    }

    public static String toBinaryString(final int i) {
        return String.format(BINARY_STRING_FORMAT, Integer.toBinaryString(i)).replace(' ', '0');
    }
}
