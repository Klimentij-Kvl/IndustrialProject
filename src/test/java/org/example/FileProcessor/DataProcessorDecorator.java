package org.example.FileProcessor;

import java.util.List;

public abstract class DataProcessorDecorator implements DataProcessor{
    private DataProcessor nextDecorator;
    DataProcessorDecorator(DataProcessor nextDecorator){
        this.nextDecorator = nextDecorator;
    }

    @Override
    public void writeData(List<String> data){
        nextDecorator.writeData(data);
    }

    @Override
    public List<String> readData(){
        return nextDecorator.readData();
    }
}
