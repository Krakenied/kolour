package dev.krakenied.kolour.converters;

import dev.krakenied.kolour.object.IntBitSet;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
public abstract class ColorConverter {

    private static final List<Integer> RGB888_BIT_SIZE_LIST = List.of(8, 8, 8);

    @FunctionalInterface
    public interface Function {

        IntBitSet apply(List<IntBitSet> bitSetList, List<Integer> bitSizeList, int index); // TODO: check if full bitSizeList is actually needed
    }

    protected final List<Function> fromRGBFunctions;
    protected final List<Function> toRGBFunctions;

    public ColorConverter() {
        this.fromRGBFunctions = new ArrayList<>();
        this.toRGBFunctions = new ArrayList<>();
    }

    public final List<IntBitSet> fromRGB(final List<IntBitSet> rgb, final List<Integer> bitSizeList) {
        final List<IntBitSet> ret = new ArrayList<>(this.fromRGBFunctions.size());

        for (int i = 0; i < this.fromRGBFunctions.size(); i++) {
            final Function function = this.fromRGBFunctions.get(i);
            ret.add(function.apply(rgb, bitSizeList, i));
        }

        return ret;
    }

    public final List<IntBitSet> toRGB(final List<IntBitSet> list) {
        final List<IntBitSet> ret = new ArrayList<>(this.toRGBFunctions.size());

        for (int i = 0; i < this.toRGBFunctions.size(); i++) {
            final Function function = this.toRGBFunctions.get(i);
            ret.add(function.apply(list, RGB888_BIT_SIZE_LIST, i));
        }

        return ret;
    }
}
