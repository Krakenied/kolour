package dev.krakenied.kolour.converters;

import org.jspecify.annotations.NullMarked;

@NullMarked
public final class RGBConverter extends ColorConverter {

    private static final int CHANNEL_COUNT = 3;

    public RGBConverter() {
        super(CHANNEL_COUNT);
    }

    @Override
    public int fromRGB(final int r, final int g, final int b, final int[] cbs) {
        final int rQ = quantize(r, cbs[0]);
        final int gQ = quantize(g, cbs[1]);
        final int bQ = quantize(b, cbs[2]);

        return (rQ << (cbs[1] + cbs[2])) | (gQ << cbs[2]) | bQ;
    }

    @Override
    public int toRGB(final int value, final int[] cbs) {
        final int rBits = cbs[0];
        final int gBits = cbs[1];
        final int bBits = cbs[2];

        final int rMask = (1 << rBits) - 1;
        final int gMask = (1 << gBits) - 1;
        final int bMask = (1 << bBits) - 1;

        final int rQ = (value >>> (gBits + bBits)) & rMask;
        final int gQ = (value >>> bBits) & gMask;
        final int bQ = value & bMask;

        final int r = dequantize(rQ, rBits);
        final int g = dequantize(gQ, gBits);
        final int b = dequantize(bQ, bBits);

        return (r << 16) | (g << 8) | b;
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
