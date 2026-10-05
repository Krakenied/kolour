package dev.krakenied.kolour.converters2;

import dev.krakenied.kolour.object.IntBitSet;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public final class RGBConverter extends ColorConverter {

    public RGBConverter() {
        for (int i = 0; i < 3; i++) {
            this.fromRGBFunctions.add(RGBConverter::fromRGB);
            this.toRGBFunctions.add(RGBConverter::toRGB);
        }
    }

    private static IntBitSet fromRGB(final List<IntBitSet> bitSetList, final List<Integer> bitSizeList, final int index) {
        final IntBitSet bitSet = bitSetList.get(index);
        final int bitSize = bitSizeList.get(index);

        return new IntBitSet(
                quantize(bitSet.value(), bitSize),
                bitSize
        );
    }

    private static IntBitSet toRGB(final List<IntBitSet> bitSetList, final List<Integer> bitSizeList, final int index) {
        final IntBitSet bitSet = bitSetList.get(index);
        final int targetBitSize = bitSizeList.get(index);
        final int bitSetSize = bitSet.size();

        final int mask = (1 << bitSetSize) - 1;
        final int quantized = bitSet.value() & mask;

        return new IntBitSet(
                dequantize(quantized, bitSetSize),
                targetBitSize
        );
    }

    private static int quantize(final int value, final int bits) {
        final int max = (1 << bits) - 1;
        return (value * max + 127) / 255;
    }

    private static int dequantize(final int value, final int bits) {
        final int max = (1 << bits) - 1;
        return (value * 255 + max / 2) / max;
    }
}
