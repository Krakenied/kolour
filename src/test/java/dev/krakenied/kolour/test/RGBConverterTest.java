package dev.krakenied.kolour.test;

import dev.krakenied.kolour.converters.RGBConverter;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@NullMarked
class RGBConverterTest {

    private final RGBConverter converter = new RGBConverter();

    @Test
    void shouldConvertRgb565() {
        final int[] bits = {5, 6, 5};

        final int packed = this.converter.fromRGB(255, 128, 64, bits);
        final int rgb = this.converter.toRGB(packed, bits);

        assertEquals(0xFF8242, rgb);
    }

    @Test
    void shouldConvertRgb888WithoutLoss() {
        final int[] bits = {8, 8, 8};

        final int original = 0x123456;

        final int packed = this.converter.fromRGB(
                (original >>> 16) & 0xFF,
                (original >>> 8) & 0xFF,
                original & 0xFF,
                bits
        );

        final int rgb = this.converter.toRGB(packed, bits);

        assertEquals(original, rgb);
    }

    @Test
    void shouldConvertBlack() {
        final int[] bits = {5, 6, 5};

        final int packed = this.converter.fromRGB(0, 0, 0, bits);
        final int rgb = this.converter.toRGB(packed, bits);

        assertEquals(0x000000, rgb);
    }

    @Test
    void shouldConvertWhite() {
        final int[] bits = {5, 6, 5};

        final int packed = this.converter.fromRGB(255, 255, 255, bits);
        final int rgb = this.converter.toRGB(packed, bits);

        assertEquals(0xFFFFFF, rgb);
    }

    @Test
    void shouldPackRgb565Correctly() {
        final int[] bits = {5, 6, 5};

        final int packed = this.converter.fromRGB(255, 255, 255, bits);

        assertEquals(0xFFFF, packed);
    }

    @Test
    void shouldPackBlackCorrectly() {
        final int[] bits = {5, 6, 5};

        final int packed = this.converter.fromRGB(0, 0, 0, bits);

        assertEquals(0x0000, packed);
    }

    @Test
    void shouldHandleDifferentChannelBitSizes() {
        final int[] bits = {3, 5, 7};

        final int packed = this.converter.fromRGB(255, 255, 255, bits);

        assertEquals((1 << 15) - 1, packed);

        final int rgb = this.converter.toRGB(packed, bits);

        assertEquals(0xFFFFFF, rgb);
    }
}
