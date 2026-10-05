package dev.krakenied.kolour.object;

import dev.krakenied.kolour.util.DebugUtil;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record IntBitSet(int value, int size) {

    public IntBitSet {
        if ((value >> size) != 0) {
            throw new IllegalArgumentException("invalid value " + DebugUtil.toBinaryString(value));
        }
    }
}
