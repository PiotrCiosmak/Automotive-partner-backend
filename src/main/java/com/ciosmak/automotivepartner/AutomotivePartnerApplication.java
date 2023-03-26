package com.ciosmak.automotivepartner;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
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
public class AutomotivePartnerApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(AutomotivePartnerApplication.class, args);
    }

}
