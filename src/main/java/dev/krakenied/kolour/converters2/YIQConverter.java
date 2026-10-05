package dev.krakenied.kolour.converters2;

import dev.krakenied.kolour.normalizers.Normalizer;
import dev.krakenied.kolour.object.IntBitSet;
import dev.krakenied.kolour.normalizers.SimpleNormalizer;
import dev.krakenied.kolour.util.Constants;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@SuppressWarnings("DuplicatedCode")
@NullMarked
public final class YIQConverter extends ColorConverter {

    private static final double Y_MIN = Constants.RGB_MIN;
    private static final double Y_MAX = Constants.RGB_MAX;
    private static final Normalizer Y_NORMALIZER = new SimpleNormalizer(Y_MIN, Y_MAX);

    private static final double I_MIN = -0.599d * Constants.RGB_MAX;
    private static final double I_MAX = 0.599d * Constants.RGB_MAX;
    private static final Normalizer I_NORMALIZER = new SimpleNormalizer(I_MIN, I_MAX);

    private static final double Q_MIN = -0.5251d * Constants.RGB_MAX;
    private static final double Q_MAX = 0.5251d * Constants.RGB_MAX;
    private static final Normalizer Q_NORMALIZER = new SimpleNormalizer(Q_MIN, Q_MAX);

    public YIQConverter() {
        this.fromRGBFunctions.add(YIQConverter::y);
        this.fromRGBFunctions.add(YIQConverter::i);
        this.fromRGBFunctions.add(YIQConverter::q);

        this.toRGBFunctions.add(YIQConverter::r);
        this.toRGBFunctions.add(YIQConverter::g);
        this.toRGBFunctions.add(YIQConverter::b);
    }

    private static IntBitSet y(final List<IntBitSet> bitSetList, final List<Integer> bitSizeList, final int index) {
        return normalizeHelper(bitSetList, bitSizeList.get(index), 0.3d, 0.59d, 0.11d, Y_NORMALIZER);
    }

    private static IntBitSet i(final List<IntBitSet> bitSetList, final List<Integer> bitSizeList, final int index) {
        return normalizeHelper(bitSetList, bitSizeList.get(index), 0.599d, -0.2773d, -0.3217d, I_NORMALIZER);
    }

    private static IntBitSet q(final List<IntBitSet> bitSetList, final List<Integer> bitSizeList, final int index) {
        return normalizeHelper(bitSetList, bitSizeList.get(index), 0.213d, -0.5251d, 0.3121d, Q_NORMALIZER);
    }

    private static IntBitSet r(final List<IntBitSet> bitSetList, final List<Integer> bitSizeList, final int index) {
        return denormalizeHelper(bitSetList, bitSizeList.get(index), 1.0d, 0.9469d, 0.6236d);
    }

    private static IntBitSet g(final List<IntBitSet> bitSetList, final List<Integer> bitSizeList, final int index) {
        return denormalizeHelper(bitSetList, bitSizeList.get(index), 1.0d, -0.2748d, -0.6357d);
    }

    private static IntBitSet b(final List<IntBitSet> bitSetList, final List<Integer> bitSizeList, final int index) {
        return denormalizeHelper(bitSetList, bitSizeList.get(index), 1.0d, -1.1d, 1.7d);
    }

    private static IntBitSet normalizeHelper(final List<IntBitSet> bitSetList, final int bitSize, final double d, final double e, final double f, final Normalizer normalizer) {
        final int i = bitSetList.get(0).value();
        final int j = bitSetList.get(1).value();
        final int k = bitSetList.get(2).value();

        final double result = d * i + e * j + f * k;

        return new IntBitSet(
                normalizer.normalize(result, 0, (1 << bitSize) - 1),
                bitSize
        );
    }

    @SuppressWarnings("SameParameterValue")
    private static IntBitSet denormalizeHelper(final List<IntBitSet> bitSetList, final int bitSize, final double d, final double e, final double f) {
        final double i = Y_NORMALIZER.denormalize(bitSetList.get(0).value(), 0, (1 << bitSetList.get(0).size()) - 1);
        final double j = I_NORMALIZER.denormalize(bitSetList.get(1).value(), 0, (1 << bitSetList.get(1).size()) - 1);
        final double k = Q_NORMALIZER.denormalize(bitSetList.get(2).value(), 0, (1 << bitSetList.get(2).size()) - 1);

        final double result = d * i + e * j + f * k;

        return new IntBitSet(
                Math.clamp(Math.round(result), 0, (1 << bitSize) - 1),
                bitSize
        );
    }
}
