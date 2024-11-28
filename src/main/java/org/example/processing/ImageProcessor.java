package org.example.processing;

import org.example.core.FractalImage;


@FunctionalInterface
public interface ImageProcessor {
    void process(FractalImage image);
}
