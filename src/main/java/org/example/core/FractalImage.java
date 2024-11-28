package org.example.core;


public record FractalImage(Pixel[][] data, int width, int height) {

    public static FractalImage create(int width, int height) {
        Pixel[][] tempField = new Pixel[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tempField[y][x] = new Pixel(0, 0, 0, 0);
            }
        }

        return new FractalImage(tempField, width, height);
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }

    public Pixel getPixel(int x, int y) {
        return this.data[y][x];
    }

    public void setPixel(int x, int y, Pixel pixel) {
        if (!contains(x, y)) {
            throw new IllegalArgumentException("Coordinates out of bounds");
        }
        this.data[y][x] = pixel;
    }

}
