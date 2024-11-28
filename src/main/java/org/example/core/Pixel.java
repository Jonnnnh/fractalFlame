package org.example.core;

public record Pixel(int red, int green, int blue, int hitCount) {

    public Pixel hit() {
        return new Pixel(red, green, blue, hitCount + 1);
    }

    public Pixel setColor(int red, int green, int blue) {
        return new Pixel(red, green, blue, hitCount);
    }
}
