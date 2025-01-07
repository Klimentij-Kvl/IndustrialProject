package org.example.Web;

import org.example.DataProcessor.Calculator.Calculator;
import org.example.DataProcessor.Extracter.Extracter;
import org.example.DataProcessor.Replacer.Replacer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/calc")
public class DataController {

    private final DynamicDataProcessorService dynamicService;

    public DataController(DynamicDataProcessorService dynamicService) {
        this.dynamicService = dynamicService;
    }

    @PostMapping("/process")
    public List<String> process(@RequestBody ProcessRequestDto dto) throws ClassNotFoundException {
        List<String> rawList = dto.getRawList();
        String extracterName = dto.getExtracterName();
        String calculatorName = dto.getCalculatorName();
        String replacerName   = dto.getReplacerName();

        Class<? extends Extracter> extracterClass =
                (Class<? extends Extracter>) Class.forName(extracterName);
        Class<? extends Calculator> calculatorClass =
                (Class<? extends Calculator>) Class.forName(calculatorName);
        Class<? extends Replacer> replacerClass =
                (Class<? extends Replacer>) Class.forName(replacerName);

        return dynamicService.process(rawList, extracterClass, calculatorClass, replacerClass);
    }
}
