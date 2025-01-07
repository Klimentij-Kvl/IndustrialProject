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
        // Spring автоматически передаст ApplicationContext в конструктор,
        // если вы пометили класс DynamicDataProcessorService аннотацией @Service
        // и добавили этот конструктор.
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
        // 1. Получаем бин-объект Extracter по типу
        //    (Нужно, чтобы в контексте Spring существовал хотя бы один бин данного класса).
        Extracter chosenExtracter = context.getBean(extracterClass);

        // 2. Получаем бин-объект Calculator
        Calculator chosenCalculator = context.getBean(calculatorClass);

        // 3. Получаем бин-объект Replacer
        Replacer chosenReplacer = context.getBean(replacerClass);

        // 4. Собираем DataProcessorFactory "на лету"
        DataProcessorFactory factory = new DataProcessorFactory(
                chosenExtracter,
                chosenReplacer,
                chosenCalculator
        );

        // 5. Вызываем process(...)
        return factory.process(rawList);
    }
}
