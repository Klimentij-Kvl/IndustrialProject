package org.example.FileProcessor.OutputStreamBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipArchiveOutputStream implements ArchiveOutputStream{
    @Override
    public OutputStream makeOutputStream(OutputStream os, String file, String archName) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(archName + ".zip"));
        ZipEntry entry = new ZipEntry(file);
        zos.putNextEntry(entry);

        return zos;
    }
}
