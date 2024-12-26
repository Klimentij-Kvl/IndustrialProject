package org.example.FileProcessor.DiffReader.DiffFileReader;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class XmlDiffFileReader extends SerializationDiffFileReader{
    public XmlDiffFileReader(File file) throws IOException {
        super(file, new XmlMapper());
    }

    public XmlDiffFileReader(String fileName) throws IOException {
        super(fileName, new XmlMapper());
    }
}
