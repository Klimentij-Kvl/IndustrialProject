package org.example.FileProcessor.StreamChooser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class   ArchivingDecorator extends StreamChooserDecorator{
    private String archiveName;

    public ArchivingDecorator(String archiveName, StreamChooserOld chooser){
        super(chooser);
        this.archiveName = archiveName;
    }

    @Override
    public OutputStream output(OutputStream out){
        return createArchiveOutput(super.output(out));
    }

    @Override
    public InputStream input(InputStream in){
        return createArchiveInput(super.input(in));
    }

    private OutputStream createArchiveOutput(OutputStream out){
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(archiveName + ".zip"));
            ZipEntry entry = new ZipEntry(archiveName + ".txt");
            zos.putNextEntry(entry);
            return zos;
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

        return out;
    }

    private InputStream createArchiveInput(InputStream in){
        return in;
    }
}
