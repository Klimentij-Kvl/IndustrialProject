package org.example.FileProcessor.DiffReader.ReaderDecorator;

import org.example.FileProcessor.DiffReader.DiffReader;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

public class ZipReaderDecorator extends DearchivingReaderDecorator{
    public ZipReaderDecorator(DiffReader dr){
        super(dr);
    }

    @Override
    protected void dearchive() throws IOException {
        String path = super.getPath();

        Pattern pattern = Pattern.compile("^(.+\\\\)(.+)(\\..+)$");
        Matcher matcher = pattern.matcher(path);
        if(matcher.matches()) {
            String pathTxt = matcher.group(1) + matcher.group(2) + ".txt";
            //String fileName = matcher.group(2) + matcher.group(3);

            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(path));
                 FileOutputStream fos = new FileOutputStream(pathTxt)
            ) {

                    zis.getNextEntry();
                    for (int c = zis.read(); c != -1; c = zis.read()) {
                        fos.write(c);
                    }
                    fos.flush();
                    zis.closeEntry();

            }
            super.setPath(pathTxt);
            super.setPath(pathTxt);
        }
        else throw new FileNotFoundException();
    }
}
