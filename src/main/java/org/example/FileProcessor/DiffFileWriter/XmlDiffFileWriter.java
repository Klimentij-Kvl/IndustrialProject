package org.example.FileProcessor.DiffFileWriter;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.OutputStream;

public class XmlDiffFileWriter extends SerializationDiffFileWriter{
   public XmlDiffFileWriter(OutputStream os){
        super(os, new XmlMapper());
    }
}
