package dev.krakenied.kolour.test;

import dev.krakenied.kolour.converters.HSVConverter;
import dev.krakenied.kolour.object.IntBitSet;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@NullMarked
class HSVConverterTest {

    private final HSVConverter converter = new HSVConverter();

    @Test
    void shouldConvertBlackToHsv() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(0, 8),
                new IntBitSet(0, 8)
        );

        final List<IntBitSet> hsv = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        assertEquals(0, hsv.get(0).value());
        assertEquals(0, hsv.get(1).value());
        assertEquals(0, hsv.get(2).value());

        assertEquals(8, hsv.get(0).size());
        assertEquals(8, hsv.get(1).size());
        assertEquals(8, hsv.get(2).size());
    }

    @Test
    void shouldConvertWhiteToHsv() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> hsv = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        assertEquals(0, hsv.get(0).value());
        assertEquals(0, hsv.get(1).value());
        assertEquals(255, hsv.get(2).value());
    }

    @Test
    void shouldConvertRedToHsv() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(0, 8),
                new IntBitSet(0, 8)
        );

        final List<IntBitSet> hsv = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        assertEquals(0, hsv.get(0).value());
        assertEquals(255, hsv.get(1).value());
        assertEquals(255, hsv.get(2).value());
    }

    @Test
    void shouldConvertGreenToHsv() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(255, 8),
                new IntBitSet(0, 8)
        );

        final List<IntBitSet> hsv = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        // 120 / 360 * 255 = 85
        assertEquals(85, hsv.get(0).value());
        assertEquals(255, hsv.get(1).value());
        assertEquals(255, hsv.get(2).value());
    }

    @Test
    void shouldConvertBlueToHsv() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(0, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> hsv = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        // 240 / 360 * 255 = 170
        assertEquals(170, hsv.get(0).value());
        assertEquals(255, hsv.get(1).value());
        assertEquals(255, hsv.get(2).value());
    }

    @Test
    void shouldConvertYellowToHsv() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(255, 8),
                new IntBitSet(0, 8)
        );

        final List<IntBitSet> hsv = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        // 60 / 360 * 255 = 42.5 -> 42
        assertEquals(42, hsv.get(0).value());
        assertEquals(255, hsv.get(1).value());
        assertEquals(255, hsv.get(2).value());
    }

    @Test
    void shouldConvertCyanToHsv() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> hsv = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        // 180 / 360 * 255 = 127.5 -> 127
        assertEquals(127, hsv.get(0).value());
        assertEquals(255, hsv.get(1).value());
        assertEquals(255, hsv.get(2).value());
    }

    @Test
    void shouldConvertMagentaToHsv() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(0, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> hsv = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        // 300 / 360 * 255 = 212.5 -> 212
        assertEquals(212, hsv.get(0).value());
        assertEquals(255, hsv.get(1).value());
        assertEquals(255, hsv.get(2).value());
    }

    @Test
    void shouldConvertGrayToHsv() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(128, 8),
                new IntBitSet(128, 8),
                new IntBitSet(128, 8)
        );

        final List<IntBitSet> hsv = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        assertEquals(0, hsv.get(0).value());
        assertEquals(0, hsv.get(1).value());
        assertEquals(128, hsv.get(2).value());
    }

    @Test
    void shouldConvertDarkGrayToHsv() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(32, 8),
                new IntBitSet(32, 8),
                new IntBitSet(32, 8)
        );

        final List<IntBitSet> hsv = this.converter.fromRGB(
                rgb,
                List.of(8, 8, 8)
        );

        assertEquals(0, hsv.get(0).value());
        assertEquals(0, hsv.get(1).value());
        assertEquals(32, hsv.get(2).value());
    }

    @Test
    void shouldConvertRedBackToRgb() {
        final List<IntBitSet> hsv = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(hsv);

        assertEquals(255, rgb.get(0).value());
        assertEquals(0, rgb.get(1).value());
        assertEquals(0, rgb.get(2).value());
    }

    @Test
    void shouldConvertGreenBackToRgb() {
        final List<IntBitSet> hsv = List.of(
                new IntBitSet(85, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(hsv);

        assertEquals(0, rgb.get(0).value());
        assertEquals(255, rgb.get(1).value());
        assertEquals(0, rgb.get(2).value());
    }

    @Test
    void shouldConvertBlueBackToRgb() {
        final List<IntBitSet> hsv = List.of(
                new IntBitSet(170, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(hsv);

        assertEquals(0, rgb.get(0).value());
        assertEquals(0, rgb.get(1).value());
        assertEquals(255, rgb.get(2).value());
    }

    @Test
    void shouldConvertYellowBackToRgb() {
        final List<IntBitSet> hsv = List.of(
                new IntBitSet(42, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(hsv);

        assertTrue(Math.abs(rgb.get(0).value() - 255) <= 2);
        assertTrue(Math.abs(rgb.get(1).value() - 252) <= 2);
        assertTrue(rgb.get(2).value() <= 2);
    }

    @Test
    void shouldConvertCyanBackToRgb() {
        final List<IntBitSet> hsv = List.of(
                new IntBitSet(127, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(hsv);

        assertTrue(rgb.get(0).value() <= 2);
        assertTrue(Math.abs(rgb.get(1).value() - 255) <= 2);
        assertTrue(Math.abs(rgb.get(2).value() - 252) <= 2);
    }

    @Test
    void shouldConvertMagentaBackToRgb() {
        final List<IntBitSet> hsv = List.of(
                new IntBitSet(212, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(hsv);

        assertTrue(Math.abs(rgb.get(0).value() - 252) <= 2);
        assertTrue(rgb.get(1).value() <= 2);
        assertTrue(Math.abs(rgb.get(2).value() - 255) <= 2);
    }

    @Test
    void shouldConvertWhiteBackToRgb() {
        final List<IntBitSet> hsv = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(0, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(hsv);

        assertEquals(255, rgb.get(0).value());
        assertEquals(255, rgb.get(1).value());
        assertEquals(255, rgb.get(2).value());
    }

    @Test
    void shouldConvertBlackBackToRgb() {
        final List<IntBitSet> hsv = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(0, 8),
                new IntBitSet(0, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(hsv);

        assertEquals(0, rgb.get(0).value());
        assertEquals(0, rgb.get(1).value());
        assertEquals(0, rgb.get(2).value());
    }

    @Test
    void shouldConvertGrayBackToRgb() {
        final List<IntBitSet> hsv = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(0, 8),
                new IntBitSet(128, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(hsv);

        assertEquals(128, rgb.get(0).value());
        assertEquals(128, rgb.get(1).value());
        assertEquals(128, rgb.get(2).value());
    }

    @Test
    void shouldPreserveRgbValuesForEightBitPrimaryColors() {
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

        for (final List<IntBitSet> rgb : colors) {
            final List<IntBitSet> result = this.converter.toRGB(
                    this.converter.fromRGB(rgb, List.of(8, 8, 8))
            );

            assertEquals(rgb.get(0).value(), result.get(0).value());
            assertEquals(rgb.get(1).value(), result.get(1).value());
            assertEquals(rgb.get(2).value(), result.get(2).value());
        }
    }

    @Test
    void shouldPreserveBlackAndWhiteDuringRoundTrip() {
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
                )
        );

        for (final List<IntBitSet> rgb : colors) {
            final List<IntBitSet> result = this.converter.toRGB(
                    this.converter.fromRGB(rgb, List.of(8, 8, 8))
            );

            assertEquals(rgb.get(0).value(), result.get(0).value());
            assertEquals(rgb.get(1).value(), result.get(1).value());
            assertEquals(rgb.get(2).value(), result.get(2).value());
        }
    }

    @Test
    void shouldPreserveChannelSizesAfterHsvToRgbConversion() {
        final List<IntBitSet> hsv = List.of(
                new IntBitSet(85, 8),
                new IntBitSet(255, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(hsv);

        assertEquals(8, rgb.get(0).size());
        assertEquals(8, rgb.get(1).size());
        assertEquals(8, rgb.get(2).size());
    }

    @Test
    void shouldSupportDifferentHsvChannelSizes() {
        final List<IntBitSet> hsv = List.of(
                new IntBitSet(15, 4),
                new IntBitSet(31, 5),
                new IntBitSet(63, 6)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(hsv);

        assertEquals(8, rgb.get(0).size());
        assertEquals(8, rgb.get(1).size());
        assertEquals(8, rgb.get(2).size());
    }

    @Test
    void shouldReturnZeroSaturationForAllGrayValues() {
        for (int value = 0; value <= 255; value++) {
            final List<IntBitSet> rgb = List.of(
                    new IntBitSet(value, 8),
                    new IntBitSet(value, 8),
                    new IntBitSet(value, 8)
            );

            final List<IntBitSet> hsv = this.converter.fromRGB(
                    rgb,
                    List.of(8, 8, 8)
            );

            assertEquals(0, hsv.get(0).value());
            assertEquals(0, hsv.get(1).value());
            assertEquals(value, hsv.get(2).value());
        }
    }

    @Test
    void shouldAlwaysProduceValidHsvValues() {
        for (int r = 0; r <= 255; r += 17) {
            for (int g = 0; g <= 255; g += 17) {
                for (int b = 0; b <= 255; b += 17) {
                    final List<IntBitSet> rgb = List.of(
                            new IntBitSet(r, 8),
                            new IntBitSet(g, 8),
                            new IntBitSet(b, 8)
                    );

                    final List<IntBitSet> hsv = this.converter.fromRGB(
                            rgb,
                            List.of(8, 8, 8)
                    );

                    assertTrue(hsv.get(0).value() >= 0);
                    assertTrue(hsv.get(0).value() <= 255);

                    assertTrue(hsv.get(1).value() >= 0);
                    assertTrue(hsv.get(1).value() <= 255);

                    assertTrue(hsv.get(2).value() >= 0);
                    assertTrue(hsv.get(2).value() <= 255);
                }
            }
        }
    }

    @Test
    void shouldQuantizeHsvChannelsToRequestedSizes() {
        final List<IntBitSet> rgb = List.of(
                new IntBitSet(255, 8),
                new IntBitSet(0, 8),
                new IntBitSet(0, 8)
        );

        final List<IntBitSet> hsv = this.converter.fromRGB(
                rgb,
                List.of(4, 5, 6)
        );

        assertEquals(4, hsv.get(0).size());
        assertEquals(5, hsv.get(1).size());
        assertEquals(6, hsv.get(2).size());

        assertTrue(hsv.get(0).value() >= 0);
        assertTrue(hsv.get(0).value() < (1 << 4));

        assertTrue(hsv.get(1).value() >= 0);
        assertTrue(hsv.get(1).value() < (1 << 5));

        assertTrue(hsv.get(2).value() >= 0);
        assertTrue(hsv.get(2).value() < (1 << 6));
    }

    @Test
    void shouldConvertHsvZeroToBlack() {
        final List<IntBitSet> hsv = List.of(
                new IntBitSet(0, 8),
                new IntBitSet(255, 8),
                new IntBitSet(0, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(hsv);

        assertEquals(0, rgb.get(0).value());
        assertEquals(0, rgb.get(1).value());
        assertEquals(0, rgb.get(2).value());
    }

    @Test
    void shouldConvertZeroSaturationToGrayRegardlessOfHue() {
        final List<IntBitSet> hsv = List.of(
                new IntBitSet(127, 8),
                new IntBitSet(0, 8),
                new IntBitSet(128, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(hsv);

        assertEquals(128, rgb.get(0).value());
        assertEquals(128, rgb.get(1).value());
        assertEquals(128, rgb.get(2).value());
    }

    @Test
    void shouldConvertMaximumValueWithZeroSaturationToWhite() {
        final List<IntBitSet> hsv = List.of(
                new IntBitSet(200, 8),
                new IntBitSet(0, 8),
                new IntBitSet(255, 8)
        );

        final List<IntBitSet> rgb = this.converter.toRGB(hsv);

        assertEquals(255, rgb.get(0).value());
        assertEquals(255, rgb.get(1).value());
        assertEquals(255, rgb.get(2).value());
    }

    @Test
    void shouldHandleHueSectorBoundaries() {
        final List<Integer> hues = List.of(0, 42, 85, 127, 170, 212);

        for (final int hue : hues) {
            final List<IntBitSet> hsv = List.of(
                    new IntBitSet(hue, 8),
                    new IntBitSet(255, 8),
                    new IntBitSet(255, 8)
            );

            final List<IntBitSet> rgb = this.converter.toRGB(hsv);

            assertTrue(rgb.get(0).value() >= 0 && rgb.get(0).value() <= 255);
            assertTrue(rgb.get(1).value() >= 0 && rgb.get(1).value() <= 255);
            assertTrue(rgb.get(2).value() >= 0 && rgb.get(2).value() <= 255);
        }
    }

    @Test
    void shouldKeepHsvValuesWithinEightBitRangeForAllRgbValues() {
        for (int value = 0; value <= 255; value++) {
            final List<IntBitSet> rgb = List.of(
                    new IntBitSet(value, 8),
                    new IntBitSet(255 - value, 8),
                    new IntBitSet(value / 2, 8)
            );

            final List<IntBitSet> hsv = this.converter.fromRGB(
                    rgb,
                    List.of(8, 8, 8)
            );

            assertTrue(hsv.get(0).value() >= 0 && hsv.get(0).value() <= 255);
            assertTrue(hsv.get(1).value() >= 0 && hsv.get(1).value() <= 255);
            assertTrue(hsv.get(2).value() >= 0 && hsv.get(2).value() <= 255);
        }
    }
}
