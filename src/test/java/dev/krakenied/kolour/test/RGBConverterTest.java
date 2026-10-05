package dev.krakenied.kolour.test;

import dev.krakenied.kolour.converters2.RGBConverter;
import dev.krakenied.kolour.object.IntBitSet;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void shouldQuantizeMinimumAndMaximumRgbValues() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(127, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> result = this.converter.fromRGB(
                rgb,
                List.of(5, 6, 5)
        );

        assertEquals(0, result.get(0).value());
        assertEquals(31, result.get(1).value());
        assertEquals(31, result.get(2).value());
    }

    @Test
    void shouldQuantizeMidpointValuesCorrectly() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(127, 8),
                new IntBitSet(127, 8),
                new IntBitSet(127, 8)
        );

        final List<IntBitSet> result = this.converter.fromRGB(
                rgb,
                List.of(5, 6, 5)
        );

        assertEquals(15, result.get(0).value());
        assertEquals(31, result.get(1).value());
        assertEquals(15, result.get(2).value());
    }

    @Test
    void shouldPreserveChannelSizesDuringQuantization() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(100, 8),
                new IntBitSet(150, 8),
                new IntBitSet(200, 8)
        );

        final List<IntBitSet> result = this.converter.fromRGB(
                rgb,
                List.of(2, 4, 8)
        );

        assertEquals(2, result.get(0).size());
        assertEquals(4, result.get(1).size());
        assertEquals(8, result.get(2).size());

        assertEquals(1, result.get(0).value());
        assertEquals(9, result.get(1).value());
        assertEquals(200, result.get(2).value());
    }

    @Test
    void shouldConvertRgb332() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(128, 8),
                new IntBitSet(64, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(3, 3, 2)
        );

        assertEquals(7, packed.get(0).value());
        assertEquals(4, packed.get(1).value());
        assertEquals(1, packed.get(2).value());

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(255, result.get(0).value());
        assertEquals(146, result.get(1).value());
        assertEquals(85, result.get(2).value());
    }

    @Test
    void shouldConvertRgb444() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(128, 8),
                new IntBitSet(64, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(4, 4, 4)
        );

        assertEquals(15, packed.get(0).value());
        assertEquals(8, packed.get(1).value());
        assertEquals(4, packed.get(2).value());

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(255, result.get(0).value());
        assertEquals(136, result.get(1).value());
        assertEquals(68, result.get(2).value());
    }

    @Test
    void shouldConvertRgb101010() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(128, 8),
                new IntBitSet(64, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(10, 10, 10)
        );

        assertEquals(1023, packed.get(0).value());
        assertEquals(514, packed.get(1).value());
        assertEquals(257, packed.get(2).value());

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(255, result.get(0).value());
        assertEquals(128, result.get(1).value());
        assertEquals(64, result.get(2).value());
    }

    @Test
    void shouldRoundTripBlackForDifferentChannelSizes() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(0, 8),
                new IntBitSet(0, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(1, 2, 3)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(0, result.get(0).value());
        assertEquals(0, result.get(1).value());
        assertEquals(0, result.get(2).value());
    }

    @Test
    void shouldRoundTripWhiteForDifferentChannelSizes() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(1, 2, 3)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(255, result.get(0).value());
        assertEquals(255, result.get(1).value());
        assertEquals(255, result.get(2).value());
    }

    @Test
    void shouldRoundTripRgb888Exactly() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(1, 8),
                new IntBitSet(127, 8),
                new IntBitSet(254, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(1, result.get(0).value());
        assertEquals(127, result.get(1).value());
        assertEquals(254, result.get(2).value());
    }

    @Test
    void shouldReturnEightBitRgbValuesAfterDequantization() {
        final List<IntBitSet> packed = List.of(
                new IntBitSet(15, 4),
                new IntBitSet(31, 5),
                new IntBitSet(63, 6)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(8, result.get(0).size());
        assertEquals(8, result.get(1).size());
        assertEquals(8, result.get(2).size());
    }

    @Test
    void shouldDequantizeDifferentChannelSizesIndependently() {
        final List<IntBitSet> packed = List.of(
                new IntBitSet(3, 2),
                new IntBitSet(7, 3),
                new IntBitSet(15, 4)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(255, result.get(0).value());
        assertEquals(255, result.get(1).value());
        assertEquals(255, result.get(2).value());
    }

    @Test
    void shouldQuantizeRgbValuesWithoutExceedingChannelMaximum() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> result = this.converter.fromRGB(
                rgb,
                List.of(1, 2, 3)
        );

        assertEquals(1, result.get(0).value());
        assertEquals(3, result.get(1).value());
        assertEquals(7, result.get(2).value());
    }

    @Test
    void shouldRoundQuantizationCorrectly() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(4, 8),
                new IntBitSet(128, 8),
                new IntBitSet(251, 8)
        );

        final List<IntBitSet> result = this.converter.fromRGB(
                rgb,
                List.of(2, 4, 6)
        );

        assertEquals(0, result.get(0).value());
        assertEquals(8, result.get(1).value());
        assertEquals(62, result.get(2).value());
    }

    @Test
    void shouldQuantizeAllEightBitValuesToValidFiveBitRange() {
        for (int value = 0; value <= 255; value++) {
            final List<IntBitSet> rgb = List.of(
                    new IntBitSet(value, 8),
                    new IntBitSet(value, 8),
                    new IntBitSet(value, 8)
            );

            final List<IntBitSet> result = this.converter.fromRGB(
                    rgb,
                    List.of(5, 5, 5)
            );

            assertTrue(result.get(0).value() >= 0 && result.get(0).value() <= 31);
            assertTrue(result.get(1).value() >= 0 && result.get(1).value() <= 31);
            assertTrue(result.get(2).value() >= 0 && result.get(2).value() <= 31);
        }
    }

    @Test
    void shouldQuantizeBoundaryValuesCorrectlyForRgb565() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> result = this.converter.fromRGB(
                rgb,
                List.of(5, 6, 5)
        );

        assertEquals(0, result.get(0).value());
        assertEquals(63, result.get(1).value());
        assertEquals(31, result.get(2).value());
    }

    @Test
    void shouldDequantizeRgb565MaximumValuesCorrectly() {
        final List<IntBitSet> packed = List.of(
                new IntBitSet(31, 5),
                new IntBitSet(63, 6),
                new IntBitSet(31, 5)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(255, result.get(0).value());
        assertEquals(255, result.get(1).value());
        assertEquals(255, result.get(2).value());
    }

    @Test
    void shouldDequantizeRgb565MinimumValuesCorrectly() {
        final List<IntBitSet> packed = List.of(
                new IntBitSet(0, 5),
                new IntBitSet(0, 6),
                new IntBitSet(0, 5)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(0, result.get(0).value());
        assertEquals(0, result.get(1).value());
        assertEquals(0, result.get(2).value());
    }

    @Test
    void shouldRoundTripRgb444() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(0x12, 8),
                new IntBitSet(0x80, 8),
                new IntBitSet(0xF0, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(4, 4, 4)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(17, result.get(0).value());
        assertEquals(136, result.get(1).value());
        assertEquals(238, result.get(2).value());
    }

    @Test
    void shouldRoundTripRgb332() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(128, 8),
                new IntBitSet(64, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(3, 3, 2)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(255, result.get(0).value());
        assertEquals(146, result.get(1).value());
        assertEquals(85, result.get(2).value());
    }

    @Test
    void shouldReturnCorrectTargetBitSizeAfterDequantization() {
        final List<IntBitSet> packed = List.of(
                new IntBitSet(3, 2),
                new IntBitSet(7, 3),
                new IntBitSet(15, 4)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(8, result.get(0).size());
        assertEquals(8, result.get(1).size());
        assertEquals(8, result.get(2).size());
    }

    @Test
    void shouldHandleAsymmetricChannelSizes() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(32, 8),
                new IntBitSet(128, 8),
                new IntBitSet(224, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(2, 6, 4)
        );

        assertEquals(0, packed.get(0).value());
        assertEquals(32, packed.get(1).value());
        assertEquals(13, packed.get(2).value());

        assertEquals(2, packed.get(0).size());
        assertEquals(6, packed.get(1).size());
        assertEquals(4, packed.get(2).size());
    }

    @Test
    void shouldConvertOneBitChannels() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(128, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(1, 1, 1)
        );

        assertEquals(0, packed.get(0).value());
        assertEquals(1, packed.get(1).value());
        assertEquals(1, packed.get(2).value());

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(0, result.get(0).value());
        assertEquals(255, result.get(1).value());
        assertEquals(255, result.get(2).value());
    }

    @Test
    void shouldConvertChannelsWithDifferentBitSizes() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(128, 8),
                new IntBitSet(64, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(1, 4, 7)
        );

        assertEquals(1, packed.get(0).value());
        assertEquals(8, packed.get(1).value());
        assertEquals(32, packed.get(2).value());

        assertEquals(1, packed.get(0).size());
        assertEquals(4, packed.get(1).size());
        assertEquals(7, packed.get(2).size());
    }

    @Test
    void shouldConvertEightBitChannelsWithoutQuantizationError() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(1, 8),
                new IntBitSet(127, 8)
        );

        final List<IntBitSet> packed = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(0, result.get(0).value());
        assertEquals(1, result.get(1).value());
        assertEquals(127, result.get(2).value());
    }

    @Test
    void shouldDequantizeMiddleValuesUsingRounding() {
        final List<IntBitSet> packed = List.of(
                new IntBitSet(16, 5),
                new IntBitSet(32, 6),
                new IntBitSet(16, 5)
        );

        final List<IntBitSet> result = this.converter.toRGB(packed);

        assertEquals(132, result.get(0).value());
        assertEquals(130, result.get(1).value());
        assertEquals(132, result.get(2).value());
    }

    @Test
    void shouldQuantizeZeroToZeroForEveryBitSize() {
        for (int bits = 1; bits <= 8; bits++) {
            final List<IntBitSet> rgb = List.of(
                    new IntBitSet(0, 8),
                    new IntBitSet(0, 8),
                    new IntBitSet(0, 8)
            );

            final List<IntBitSet> result = this.converter.fromRGB(
                    rgb,
                    List.of(bits, bits, bits)
            );

            assertEquals(0, result.get(0).value());
            assertEquals(0, result.get(1).value());
            assertEquals(0, result.get(2).value());
        }
    }

    @Test
    void shouldQuantize255ToMaximumForEveryBitSize() {
        for (int bits = 1; bits <= 8; bits++) {
            final int max = (1 << bits) - 1;

            final List<IntBitSet> rgb = List.of(
                    new IntBitSet(255, 8),
                    new IntBitSet(255, 8),
                    new IntBitSet(255, 8)
            );

            final List<IntBitSet> result = this.converter.fromRGB(
                    rgb,
                    List.of(bits, bits, bits)
            );

            assertEquals(max, result.get(0).value());
            assertEquals(max, result.get(1).value());
            assertEquals(max, result.get(2).value());
        }
    }

    @Test
    void shouldDequantizeZeroToZeroForEveryBitSize() {
        for (int bits = 1; bits <= 8; bits++) {
            final List<IntBitSet> packed = List.of(
                    new IntBitSet(0, bits),
                    new IntBitSet(0, bits),
                    new IntBitSet(0, bits)
            );

            final List<IntBitSet> result = this.converter.toRGB(packed);

            assertEquals(0, result.get(0).value());
            assertEquals(0, result.get(1).value());
            assertEquals(0, result.get(2).value());
        }
    }

    @Test
    void shouldDequantizeMaximumTo255ForEveryBitSize() {
        for (int bits = 1; bits <= 8; bits++) {
            final int max = (1 << bits) - 1;

            final List<IntBitSet> packed = List.of(
                    new IntBitSet(max, bits),
                    new IntBitSet(max, bits),
                    new IntBitSet(max, bits)
            );

            final List<IntBitSet> result = this.converter.toRGB(packed);

            assertEquals(255, result.get(0).value());
            assertEquals(255, result.get(1).value());
            assertEquals(255, result.get(2).value());
        }
    }

    @Test
    void shouldNotChangeEightBitValues() {
        for (int value = 0; value <= 255; value++) {
            final List<IntBitSet> rgb = List.of(
                    new IntBitSet(value, 8),
                    new IntBitSet(value, 8),
                    new IntBitSet(value, 8)
            );

            final List<IntBitSet> result = this.converter.toRGB(
                    this.converter.fromRGB(rgb, List.of(8, 8, 8))
            );

            assertEquals(value, result.get(0).value());
            assertEquals(value, result.get(1).value());
            assertEquals(value, result.get(2).value());
        }
    }

    @Test
    void shouldAlwaysProduceEightBitRgbValues() {
        for (int bits = 1; bits <= 8; bits++) {
            final int max = (1 << bits) - 1;

            final List<IntBitSet> packed = List.of(
                    new IntBitSet(max / 2, bits),
                    new IntBitSet(max / 2, bits),
                    new IntBitSet(max / 2, bits)
            );

            final List<IntBitSet> result = this.converter.toRGB(packed);

            assertEquals(8, result.get(0).size());
            assertEquals(8, result.get(1).size());
            assertEquals(8, result.get(2).size());
        }
    }
}
