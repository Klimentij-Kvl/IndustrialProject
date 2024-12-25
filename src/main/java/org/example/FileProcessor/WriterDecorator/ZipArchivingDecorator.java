package org.example.FileProcessor.WriterDecorator;

import org.example.FileProcessor.DiffWriter;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipArchivingDecorator extends ArchivingDecorator{
    public ZipArchivingDecorator(DiffWriter dw){
        super(dw);
    }

    @Override
    public void archive() throws IOException {
        String path = super.getPath();
        File file = new File(path);
        Pattern pattern = Pattern.compile("^(.+\\\\)(.+)(\\..+)$");
        Matcher matcher = pattern.matcher(path);
        if(matcher.matches()) {
            String pathZip = matcher.group(1) + matcher.group(2) + ".zip";
            String fileName = matcher.group(2) + matcher.group(3);

            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(pathZip));
                 FileInputStream fis = new FileInputStream(path)
            ) {
                zos.putNextEntry(new ZipEntry(fileName));
                byte[] b = fis.readAllBytes();
                zos.write(b);
                zos.closeEntry();
            }

            super.close();
            super.path = pathZip;
            file.delete();
        }
        else throw new FileNotFoundException();
    }
}
