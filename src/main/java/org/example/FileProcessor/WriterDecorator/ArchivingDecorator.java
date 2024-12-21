package org.example.FileProcessor.WriterDecorator;

import org.example.FileProcessor.DiffWriter;

public class ArchivingDecorator extends WriterDecorator{
    public ArchivingDecorator(DiffWriter dw){
        super(dw);
    }
}
