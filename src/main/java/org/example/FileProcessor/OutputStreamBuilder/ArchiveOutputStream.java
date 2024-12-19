package org.example.FileProcessor.OutputStreamBuilder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public interface ArchiveOutputStream {
    OutputStream makeOutputStream(OutputStream os, String file, String archName) throws IOException;
}
