package org.example.FileProcessor.DiffFileWriter;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class XmlDiffFileWriter extends SerializationDiffFileWriter{
    public XmlDiffFileWriter(OutputStream os){
        super(os, new XmlMapper());
    }

    public XmlDiffFileWriter(File file) throws IOException {
        super(file, new XmlMapper());
    }

    public XmlDiffFileWriter(String fileName) throws IOException {
        super(fileName, new XmlMapper());
    }
}
