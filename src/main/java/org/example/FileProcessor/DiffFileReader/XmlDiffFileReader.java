package org.example.FileProcessor.DiffFileReader;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class XmlDiffFileReader extends SerializationDiffFileReader{
    public XmlDiffFileReader(InputStream is){
        super(is, new XmlMapper());
    }

    public XmlDiffFileReader(File file) throws IOException {
        super(file, new XmlMapper());
    }

    public XmlDiffFileReader(String fileName) throws IOException {
        super(fileName, new XmlMapper());
    }
}
