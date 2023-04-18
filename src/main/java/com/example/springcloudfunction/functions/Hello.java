package com.example.springcloudfunction.functions;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class Hello implements Function<String, String> {
    @Override
    public String apply(String s) {

        /*
        > curl localhost:8080/hello -H "Content-Type: text/plain" -d "Carlos"
Hello <Carlos> and welcome to Serverless World!!!%
         */
        return String.format("Hello <%s> and welcome to Serverless World!!!", s);
    }
}