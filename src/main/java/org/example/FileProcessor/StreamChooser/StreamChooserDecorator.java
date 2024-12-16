package org.example.FileProcessor.StreamChooser;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class StreamChooserDecorator implements StreamChooserOld {
    final private StreamChooserOld next;

    StreamChooserDecorator(StreamChooserOld next){
        this.next = next;
    }

    @Override
    public OutputStream output(OutputStream out){
        return next.output(out);
    }

    @Override
    public InputStream input(InputStream in){
        return next.input(in);
    }
}
