package dev.krakenied.kolour.normalizers;

public interface Normalizer {

    int normalize(double value, int targetMin, int targetMax);

    double denormalize(int value, double sourceMin, double sourceMax);
}
