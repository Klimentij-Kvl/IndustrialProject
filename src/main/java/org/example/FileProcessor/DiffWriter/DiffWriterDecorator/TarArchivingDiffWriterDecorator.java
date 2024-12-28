package org.example.FileProcessor.DiffWriter.DiffWriterDecorator;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.example.FileProcessor.DiffWriter.DiffWriter;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TarArchivingDiffWriterDecorator extends ArchivingDiffWriterDecorator{
    public TarArchivingDiffWriterDecorator(DiffWriter dw){
        super(dw);
    }

    @Override
    public void archive() throws IOException {
        String path = super.getPath();
        File file = new File(path);
        Pattern pattern = Pattern.compile("^(.+\\\\)(.+)(\\..+)$");
        Matcher matcher = pattern.matcher(path);
        if(matcher.matches()) {
            String pathTar = matcher.group(1) + matcher.group(2) + ".tar";
            String fileName = matcher.group(2) + matcher.group(3);

            try (TarArchiveOutputStream tos = new TarArchiveOutputStream(new FileOutputStream(pathTar));
                 FileInputStream fis = new FileInputStream(path);
            ) {
                tos.putArchiveEntry(new TarArchiveEntry(file, fileName));
                byte[] b =  fis.readAllBytes();
                tos.write(b);
                tos.closeArchiveEntry();
            }

            super.close();
            super.path = pathTar;
            file.delete();
        }
        else throw new FileNotFoundException();
    }
}
