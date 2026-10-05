package dev.krakenied.kolour.normalizers;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class DummyNormalizer implements Normalizer {

    public static final DummyNormalizer INSTANCE = new DummyNormalizer();

    private DummyNormalizer() {
    }

    @Override
    public int normalize(final double value, final int targetMin, final int targetMax) {
        return (int) Math.round(value);
    }

    @Override
    public double denormalize(int value, double sourceMin, double sourceMax) {
        return value;
    }
}
