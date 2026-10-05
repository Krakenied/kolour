package dev.krakenied.kolour.converters2;

import dev.krakenied.kolour.object.IntBitSet;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public class HSVConverter extends ColorConverter {
    public HSVConverter() {
        super();

        this.toRGBFunctions.add(HSVConverter::hueFromRGB);
        this.toRGBFunctions.add(HSVConverter::saturationFromRGB);
        this.toRGBFunctions.add(HSVConverter::valueFromRGB);

        this.fromRGBFunctions.add(HSVConverter::HSVToRed);
        this.fromRGBFunctions.add(HSVConverter::HSVToGreen);
        this.fromRGBFunctions.add(HSVConverter::HSVToBlue);
    }

    private record FloatTuple3(float first, float second, float third) {}

    private static FloatTuple3 getNormalizedChannels(List<IntBitSet> bitSetList) {
        return new FloatTuple3(
            (float) bitSetList.get(0).value() / bitSetList.get(0).size(),
            (float) bitSetList.get(1).value() / bitSetList.get(1).size(),
            (float) bitSetList.get(2).value() / bitSetList.get(2).size()
        );
    }

    private static IntBitSet hueFromRGB(List<IntBitSet> bitSetList, List<Integer> bitSizeList, int index) {
        FloatTuple3 norm = getNormalizedChannels(bitSetList);
        float min = Math.min(Math.min(norm.first, norm.second), norm.third);
        float max = norm.first;
        int max_idx = 0;

        if (norm.second > max && norm.second > norm.third) {
            max = norm.second;
            max_idx = 1;
        } else if (norm.third > max) {
            max = norm.third;
            max_idx = 2;
        }

        float diff = max - min;

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
                (int) (hue / TAU * ((1 << bitSizeList.get(index) - 1))),
                bitSizeList.get(index)
        );
    }

    private static IntBitSet saturationFromRGB(List<IntBitSet> bitSetList, List<Integer> bitSizeList, int index) {
        FloatTuple3 norm = getNormalizedChannels(bitSetList);
        float min = Math.min(Math.min(norm.first, norm.second), norm.third);
        float max = Math.max(Math.max(norm.first, norm.second), norm.third);

        float sat = 0.0f;

        if (max > 0.0f) {
            sat = (max - min) / max;
        }

        return new IntBitSet(
                (int) (sat * ((1 << bitSizeList.get(index)) - 1)),
                bitSizeList.get(index)
        );
    }

    private static IntBitSet valueFromRGB(List<IntBitSet> bitSetList, List<Integer> bitSizeList, int index) {
        FloatTuple3 norm = getNormalizedChannels(bitSetList);

        return new IntBitSet(
                (int) (Math.max(Math.max(norm.first, norm.second), norm.third) * ((1 << bitSizeList.get(index)) - 1)),
                bitSizeList.get(index)
        );
    }

    private static FloatTuple3 ChromaIntermediateMatch(final FloatTuple3 normalizedHSV) {
        float hue = normalizedHSV.first * 360.0f;

        float chroma = normalizedHSV.second * normalizedHSV.third;
        float intermediate = (float) (chroma * (1 - Math.abs(hue / 60.0 % 2 - 1)));
        float match = normalizedHSV.third - chroma;

        return new FloatTuple3(chroma, intermediate, match);
    }

    private static IntBitSet HSVToRed(List<IntBitSet> bitSetList, List<Integer> bitSizeList, int index) {
        FloatTuple3 norm = getNormalizedChannels(bitSetList);
        FloatTuple3 cim = ChromaIntermediateMatch(norm);
        float hue = norm.first * 360.0f;

        float r = 0.0f;
        if (hue < 60.0f || hue >= 300.0f) {
            r = cim.first;
        } else if (hue < 120.0f || hue >= 240.0f) {
            r = cim.second;
        }

        return new IntBitSet(
                (int) ((r + cim.third) * ((1 << bitSizeList.get(index) - 1))),
                bitSizeList.get(index)
        );
    }

    private static IntBitSet HSVToGreen(List<IntBitSet> bitSetList, List<Integer> bitSizeList, int index) {
        FloatTuple3 norm = getNormalizedChannels(bitSetList);
        FloatTuple3 cim = ChromaIntermediateMatch(norm);
        float hue = norm.first * 360.0f;

        float g = 0.0f;
        if (hue >= 60.0f && hue < 180.0f) {
            g = cim.first;
        } else if (hue < 240.0f) {
            g = cim.second;
        }

        return new IntBitSet(
                (int) ((g + cim.third) * ((1 << bitSizeList.get(index) - 1))),
                bitSizeList.get(index)
        );
    }

    private static IntBitSet HSVToBlue(List<IntBitSet> bitSetList, List<Integer> bitSizeList, int index) {
        FloatTuple3 norm = getNormalizedChannels(bitSetList);
        FloatTuple3 cim = ChromaIntermediateMatch(norm);
        float hue = norm.first * 360.0f;

        float b = 0.0f;
        if (hue >= 180.0f && hue < 300.0f) {
            b = cim.first;
        } else if (hue > 180.0f) {
            b = cim.second;
        }

        return new IntBitSet(
                (int) ((b + cim.third) * ((1 << bitSizeList.get(index) - 1))),
                bitSizeList.get(index)
        );
    }
}
