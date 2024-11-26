package org.example.FileProcessor.DiffFileReader;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class XmlFileReader implements DiffFileReader {

    public List<String> read(String filePath) {
        List<String> data = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(filePath));
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("operation");
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    data.add(element.getTextContent().trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
