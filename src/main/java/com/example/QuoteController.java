package com.example;

import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller()
public class QuoteController {

    private final QuoteRepository quoteRepository;
    private final Environment environment;

    public QuoteController(QuoteRepository quoteRepository, Environment environment) {
        this.quoteRepository = quoteRepository;
        this.environment = environment;
    }

    @GetMapping("/quotes")
    public String quotes()
    {
        return "quotes";
    }

    @GetMapping("/quotes/random")
    @ResponseBody
    public Quote random()
    {
        Quote result = quoteRepository.findRandomQuote();
        if(CloudPlatform.KUBERNETES.isActive(environment)) {
            result.setK8s(true);
        }
        return result;
    }
}
