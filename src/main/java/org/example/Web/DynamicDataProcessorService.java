package org.example.Web;

import org.example.DataProcessor.Calculator.Calculator;
import org.example.DataProcessor.Extracter.Extracter;
import org.example.DataProcessor.Replacer.Replacer;
import org.example.DataProcessorFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicDataProcessorService {

    private final ApplicationContext context;

    public DynamicDataProcessorService(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Вызываем DataProcessorFactory.process() с реализациями,
     * которые находим по типу класса (не по строковым именам).
     */
    public List<String> process(
            List<String> rawList,
            Class<? extends Extracter> extracterClass,
            Class<? extends Calculator> calculatorClass,
            Class<? extends Replacer> replacerClass
    ) {
        Extracter chosenExtracter = context.getBean(extracterClass);

        Calculator chosenCalculator = context.getBean(calculatorClass);

        Replacer chosenReplacer = context.getBean(replacerClass);

        DataProcessorFactory factory = new DataProcessorFactory(
                chosenExtracter,
                chosenReplacer,
                chosenCalculator
        );

        return factory.process(rawList);
    }
}
