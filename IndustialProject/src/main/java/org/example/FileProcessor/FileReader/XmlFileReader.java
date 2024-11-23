package org.example.FileProcessor.FileReader;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class XmlFileReader {

    public static List<String> read(String filePath) {
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
                    data.addAll(extractEquations(element.getTextContent()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static List<String> extractEquations(String text) {
        List<String> equations = new ArrayList<>();
        Pattern pattern = Pattern.compile("[\\d\\s+\\-*/÷()]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            equations.add(matcher.group().trim());
        }
        return equations;
    }
}
