package dev.krakenied.kolour.normalizers;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class SimpleNormalizer implements Normalizer {

    private final double min;
    private final double max;

    public SimpleNormalizer(final double min, final double max) {
        if (min >= max) {
            throw new IllegalArgumentException("min must be less than max");
        }

        this.min = min;
        this.max = max;
    }

    @Override
    public int normalize(final double value, final int targetMin, final int targetMax) {
        return targetMin + (int) Math.round((value - this.min) * (targetMax - targetMin) / (this.max - this.min));
    }

    @Override
    public double denormalize(final int value, final double sourceMin, final double sourceMax) {
        return (value - sourceMin) * (this.max - this.min) / (sourceMax - sourceMin) + this.min;
    }
}
