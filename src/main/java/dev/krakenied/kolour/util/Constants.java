package dev.krakenied.kolour.util;

import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public final class Constants {

    public static final List<Integer> RGB_BIT_SIZE_LIST = List.of(8, 8, 8);
    public static final int RGB_CHAN_COUNT = 3;
    public static final int RGB_CHAN_MIN = 0x00;
    public static final int RGB_CHAN_MAX = 0xFF;

    private Constants() {
        throw new UnsupportedOperationException("Utility classes cannot be instanced");
    }
}
