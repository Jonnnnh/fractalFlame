package org.example.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageFormat {
    JPEG("jpeg"),
    BMP("bmp"),
    PNG("png");

    private final String stringFormat;
}
