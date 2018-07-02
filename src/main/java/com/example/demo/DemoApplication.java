package com.example.demo;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

@Data
@Builder
class Steak {
    String title;
}

@Slf4j
@CrossOrigin
@RestController
class SteakController {

    @PostMapping("/steaks")
    public ResponseEntity<String> create(@RequestBody Steak steak) {
        log.info("Steak {}", steak);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

@Slf4j
@Component
class SteakConverter implements Converter<String, Steak> {

    @Override
    public Steak convert(String value) {
        log.info("converter value {}", value);

        return Steak.builder().title("Hello!").build();
    }
}

@Slf4j
@Configuration
class WebConfig implements WebMvcConfigurer {

    private final SteakConverter steakConverter;

    public WebConfig(SteakConverter steakConverter) {
        this.steakConverter = steakConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.info("Converter registered");
        registry.addConverter(steakConverter);
    }

}