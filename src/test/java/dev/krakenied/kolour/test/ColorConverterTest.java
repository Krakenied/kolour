package dev.krakenied.kolour.test;

import dev.krakenied.kolour.converters2.RGBConverter;
import dev.krakenied.kolour.object.IntBitSet;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@NullMarked
class ColorConverterTest {

    private final RGBConverter converter = new RGBConverter();

    @Test
    void shouldAlwaysReturnThreeChannelsFromRgb() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(10, 8),
                new IntBitSet(20, 8),
                new IntBitSet(30, 8)
        );

        final List<IntBitSet> result = this.converter.fromRGB(
                rgb,
                List.of(5, 6, 5)
        );

        assertEquals(3, result.size());
    }

    @Test
    void shouldAlwaysReturnThreeChannelsToRgb() {
        final List<IntBitSet> packed = List.of(
                new IntBitSet(1, 5),
                new IntBitSet(2, 6),
                new IntBitSet(3, 5)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(3, result.size());
    }

    @Test
    void shouldAlwaysConvertToEightBitRgb() {
        final List<IntBitSet> packed = List.of(
                new IntBitSet(1, 1),
                new IntBitSet(3, 2),
                new IntBitSet(7, 3)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(8, result.get(0).size());
        assertEquals(8, result.get(1).size());
        assertEquals(8, result.get(2).size());
    }

    @Test
    void shouldUseIndividualBitSizeForEachChannel() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> result = this.converter.fromRGB(
                rgb,
                List.of(1, 4, 8)
        );

        assertEquals(1, result.get(0).size());
        assertEquals(4, result.get(1).size());
        assertEquals(8, result.get(2).size());

        assertEquals(1, result.get(0).value());
        assertEquals(15, result.get(1).value());
        assertEquals(255, result.get(2).value());
    }

    @Test
    void shouldPreserveChannelOrder() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(0, 8),
                new IntBitSet(128, 8)
        );

        final List<IntBitSet> result = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        assertEquals(255, result.get(0).value());
        assertEquals(0, result.get(1).value());
        assertEquals(128, result.get(2).value());
    }
}
