package org.example.FileProcessor.StreamChooser;

import java.io.InputStream;
import java.io.OutputStream;

public class FileStreamChooser implements StreamChooser {
    @Override
    public OutputStream output(OutputStream out){
        return out;
    }

    @Override
    public InputStream input(InputStream in){
        return in;
    }
}
