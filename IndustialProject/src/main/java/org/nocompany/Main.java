package org.nocompany;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> kekw = new ArrayList<>();
        kekw.add("LOL KEK-");
        kekw.add("bozhecki koshecki");
        fileWriter out = new fileWriter();
        out.setFileNameAndFormat("out.json");
        out.write(kekw);
    }
}