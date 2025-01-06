package org.example.FileProcessor.DiffWriter.DiffWriterDecorator;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.example.FileProcessor.DiffWriter.DiffWriter;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TarArchivingDiffWriterDecorator extends ArchivingDiffWriterDecorator{
    private String path;

    public TarArchivingDiffWriterDecorator(DiffWriter dw){
        super(dw);
    }

    @Override
    public void archive() throws IOException {
        String path = super.getPath();
        File oldFile = new File(path);
        Pattern pattern = Pattern.compile("^(.+\\\\)(.+)(\\..+)$");
        Matcher matcher = pattern.matcher(path);
        if(matcher.matches()) {
            String newPath = matcher.group(1) + "new" + matcher.group(2) + matcher.group(3);
            File file = new File(newPath);
            oldFile.renameTo(file);
            String pathTar = matcher.group(1) + matcher.group(2) + ".tar";
            String fileName = matcher.group(2) + matcher.group(3);

            byte[] b;
            try(FileInputStream fis = new FileInputStream(newPath)){
                b = fis.readAllBytes();
            }

            try (TarArchiveOutputStream tos = new TarArchiveOutputStream(new FileOutputStream(pathTar))) {
                tos.putArchiveEntry(new TarArchiveEntry(file, fileName));
                tos.write(b);
                tos.closeArchiveEntry();
            }

            super.close();
            this.path = pathTar;
            if(!newPath.equals(pathTar))
                file.delete();
        }
        else throw new FileNotFoundException();
    }

    @Override
    public String getPath(){
        return path;
    }
}
