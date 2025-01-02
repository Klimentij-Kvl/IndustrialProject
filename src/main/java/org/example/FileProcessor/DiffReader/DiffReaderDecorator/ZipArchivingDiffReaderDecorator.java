package org.example.FileProcessor.DiffReader.DiffReaderDecorator;

import org.example.FileProcessor.DiffReader.DiffReader;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

public class ZipArchivingDiffReaderDecorator extends DearchivingDiffReaderDecorator {
    public ZipArchivingDiffReaderDecorator(DiffReader dr){
        super(dr);
    }

    @Override
    protected void dearchive() throws IOException {
        String path = super.getPath();

        Pattern pattern = Pattern.compile("^(.+\\\\)(.+)(\\..+)$");
        Matcher matcher = pattern.matcher(path);
        if(matcher.matches()) {
            String pathRet = matcher.group(1) + "new" + matcher.group(2) + "." + super.getNextType();

            try (ZipInputStream zis = new ZipInputStream(new FileInputStream(path));
                 FileOutputStream fos = new FileOutputStream(pathRet)
            ) {

                    zis.getNextEntry();
                    for (int c = zis.read(); c != -1; c = zis.read()) {
                        fos.write(c);
                    }
                    fos.flush();
                    zis.closeEntry();

            }
            super.setPath(pathRet);
        }
        else throw new FileNotFoundException();
    }

    @Override
    public String getType(){return "zip";}
}
