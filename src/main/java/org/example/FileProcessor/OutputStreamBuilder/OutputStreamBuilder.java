package org.example.FileProcessor.OutputStreamBuilder;

import javax.crypto.CipherOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamBuilder {
    private final File path;
    private OutputStream output;

    public OutputStreamBuilder(File file) throws IOException {
        path = file;
        output = new FileOutputStream(file);
    }

    public OutputStreamBuilder(String fileName) throws IOException {
        path = new File(fileName);
        output = new FileOutputStream(fileName);
    }

    public void BuildEncryption(String key){
        FileEncrypterDecrypter fed = new FileEncrypterDecrypter(key);
        output = new CipherOutputStream(output, fed.encCipher());
    }

    public void BuildArchive(){

    }

    public OutputStream getResult(){
        return output;
    }
}
