package dev.krakenied.kolour.converters;

import dev.krakenied.kolour.object.IntBitSet;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@NullMarked
public final class HSVConverter extends ColorConverter<Object, Object> {

    public HSVConverter() {
        super();

        this.fromRGBFunctions.add(HSVConverter::hueFromRGB);
        this.fromRGBFunctions.add(HSVConverter::saturationFromRGB);
        this.fromRGBFunctions.add(HSVConverter::valueFromRGB);

        this.toRGBFunctions.add(HSVConverter::HSVToRed);
        this.toRGBFunctions.add(HSVConverter::HSVToGreen);
        this.toRGBFunctions.add(HSVConverter::HSVToBlue);
    }

    private record FloatTuple3(float first, float second, float third) {
    }

    private static FloatTuple3 getNormalizedChannels(final List<IntBitSet> bitSetList) {
        return new FloatTuple3(
                (float) bitSetList.get(0).value() / ((1 << bitSetList.get(0).size()) - 1),
                (float) bitSetList.get(1).value() / ((1 << bitSetList.get(1).size()) - 1),
                (float) bitSetList.get(2).value() / ((1 << bitSetList.get(2).size()) - 1)
        );
    }

    private static IntBitSet hueFromRGB(final List<IntBitSet> bitSetList, final List<Integer> bitSizeList, final int index, final @Nullable Object data) {
        final FloatTuple3 norm = getNormalizedChannels(bitSetList);
        final float min = Math.min(Math.min(norm.first, norm.second), norm.third);

        final float max;
        final int max_idx;

        if (norm.second > norm.first && norm.second > norm.third) {
            max = norm.second;
            max_idx = 1;
        } else if (norm.third > norm.first) {
            max = norm.third;
            max_idx = 2;
        } else {
            max = norm.first;
            max_idx = 0;
        }

        final float diff = max - min;

        if (diff == 0.0f) {
            return new IntBitSet(
                    0,
                    bitSizeList.get(index)
            );
        }

        float hue = 60.0f * switch (max_idx) {
            case 0 -> ((norm.second - norm.third) / diff) % 6;
            case 1 -> (norm.third - norm.first) / diff + 2.0f;
            case 2 -> (norm.first - norm.second) / diff + 4.0f;
            default -> 0.0f;
        };

        final float TAU = 360.0f;

        while (hue < 0.0f) {
            hue += TAU;
        }

        return new IntBitSet(
                (int) (hue / TAU * ((1 << bitSizeList.get(index)) - 1)),
                bitSizeList.get(index)
        );
    }

    private static IntBitSet saturationFromRGB(final List<IntBitSet> bitSetList, final List<Integer> bitSizeList, final int index, final @Nullable Object data) {
        final FloatTuple3 norm = getNormalizedChannels(bitSetList);
        final float min = Math.min(Math.min(norm.first, norm.second), norm.third);
        final float max = Math.max(Math.max(norm.first, norm.second), norm.third);

        final float sat;

        if (max > 0.0f) {
            sat = (max - min) / max;
        } else {
            sat = 0.0f;
        }

        return new IntBitSet(
                (int) (sat * ((1 << bitSizeList.get(index)) - 1)),
                bitSizeList.get(index)
        );
    }

    private static IntBitSet valueFromRGB(final List<IntBitSet> bitSetList, final List<Integer> bitSizeList, final int index, final @Nullable Object data) {
        final FloatTuple3 norm = getNormalizedChannels(bitSetList);

        return new IntBitSet(
                (int) (Math.max(Math.max(norm.first, norm.second), norm.third) * ((1 << bitSizeList.get(index)) - 1)),
                bitSizeList.get(index)
        );
    }

    private static FloatTuple3 ChromaIntermediateMatch(final FloatTuple3 normalizedHSV) {
        final float hue = normalizedHSV.first * 360.0f;

        final float chroma = normalizedHSV.second * normalizedHSV.third;
        final float intermediate = (float) (chroma * (1 - Math.abs(hue / 60.0 % 2 - 1)));
        final float match = normalizedHSV.third - chroma;

        return new FloatTuple3(chroma, intermediate, match);
    }

    private static IntBitSet HSVToRed(final List<IntBitSet> bitSetList, final List<Integer> bitSizeList, final int index, final @Nullable Object data) {
        final FloatTuple3 norm = getNormalizedChannels(bitSetList);
        final FloatTuple3 cim = ChromaIntermediateMatch(norm);
        final float hue = norm.first * 360.0f;

        final float r;

        if (hue < 60.0f || hue >= 300.0f) {
            r = cim.first;
        } else if (hue < 120.0f || hue >= 240.0f) {
            r = cim.second;
        } else {
            r = 0.0f;
        }

        return new IntBitSet(
                (int) ((r + cim.third) * ((1 << bitSizeList.get(index)) - 1)),
                bitSizeList.get(index)
        );
    }

    private static IntBitSet HSVToGreen(final List<IntBitSet> bitSetList, final List<Integer> bitSizeList, final int index, final @Nullable Object data) {
        final FloatTuple3 norm = getNormalizedChannels(bitSetList);
        final FloatTuple3 cim = ChromaIntermediateMatch(norm);
        final float hue = norm.first * 360.0f;

        final float g;

        if (hue >= 60.0f && hue < 180.0f) {
            g = cim.first;
        } else if (hue < 240.0f) {
            g = cim.second;
        } else {
            g = 0.0f;
        }

        return new IntBitSet(
                (int) ((g + cim.third) * ((1 << bitSizeList.get(index)) - 1)),
                bitSizeList.get(index)
        );
    }

    private static IntBitSet HSVToBlue(final List<IntBitSet> bitSetList, final List<Integer> bitSizeList, final int index, final @Nullable Object data) {
        final FloatTuple3 norm = getNormalizedChannels(bitSetList);
        final FloatTuple3 cim = ChromaIntermediateMatch(norm);
        final float hue = norm.first * 360.0f;

        final float b;

        if (hue >= 180.0f && hue < 300.0f) {
            b = cim.first;
        } else if (hue > 120.0f) {
            b = cim.second;
        } else {
            b = 0.0f;
        }

        return new IntBitSet(
                (int) ((b + cim.third) * ((1 << bitSizeList.get(index)) - 1)),
                bitSizeList.get(index)
        );
    }
}
