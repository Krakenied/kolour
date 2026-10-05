package dev.krakenied.kolour.util;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class Constants {

    public static final int RGB_MIN = 0x00;
    public static final int RGB_MAX = 0xFF;

    private Constants() {
        throw new UnsupportedOperationException("Utility classes cannot be instanced");
    }
}
