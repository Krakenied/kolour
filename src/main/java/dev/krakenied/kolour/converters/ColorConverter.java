package dev.krakenied.kolour.converters;

import org.jspecify.annotations.NullMarked;

@NullMarked
public abstract class ColorConverter {

    protected final int channelCount;

    public ColorConverter(final int channelCount) {
        if (channelCount <= 0) {
            throw new IllegalArgumentException("channel count must be positive");
        }

        this.channelCount = channelCount;
    }

    public final int channelCount() {
        return this.channelCount;
    }

    public abstract int fromRGB(final int r, final int g, final int b, final int[] channelBitSizes);

    public abstract int toRGB(final int value, final int[] channelBitSizes);
}
