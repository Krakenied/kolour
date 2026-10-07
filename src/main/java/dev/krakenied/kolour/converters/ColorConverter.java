package dev.krakenied.kolour.converters;

import dev.krakenied.kolour.object.IntBitSet;
import dev.krakenied.kolour.util.Constants;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@NullMarked
public abstract class ColorConverter<F, T> {

    @FunctionalInterface
    public interface DataFunction<D extends @Nullable Object> {

        D get(List<IntBitSet> bitSetList, List<Integer> bitSizeList, int index);
    }

    @FunctionalInterface
    public interface ConvertingFunction<D extends @Nullable Object> {

        IntBitSet apply(List<IntBitSet> bitSetList, List<Integer> bitSizeList, int index, final @Nullable D data);
    }

    protected final List<ConvertingFunction<F>> fromRGBFunctions;
    protected final List<ConvertingFunction<T>> toRGBFunctions;

    public ColorConverter() {
        this.fromRGBFunctions = new ArrayList<>();
        this.toRGBFunctions = new ArrayList<>(Constants.RGB_CHAN_COUNT);
    }

    public @Nullable F fromRGBData(List<IntBitSet> bitSetList, List<Integer> bitSizeList, int index) {
        return null;
    }

    public final List<IntBitSet> fromRGB(final List<IntBitSet> bitSetList, final List<Integer> bitSizeList) {
        return convert(bitSetList, bitSizeList, this::fromRGBData, this.fromRGBFunctions);
    }

    public @Nullable T toRGBData(List<IntBitSet> bitSetList, List<Integer> bitSizeList, int index) {
        return null;
    }

    public final List<IntBitSet> toRGB(final List<IntBitSet> bitSetList) {
        return convert(bitSetList, Constants.RGB_BIT_SIZE_LIST, this::toRGBData, this.toRGBFunctions);
    }

    private static <U extends @Nullable Object> List<IntBitSet> convert(final List<IntBitSet> bitSetList, final List<Integer> bitSizeList, final DataFunction<U> dataFunction, final List<ConvertingFunction<U>> functions) {
        final List<IntBitSet> ret = new ArrayList<>(functions.size());

        for (int i = 0; i < functions.size(); i++) {
            final ConvertingFunction<U> function = functions.get(i);
            final U data = dataFunction.get(bitSetList, bitSizeList, i);
            ret.add(function.apply(bitSetList, bitSizeList, i, data));
        }

        return ret;
    }
}
