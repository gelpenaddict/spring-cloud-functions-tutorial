package com.example.springcloudfunction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class SpringCloudFunctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudFunctionApplication.class, args);
    }

    @Bean
    public Function<String, String> lowerCaseString() {
        /*
        > curl localhost:8080/lowerCaseString -H "Content-Type: text/plain" -d "Spring cloud FUNCTION"
        Output: spring cloud function%
         */
        return String::toLowerCase;
    }

    @Bean
    public Function<String, String> reverse() {
        /*
        > curl localhost:8080/reverse -H "Content-Type: text/plain" -d "Spring cloud FUNCTION"
        Output: NOITCNUF duolc gnirpS
         */
        return s -> {
            System.out.println("XXX SOUT in reverse(): " + s);
            return new StringBuilder(s).reverse().append("\n\n").toString();
        };
    }

}
