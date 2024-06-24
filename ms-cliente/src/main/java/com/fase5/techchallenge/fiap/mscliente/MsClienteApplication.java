package com.fase5.techchallenge.fiap.mscliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsClienteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsClienteApplication.class, args);
    }

}