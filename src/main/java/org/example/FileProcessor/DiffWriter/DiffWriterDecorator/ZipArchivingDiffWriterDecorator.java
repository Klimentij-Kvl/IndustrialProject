package org.example.FileProcessor.DiffWriter.DiffWriterDecorator;

import org.example.FileProcessor.DiffWriter.DiffWriter;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipArchivingDiffWriterDecorator extends ArchivingDiffWriterDecorator {

    private String path;

    public ZipArchivingDiffWriterDecorator(DiffWriter dw){
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

            byte[] b;
            try(FileInputStream fis = new FileInputStream(path)){
                b = fis.readAllBytes();
            }

            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(pathZip))) {
                zos.putNextEntry(new ZipEntry(fileName));
                zos.write(b);
                zos.closeEntry();
            }

            super.close();
            this.path = pathZip;
            if(!pathZip.equals(path))
                file.delete();
        }
        else throw new FileNotFoundException();
    }

    @Override
    public String getPath(){
        return path;
    }
}
