package main.java.org.example.UIProcessor;

public interface UIProcessor {
    void start();
    void stop();
    void addOption(String name, Runnable runnable);
    void removeOption(String name);
}
