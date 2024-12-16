package org.example.FileProcessor.StreamChooser;

import java.io.InputStream;
import java.io.OutputStream;

public interface StreamChooserOld {
    OutputStream output(OutputStream out);

    InputStream input(InputStream in);
}
