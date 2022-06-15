package com.msr.util;

import io.micrometer.core.instrument.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ClasspathUtil {
    public static String readFileToString(String path, Class aClass) throws IOException {

        try (InputStream stream = aClass.getClassLoader()
                .getResourceAsStream(path)) {
            if (stream == null) {
                throw new IOException("Stream is null");
            }
            return IOUtils.toString(stream, Charset.defaultCharset());
        }
    }
}