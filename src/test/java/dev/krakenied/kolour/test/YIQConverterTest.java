package dev.krakenied.kolour.test;

import dev.krakenied.kolour.converters.YIQConverter;
import dev.krakenied.kolour.object.IntBitSet;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@NullMarked
class YIQConverterTest {

    private final YIQConverter converter = new YIQConverter();

    @Test
    void shouldConvertBlackToYiq() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(0, 8),
                new IntBitSet(0, 8)
        );

        final List<IntBitSet> yiq = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        assertEquals(0, yiq.get(0).value());
        assertEquals(128, yiq.get(1).value(), 1);
        assertEquals(128, yiq.get(2).value(), 1);
    }

    @Test
    void shouldConvertWhiteToYiq() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> yiq = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        assertEquals(255, yiq.get(0).value());
        assertEquals(128, yiq.get(1).value(), 1);
        assertEquals(128, yiq.get(2).value(), 1);
    }

    @Test
    void shouldConvertNeutralGrayToYiq() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(128, 8),
                new IntBitSet(128, 8),
                new IntBitSet(128, 8)
        );

        final List<IntBitSet> yiq = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        assertEquals(128, yiq.get(0).value());
        assertEquals(128, yiq.get(1).value(), 1);
        assertEquals(128, yiq.get(2).value(), 1);
    }

    @Test
    void shouldConvertRedToYiq() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(0, 8),
                new IntBitSet(0, 8)
        );

        final List<IntBitSet> yiq = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        assertEquals(77, yiq.get(0).value());
        assertEquals(255, yiq.get(1).value());
        assertEquals(179, yiq.get(2).value());
    }

    @Test
    void shouldConvertGreenToYiq() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(255, 8),
                new IntBitSet(0, 8)
        );

        final List<IntBitSet> yiq = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        assertEquals(150, yiq.get(0).value());
        assertEquals(68, yiq.get(1).value());
        assertEquals(0, yiq.get(2).value());
    }

    @Test
    void shouldConvertBlueToYiq() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(0, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> yiq = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        assertEquals(28, yiq.get(0).value());
        assertEquals(59, yiq.get(1).value());
        assertEquals(203, yiq.get(2).value());
    }

    @Test
    void shouldPreserveRequestedYiqBitSizes() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> yiq = this.converter.fromRGB(
                rgb,
                List.of(5, 6, 7)
        );

        assertEquals(5, yiq.get(0).size());
        assertEquals(6, yiq.get(1).size());
        assertEquals(7, yiq.get(2).size());
    }

    @Test
    void shouldUseIndependentBitSizesForYiqChannels() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(0, 8),
                new IntBitSet(0, 8)
        );

        final List<IntBitSet> yiq = this.converter.fromRGB(
                rgb,
                List.of(4, 5, 6)
        );

        assertEquals(4, yiq.get(0).size());
        assertEquals(5, yiq.get(1).size());
        assertEquals(6, yiq.get(2).size());

        assertEquals(5, yiq.get(0).value());
        assertEquals(31, yiq.get(1).value());
        assertEquals(44, yiq.get(2).value());
    }

    @Test
    void shouldAlwaysReturnEightBitRgbValues() {
        final List<IntBitSet> yiq = List.of(
                new IntBitSet(128, 8),
                new IntBitSet(128, 8),
                new IntBitSet(128, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(yiq);

        assertEquals(8, rgb.get(0).size());
        assertEquals(8, rgb.get(1).size());
        assertEquals(8, rgb.get(2).size());
    }

    @Test
    void shouldConvertBlackYiqToBlack() {
        final List<IntBitSet> yiq = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(128, 8),
                new IntBitSet(128, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(yiq);

        assertEquals(0, rgb.get(0).value(), 1);
        assertEquals(0, rgb.get(1).value(), 1);
        assertEquals(0, rgb.get(2).value(), 1);
    }

    @Test
    void shouldConvertNeutralGrayYiqToGray() {
        final List<IntBitSet> yiq = List.of(
                new IntBitSet(128, 8),
                new IntBitSet(128, 8),
                new IntBitSet(128, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(yiq);

        assertEquals(128, rgb.get(0).value(), 1);
        assertEquals(128, rgb.get(1).value(), 1);
        assertEquals(128, rgb.get(2).value(), 1);
    }

    @Test
    void shouldConvertYellowToYiq() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(255, 8),
                new IntBitSet(0, 8)
        );

        final List<IntBitSet> yiq = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        assertEquals(227, yiq.get(0).value());
        assertEquals(196, yiq.get(1).value());
        assertEquals(52, yiq.get(2).value());
    }

    @Test
    void shouldConvertCyanToYiq() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> yiq = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        assertEquals(178, yiq.get(0).value(), 1);
        assertEquals(0, yiq.get(1).value());
        assertEquals(76, yiq.get(2).value());
    }

    @Test
    void shouldConvertMagentaToYiq() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(0, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> yiq = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        assertEquals(105, yiq.get(0).value());
        assertEquals(187, yiq.get(1).value());
        assertEquals(255, yiq.get(2).value());
    }

    @Test
    void shouldConvertWhiteYiqToWhite() {
        final List<IntBitSet> yiq = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(128, 8),
                new IntBitSet(128, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(yiq);

        assertEquals(255, rgb.get(0).value(), 1);
        assertEquals(255, rgb.get(1).value(), 1);
        assertEquals(255, rgb.get(2).value(), 1);
    }

    @Test
    void shouldKeepChrominanceCenteredAroundMiddleOfEightBitRange() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(128, 8),
                new IntBitSet(128, 8),
                new IntBitSet(128, 8)
        );

        final List<IntBitSet> yiq = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        assertEquals(128, yiq.get(0).value());
        assertEquals(128, yiq.get(1).value(), 1);
        assertEquals(128, yiq.get(2).value(), 1);
    }

    @Test
    void shouldRoundTripPrimaryColorsWithinOneValue() {
        final List<List<IntBitSet>> colors = List.of(
                List.of(
                        new IntBitSet(255, 8),
                        new IntBitSet(0, 8),
                        new IntBitSet(0, 8)
                ),
                List.of(
                        new IntBitSet(0, 8),
                        new IntBitSet(255, 8),
                        new IntBitSet(0, 8)
                ),
                List.of(
                        new IntBitSet(0, 8),
                        new IntBitSet(0, 8),
                        new IntBitSet(255, 8)
                )
        );

        for (final List<IntBitSet> original : colors) {
            final List<IntBitSet> yiq = this.converter.fromRGB(
                    original,
                    List.of(8, 8, 8)
            );

            final List<IntBitSet> rgb = this.converter.toRGB(yiq);

            assertEquals(original.get(0).value(), rgb.get(0).value(), 1);
            assertEquals(original.get(1).value(), rgb.get(1).value(), 1);
            assertEquals(original.get(2).value(), rgb.get(2).value(), 2);
        }
    }

    @Test
    void shouldRoundTripSecondaryColorsWithinOneValue() {
        final List<List<IntBitSet>> colors = List.of(
                List.of(
                        new IntBitSet(255, 8),
                        new IntBitSet(255, 8),
                        new IntBitSet(0, 8)
                ),
                List.of(
                        new IntBitSet(0, 8),
                        new IntBitSet(255, 8),
                        new IntBitSet(255, 8)
                ),
                List.of(
                        new IntBitSet(255, 8),
                        new IntBitSet(0, 8),
                        new IntBitSet(255, 8)
                )
        );

        for (final List<IntBitSet> original : colors) {
            final List<IntBitSet> yiq = this.converter.fromRGB(
                    original,
                    List.of(8, 8, 8)
            );

            final List<IntBitSet> rgb = this.converter.toRGB(yiq);

            assertEquals(original.get(0).value(), rgb.get(0).value(), 1);
            assertEquals(original.get(1).value(), rgb.get(1).value(), 1);
            assertEquals(original.get(2).value(), rgb.get(2).value(), 2);
        }
    }

    @Test
    void shouldSupportAsymmetricYiqBitSizes() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(128, 8),
                new IntBitSet(64, 8)
        );

        final List<IntBitSet> yiq = this.converter.fromRGB(
                rgb,
                List.of(4, 5, 6)
        );

        assertEquals(4, yiq.get(0).size());
        assertEquals(5, yiq.get(1).size());
        assertEquals(6, yiq.get(2).size());

        assertEquals(9, yiq.get(0).value());
        assertEquals(25, yiq.get(1).value());
        assertEquals(33, yiq.get(2).value());
    }

    @Test
    void shouldAlwaysProduceValidEightBitRgbValues() {
        final List<IntBitSet> yiq = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(yiq);

        assertEquals(8, rgb.get(0).size());
        assertEquals(8, rgb.get(1).size());
        assertEquals(8, rgb.get(2).size());

        for (final IntBitSet channel : rgb) {
            assertTrue(channel.value() >= 0);
            assertTrue(channel.value() <= 255);
        }
    }

    @Test
    void shouldClampOutOfRangeYiqToValidRgbValues() {
        final List<IntBitSet> yiq = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(0, 8),
                new IntBitSet(0, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(yiq);

        for (final IntBitSet channel : rgb) {
            assertTrue(channel.value() >= 0);
            assertTrue(channel.value() <= 255);
        }
    }

    @Test
    void shouldPreserveEightBitOutputRegardlessOfInputBitSizes() {
        final List<IntBitSet> yiq = List.of(
                new IntBitSet(15, 4),
                new IntBitSet(31, 5),
                new IntBitSet(63, 6)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(yiq);

        assertEquals(8, rgb.get(0).size());
        assertEquals(8, rgb.get(1).size());
        assertEquals(8, rgb.get(2).size());
    }

    @Test
    void shouldRoundTripBlackWhiteAndGray() {
        final List<List<IntBitSet>> colors = List.of(
                List.of(
                        new IntBitSet(0, 8),
                        new IntBitSet(0, 8),
                        new IntBitSet(0, 8)
                ),
                List.of(
                        new IntBitSet(255, 8),
                        new IntBitSet(255, 8),
                        new IntBitSet(255, 8)
                ),
                List.of(
                        new IntBitSet(128, 8),
                        new IntBitSet(128, 8),
                        new IntBitSet(128, 8)
                )
        );

        for (final List<IntBitSet> original : colors) {
            final List<IntBitSet> yiq = this.converter.fromRGB(
                    original,
                    List.of(8, 8, 8)
            );

            final List<IntBitSet> rgb = this.converter.toRGB(yiq);

            assertEquals(original.get(0).value(), rgb.get(0).value(), 1);
            assertEquals(original.get(1).value(), rgb.get(1).value(), 1);
            assertEquals(original.get(2).value(), rgb.get(2).value(), 1);
        }
    }
}
