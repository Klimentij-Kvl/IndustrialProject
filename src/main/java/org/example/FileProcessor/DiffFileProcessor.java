package org.example.FileProcessor;

import java.io.*;
import java.util.*;

public abstract class DiffFileProcessor {
    public abstract List<String> read() throws IOException;
    public abstract void write() throws IOException;
}
