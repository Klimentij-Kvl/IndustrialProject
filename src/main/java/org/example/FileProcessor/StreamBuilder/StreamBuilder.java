package org.example.FileProcessor.StreamBuilder;

public interface StreamBuilder {
    void BuildArchiving(String archiveType);
    void BuildEncrypting(String secretKeyString);
    void BuildStream();
    
}
