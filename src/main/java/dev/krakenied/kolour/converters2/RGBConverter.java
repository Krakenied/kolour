package dev.krakenied.kolour.converters2;

import dev.krakenied.kolour.object.IntBitSet;
import org.jspecify.annotations.NullMarked;

@NullMarked
public final class RGBConverter extends ColorConverter {

    public RGBConverter() {
        final Function fromRGBFunction = (bitSetList, bitSizeList, index) -> {
            final IntBitSet bitSet = bitSetList.get(index);
            final int bitSize = bitSizeList.get(index);

            return new IntBitSet(
                    quantize(bitSet.value(), bitSize),
                    bitSize
            );
        };

        this.fromRGBFunctions.add(fromRGBFunction);
        this.fromRGBFunctions.add(fromRGBFunction);
        this.fromRGBFunctions.add(fromRGBFunction);

        final Function toRGBFunction = (bitSetList, bitSizeList, index) -> {
            final IntBitSet bitSet = bitSetList.get(index);
            final int targetBitSize = bitSizeList.get(index);
            final int bitSetSize = bitSet.size();

            final int mask = (1 << bitSetSize) - 1;
            final int quantized = bitSet.value() & mask;

            return new IntBitSet(
                    dequantize(quantized, bitSetSize),
                    targetBitSize
            );
        };

        this.toRGBFunctions.add(toRGBFunction);
        this.toRGBFunctions.add(toRGBFunction);
        this.toRGBFunctions.add(toRGBFunction);
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
