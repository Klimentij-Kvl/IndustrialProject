package org.example.FileProcessor.DiffFileReader;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.InputStream;

public class XmlDiffFileReader extends SerializationDiffFileReader{
    public XmlDiffFileReader(InputStream is){
        super(is, new XmlMapper());
    }
}
