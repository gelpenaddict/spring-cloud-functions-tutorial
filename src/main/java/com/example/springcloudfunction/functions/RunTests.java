package com.example.springcloudfunction.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.function.Function;

@Component
public class RunTests implements Function<String, String> {

    Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    // TODO: debug why this doesn't work
    /*
curl localhost:8080/runtests -H "Content-Type: text/plain" -d "Spring cloud FUNCTION"
{"timestamp":"2023-04-19T00:30:00.778+00:00","status":404,"error":"Not Found","path":"/runtests"}%

The bean does get registered (conflict if defining a function w/ same name in main).
     */

    @Override
    public String apply(String s) {
        log.info("inside RunTests lambda");
        return "returning from the RunTest function";
    }
}
