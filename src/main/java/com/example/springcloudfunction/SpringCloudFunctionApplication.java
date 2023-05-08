package com.example.springcloudfunction;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.lang.invoke.MethodHandles;
import java.util.function.Function;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.springcloudfunction.functions")
public class SpringCloudFunctionApplication {

    Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudFunctionApplication.class, args);
    }

    /*
    JUnit cannot programmatically call tests in the src/test/ dir, only in src/main
     */
    @Bean
    public Function<String, String> runTestBean() {
        log.info("inside RunTests lambda");

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
            .request()
            .selectors(selectPackage("com.example.springcloudfunction"),
                selectClass(SpringCloudFunctionApplication.class))
            .filters(includeClassNamePatterns(".*Test")).build();
        Launcher launcher = LauncherFactory.create();
        TestPlan plan = launcher.discover(request);

        TestExecutionListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        return s -> "returning from the runTestBean function";
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
