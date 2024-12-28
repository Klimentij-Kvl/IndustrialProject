package org.example.FileProcessor.DiffReader.DiffReaderDecorator;

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.example.FileProcessor.DiffReader.DiffReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipInputStream;

public class TarDearchivingDiffReaderDecorator extends DearchivingDiffReaderDecorator{
    public TarDearchivingDiffReaderDecorator(DiffReader dr){
       super(dr);
    }

    @Override
    public void dearchive() throws IOException {
        String path = super.getPath();

        Pattern pattern = Pattern.compile("^(.+\\\\)(.+)(\\..+)$");
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            String pathTxt = matcher.group(1) + matcher.group(2) + ".txt";
            //String fileName = matcher.group(2) + matcher.group(3);

            try (TarArchiveInputStream tis = new TarArchiveInputStream(new FileInputStream(path));
                 FileOutputStream fos = new FileOutputStream(pathTxt)
            ) {
                tis.getNextEntry();
                for (int c = tis.read(); c != -1; c = tis.read()) {
                    fos.write(c);
                }
                fos.flush();
            }
            super.setPath(pathTxt);
            super.setPath(pathTxt);
        } else throw new FileNotFoundException();
    }
}
