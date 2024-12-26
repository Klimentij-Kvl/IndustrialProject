package org.example.FileProcessor.DiffWriter.DiffFileWriter;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class XmlDiffFileWriter extends SerializationAdapterDiffFileWriter {
    public XmlDiffFileWriter(File file) throws IOException {
        super(file, new XmlMapper());
    }

    public XmlDiffFileWriter(String fileName) throws IOException {
        super(fileName, new XmlMapper());
    }
}
