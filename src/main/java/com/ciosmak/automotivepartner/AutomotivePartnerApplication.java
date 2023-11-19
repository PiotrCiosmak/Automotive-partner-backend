package com.ciosmak.automotivepartner;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
        info = @Info(
                title = "Automotive Partner",
                version = "0.0.1",
                contact = @Contact(
                        name = "Piotr Ciosmak",
                        email = "piotrciosmak2001@gmail.com"
                )
        )
)
@EnableScheduling
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AutomotivePartnerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(AutomotivePartnerApplication.class, args);
    }

}
