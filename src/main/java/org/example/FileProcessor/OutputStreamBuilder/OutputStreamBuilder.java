package org.example.FileProcessor.OutputStreamBuilder;

import javax.crypto.CipherOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamBuilder {
    private final String path;
    private OutputStream output;

    public OutputStreamBuilder(File file) throws IOException {
        path = file.getName();
        output = new FileOutputStream(file);
    }

    public OutputStreamBuilder(String fileName) throws IOException {
        path = fileName;
        output = new FileOutputStream(fileName);
    }

    public void BuildEncryption(String key){
        FileEncrypterDecrypter fed = new FileEncrypterDecrypter(key);
        output = new CipherOutputStream(output, fed.encCipher());
    }

    public void BuildArchive(String archName) throws IOException{
        ArchiveOutputStream aos = new ZipArchiveOutputStream();
        output = aos.makeOutputStream(output, path, archName);
    }

    public OutputStream getResult(){
        return output;
    }
}
