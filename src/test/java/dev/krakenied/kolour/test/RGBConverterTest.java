package dev.krakenied.kolour.test;

import dev.krakenied.kolour.converters2.RGBConverter;
import dev.krakenied.kolour.object.IntBitSet;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@NullMarked
class RGBConverterTest {

    private final RGBConverter converter = new RGBConverter();

    @Test
    void shouldConvertRgb565() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(128, 8),
                new IntBitSet(64, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(5, 6, 5)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(0xFF, result.get(0).value());
        assertEquals(0x82, result.get(1).value());
        assertEquals(0x42, result.get(2).value());

        assertEquals(8, result.get(0).size());
        assertEquals(8, result.get(1).size());
        assertEquals(8, result.get(2).size());
    }

    @Test
    void shouldConvertRgb888WithoutLoss() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(0x12, 8),
                new IntBitSet(0x34, 8),
                new IntBitSet(0x56, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(0x12, result.get(0).value());
        assertEquals(0x34, result.get(1).value());
        assertEquals(0x56, result.get(2).value());
    }

    @Test
    void shouldConvertBlack() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(0, 8),
                new IntBitSet(0, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(5, 6, 5)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(0, result.get(0).value());
        assertEquals(0, result.get(1).value());
        assertEquals(0, result.get(2).value());
    }

    @Test
    void shouldConvertWhite() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(5, 6, 5)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(255, result.get(0).value());
        assertEquals(255, result.get(1).value());
        assertEquals(255, result.get(2).value());
    }

    @Test
    void shouldQuantizeRgb565Correctly() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(128, 8),
                new IntBitSet(64, 8)
        );

        final List<IntBitSet> result = this.converter.fromRGB(
                rgb,
                List.of(5, 6, 5)
        );

        assertEquals(31, result.get(0).value());
        assertEquals(32, result.get(1).value());
        assertEquals(8, result.get(2).value());

        assertEquals(5, result.get(0).size());
        assertEquals(6, result.get(1).size());
        assertEquals(5, result.get(2).size());
    }

    @Test
    void shouldPackMaximumValuesForDifferentChannelSizes() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> result = this.converter.fromRGB(
                rgb,
                List.of(3, 5, 7)
        );

        assertEquals(7, result.get(0).value());
        assertEquals(31, result.get(1).value());
        assertEquals(127, result.get(2).value());

        assertEquals(3, result.get(0).size());
        assertEquals(5, result.get(1).size());
        assertEquals(7, result.get(2).size());
    }

    @Test
    void shouldDequantizeMaximumValuesTo255() {
        final List<IntBitSet> packed = List.of(
                new IntBitSet(7, 3),
                new IntBitSet(31, 5),
                new IntBitSet(127, 7)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(255, result.get(0).value());
        assertEquals(255, result.get(1).value());
        assertEquals(255, result.get(2).value());
    }

    @Test
    void shouldDequantizeZeroValuesToZero() {
        final List<IntBitSet> packed = List.of(
                new IntBitSet(0, 3),
                new IntBitSet(0, 5),
                new IntBitSet(0, 7)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(0, result.get(0).value());
        assertEquals(0, result.get(1).value());
        assertEquals(0, result.get(2).value());
    }

    @Test
    void shouldRoundTripRgb565() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(128, 8),
                new IntBitSet(64, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(5, 6, 5)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(0xFF, result.get(0).value());
        assertEquals(0x82, result.get(1).value());
        assertEquals(0x42, result.get(2).value());
    }
}
